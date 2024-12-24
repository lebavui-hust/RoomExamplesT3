package vn.edu.hust.roomexamples

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vn.edu.hust.roomexamples.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  lateinit var binding : ActivityMainBinding
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.main)

    val studentDao = StudentDatabase.getInstance(this).studentDao()

    binding.buttonInsert.setOnClickListener {
      val hoten = binding.editHoten.text.toString()
      val mssv = binding.editMssv.text.toString()
      lifecycleScope.launch(Dispatchers.IO) {
        val result = studentDao.insertStudent(Student(hoten = hoten, mssv = mssv))
        Log.v("TAG", "Result: $result")
        withContext(Dispatchers.Main) {
          Snackbar.make(binding.main, "Result: $result", Snackbar.LENGTH_LONG).show()
        }
      }
    }

    binding.buttonGetAll.setOnClickListener {
      lifecycleScope.launch(Dispatchers.IO) {
        val students = studentDao.getAllStudents()
        for (student in students)
          Log.v("TAG", student.toString())
      }
    }

    binding.buttonSearchMssv.setOnClickListener {
      val mssv = binding.editMssv.text.toString()
      lifecycleScope.launch(Dispatchers.IO) {
        val students = studentDao.getStudentsByMssv(mssv)
        for (student in students)
          Log.v("TAG", student.toString())
      }
    }

    binding.buttonSearchName.setOnClickListener {
      val name = binding.editHoten.text.toString()
      lifecycleScope.launch(Dispatchers.IO) {
        val students = studentDao.getStudentsByName(name)
        for (student in students)
          Log.v("TAG", student.toString())
      }
    }

    binding.buttonUpdate.setOnClickListener {
      val student = Student(binding.editId.text.toString().toInt(),
        binding.editHoten.text.toString(),
        binding.editMssv.text.toString())
      lifecycleScope.launch(Dispatchers.IO) {
        val result = studentDao.updateStudent(student)
        Log.v("TAG", "Result: $result")
      }
    }

    binding.buttonDelete.setOnClickListener {
//      val student = Student(binding.editId.text.toString().toInt(),
//        binding.editHoten.text.toString(),
//        binding.editMssv.text.toString())
      lifecycleScope.launch(Dispatchers.IO) {
        //val result = studentDao.deleteStudent(student)
        val result = studentDao.deleteByMssv(binding.editMssv.text.toString())
        Log.v("TAG", "Result: $result")
      }
    }
  }
}