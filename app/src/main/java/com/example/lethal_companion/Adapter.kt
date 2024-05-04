package com.example.lethal_companion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.itemName)
}


class MonsterAdapter(private val data: ResponseModel) : RecyclerView.Adapter<MyViewHolder>() {

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.Monsters.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data
        holder.textView.text = item.Monsters[position].name

        holder.itemView.setOnClickListener {
            mListener?.onItemClick(position)
        }
    }
}

class LogsAdapter(private val data: ResponseModel) : RecyclerView.Adapter<MyViewHolder>() {

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.Logs.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data
        holder.textView.text = item.Logs[position].name

        holder.itemView.setOnClickListener {
            mListener?.onItemClick(position)
        }
    }

}

class StoreAdapter(private val data: ResponseModel) : RecyclerView.Adapter<MyViewHolder>() {

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.Store.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data
        holder.textView.text = item.Store[position].name

        holder.itemView.setOnClickListener {
            mListener?.onItemClick(position)
        }
    }
}

class TipsAdapter(private val data : ResponseModel) : RecyclerView.Adapter<MyViewHolder>() {

    private var mListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.Tips.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data
        holder.textView.text = item.Tips[position].name

        holder.itemView.setOnClickListener {
            mListener?.onItemClick(position)
        }
    }

}