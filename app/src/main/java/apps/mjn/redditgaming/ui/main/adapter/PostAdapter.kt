package apps.mjn.redditgaming.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import apps.mjn.domain.entity.RedditPostItem
import apps.mjn.redditgaming.R

class PostAdapter(
    private var postItems: ArrayList<RedditPostItem?>,
    private val onItemClick: (RedditPostItem) -> (Unit)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var isLoading = false
    private val typeLoading = 0
    private val typePost = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == typeLoading) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_loading, parent, false)
            return PostLoadingViewHolder(view)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is PostViewHolder){
            holder.bind(postItems[position]!!, onItemClick)
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

    override fun getItemCount() = postItems.size

    override fun getItemViewType(position: Int): Int {
        return if (position == postItems.size - 1 && isLoading) {
            typeLoading
        } else {
            typePost
        }
    }
}