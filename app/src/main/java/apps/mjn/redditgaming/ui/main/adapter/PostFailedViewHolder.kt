package apps.mjn.redditgaming.ui.main.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class PostFailedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(onTryAgainClick: ()->(Unit)) = with(itemView) {
        setOnClickListener {
            onTryAgainClick()
        }
    }
}