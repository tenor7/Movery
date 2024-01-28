package com.example.movery.presentation.viewmodel

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.movery.R
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText // текстовое поле для ввода электронной почты
    private lateinit var passwordEditText: EditText // текстовое поле для ввода пароля
    private lateinit var auth: FirebaseAuth // экземпляр класса FirebaseAuth для аутентификации пользователей

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        auth = FirebaseAuth.getInstance() // получаем экземпляр класса FirebaseAuth

        emailEditText = findViewById(R.id.editTextEmailAddress) // находим текстовое поле для ввода электронной почты в макете
        passwordEditText = findViewById(R.id.editTextPassword) // находим текстовое поле для ввода пароля в макете

    }
    // метод для регистрации нового пользователя
    fun register(view: View){
        val email = emailEditText.text.toString() // получаем введенный пользователем адрес электронной почты
        val password = passwordEditText.text.toString() // получаем введенный пользователем пароль

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if(task.isSuccessful){ // если регистрация прошла успешно
                val intent= Intent(this, AccountActivity::class.java) // создаем намерение для перехода на главный экран
                startActivity(intent) // запускаем активность главного экрана
                finish() // закрываем текущую активность (активность регистрации)
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(applicationContext, exception.localizedMessage, Toast.LENGTH_LONG).show() // выводим сообщение об ошибке в случае неудачной регистрации
        }
    }

    fun goToLogin(view: View){
        val intent= Intent(this, AuthorizationActivity::class.java)
        startActivity(intent)
    }
}
