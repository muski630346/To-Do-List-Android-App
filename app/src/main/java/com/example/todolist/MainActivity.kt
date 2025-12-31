package com.example.todolist

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var text: EditText
    lateinit var add: Button
    lateinit var list: ListView
    var items = ArrayList<String>()
    var fileHelper = FileHelper()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        text = findViewById(R.id.edittext)
        add = findViewById(R.id.add)
        list = findViewById(R.id.list)

        // Load saved items
        items = fileHelper.readData(this)

        // Adapter
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        list.adapter = arrayAdapter

        // Add button click
        add.setOnClickListener {
            val input = text.text.toString().trim()
            if (input.isNotEmpty()) {
                items.add(input)
                fileHelper.writeData(items, applicationContext)
                arrayAdapter.notifyDataSetChanged()
                text.text.clear()

            }
        }

        // Delete item on click
        list.setOnItemClickListener { _, _, i, _ ->
            AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want to delete this item from the list?")
                .setCancelable(false)
                .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                .setPositiveButton("Yes") { _, _ ->
                    items.removeAt(i)
                    arrayAdapter.notifyDataSetChanged()
                    fileHelper.writeData(items, applicationContext)
                }
                .show()
        }
    }
}
