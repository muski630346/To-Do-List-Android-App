package com.example.todolist

import android.content.Context
import java.io.*

class FileHelper {
    private val FILENAME = "listInfo.dat"

    // Save the list to internal storage
    fun writeData(items: ArrayList<String>, context: Context) {
        try {
            context.openFileOutput(FILENAME, Context.MODE_PRIVATE).use { fos ->
                ObjectOutputStream(fos).use { oos ->
                    oos.writeObject(items)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    // Read the list from internal storage
    fun readData(context: Context): ArrayList<String> {
        return try {
            context.openFileInput(FILENAME).use { fis ->
                ObjectInputStream(fis).use { ois ->
                    val obj = ois.readObject()
                    if (obj is ArrayList<*>) {
                        @Suppress("UNCHECKED_CAST")
                        obj as ArrayList<String>
                    } else {
                        ArrayList()
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            ArrayList() // No file yet → return empty list
        } catch (e: Exception) {
            e.printStackTrace()
            ArrayList() // Any error → return empty list
        }
    }
}
