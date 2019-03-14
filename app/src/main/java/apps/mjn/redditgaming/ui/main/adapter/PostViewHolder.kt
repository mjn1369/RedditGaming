package apps.mjn.redditgaming.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import apps.mjn.redditgaming.ui.model.entity.RedditPostItem
import kotlinx.android.synthetic.main.item_post.view.*

class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(item: RedditPostItem, onClick: (RedditPostItem)->(Unit)) = with(itemView) {
        tvPostItemTitle.text = item.title
        tvPostItemTitle.isSelected = true
        tvPostItemScore.text = "${item.score}"
        tvPostItemSubReddit.text = item.subReddit
        setOnClickListener{
            onClick(item)
        }
    }
}