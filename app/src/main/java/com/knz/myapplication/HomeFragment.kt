package com.knz.myapplication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var newRecycleView: RecyclerView
    private lateinit var newArrayList: ArrayList<HomeFragmentDataClass>
    private lateinit var imageid: Array<Int>
    private lateinit var textId: Array<String>
    private lateinit var textBonkId: Array<String>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageid = arrayOf(
            R.drawable.alien_outline,
            R.drawable.account_box_edit_outline,
            R.drawable.music_box_multiple_outline,
            R.drawable.music_note,
            R.drawable.briefcase_outline,
            R.drawable.calculator,
            R.drawable.clock_fast,
            R.drawable.cat,
            R.drawable.view_dashboard_outline,
            R.drawable.sonic_frame_0003,
            R.drawable.microphone
        )

        textId = resources.getStringArray(R.array.head)
        textBonkId = resources.getStringArray(R.array.description)

        newRecycleView = view.findViewById(R.id.recycleView)

        newRecycleView.layoutManager = LinearLayoutManager(requireContext())
        newRecycleView.setHasFixedSize(true)

        newArrayList = arrayListOf()
        getUserdata()
    }

    private fun getUserdata() {
        for (i in imageid.indices) {
            val data = HomeFragmentDataClass(imageid[i], textId[i], textBonkId[i])
            newArrayList.add(data)
        }
        newRecycleView.adapter = HomeFragmentAdapter(newArrayList)
    }
}