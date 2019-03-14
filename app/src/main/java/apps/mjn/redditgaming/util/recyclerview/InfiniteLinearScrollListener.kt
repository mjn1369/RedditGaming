package apps.mjn.redditgaming.util.recyclerview

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfiniteLinearScrollListener(
    private val visibleThreshold: Int,
    private val layoutManager: LinearLayoutManager,
    private val onLoadMore: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var visibleItemCount = 0
    private var totalItemCount = 0
    private var firstVisibleItem = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            visibleItemCount = recyclerView.childCount
            totalItemCount = layoutManager.itemCount
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

            if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                onLoadMore()
            }
        }
    }
}