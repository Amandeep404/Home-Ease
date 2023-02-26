package com.example.homeease

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter

class OneTimeSliderAdapter(val context : Context, val sliderList :  ArrayList<SliderData>): PagerAdapter(){
    override fun getCount(): Int {
        return sliderList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as LinearLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        val layoutInflater: LayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val view: View = layoutInflater.inflate(R.layout.item_slider, container, false)

        val imageView: ImageView = view.findViewById(R.id.ivItemSlider)
        val sliderData: SliderData = sliderList[position]
        imageView.setImageResource(sliderData.slideImage)

        container.addView(view)

        return view

    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {

        container.removeView(`object` as LinearLayout)
    }
}