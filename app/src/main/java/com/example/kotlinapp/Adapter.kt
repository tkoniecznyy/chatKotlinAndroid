package com.example.kotlinapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinapp.SwipeToDeleteCallback
import java.util.*

class Adapter(var content: ArrayList<MessageAPI>) :
    RecyclerView.Adapter<MessageViewHolder>() {

    lateinit var  mContacts: MutableList<MessageAPI>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {

        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_items, parent, false)
        val context = parent.context
        val inflater = LayoutInflater.from(context)


        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val Item = content[position]
        holder.name.text = Item.login
        holder.txt.text = Item.content
        holder.date.text = Item.date
    }

    override fun getItemCount(): Int {
        return content.size

    }

   /* fun removeAt(position: Int) {
        mContacts.removeAt(position)
        notifyItemRemoved(position) }
*/

    fun idOfCurrent(position: Int): String? {
        return content[content.count()-position-1].id
    }
}



    class MessageViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var name: TextView
        var txt: TextView
        var date: TextView

        init {
            name = itemView.findViewById(R.id.nickname)
            txt = itemView.findViewById(R.id.content)
            date = itemView.findViewById(R.id.date)
        }
    }




