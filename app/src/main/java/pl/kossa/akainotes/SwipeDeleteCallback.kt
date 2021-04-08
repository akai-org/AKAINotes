package pl.kossa.akainotes

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class SwipeDeleteCallback(context: Context) : ItemTouchHelper.Callback() {

    private val deleteDrawable = ContextCompat.getDrawable(context, R.drawable.ic_delete)!!
    private val editDrawable = ContextCompat.getDrawable(context, R.drawable.ic_edit)!!
    private val background = ColorDrawable()
    private val deleteColor = ContextCompat.getColor(context, R.color.color_delete)
    private val editColor = ContextCompat.getColor(context, R.color.color_edit)
    private val clearPaint = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return makeMovementFlags(0, ItemTouchHelper.START.or(ItemTouchHelper.END))
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
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        val itemView = viewHolder.itemView
        val isCanceled = dX == 0f || !isCurrentlyActive
        if (isCanceled) {
            if (dX > 0) {
                clearCanvas(
                    c,
                    itemView.left.toFloat(),
                    itemView.top.toFloat(),
                    itemView.left + dX,
                    itemView.bottom.toFloat()
                )

            } else {
                clearCanvas(
                    c,
                    itemView.right + dX,
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),
                    itemView.bottom.toFloat()
                )
            }
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }
        if (dX > 0) {
            background.color = editColor
            background.setBounds(
                itemView.left,
                itemView.top,
                itemView.left + dX.toInt(),
                itemView.bottom
            )
            background.draw(c)

            val editTop = itemView.top + (itemView.height - editDrawable.intrinsicHeight) / 2
            val editMargin = (itemView.height - editDrawable.intrinsicHeight) / 2
            val editLeft = itemView.left + editMargin
            val editRight = itemView.left + editMargin + editDrawable.intrinsicWidth
            val editBottom = editTop + editDrawable.intrinsicHeight
            editDrawable.setBounds(editLeft, editTop, editRight, editBottom)
            editDrawable.draw(c)
        } else {
            background.color = deleteColor
            background.setBounds(
                itemView.right + dX.toInt(),
                itemView.top,
                itemView.right,
                itemView.bottom
            )
            background.draw(c)

            val deleteTop = itemView.top + (itemView.height - deleteDrawable.intrinsicHeight) / 2
            val deleteMargin = (itemView.height - deleteDrawable.intrinsicHeight) / 2
            val deleteLeft = itemView.right - deleteMargin - deleteDrawable.intrinsicWidth
            val deleteRight = itemView.right - deleteMargin
            val deleteBottom = deleteTop + deleteDrawable.intrinsicHeight
            deleteDrawable.setBounds(deleteLeft, deleteTop, deleteRight, deleteBottom)
            deleteDrawable.draw(c)
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

    }

    private fun clearCanvas(c: Canvas, left: Float, top: Float, right: Float, bottom: Float) {
        c.drawRect(left, top, right, bottom, clearPaint)
    }


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false


    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.7f
    }
}