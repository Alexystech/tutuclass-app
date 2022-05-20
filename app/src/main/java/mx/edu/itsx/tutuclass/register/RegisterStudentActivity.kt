package mx.edu.itsx.tutuclass.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.edu.itsx.tutuclass.R
import mx.edu.itsx.tutuclass.databinding.ActivityRegisterStudentBinding
import mx.edu.itsx.tutuclass.login.StudentActivity

class RegisterStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterStudentBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            val name = binding.inputTextName.text.toString()
            val fLastName = binding.inputTextFLastName.text.toString()
            val lastName = binding.inputTextLastName.text.toString()
            val email = binding.inputTextEmail.text.toString()
            val password = binding.inputTextPassword.text.toString()
            val address = binding.inputTextAddress.text.toString()
            val cp = binding.inputTextCP.text.toString()

            if ( name.isNotEmpty() && fLastName.isNotEmpty() && lastName.isNotEmpty() &&
                    email.isNotEmpty() && password.isNotEmpty() && address.isNotEmpty() &&
                    cp.isNotEmpty()) {
                registerUser(name, fLastName, lastName, email, password, address, cp)
            } else {
                Toast.makeText(this, "Llena todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        auth = Firebase.auth
    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if ( currentUser != null ) {
            reload()
        } else {

        }
    }

    private fun registerUser(
        name: String,
        fLastName: String,
        lastName: String,
        email: String,
        password: String,
        address: String,
        cp: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val uid = user!!.uid
                    val map = hashMapOf<String, String>(
                        "name" to name,
                        "fLastName" to fLastName,
                        "lastName" to lastName,
                        "email" to email,
                        "address" to address,
                        "cp" to cp
                    )

                    val db = Firebase.firestore

                    db.collection("students").document(uid).set(map).addOnSuccessListener {
                        val loginStudentIntent = Intent(this, StudentActivity::class.java)
                        startActivity(loginStudentIntent)
                        Toast.makeText(this, "Estudiante registrado", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(
                            this,
                            "An error has occurred",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT
                    )
                }
            }
    }

    private fun reload() {}
}