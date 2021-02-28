package com.codeandcore.test.ui


import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.codeandcore.test.R
import com.codeandcore.test.datamodels.ConfigurableOption
import kotlinx.android.synthetic.main.item_order_bottomsheet.view.*


class OrderItemBottomSheetAdapter(
        var configurableOption: ArrayList<ConfigurableOption>,
        var context: Context?,
        var listnerForUpdateQty: OrderItemSuggestionBottomsheet.ListnerForUpdateQty
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var selectedPos: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_order_bottomsheet,
            parent,
            false
        )
        return ItemViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder) {
        var configurableOption= configurableOption.get(position)
            holder.itemView.tvQtyType.text=configurableOption.attribute_text
            holder.itemView.tvName.text=configurableOption.attributes?.get(0)?.value
            if(!configurableOption.is_Selcte)
            selectedRow(holder.itemView.tvName)
            else{
                unselectedRow(holder.itemView.tvName)
            }

        }
    }

   @RequiresApi(Build.VERSION_CODES.M)
    private fun selectedRow(tvName:TextView) {
       tvName.setTextColor(context!!.getColor(R.color.colorBlack))
       tvName.background = context!!.getDrawable(R.drawable.order_border)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun unselectedRow(tvName:TextView) {
         tvName.setTextColor(context!!.getColor(R.color.colorPrimary))
         tvName.background = context!!.getDrawable(R.drawable.order_border_color2)
    }

    override fun getItemCount(): Int {
        return configurableOption.size!!
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            listnerForUpdateQty.updateQty(configurableOption[adapterPosition],configurableOption[adapterPosition].attribute_id)
            selectedPos = adapterPosition
            notifyDataSetChanged();
        }

    }




}