package com.weatherclothes.artist.presentation.screens.places.recyclerView

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Canvas
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MyLog"

@SuppressLint("ClickableViewAccessibility")
fun swipeToDelete(recyclerView: RecyclerView, onDelete: (Int) -> Unit) {

    var itemTouchHelper: ItemTouchHelper? = null

    var fromPosition = 0
    var toPosition = 0

    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT
                or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            fromPosition = viewHolder.adapterPosition
            toPosition = target.adapterPosition
            recyclerView.adapter?.notifyItemMoved(fromPosition, toPosition)
            val adapter = recyclerView.adapter as PlacesAdapter
            adapter.itemMove(fromPosition, toPosition)
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            when (direction) {
                ItemTouchHelper.LEFT -> {
                    Log.d(TAG, "onSwiped: left")
                }

                ItemTouchHelper.RIGHT -> {
                    Log.d(TAG, "onSwiped: right")
                }
            }
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            val viewHolder = viewHolder as PlacesAdapter.ViewHolder
            val iconWidth = dpToPixel(56)

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                viewHolder.binding.background.visibility = View.VISIBLE
                if (viewHolder.binding.foreground.translationX != -iconWidth) {
                    if (dX < 0) {
                        if (dX < -iconWidth) {
                            viewHolder.binding.foreground.translationX = -iconWidth
                        } else {
                            viewHolder.binding.foreground.translationX = dX
                        }
                    }
                }
            } else {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
            if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                val placesViewHolder = viewHolder as PlacesAdapter.ViewHolder
                placesViewHolder.onItemSelected()
            }
            super.onSelectedChanged(viewHolder, actionState)
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            val placesViewHolder = viewHolder as PlacesAdapter.ViewHolder
            placesViewHolder.onItemClear()
        }
    }

    itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
    itemTouchHelper.attachToRecyclerView(recyclerView)
}

fun dpToPixel(dpValue: Int): Float {
    val density = Resources.getSystem().displayMetrics.density
    return dpValue * density
}