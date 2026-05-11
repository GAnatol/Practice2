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
            R.drawable.cat,
            R.drawable.view_dashboard_outline,
            R.drawable.cloud_outline
        )


        textId = resources.getStringArray(R.array.head)
        textBonkId = resources.getStringArray(R.array.description)


        newRecycleView = findViewById(R.id.recycleView)
        newRecycleView.layoutManager = LinearLayoutManager(this)
        newRecycleView.setHasFixedSize(true)
        newArrayList = arrayListOf<DataClass>()
        getUserdata()

    }

    private fun getUserdata() {
        for (i in imageid.indices)
        {
            val data = DataClass(imageid[i],textId[i],textBonkId[i])
            newArrayList.add(data)
        }
        newRecycleView.adapter = MyAdapter(newArrayList)
    }

    private fun getColorFromAttr(attrResId: Int): Int {
        val typedValue = TypedValue()
        theme.resolveAttribute(attrResId, typedValue, true)
        return typedValue.data
    }
}