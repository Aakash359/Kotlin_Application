package com.example.kotline

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(val userlist: ArrayList<Android>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
       fun bindItems(user: Android){
         val tv_name =itemView.findViewById(R.id.tv_name) as TextView
           val tv_address =itemView.findViewById(R.id.tv_address) as TextView

           tv_name.text=user.name
           tv_address.text=user.version
           tv_name.setOnClickListener {
               Toast.makeText(itemView.context,"Name:"+user.name,Toast.LENGTH_SHORT).show()


               itemView.context.startActivity(Intent(itemView.context,PicassoImage::class.java))


           }

       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
    val v=LayoutInflater.from(parent.context).inflate(R.layout.item_adapter,parent,false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
     return userlist.size
    }

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
       holder.bindItems(userlist[position])
    }

}

