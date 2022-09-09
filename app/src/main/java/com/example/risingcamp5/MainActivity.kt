package com.example.risingcamp5

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.risingcamp5.databinding.ActivityMainBinding
import com.example.risingcamp5.fragment.*


lateinit var homeFragment: HomeFragment
lateinit var storeFragment: StoreFragment
lateinit var bookFragment: BookFragment
lateinit var mobileOrderFragment: MobileOrderFragment
lateinit var myFragment: MyFragment

lateinit var mainActivity: MainActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mainActivity = this

        // 로그인 여부에 대한 데이터 저장
        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE)
        var login = sharedPreferences.getString("login", "").toString()
        if(login == ""){ // 로그인 한 적이 없다면
            var editor = sharedPreferences.edit()
            editor.putString("login", "false") // "false" 값으로 저장
            editor.commit()
        }

        homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().add(R.id.frameLayout, homeFragment).commit()
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.menuHome -> {
                    homeFragment = HomeFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, homeFragment).commit()
                    true
                }
                R.id.menuStore -> {
                    storeFragment = StoreFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, storeFragment).commit()
                    true
                }
                R.id.menuBook -> {
                    bookFragment = BookFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, bookFragment).commit()
                    true
                }
                R.id.menuMobileOrder -> {
                    mobileOrderFragment = MobileOrderFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, mobileOrderFragment).commit()
                    true
                }
                R.id.menuMy -> {
                    myFragment = MyFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.frameLayout, myFragment).commit()
                    true
                }
                else -> false
            }
        }


    }

    override fun onResume() {
        super.onResume()
        homeFragment = HomeFragment()
    }

}
