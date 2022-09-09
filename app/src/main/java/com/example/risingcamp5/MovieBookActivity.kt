package com.example.risingcamp5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.risingcamp5.databinding.ActivityMovieBookBinding
import com.example.risingcamp5.fragment.MovieBookFragment
import com.google.android.material.tabs.TabLayout



class MovieBookActivity : AppCompatActivity() {
    lateinit var binding: ActivityMovieBookBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBookBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 메뉴 탭바 선택 리스너
        supportFragmentManager.beginTransaction().add(R.id.frameLayout,MovieBookFragment()).commit()
        binding.tabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab!!.position){
                    0 -> {supportFragmentManager.beginTransaction().replace(R.id.frameLayout,MovieBookFragment()).commit()}
                    1 -> {supportFragmentManager.beginTransaction().replace(R.id.frameLayout,MovieBookFragment()).commit()}
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.ivCancel.setOnClickListener {
            finish()
        }

    }
}
