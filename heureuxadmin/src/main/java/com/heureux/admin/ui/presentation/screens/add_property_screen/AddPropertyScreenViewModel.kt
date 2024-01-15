package com.heureux.admin.ui.presentation.screens.add_property_screen

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.heureux.admin.data.FirebaseDirectories
import com.heureux.admin.data.repositories.ProfileRepository
import com.heureux.admin.data.types.HeureuxProperty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddPropertyScreenViewModel(
    private val profileRepository: ProfileRepository,
//    private val propertiesRepository: PropertiesRepository,
) : ViewModel() {

    private var _uiState = MutableStateFlow(AddPropertyUiState())
    val uiState = _uiState.asStateFlow()


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val userProfileData =
        profileRepository.getUserProfileData(onSuccess = {}, onFailure = {}).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = null
        )

    fun loadUserPhoneNumber() {
        _uiState.value = _uiState.value.copy(userPhoneNumber = userProfileData.value?.phone ?: "")
    }

    fun loadCurrentProperty(property: HeureuxProperty) {
        _uiState.value = _uiState.value.copy(
            propertyName = property.name,
            propertyDescription = property.description,
            propertyPrice = property.price,
            propertyLocation = property.location,
            propertiesImagesCount = property.imageUrls.size,
            propertyImages = property.imageUrls as MutableList,
        )
    }

    fun onPropertyNameChanged(name: String) {
        _uiState.value = _uiState.value.copy(propertyName = name, propertyNameError = false)
    }

    fun onPropertyLocationChanged(location: String) {
        _uiState.value =
            _uiState.value.copy(propertyLocation = location, propertyLocationError = false)
    }


    fun onPropertyDescriptionChanged(description: String) {
        _uiState.value =
            _uiState.value.copy(propertyDescription = description, propertyDescriptionError = false)
    }

    fun omPropertyPriceChanged(price: String) {
        _uiState.value = _uiState.value.copy(propertyPrice = price, propertyPriceError = false)
    }

    fun onPhoneNumberChanged(phone: String) {
        _uiState.value = _uiState.value.copy(userPhoneNumber = phone, userPhoneNumberError = false)
    }

    fun updatePhotoURLAndCount(photoURL: String) {
        val newPhotoUrlList = uiState.value.propertyImages
        newPhotoUrlList.add(photoURL)
        _uiState.update {
            it.copy(
                propertyImages = newPhotoUrlList,
                propertiesImagesCount = it.propertiesImagesCount + 1
            )
        }
    }

    fun uploadPropertyImage(
        uri: Uri,
        onSuccess: (downloadUrl: String) -> Unit,
        onFailure: (exception: Exception) -> Unit,
    ) = viewModelScope.launch {
        profileRepository.uploadImageGetUrl(
            uri = uri,
            directory = "${FirebaseDirectories.AdminsStorageReference.name}/${userProfileData.value?.userEmail ?: Firebase.auth.currentUser?.email!!}/property listings images/${uiState.value.propertyName.ifEmpty { "No name" }}/image ${uiState.value.propertiesImagesCount}.png",
            onSuccessListener = onSuccess,
            onFailure = onFailure,
        )
    }

    fun clearImageSelection() {
        _uiState.update { it.copy(propertiesImagesCount = 0, propertyImages = mutableListOf()) }
    }


}