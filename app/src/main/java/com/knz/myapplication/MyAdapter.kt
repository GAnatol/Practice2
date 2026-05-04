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

// Импорт DataClass не нужен, если ты находишься в том же пакете

class MyAdapter(private val newList : ArrayList<DataClass>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // 1. Исправлено название на onCreateViewHolder (с маленькой 'o')
    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): MyViewHolder {
        // Убедись, что файл list_item2.xml существует в папке res/layout
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item2, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return newList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = newList[position]
        // 2. Используем правильные имена переменных в camelCase
        holder.imageView.setImageResource(currentItem.titleImage)
        holder.txtHead.text = currentItem.heading
        holder.txtBonk.text = currentItem.bonktxt


/*        <item>стартовая активность</item>
        <item>характеристика</item>
        <item>плейлист</item>
        <item>расписание</item>
        <item>секундомер</item>
        <item>кошка</item>*/
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context // Берем контекст здесь
            try {
                when (position) {
                    0 -> context.startActivity(Intent(context, MainActivity::class.java))
                    1 -> context.startActivity(Intent(context, SecondActivity::class.java))
                    2 -> context.startActivity(Intent(context, ListViewTest::class.java))
                    3 -> context.startActivity(Intent(context, ThirdActivity::class.java))
                    4 -> context.startActivity(Intent(context, CalculatorActivity::class.java))
                    5 -> context.startActivity(Intent(context, SecundomerShpiko::class.java))
                    6 -> {
                        val url = "https://ru.wikipedia.org/wiki/%D0%9A%D0%BE%D1%88%D0%BA%D0%B0"
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        try {
                            context.startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(context, "не удалось", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } catch (e: Exception) {
                // Если активность не найдена или ошибка в коде, приложение не упадет,
                // а покажет уведомление
                Toast.makeText(context, "Ошибка: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace() // Выведет ошибку в Logcat
            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView : ShapeableImageView = itemView.findViewById(R.id.imageItem)
        val txtHead : TextView = itemView.findViewById(R.id.txtItem)
        val txtBonk : TextView = itemView.findViewById(R.id.txtBonk)
    }
}