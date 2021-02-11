package com.example.miwok

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.android.miwok.R
import java.util.*

class WordAdapter(context: Context, words: ArrayList<Word?>, var colorRes: Int) : ArrayAdapter<Word?>(context, 0, words) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                    R.layout.list_item, parent, false)
        }
        val currentWord = getItem(position)
        val defaultTextView = listItemView!!.findViewById<TextView>(R.id.defaultTextView)
        defaultTextView.text = currentWord!!.defaultTranslation
        val miwokTextView = listItemView.findViewById<TextView>(R.id.miwokTextView)
        miwokTextView.text = currentWord.miwokTranslation
        val image = listItemView.findViewById<ImageView>(R.id.image_icon)
        if (currentWord.hasImage()) {
            image.setImageResource(currentWord.imageSrc)
            image.visibility = View.VISIBLE
        } else {
            image.visibility = View.GONE
        }
        val textContainer = listItemView.findViewById<View>(R.id.textContainer)
        val color = ContextCompat.getColor(context, colorRes)
        textContainer.setBackgroundColor(color)
        return listItemView
    }

    companion object {
        private val LOG_TAG = WordAdapter::class.java.simpleName
    }

}