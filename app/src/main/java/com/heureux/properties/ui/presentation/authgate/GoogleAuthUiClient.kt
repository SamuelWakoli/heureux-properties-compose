package com.heureux.properties.ui.presentation.authgate

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.heureux.properties.R
import com.heureux.properties.data.types.UserProfileData
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

data class GoogleSignInResult(
    val data: UserProfileData?,
    val errorMessage: String?,
)

class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
) {
    private val auth = Firebase.auth

    private fun buildSignInRequest(): BeginSignInRequest =
        BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setFilterByAuthorizedAccounts(false).setServerClientId(
                    context.getString(
                        R.string.web_client_id
                    )
                ).build()
        ).setAutoSelectEnabled(true).build()

    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(buildSignInRequest()).await()
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            null
        }

        return result?.pendingIntent?.intentSender
    }

    suspend fun signInByIntent(intent: Intent): GoogleSignInResult {
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIDToken = credential.googleIdToken
        val googleCredentials =
            GoogleAuthProvider.getCredential(googleIDToken, null)

        return try {
            val user = auth.signInWithCredential(googleCredentials).await().user
            GoogleSignInResult(
                data = user?.run {
                    UserProfileData(
                        userID = uid,
                        displayName = displayName,
                        photoURL = photoUrl,
                        userEmail = email
                    )
                },
                errorMessage = null
            )

        } catch (e: Exception) {
            if (e is CancellationException) throw e
            GoogleSignInResult(
                data = null,
                errorMessage = e.message
            )
        }
    }
}