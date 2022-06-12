package com.example.navfragcruddb.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.navfragcruddb.R
import com.example.navfragcruddb.databinding.ActivityMainBinding
import com.example.navfragcruddb.databinding.FragmentEntryBinding
import com.example.navfragcruddb.model.Event
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*


class EntryFragment : Fragment() {

    private lateinit var binding_date: ActivityMainBinding

    private val binding by lazy {
        FragmentEntryBinding.inflate(layoutInflater)
    }

    private fun saveFireStore(category :String,date :String,name :String) { //INSERT FUNCTION
        val db = FirebaseFirestore.getInstance()
        val eventss:MutableMap<String, Any> = HashMap()
        eventss["category"] = category
        eventss["date"] = date
        eventss["name"] = name

        db.collection("events")
            .add(eventss)
            .addOnSuccessListener {
                //ADDED
            }
            .addOnFailureListener{
                //ERROR
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val SimpleF = SimpleDateFormat("MM/dd/yyyy")
        var date: String
        binding.calendarEvent.setOnDateChangeListener{view,year,month,dayOfMonth ->
            date = "${month+1}/$dayOfMonth/$year"
        }.let {
            date = SimpleF.format(binding.calendarEvent.date)
        }

        binding.eventNameEntry.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // no-op
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.saveEventBtn.isEnabled = p0?.isNotEmpty() ?: false
            }

            override fun afterTextChanged(p0: Editable?) {
                // no-op
            }

        })

        binding.saveEventBtn.setOnClickListener {
            val name = binding.eventNameEntry.text.toString()
            val category = binding.eventCategoryEntry.text.toString()
            //val date = binding.calendarEvent.date.toString()

            saveFireStore(category,date,name)//INSERT DB

            Event(name, category, date).also {
                findNavController().navigate(R.id.action_EntryFragment_to_MainFragment, bundleOf(
                    Pair(EVENT_DATA, it)
                ))
            }
        }

        return binding.root
    }


    companion object {
        const val EVENT_DATA = "EVENT_DATA"
    }
}