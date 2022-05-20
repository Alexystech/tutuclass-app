package mx.edu.itsx.tutuclass

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import mx.edu.itsx.tutuclass.login.StudentActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun goStudentLogin(view: View) {
        val intent = Intent(this, StudentActivity::class.java)
        startActivity(intent)
    }
}
