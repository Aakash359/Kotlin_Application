package com.example.kotline

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Home : AppCompatActivity() {
    lateinit var txt :TextView
    lateinit var button: Button
    lateinit var sharedPreferences: SharedPreferences
    lateinit var imageview :ImageView
    lateinit var btnCamera:Button
    lateinit var relativeLayout: RelativeLayout
    lateinit var img_cancel: ImageView
    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null
    private var mCompositeDisposable: CompositeDisposable? = null
    lateinit var recyclerview:RecyclerView

    val permissions = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = this.getSharedPreferences("MyToken", Context.MODE_PRIVATE)

        mCompositeDisposable = CompositeDisposable()

        setContentView(R.layout.activity_home)
        txt = findViewById(R.id.txt_name)
        button = findViewById(R.id.btn_logout)
        imageview=findViewById(R.id.img)
        relativeLayout=findViewById(R.id.relative_layout)
        img_cancel=findViewById(R.id.img_cancel)
        btnCamera=findViewById(R.id.btn_camera)

        //Cancel Image
        img_cancel.setOnClickListener {
            image_uri=null
            relativeLayout.visibility=View.INVISIBLE

        }
        //camera
        btnCamera.setOnClickListener {

            if (hasNoPermissions()) {
                requestPermission()
            }else{
                openCamera()

            }




        }

        //list
         recyclerview = findViewById(R.id.recyclerview)
        recyclerview.layoutManager = LinearLayoutManager(this,LinearLayout.VERTICAL,false)


//        val user = ArrayList<User>()
//        user.add(User("Rahul","Ghz"))
//
//        val adapter = CustomAdapter(user)
//        recyclerview.adapter=adapter


        button.setOnClickListener {
            Toast.makeText(applicationContext,"test",Toast.LENGTH_SHORT).show()
            sharedPreferences.edit().clear().commit();
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        txt.setText(intent.getStringExtra("keyIdentifier"))

       val editor:SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("name_key",intent.getStringExtra("keyIdentifier"))
        editor.apply()
        editor.commit()



        loadJSON()
    }

    private fun loadJSON() {

        val requestInterface = Retrofit.Builder()
            .baseUrl("https://learn2crack-json.herokuapp.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RequestInterface::class.java)

        mCompositeDisposable?.add(requestInterface.getData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError))
    }

    private fun handleResponse(androidList: List<Android>) {

      val  mAndroidArrayList = ArrayList(androidList)





        val adapter = CustomAdapter(mAndroidArrayList)
        recyclerview.adapter=adapter
    }

    private fun handleError(error: Throwable) {

        Log.d("err" ,error.localizedMessage)

        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        if (hasNoPermissions()) {
            requestPermission()
        }
    }
    private fun hasNoPermissions(): Boolean{
        return ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(){
        ActivityCompat.requestPermissions(this, permissions,0)
    }

    private fun openCamera() {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.TITLE, "New Picture")
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
        image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        //camera intent
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
      startActivityForResult(cameraIntent,IMAGE_CAPTURE_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //called when image was captured from camera intent
        if (resultCode == Activity.RESULT_OK){
            //set image captured to image view
            imageview.setImageURI(image_uri)
            Log.d("imageview",""+image_uri);
            relativeLayout.visibility=View.VISIBLE

        }
    }
}