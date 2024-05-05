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

class MyViewHolder2(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.itemName)
    val score: TextView = itemView.findViewById(R.id.score)
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
        return data.record.Monsters.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data
        holder.textView.text = item.record.Monsters[position].name

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
        return data.record.Logs.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data
        holder.textView.text = item.record.Logs[position].name

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
        return data.record.Store.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data
        holder.textView.text = item.record.Store[position].name

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
        return data.record.Tips.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = data
        holder.textView.text = item.record.Tips[position].name

        holder.itemView.setOnClickListener {
            mListener?.onItemClick(position)
        }
    }

}
class ScoreAdapter(private val data : ResponseModel) : RecyclerView.Adapter<MyViewHolder2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.rv_score, parent, false)
        return MyViewHolder2(view)
    }

    override fun getItemCount(): Int {
        return data.record.Game.size
    }

    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {
        val item = data
        holder.textView.text = item.record.Game[position].name
        holder.score.text = item.record.Game[position].hiScore.toString()

    }
}