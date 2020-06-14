package br.com.oversight.ambienta.utils

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import br.com.oversight.ambienta.R
import br.com.oversight.ambienta.ui.home.HomeFragment


class SwipeToDeleteCallback(
    private val listener: SwipeActions,
    context: Context,
    adapter: RecyclerView.Adapter<*>
) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
    private val icon: Drawable?
    private val background: ColorDrawable
    private val adapter: RecyclerView.Adapter<*>
    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        val backgroundCornerOffset =
            20 //so background is behind the rounded corners of itemView
        val iconMargin = (itemView.height - icon!!.intrinsicHeight) / 2
        val iconTop =
            itemView.top + (itemView.height - icon.intrinsicHeight) / 2
        val iconBottom = iconTop + icon.intrinsicHeight
        Log.i("SWIPE DX", "" + dX)
        if (dX > 0) { // Swiping to the right
            val iconLeft = itemView.left + iconMargin
            val iconRight = itemView.left + iconMargin + icon.intrinsicWidth
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            background.setBounds(
                itemView.left, itemView.top,
                itemView.left + dX.toInt() + backgroundCornerOffset, itemView.bottom
            )
        } else if (dX < 0) { // Swiping to the left
            val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
            val iconRight = itemView.right - iconMargin
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            background.setBounds(
                itemView.right + dX.toInt() - backgroundCornerOffset,
                itemView.top, itemView.right, itemView.bottom
            )
        } else { // view is unSwiped
            background.setBounds(0, 0, 0, 0)
        }
        background.draw(c)
        icon.draw(c)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        Log.i("SWIPE", "MOVE")
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        adapter.notifyItemChanged(position)
        if (listener != null) {
            if (direction == ItemTouchHelper.LEFT) listener.onSwipeToLeft(position)
            if (direction == ItemTouchHelper.RIGHT) listener.onSwipeToRight(position)
        }
    }

    interface SwipeActions {
        fun onSwipeToLeft(position: Int)
        fun onSwipeToRight(position: Int)
    }

    init {
        icon =
            VectorDrawableCompat.create(context.resources, R.drawable.ic_delete_forever, null)
        this.adapter = adapter
        background = ColorDrawable(ContextCompat.getColor(context, R.color.swipeDelete))
    }
}