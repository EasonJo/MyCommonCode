package com.eason.commonlib.adapter

import android.support.v7.util.DiffUtil

/**
 * DiffUtil callback impl
 * @see [DiffUtil.Callback]
 */
internal class DiffUtilCallback<ITEM>(private val oldItems: List<ITEM>,
                                      private val newItems: List<ITEM>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldItems[oldItemPosition] == newItems[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldItems[oldItemPosition] == newItems[newItemPosition]
}
