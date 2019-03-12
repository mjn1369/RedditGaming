package apps.mjn.redditgaming.ui.main.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class VerticalSpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) < parent.childCount - 1) {
                bottom = space
            }
        }
    }
}