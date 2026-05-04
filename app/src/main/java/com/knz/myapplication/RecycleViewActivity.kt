package com.knz.myapplication

import android.os.Bundle
import android.util.TypedValue
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecycleViewActivity : AppCompatActivity() {
    private lateinit var newRecycleView : RecyclerView
    private lateinit var newArrayList : ArrayList<DataClass>
    private lateinit var imageid : Array<Int>
    private lateinit var textId : Array<String>
    private lateinit var textBonkId : Array<String>





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recycle_view)

        window.statusBarColor = getColorFromAttr(androidx.appcompat.R.attr.colorPrimary)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        imageid = arrayOf(
            R.drawable.alien_outline,
            R.drawable.account_box_edit_outline,
            R.drawable.music_box_multiple_outline,
            R.drawable.briefcase_outline,
            R.drawable.calculator,
            R.drawable.clock_fast,
            R.drawable.cat
        )

        // Получение строковых ресурсов и сохранение их в массивах
        textId = resources.getStringArray(R.array.head)
        textBonkId = resources.getStringArray(R.array.description)


        // Находим RecyclerView в макете по его ID
        newRecycleView = findViewById(R.id.recycleView)
        // Установка менеджера компоновки для RecyclerView
        newRecycleView.layoutManager = LinearLayoutManager(this)
        //recyclerView не планирует изменять размеры своих дочерних элементов
//        динамически.
        newRecycleView.setHasFixedSize(true)

        // Инициализация нового списка для хранения данных
        newArrayList = arrayListOf<DataClass>()
        getUserdata()

    }

    private fun getUserdata() {
    // Проход по индексам изображений
        for (i in imageid.indices)
        {
            // Создание нового экземпляра DataClass для каждого элемента
            val data = DataClass(imageid[i],textId[i],textBonkId[i])
    // Добавление созданного экземпляра в список newArrayList
            newArrayList.add(data)
        }
    // Установка адаптера для RecyclerView с заполненным списком данных
        newRecycleView.adapter = MyAdapter(newArrayList)
    }

    private fun getColorFromAttr(attrResId: Int): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(attrResId, typedValue, true)
        return typedValue.data
    }
}