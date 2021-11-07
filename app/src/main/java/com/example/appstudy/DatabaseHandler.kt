package com.example.appstudy

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.appstudy.data.StudyNote

class DatabaseHandler(context : Context): SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION)
{

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "StudyApp"
    }
    var sqlDb: SQLiteDatabase = writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE Study (_id integer primary key autoincrement, Title text, details text)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS Study")
        onCreate(db)
    }
    fun addNoteStudy(title: String,  details: String): Long{

        val contentValues = ContentValues()
        contentValues.put("Title", title)
        contentValues.put("details",details)

        val success = sqlDb.insert("Study", null, contentValues)

        sqlDb.close()
        return success
    }
   @SuppressLint("Range")
   fun getStudyNotes(): ArrayList<StudyNote>{
        val notes: ArrayList<StudyNote> = ArrayList()
        val tableName = "Study"
        val selectQuery = "SELECT * FROM $tableName"

        var cursor: Cursor? = null

        try{
            cursor = sqlDb.rawQuery(selectQuery, null)
        }catch(e: SQLiteException){
            sqlDb.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var title: String
        var details: String

        if(cursor.moveToFirst()){
            do{

                id = cursor.getInt(cursor.getColumnIndex("_id"))
                title = cursor.getString(cursor.getColumnIndex("Title"))
                details = cursor.getString(cursor.getColumnIndex("details"))

                val singleNote = StudyNote(id, title, details )
                notes.add(singleNote)
            }while(cursor.moveToNext())
        }

        return notes
    }
    fun updateNote(note: StudyNote): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put("Title", note.title)
        contentValues.put("details", note.details)

        val success = db.update("Study", contentValues, "_id = ${note.id}", null)

        db.close()
        return success
    }
    fun deleteNote(note: StudyNote): Int{

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("_id", note.id)
        val success = db.delete("Study", "_id = ${note.id}", null)
        db.close()
        return success
//        success > 0 means it worked
    }

}