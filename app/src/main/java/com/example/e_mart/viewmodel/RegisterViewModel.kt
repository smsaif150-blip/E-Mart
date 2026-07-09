package com.example.e_mart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_mart.data.User
import com.example.e_mart.util.Collections.USER_COLLECTIONS
import com.example.e_mart.util.RegisterStateCheck
import com.example.e_mart.util.RegisterValidation
import com.example.e_mart.util.Resource
import com.example.e_mart.util.validationEmailCheck
import com.example.e_mart.util.validationPasswordCheck
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val db: FirebaseFirestore
): ViewModel(){

    private val _register = MutableStateFlow<Resource<User>>(Resource.Test())
    val register: Flow<Resource<User>> = _register
    private val _validation = Channel<RegisterStateCheck>()
    val validation = _validation.receiveAsFlow()
    fun createAccountWithEmailAndPassword(user: User,password: String)
    {
       if (CheckValidation(user.email,password)){
           _register.value = Resource.Loading()
           firebaseAuth.createUserWithEmailAndPassword(user.email,password)
               .addOnSuccessListener {authResult ->
                   authResult.user?.let {firebaseUser ->
                       saveUsers(firebaseUser.uid,user)
                   }
               }.addOnFailureListener {
                   _register.value = Resource.Error(it.message.toString())
               }
       }
        else
       {
           viewModelScope.launch {
               val registerStateCheck = RegisterStateCheck(
                   validationEmailCheck(user.email),validationPasswordCheck(password)
               )
               _validation.send(registerStateCheck)
           }
       }
    }


    fun CheckValidation(email: String, password: String): Boolean
    {
        val validEmail = validationEmailCheck(email)
        val validPassword = validationPasswordCheck(password)
        val shouldRegister = validEmail is RegisterValidation.Success && validPassword is RegisterValidation.Success
        return shouldRegister
    }

    private fun saveUsers(uid: String, user: User) {
        db.collection(USER_COLLECTIONS)
            .document(uid)
            .set(user)
            .addOnSuccessListener {
                _register.value = Resource.Success(user)
            }.addOnFailureListener {
                _register.value = Resource.Error(it.message.toString())
            }
    }
}

