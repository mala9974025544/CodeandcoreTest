package com.codeandcore.test.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import com.codeandcore.test.R
import com.codeandcore.test.datamodels.AtttibuteResponseList
import com.codeandcore.test.datamodels.ConfigurableOption
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.layout_photo_bottom_sheet.*
import kotlinx.android.synthetic.main.layout_photo_bottom_sheet.view.*


class OrderItemSuggestionBottomsheet(var configurableOption: ArrayList<ConfigurableOption>) : BottomSheetDialogFragment() {

    lateinit var listnerForUpdateQty: ListnerForUpdateQty

    private lateinit var orderItemBottomSheetAdapter: OrderItemBottomSheetAdapter
    override fun onCreateView(
            inflater: LayoutInflater,
            @Nullable container: ViewGroup?,
            @Nullable savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_photo_bottom_sheet, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI(view)
    }

    private fun setupUI(view: View) {


        orderItemBottomSheetAdapter = OrderItemBottomSheetAdapter(configurableOption, context, listnerForUpdateQty)

        view.rvList.adapter = orderItemBottomSheetAdapter
    }

    fun setAddToCartLister(listnerForUpdateQty: ListnerForUpdateQty) {
        this.listnerForUpdateQty = listnerForUpdateQty

    }

    interface ListnerForUpdateQty {
        fun updateQty(configurableOption: ConfigurableOption, adapterPosition: String?)
    }


    fun startAnim() {
        if (avLoading != null) {
            avLoading.show()
            avLoading.visibility = View.VISIBLE
        }

    }

    fun stopAnim() {
        if (avLoading != null) {
            avLoading.hide()
            avLoading.visibility = View.GONE
        }

    }

    fun notifyUI(atttibuteResponse: AtttibuteResponseList?, attrId: String) {
        if (atttibuteResponse != null)

            if (::orderItemBottomSheetAdapter.isInitialized) {
                for ((index, value) in configurableOption.withIndex())

                    if (value.attribute_id == attrId) {
                        value.is_Selcte = true
                        orderItemBottomSheetAdapter.notifyDataSetChanged()
                    }
            }

    }

}