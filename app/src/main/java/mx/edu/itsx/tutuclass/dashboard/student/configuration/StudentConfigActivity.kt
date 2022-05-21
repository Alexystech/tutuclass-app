package mx.edu.itsx.tutuclass.dashboard.student.configuration

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import mx.edu.itsx.tutuclass.databinding.ActivityStudentConfigBinding
import mx.edu.itsx.tutuclass.login.StudentActivity

class StudentConfigActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentConfigBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        val currentUser = auth.currentUser
        val uid = currentUser!!.uid
        val db = Firebase.firestore

        binding.textViewProfile.setOnClickListener {
            val intentStudentProfile = Intent(this, StudentProfileActivity::class.java)
            startActivity(intentStudentProfile)
        }

        binding.textViewDelete.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Alerta!")
                .setMessage("¿Estas seguro de eliminar tu perfil?")
                .setCancelable(true)
                .setPositiveButton("Si", DialogInterface.OnClickListener { dialog, id ->
                    auth.currentUser?.delete()
                    db.collection("students").document(uid).delete()
                    Toast.makeText(this, "Tu perfil ha sido eliminado", Toast.LENGTH_SHORT).show()
                    val intentStudent = Intent(this, StudentActivity::class.java)
                    startActivity(intentStudent)
                }).setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->

                }).show()
        }
    }
}