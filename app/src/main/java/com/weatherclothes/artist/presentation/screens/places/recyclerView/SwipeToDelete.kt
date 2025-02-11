package com.weatherclothes.artist.presentation.screens.places.recyclerView

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "MyLog"
fun swipeToDelete(recyclerView: RecyclerView, onDelete: (Int) -> Unit) {

    var fromPosition = 0
    var toPosition = 0

    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT
//                or ItemTouchHelper.RIGHT
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
            // Логика обработки свайпа
            val position = viewHolder.adapterPosition

            when (direction) {
                ItemTouchHelper.LEFT -> {
                    // Свайп влево
                    // Например, удалить элемент
                    // yourList.removeAt(position)
                    // recyclerView.adapter?.notifyItemRemoved(position)
                }
                ItemTouchHelper.RIGHT -> {
                    // Свайп вправо
                    // Например, выполнить другое действие
                }
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