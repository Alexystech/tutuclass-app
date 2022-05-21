package mx.edu.itsx.tutuclass.dashboard.student.configuration

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.edu.itsx.tutuclass.databinding.ActivityStudentProfileBinding

class StudentProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentProfileBinding
    private lateinit var auth: FirebaseAuth

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        val db = Firebase.firestore

        db.collection("students").document(uid).get().addOnSuccessListener {
            binding.textViewProfileEmail.text = (it.get("email") as String?)
            binding.textViewProfileFullName.text = (it.get("name") as String? + " " +
                    it.get("fLastName") as String? + " " +
                    it.get("lastName") as String?)
            binding.textViewProfileAddress.text = (it.get("address") as String?)
            binding.textViewProfileCP.text = (it.get("cp") as String?)
        }

        binding.updateButton.setOnClickListener {
            val intentUpdateStudentProfile = Intent(this, UpdateStudentProfileActivity::class.java)
            startActivity(intentUpdateStudentProfile)
        }

    }
}