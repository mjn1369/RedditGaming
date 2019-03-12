package apps.mjn.redditgaming.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import apps.mjn.domain.entity.RedditPostItem
import kotlinx.android.synthetic.main.item_post.view.*

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: RedditPostItem) = with(itemView) {
        tvPostItemTitle.text = item.title
        tvPostItemTitle.isSelected = true
        tvPostItemScore.text = "${item.score}"
        tvPostItemSubReddit.text = item.subReddit
    }
}