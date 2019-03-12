package apps.mjn.redditgaming.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import apps.mjn.domain.entity.RedditPostItem
import apps.mjn.redditgaming.R

class PostAdapter(private var postItems: ArrayList<RedditPostItem>) : RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) =
        holder.bind(postItems[position])

    fun addItems(items: List<RedditPostItem>){
        postItems.addAll(items)
        notifyItemRangeInserted(itemCount - items.size, itemCount)
    }

    override fun getItemCount() = postItems.size
}