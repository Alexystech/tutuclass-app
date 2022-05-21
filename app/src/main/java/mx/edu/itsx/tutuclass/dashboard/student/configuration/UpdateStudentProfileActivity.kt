package mx.edu.itsx.tutuclass.dashboard.student.configuration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.edu.itsx.tutuclass.R
import mx.edu.itsx.tutuclass.databinding.ActivityUpdateStudentProfileBinding

class UpdateStudentProfileActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityUpdateStudentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateStudentProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        val db = Firebase.firestore
        var email: String = ""

        db.collection("students").document(uid).get().addOnSuccessListener {
            binding.textInputName.setText(it.get("name") as String?)
            binding.textInputFLastName.setText(it.get("fLastName") as String?)
            binding.textInputLastName.setText(it.get("lastName") as String?)
            email = (it.get("email") as String)
            binding.textInputAddress.setText(it.get("address") as String?)
            binding.textInputCP.setText(it.get("cp") as String?)
        }

        binding.updateProfileDataButton.setOnClickListener {
            var name = binding.textInputName.text.toString()
            var fLastName = binding.textInputFLastName.text.toString()
            var lastName = binding.textInputLastName.text.toString()
            var address = binding.textInputAddress.text.toString()
            var cp = binding.textInputCP.text.toString()

            val map = hashMapOf<String, String>(
                "name" to name,
                "fLastName" to fLastName,
                "lastName" to lastName,
                "email" to email,
                "address" to address,
                "cp" to cp
            )

            db.collection("students").document(uid).update(map as Map<String, Any>)
                .addOnSuccessListener {
                    val intentStudentProfile = Intent(this, StudentProfileActivity::class.java)
                    startActivity(intentStudentProfile)
                    Toast.makeText(this, "Estudiante actualizado", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "An error has occurred", Toast.LENGTH_SHORT).show()
                }
        }
    }
}