package com.weatherclothes.artist.presentation.screens.places.recyclerView

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.view.MotionEvent
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import java.util.LinkedList
import kotlin.math.abs
import kotlin.math.max

@SuppressLint("ClickableViewAccessibility")
abstract class MoveHelper(private val recyclerView: RecyclerView) : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or ItemTouchHelper.DOWN,
        ItemTouchHelper.LEFT,
    ) {

    private var fromPosition = 0
    private var toPosition = 0
    private var swipedPosition = -1
    private val buttonsBuffer: MutableMap<Int, List<UnderlayButton>> = mutableMapOf()
    private var swipeProgressMap = mutableMapOf<Int, Float>()
    private val recoverQueue = object : LinkedList<Int>() {
        override fun add(element: Int): Boolean {
            if (contains(element)) return false
            return super.add(element)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private val touchListener = View.OnTouchListener { _, event ->
        if (swipedPosition < 0) return@OnTouchListener false
        buttonsBuffer[swipedPosition]?.forEach { it.handle(event) }
        recoverQueue.add(swipedPosition)
        swipedPosition = -1
        recoverSwipedItem()
        true
    }

    init {
        recyclerView.setOnTouchListener(touchListener)
    }

    private fun recoverSwipedItem() {
        while (recoverQueue.isNotEmpty()) {
            val position = recoverQueue.poll() ?: return
            recyclerView.adapter?.notifyItemChanged(position)
        }
    }

    private fun drawButtons(
        canvas: Canvas,
        buttons: List<UnderlayButton>,
        itemView: View,
        dX: Float
    ) {
        var right = itemView.right
        buttons.forEach { button ->
            val width = button.intrinsicWidth / buttons.intrinsicWidth() * abs(dX)
            val left = right - width
            button.onDraw(
                canvas,
                RectF(left, itemView.top.toFloat(), right.toFloat(), itemView.bottom.toFloat())
            )
            right = left.toInt()
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
        val iconWidth = dpToPixel(56)
        val viewHolder = viewHolder as PlacesAdapter.ViewHolder
        val position = viewHolder.adapterPosition
        var maxDX = dX
        val itemView = viewHolder.itemView
        val foregroundView = viewHolder.binding.foreground
        val limit = -iconWidth
        val newDX = if (dX < limit) limit else dX

        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            swipeProgressMap[position] = abs(dX) / viewHolder.itemView.width.toFloat()
            if (!buttonsBuffer.containsKey(position)) {
                buttonsBuffer[position] = instantiateUnderlayButton(position)
            }
            val buttons = buttonsBuffer[position] ?: return
            if (buttons.isEmpty()) return
            maxDX = max(-buttons.intrinsicWidth() * 1.5f, dX)
            drawButtons(c, buttons, itemView, maxDX)

            getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, newDX, dY, actionState, isCurrentlyActive)
        }
    }

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

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.1f
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        val placesViewHolder = viewHolder as PlacesAdapter.ViewHolder
        placesViewHolder.onItemClear()
        swipeProgressMap.remove(viewHolder.adapterPosition)
        val foregroundView = placesViewHolder.binding.foreground
        getDefaultUIUtil().clearView(foregroundView)
        foregroundView.translationX = 0f
        foregroundView.animate().cancel()
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (swipedPosition != position) recoverQueue.add(swipedPosition)
        swipedPosition = position
        recoverSwipedItem()
    }

    abstract fun instantiateUnderlayButton(position: Int): List<UnderlayButton>

    interface UnderlayButtonClickListener {
        fun onClick()
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            val placesViewHolder = viewHolder as PlacesAdapter.ViewHolder
            placesViewHolder.onItemSelected()
        }
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val foregroundView = (viewHolder as PlacesAdapter.ViewHolder).binding.foreground
            getDefaultUIUtil().onSelected(foregroundView)
        }
    }

    class UnderlayButton(
        private val context: Context,
        private val title: String,
        textSize: Float,
        @ColorRes private val colorRes: Int,
        @DrawableRes private val iconRes: Int? = null,
        iconSize: Int? = null,
        private val clickListener: UnderlayButtonClickListener
    ) {
        private var clickableRegion: RectF? = null
        private val textSizeInPixel: Float = textSize * context.resources.displayMetrics.density
        private val iconSizeInPixel: Float = iconSize?.let { it * context.resources.displayMetrics.density } ?: 100f
        private val horizontalPadding = 50.0f
        val intrinsicWidth: Float

        init {
            val paint = Paint()
            paint.textSize = textSizeInPixel
            paint.typeface = Typeface.DEFAULT_BOLD
            paint.textAlign = Paint.Align.LEFT
            val titleBounds = Rect()
            paint.getTextBounds(title, 0, title.length, titleBounds)
            intrinsicWidth = (titleBounds.width() + 2 * horizontalPadding)
        }

        fun onDraw(canvas: Canvas, rect: RectF) {
            val paint = Paint()
            paint.color = ContextCompat.getColor(context, colorRes)
            canvas.drawRect(rect, paint)
            val iconLeft = rect.left + (rect.width() - iconSizeInPixel) / 2
            val iconTop = rect.top + (rect.height() * 0.8f - iconSizeInPixel) / 2
            iconRes?.let {
                val drawable = ContextCompat.getDrawable(context, it)
                drawable?.let { icon ->
                    icon.setBounds(
                        iconLeft.toInt(),
                        iconTop.toInt(),
                        (iconLeft + iconSizeInPixel).toInt(),
                        (iconTop + iconSizeInPixel).toInt()
                    )
                    icon.draw(canvas)
                }
            }

            paint.color = ContextCompat.getColor(context, android.R.color.transparent)
            paint.textSize = textSizeInPixel
            paint.typeface = Typeface.DEFAULT
            paint.textAlign = Paint.Align.CENTER

            val titleBounds = Rect()
            paint.getTextBounds(title, 0, title.length, titleBounds)
            clickableRegion = rect
        }

        fun handle(event: MotionEvent) {
            clickableRegion?.let {
                if (it.contains(event.x, event.y)) {
                    clickListener.onClick()
                }
            }
        }
    }
}

fun dpToPixel(dpValue: Int): Float {
    val density = Resources.getSystem().displayMetrics.density
    return dpValue * density
}

private fun List<MoveHelper.UnderlayButton>.intrinsicWidth(): Float {
    if (isEmpty()) return 0.0f
    return map { it.intrinsicWidth }.reduce { acc, fl -> acc + fl }
}