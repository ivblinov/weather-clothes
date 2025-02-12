package com.weatherclothes.artist.presentation.screens.places.recyclerView

import android.content.res.Resources
import android.graphics.Canvas
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MyLog"
fun swipeToDelete(recyclerView: RecyclerView, onDelete: (Int) -> Unit) {

    var fromPosition = 0
    var toPosition = 0

    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT
                or ItemTouchHelper.RIGHT
    ) {

        var isSwipeActive = false

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
            if (isSwipeActive) return

            val position = viewHolder.adapterPosition

            val viewHolder = viewHolder as PlacesAdapter.ViewHolder

//            viewHolder.binding.foreground.isClickable = false
//            viewHolder.binding.foreground.isFocusable = false
//
//            viewHolder.binding.iconDelete.isClickable = true
//            viewHolder.binding.iconDelete.isFocusable = true

//            viewHolder.binding.iconDelete.setOnClickListener {
//                Log.d(TAG, "onSwiped: onDelete")
//            }

            when (direction) {
                ItemTouchHelper.LEFT -> {

                    Log.d(TAG, "onSwiped: left")
                }
                ItemTouchHelper.RIGHT -> {

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

//                if (dX > -iconWidth) {
//                    Log.d(TAG, "onChildDraw: dX = $dX")
//                    isSwipeActive = true
//                    viewHolder.binding.foreground.translationX = dX
//                } else {
//                    Log.d(TAG, ": dX = $dX")
//                    isSwipeActive = false
//                    viewHolder.binding.foreground.translationX = -iconWidth
//
//                }

                if (dX < 0) {
                    if (viewHolder.binding.foreground.translationX != -iconWidth) {


//                        Log.d(TAG, "onChildDraw: ")

                        if (dX < -iconWidth) {
                            Log.d(TAG, "dx < -iconWidth")
                            isSwipeActive = true // Отключаем свайп
                            viewHolder.binding.foreground.translationX = -iconWidth

                        } else {
//                            Log.d(TAG, "else")
                            isSwipeActive = false // Включаем свайп обратно
                            viewHolder.binding.foreground.translationX = dX
                        }
                    }
                } else if (dX > 0) {
//                    Log.d(TAG, "onChildDraw: dx = $dX")
//                    viewHolder.binding.foreground.translationX = 0f
//                    isSwipeActive = false
//                    viewHolder.binding.foreground.isClickable = false
//                    viewHolder.binding.foreground.isFocusable = false
//
//                    viewHolder.binding.iconDelete.isClickable = true
//                    viewHolder.binding.iconDelete.isFocusable = true
                }

                viewHolder.binding.background.visibility = View.VISIBLE
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

    val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
    itemTouchHelper.attachToRecyclerView(recyclerView)
}

fun dpToPixel(dpValue: Int): Float {
    val density = Resources.getSystem().displayMetrics.density
    return dpValue * density
}