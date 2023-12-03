package com.example.draganddrop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.draganddrop.databinding.ActivityMainBinding
import java.util.Collections

class MainActivity : AppCompatActivity() {

    //binding
    private lateinit var binding :ActivityMainBinding
    //others
    private val personsData = arrayListOf<PersonData>(
        PersonData("Hadi", 23),
        PersonData("ali"),
        PersonData("Hamed", 21),
        PersonData("Hossein", 33),
        PersonData("Hassan"),
    )
    private val personAdapter = PersonsAdapter()
    private val itemTouchHelper : ItemTouchHelper by lazy {
        val simple = object :ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT, 0){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                recyclerView.adapter = personAdapter
                Collections.swap(personsData, from, to)
                (recyclerView.adapter as PersonsAdapter).notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                TODO("Not yet implemented")
            }

        }
        ItemTouchHelper(simple)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView( binding.root )

        personAdapter.differ.submitList(personsData)

        binding.apply {
            personRec.apply {
                adapter = personAdapter
                layoutManager = GridLayoutManager(this@MainActivity, 2)
            }
            itemTouchHelper.attachToRecyclerView(personRec)
        }

    }
}