package mx.edu.itsx.tutuclass.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mx.edu.itsx.tutuclass.dashboard.StudentDashboardActivity
import mx.edu.itsx.tutuclass.databinding.ActivityStudentBinding
import mx.edu.itsx.tutuclass.register.RegisterStudentActivity

class StudentActivity : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var binding: ActivityStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.goToRegisterLayoutButton.setOnClickListener {
            val intentRegister = Intent(this, RegisterStudentActivity::class.java)
            startActivity(intentRegister)
        }

        auth = Firebase.auth

        binding.loginStudentButton.setOnClickListener {
            if ( binding.inputTextLoginEmail.text.toString().isNotEmpty() &&
                    binding.inputTextLoginPassword.text.toString().isNotEmpty()) {
                signIn(
                    binding.inputTextLoginEmail.text.toString(),
                    binding.inputTextLoginPassword.text.toString()
                )
            } else {
                Toast.makeText(this, "Llene todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        checkUserAlreadyLoggedIn(currentUser)
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Sesion iniciada", Toast.LENGTH_SHORT).show()
                    val studentDashboardIntent = Intent(this,
                        StudentDashboardActivity::class.java)
                    startActivity(studentDashboardIntent)
                } else {
                    Toast.makeText(baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun checkUserAlreadyLoggedIn(currentUser: FirebaseUser?) {
        if ( currentUser != null ) {

        } else {

        }
    }
}