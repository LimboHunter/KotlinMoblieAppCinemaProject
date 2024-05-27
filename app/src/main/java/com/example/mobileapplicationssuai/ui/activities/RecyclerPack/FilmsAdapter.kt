package com.example.mobileapplicationssuai.ui.activities.RecyclerPack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileapplicationssuai.R
import com.example.mobileapplicationssuai.databinding.FigureItemBinding
import model.Film

class FilmsAdapter: RecyclerView.Adapter<FilmsAdapter.FiguresHolder>(){

    private lateinit var mListener : onItemClickListener

    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener : onItemClickListener){
        mListener = listener
    }

    val shapesList = ArrayList<Film>()

    class FiguresHolder(item : View, listener: onItemClickListener) : RecyclerView.ViewHolder(item){
        val binding = FigureItemBinding.bind(item)
        fun bind(film: Film){
            binding.ItemImage.setImageResource(film.imageResId)
            binding.ItemTitle.text = film.name
        }

        init {
            item.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiguresHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.figure_item, parent, false)
        return FiguresHolder(view, mListener)
    }

    override fun getItemCount(): Int {
        return shapesList.size
    }

    override fun onBindViewHolder(holder: FiguresHolder, position: Int) {
        holder.bind(shapesList[position])
    }

    fun addShape(film: Film){
        shapesList.add(film)
        notifyDataSetChanged()
    }
}