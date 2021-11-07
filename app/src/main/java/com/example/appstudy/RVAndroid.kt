package com.example.appstudy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appstudy.data.StudyNote
import com.example.appstudy.data.StudyNoteAndroid
import com.example.appstudy.databinding.ItemRowBinding


class RVAndroid(private val activity: AndroidActivity, private val items: ArrayList<StudyNoteAndroid>):
    RecyclerView.Adapter<RVAndroid.ItemViewHolder>() {
    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val notes = items[position]
        holder.binding.apply {
            tvTitle.text = notes.title
            tvDetails.text = notes.details

            ibEditNote.setOnClickListener {
                activity.raiseDialog(notes.id)
            }
            ibDeleteNote.setOnClickListener {
                activity.deleteNote(notes.id)
            }

        }
    }

    override fun getItemCount() = items.size
}