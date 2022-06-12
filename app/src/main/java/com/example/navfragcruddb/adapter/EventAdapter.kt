package com.example.navfragcruddb.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.navfragcruddb.databinding.TodoItemBinding
import com.example.navfragcruddb.model.Event
import com.google.firebase.firestore.FirebaseFirestore

private val eventsList: MutableList<Event> = items.list


class EventAdapter(

    ) : RecyclerView.Adapter<EventViewHolder>() {

    fun updateEventsList(event: Event) { // UNO
        eventsList.add(event)
        readFireStoreData()
        notifyItemInserted(eventsList.indexOf(event))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder =
        EventViewHolder(
            TodoItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                parent,
                false
            )

        )

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) { //TRES
        holder.bind(eventsList[position])
    }

    fun readFireStoreData(){
        val db = FirebaseFirestore.getInstance()
        db.collection("events")
            .get()
            .addOnCompleteListener {

                val result: StringBuffer = StringBuffer()

                eventsList.clear()
                if(it.isSuccessful){
                    for(document in it.result!!){

                        val name = document.data.getValue("name").toString()
                        val category = document.data.getValue("category").toString()
                        val date = document.data.getValue("date").toString()

                        eventsList.add(Event(name,category,date))
                    }
                }
            }
    }

    override fun getItemCount(): Int {
        readFireStoreData()
        return eventsList.size
    }

}

class EventViewHolder(
    private val binding: TodoItemBinding

) : RecyclerView.ViewHolder(binding.root) {

    fun bind(event: Event) { //DOS

        binding.eventName.text = event.name
        binding.eventCategory.text = event.category
        binding.eventDate.text = event.date
    }


}









