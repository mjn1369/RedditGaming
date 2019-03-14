package apps.mjn.redditgaming.ui.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RedditPostListItem (
    val data: RedditPostListDataItem?
): Parcelable

@Parcelize
data class RedditPostListDataItem (
    val childCount: Int?,
    val posts: ArrayList<RedditPostContainerItem>,
    val nextPageTag: String?
): Parcelable