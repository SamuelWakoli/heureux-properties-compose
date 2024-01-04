package com.heureux.properties

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.heureux.properties.data.repositories.UserPreferencesRepository
import com.heureux.properties.ui.presentation.authgate.GoogleAuthUiClient
import com.heureux.properties.ui.presentation.navigation.NavGraph
import com.heureux.properties.ui.AppViewModelProvider
import com.heureux.properties.ui.presentation.authgate.AuthViewModel
import com.heureux.properties.ui.presentation.main.bottom_bar_destinations.MainScreenViewModel
import com.heureux.properties.ui.theme.HeureuxPropertiesTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    val userDataStore = UserPreferencesRepository(dataStore = user_dataStore)


    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    private lateinit var authViewModel: AuthViewModel
    private lateinit var mainScreenViewModel: MainScreenViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val currentUser = Firebase.auth.currentUser
            authViewModel = viewModel(factory = AppViewModelProvider.Factory)
            mainScreenViewModel = viewModel(factory = AppViewModelProvider.Factory)


            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = {
                    if (it.resultCode == RESULT_OK) {
                        lifecycleScope.launch {
                            val signInResult = googleAuthUiClient.signInByIntent(
                                intent = it.data ?: return@launch
                            )
                            authViewModel.onSignInResult(signInResult)
                        }
                    }
                }
            )

            val themeData = userDataStore.getThemeData.collectAsState(initial = "Light").value
            val dynamicColor = userDataStore.getDynamicColor.collectAsState(initial = false).value
            HeureuxPropertiesTheme(
                darkTheme = when (themeData) {
                    "Light" -> false
                    "Dark" -> true
                    else -> isSystemInDarkTheme()
                },
                dynamicColor = dynamicColor,
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainNavController = rememberNavController()
                    NavGraph(
                        navController = mainNavController,
                        currentUserSignedIn = currentUser != null,
                        authViewModel = authViewModel,
                        mainScreenViewModel = mainScreenViewModel,
                    ) {
                        // now Sign in with Google
                        lifecycleScope.launch {
                            val signInIntentSender = googleAuthUiClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    }
                }
            }
        }
    }
}