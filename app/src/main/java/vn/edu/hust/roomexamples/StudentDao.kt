package vn.edu.hust.roomexamples

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface StudentDao {
  @Query("select * from students")
  fun getAllStudents(): Array<Student>

  @Query("select * from students where mssv = :mssv")
  fun getStudentsByMssv(mssv: String): Array<Student>

  @Query("select * from students where hoten like '%' || :name || '%'")
  fun getStudentsByName(name: String): Array<Student>

  @Insert
  fun insertStudent(student: Student): Long

  @Update
  fun updateStudent(student: Student): Int

  @Delete
  fun deleteStudent(student: Student): Int

  @Query("delete from students where mssv = :mssv")
  fun deleteByMssv(mssv: String): Int
}