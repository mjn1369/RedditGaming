package apps.mjn.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RedditPostContainerItem (
    val data: RedditPostItem?
): Parcelable

@Parcelize
data class RedditPostItem (
    val title: String?,
    val score: Long?,
    val subReddit: String?,
    val permanentLink: String?
): Parcelable