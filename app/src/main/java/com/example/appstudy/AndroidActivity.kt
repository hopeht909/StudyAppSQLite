package com.example.appstudy

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appstudy.data.StudyNote
import com.example.appstudy.data.StudyNoteAndroid


class AndroidActivity : AppCompatActivity() {

    lateinit var rvAndroid : RecyclerView
    private lateinit var db: DatabaseHandlerAndroid
    lateinit var etTitleA : EditText
    lateinit var etDetailsA : EditText
    lateinit var btAddA : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_android)

        btAddA = findViewById(R.id.btAddA)
        etTitleA = findViewById(R.id.etTitleA)
        etDetailsA = findViewById(R.id.etDetailsA)
        rvAndroid = findViewById(R.id.rvAndroid)
        db = DatabaseHandlerAndroid(this)


        btAddA.setOnClickListener {
            addStudyNote()
        }
        updateRV()
        title = "Android Review"
    }

    private fun addStudyNote() {
        val status = db.addNoteStudy(etTitleA.text.toString(), etDetailsA.text.toString())
        etTitleA.text.clear()
        etTitleA.clearFocus()
        etDetailsA.text.clear()
        etDetailsA.clearFocus()
        Toast.makeText(this, "Note Added $status", Toast.LENGTH_LONG).show()
        updateRV()
    }

    private fun updateRV(){
        rvAndroid.adapter = RVAndroid(this, getItemsList())
        rvAndroid.layoutManager = LinearLayoutManager(this)

    }
    private fun getItemsList(): ArrayList<StudyNoteAndroid>{
        return db.getStudyNotes()
    }
    fun deleteNote(noteID: Int){
        db.deleteNote(StudyNoteAndroid(noteID, "",""))
        updateRV()
    }

    fun raiseDialog(id: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val mLayout = LinearLayout(this)
        val updatedtitle = EditText(this)
        updatedtitle.hint = "Enter the new title"
        val updatedDetail = EditText(this)
        updatedDetail.hint = "Enter the new Details"

        mLayout.orientation = LinearLayout.VERTICAL
        mLayout.addView(updatedtitle)
        mLayout.addView(updatedDetail)
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    _, _ -> db.updateNote(StudyNoteAndroid(id, updatedtitle.text.toString(),updatedDetail.text.toString()))
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Study Note")
        alert.setView(mLayout)
        alert.show()
    }

}