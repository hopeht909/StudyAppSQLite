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


class KotlinActivity : AppCompatActivity() {

    lateinit var rvKotlin : RecyclerView
    private lateinit var db: DatabaseHandler
    private lateinit var rvAdapter: RVAdapter
    lateinit var etTitle : EditText
    lateinit var etDetails : EditText
    lateinit var btAdd : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)

        btAdd = findViewById(R.id.btAdd)
        etTitle = findViewById(R.id.etTitle)
        etDetails = findViewById(R.id.etDetails)
        rvKotlin = findViewById(R.id.rvKotlin)
        db = DatabaseHandler(this)


        btAdd.setOnClickListener {
            addStudyNote()
        }
        updateRV()
        title = "Kotlin Review"
    }

    private fun addStudyNote() {
       val status = db.addNoteStudy(etTitle.text.toString(), etDetails.text.toString())
        etTitle.text.clear()
        etTitle.clearFocus()
        etDetails.text.clear()
        etDetails.clearFocus()
        Toast.makeText(this, "Note Added $status", Toast.LENGTH_LONG).show()
        updateRV()
    }

    private fun updateRV(){
        rvKotlin.adapter = RVAdapter(this, getItemsList())
        rvKotlin.layoutManager = LinearLayoutManager(this)

    }
    private fun getItemsList(): ArrayList<StudyNote>{
        return db.getStudyNotes()
    }
    fun deleteNote(noteID: Int){
        db.deleteNote(StudyNote(noteID, "",""))
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
                    _, _ -> db.updateNote(StudyNote(id, updatedtitle.text.toString(),updatedDetail.text.toString()))
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