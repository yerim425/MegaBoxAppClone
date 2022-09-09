package com.example.risingcamp5

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.risingcamp5.databinding.ActivitySignInBinding
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse


class SignInActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignInBinding
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivCancel.setOnClickListener {
            finish()
        }

        binding.ivNaver.setOnClickListener {

            // 네이버 SDK 초기화화
           NaverIdLoginSDK.initialize(this,
               BuildConfig.NAVER_API_CLIENT_ID, BuildConfig.NAVER_API_CLIENT_SECRET , "RisingCamp5")


            // 네이버 로그인 유저 정보 콜백
            val profileCallback = object : NidProfileCallback<NidProfileResponse> {
                override fun onSuccess(response: NidProfileResponse) {
                    val userName = response.profile?.name.toString()
                    val userEmail = response.profile?.email.toString()

                    Log.d("signInActivity", userName + " " + userEmail)

                    sharedPreferences = getSharedPreferences("userData", MODE_PRIVATE)
                    var editor = sharedPreferences.edit()
                    editor.putString("login", "true")
                    editor.putString("userName", userName)
                    editor.commit()

                }
                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    Log.d("signInActivity", "profileCallback failure errorCode: ${errorCode}")
                }
                override fun onError(errorCode: Int, message: String) {
                    Log.d("signInActivity", "profileCallback error message: ${message}")
                }
            }

            // 네이버 로그인 인증 콜백
            val oauthLoginCallback = object : OAuthLoginCallback {
                override fun onSuccess() {

                    NidOAuthLogin().callProfileApi(profileCallback) // 로그인 유저 정보 가져옴

                    // 메인 액티비티 재시작
                    mainActivity.finish()
                    startActivity(Intent(this@SignInActivity, MainActivity::class.java))
                    finish()

                }
                override fun onFailure(httpStatus: Int, message: String) {
                    val errorCode = NaverIdLoginSDK.getLastErrorCode().code
                    val errorDescription = NaverIdLoginSDK.getLastErrorDescription()
                    Log.d("signInActivity", "errorCode: ${errorCode}, errorDescription: ${errorDescription}")
                }
                override fun onError(errorCode: Int, message: String) {
                    onFailure(errorCode, message)
                }
            }
            NaverIdLoginSDK.authenticate(this, oauthLoginCallback)
        }
    }
}