package com.example.risingcamp5

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.risingcamp5.adapters.HomeSimpleAdapter
import com.example.risingcamp5.databinding.ActivityMenuBinding
import com.example.risingcamp5.datas.SimpleItem
import com.navercorp.nid.NaverIdLoginSDK

class MenuActivity : AppCompatActivity() {
    lateinit var binding: ActivityMenuBinding
    lateinit var menuAdapter: HomeSimpleAdapter
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE)
        var login = sharedPreferences.getString("login", "").toString()
        if(login == "true"){ // 로그인 상태라면 메뉴 Activity에 유저 이름 표시
            var userName = sharedPreferences.getString("userName", "null").toString()
            binding.tvUserName.text = userName+"님"
            binding.layoutLogout.visibility = View.INVISIBLE
            binding.layoutLogin.visibility = View.VISIBLE
        }

        menuAdapter = HomeSimpleAdapter()
        menuAdapter.getListFromView(setMenuList())
        binding.rvMenu.layoutManager = GridLayoutManager(this, 4)
        binding.rvMenu.adapter = menuAdapter


        binding.ivCancel.setOnClickListener {
            finish()
        }

        // 로그인 textView 클릭 리스너
        binding.tvSignIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        // 로그아웃 TextView 클릭 리스너
        binding.tvLogout.setOnClickListener {
            NaverIdLoginSDK.logout()

            Log.d("menuActivity", "logout")
            sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE)
            sharedPreferences.edit().putString("login", "false").commit() // 로그아웃 상태로 저장

            mainActivity.finish()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
       }


    }

    fun setMenuList(): MutableList<SimpleItem>{
        var list = mutableListOf<SimpleItem>()

        list.add(SimpleItem(R.drawable.ic_menu_1, "영화별 예매", 3))
        list.add(SimpleItem(R.drawable.ic_menu_2, "극장별 예매", 3))
        list.add(SimpleItem(R.drawable.ic_menu_3, "특별관 예매", 3))
        list.add(SimpleItem(R.drawable.ic_menu_4, "더 부티크 프라이빗", 3))
        list.add(SimpleItem(R.drawable.ic_menu_5, "이벤트", 3))
        list.add(SimpleItem(R.drawable.ic_menu_6, "게임/리워드", 3))
        list.add(SimpleItem(R.drawable.ic_menu_7, "제휴/할인", 3))
        list.add(SimpleItem(R.drawable.ic_menu_8, "상담톡", 3))

        return list
    }
}