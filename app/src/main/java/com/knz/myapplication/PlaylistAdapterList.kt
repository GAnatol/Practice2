package com.knz.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView


class PlaylistAdapterList(context : Context, private val items: List<PlaylistListItem>) : ArrayAdapter<PlaylistListItem>(context,0,items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.playlist_list_item,parent,false)

        val imageView : ImageView = view.findViewById(R.id.ImageView)
        val textView : TextView = view.findViewById(R.id.tvList)

        item?.let {
            imageView.setImageResource(it.imageR)
            textView.text = it.text
        }

        return view
    }
}