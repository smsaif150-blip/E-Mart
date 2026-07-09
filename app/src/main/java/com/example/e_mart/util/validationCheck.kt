package com.example.e_mart.util

import android.util.Patterns
import org.intellij.lang.annotations.Pattern

fun validationEmailCheck(email: String): RegisterValidation
{
    if (email.isEmpty())
    {
      return  RegisterValidation.Failure("Email cannot be empty")
    }
    if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
       return RegisterValidation.Failure("Wrong Email Formate")

    return RegisterValidation.Success
}

fun validationPasswordCheck(password: String): RegisterValidation{
    if (password.isEmpty())
        return RegisterValidation.Failure("Password cannot be empty")

    if (password.length<6)
        return RegisterValidation.Failure("Password should be 6 character")

    return RegisterValidation.Success
}