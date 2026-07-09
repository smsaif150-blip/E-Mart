package com.example.e_mart.util

sealed class RegisterValidation {
    object Success: RegisterValidation()
    data class Failure(val message: String): RegisterValidation()
}

data class RegisterStateCheck(
    val Email: RegisterValidation,
    val Password: RegisterValidation
)