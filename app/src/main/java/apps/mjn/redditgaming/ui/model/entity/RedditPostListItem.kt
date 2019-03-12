package apps.mjn.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RedditPostListItem (
    val data: RedditPostListDataItem
): Parcelable

@Parcelize
data class RedditPostListDataItem (
    val childCount: Int?,
    val posts: List<RedditPostContainerItem>,
    val nextPageTag: String?
): Parcelable