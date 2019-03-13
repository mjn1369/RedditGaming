package apps.mjn.redditgaming.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import apps.mjn.domain.entity.RedditPostItem
import apps.mjn.redditgaming.R

class PostAdapter(
    private var postItems: ArrayList<RedditPostItem?>,
    private val onItemClick: (RedditPostItem) -> (Unit),
    private val onTryAgainClick: () -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading = false
    private var isFailed = false
    private val typeLoading = 0
    private val typePost = 1
    private val typeFailed = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == typeLoading) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_loading, parent, false)
            return PostLoadingViewHolder(view)
        }
        if (viewType == typeFailed) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_failed, parent, false)
            return PostFailedViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is PostViewHolder -> holder.bind(postItems[position]!!, onItemClick)
            is PostFailedViewHolder -> holder.bind(onTryAgainClick)
        }
    }

    fun addItems(items: List<RedditPostItem>?) {
        items?.let {
            postItems.addAll(items)
            notifyItemRangeInserted(itemCount - items.size, itemCount)
        }
    }

    fun showLoading() {
        isLoading = true
        postItems.add(null)
        notifyItemInserted(itemCount - 1)
    }

    fun hideLoading(){
        isLoading = false
        postItems.removeAt(itemCount - 1)
        notifyItemRemoved(itemCount)
    }

    fun showFailed() {
        isFailed = true
        postItems.add(null)
        notifyItemInserted(itemCount - 1)
    }

    fun hideFailed(){
        isFailed = false
        postItems.removeAt(itemCount - 1)
        notifyItemRemoved(itemCount)
    }

    override fun getItemCount() = postItems.size

    override fun getItemViewType(position: Int): Int {
        if (position == postItems.size - 1) {
            if(isLoading) {
                return typeLoading
            }
            if(isFailed){
                return typeFailed
            }
            return typePost
        } else {
            return typePost
        }
    }
}