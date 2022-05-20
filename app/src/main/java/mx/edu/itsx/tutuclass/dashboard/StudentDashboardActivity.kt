package mx.edu.itsx.tutuclass.dashboard

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import mx.edu.itsx.tutuclass.R
import mx.edu.itsx.tutuclass.dashboard.student.FirstFragment
import mx.edu.itsx.tutuclass.dashboard.student.SecondFragment
import mx.edu.itsx.tutuclass.dashboard.student.ThirdFragment
import mx.edu.itsx.tutuclass.databinding.ActivityStudentDashboardBinding
import mx.edu.itsx.tutuclass.login.StudentActivity

class StudentDashboardActivity : AppCompatActivity() {

    var firstFragment: FirstFragment = FirstFragment()
    var secondFragment: SecondFragment = SecondFragment()
    var thirdFragment: ThirdFragment = ThirdFragment()

    private lateinit var binding: ActivityStudentDashboardBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var navigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener)

    }

    private var navigationItemSelectedListener: BottomNavigationView.OnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId) {
            R.id.firstFragment -> {
                loadFragment(firstFragment)
                true
            }
            R.id.secondFragment -> {
                loadFragment(secondFragment)
                true
            }
            R.id.thirdFragment -> {
                loadFragment(thirdFragment)
                true
            }
            else -> false
        }
    }

    fun loadFragment(fragment: Fragment) {
        var transaction : FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.commit()
    }

    override fun onStart() {
        super.onStart()
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if ( currentUser != null ) {
            reload()
        } else {

        }
    }

    private fun reload() {}

}