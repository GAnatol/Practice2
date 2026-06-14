package com.knz.myapplication

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import kotlin.jvm.java

class HomeFragmentAdapter(private val newList : ArrayList<HomeFragmentDataClass>) : RecyclerView.Adapter<HomeFragmentAdapter.MyViewHolder>() {
    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.home_list_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newList[position]
        holder.imageView.setImageResource(currentItem.titleImage)
        holder.txtHead.text = currentItem.heading
        holder.txtBonk.text = currentItem.bonktxt


        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            try {
                when (position) {
                    0 -> context.startActivity(Intent(context, MainActivity::class.java))
                    1 -> context.startActivity(Intent(context, SecondActivity::class.java))
                    2 -> context.startActivity(Intent(context, PlaylistActivity::class.java))
                    3 -> context.startActivity(Intent(context, MusicPlayerActivity::class.java))
                    4 -> context.startActivity(Intent(context, TimetableActivity::class.java))
                    5 -> context.startActivity(Intent(context, CalculatorSplash::class.java))
                    6 -> context.startActivity(Intent(context, StopwatchActivity::class.java))
                    7 -> {
                        val url = "https://ru.wikipedia.org/wiki/%D0%9A%D0%BE%D1%88%D0%BA%D0%B0"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "не удалось перейти по ссылке", Toast.LENGTH_SHORT).show()
                        }
                    }
                    8 -> context.startActivity(Intent(context, FragmentsActivity::class.java))
                    9 -> context.startActivity(Intent(context, AnimsActivitySplash::class.java))
                    10 -> context.startActivity(Intent(context, VoiceAssistantActivity::class.java))
                }
            } catch (e: Exception) {
                Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace()
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView : ShapeableImageView = itemView.findViewById(R.id.imageItem)
        val txtHead : TextView = itemView.findViewById(R.id.txtItem)
        val txtBonk : TextView = itemView.findViewById(R.id.txtBonk)
    }
}