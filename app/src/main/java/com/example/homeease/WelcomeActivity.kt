package com.example.homeease

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth

class WelcomeActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    lateinit var sliderAdapter: OneTimeSliderAdapter
    lateinit var sliderList: ArrayList<SliderData>
    lateinit var btn: Button
    lateinit var indicatorSlideOneTV: TextView
    lateinit var indicatorSlideTwoTV: TextView
    lateinit var indicatorSlideThreeTV: TextView
    lateinit var heading: TextView

    lateinit var subtitle: TextView
    private var isViewPagerScrolling = false

    private var auth = FirebaseAuth.getInstance()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        viewPager = findViewById(R.id.idViewPager)
        btn = findViewById(R.id.idBtnNext)
        indicatorSlideOneTV = findViewById(R.id.idTVSlideOne)
        indicatorSlideTwoTV = findViewById(R.id.idTVSlideTwo)
        indicatorSlideThreeTV = findViewById(R.id.idTVSlideThree)
        heading = findViewById(R.id.tvSliderHeading)
        subtitle = findViewById(R.id.tvSliderSubtitle)


        btn.setOnClickListener {
            if (btn.text == "Register Now") {
                startActivity(Intent(this, LogInActivity::class.java))
                finish()
            }else{
                viewPager.currentItem += 1
            }
        }

        /////////////////////////////
        sliderList = ArrayList()
        sliderList.add(
            SliderData(R.drawable.slider_washing_machine)
        )
        sliderList.add(
            SliderData(R.drawable.slider_mopper)
        )
        sliderList.add(
            SliderData(R.drawable.slider_washing_machine)
        )

        //Adapter Class
        sliderAdapter = OneTimeSliderAdapter(this, sliderList)

        viewPager.adapter = sliderAdapter
        viewPager.addOnPageChangeListener(viewListener)
    }

    // creating a method for view pager for on page change listener.
    var viewListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {

        }

        override fun onPageSelected(position: Int) {
            if (position == 0) {
                indicatorSlideTwoTV.setTextColor(resources.getColor(R.color.text_grey))
                indicatorSlideThreeTV.setTextColor(resources.getColor(R.color.text_grey))
                indicatorSlideOneTV.setTextColor(resources.getColor(R.color.text_blue))
                heading.text = "Need a handyman for those DIY tasks around the house?"
                subtitle.text = "Let us take care of all your home service needs..\nFrom plumbing to electrical, we've got you covered."
                btn.text = "Next"
                btn.setBackgroundColor(resources.getColor(R.color.text_blue))
                btn.setTextColor(resources.getColor(R.color.white))


            } else if (position == 1) {
                indicatorSlideTwoTV.setTextColor(resources.getColor(R.color.text_blue))
                indicatorSlideThreeTV.setTextColor(resources.getColor(R.color.text_grey))
                indicatorSlideOneTV.setTextColor(resources.getColor(R.color.text_grey))
                heading.text = "Tired of cleaning up after yourself?"
                subtitle.text = "We offer a range of cleaning services to suit your needs. Let \n our professional cleaners handle it for you."
                btn.text = "Next"
                btn.setBackgroundColor(resources.getColor(R.color.text_blue))
                btn.setTextColor(resources.getColor(R.color.white))
            } else {
                indicatorSlideTwoTV.setTextColor(resources.getColor(R.color.text_grey))
                indicatorSlideThreeTV.setTextColor(resources.getColor(R.color.text_blue))
                indicatorSlideOneTV.setTextColor(resources.getColor(R.color.text_grey))
                heading.text = "Moving to a new place and need help with packing?"
                subtitle.text = "Let our expert movers take the stress out of your move."
                btn.text = "Register Now"
                btn.setBackgroundColor(resources.getColor(R.color.primary_orange))
                btn.setTextColor(resources.getColor(R.color.black))

            }
        }

        override fun onPageScrollStateChanged(state: Int) { }

    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }


}


