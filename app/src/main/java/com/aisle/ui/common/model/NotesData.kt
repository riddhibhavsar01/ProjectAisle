package com.aisle.ui.common.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@JsonClass(generateAdapter = true)
data class NotesData(
    @Json(name = "invites") var invites: Invites? = Invites(),
    @Json(name = "likes") var likes: Likes? = Likes()

)


@JsonClass(generateAdapter = true)
data class Invites(
    @Json(name = "profiles") var profiles: List<Profiles>? = null,
    @Json(name = "totalPages") var totalPages: Int? = null,
    @Json(name = "pending_invitations_count") var pendingInvitationsCount: Int? = null

)

@Parcelize
@JsonClass(generateAdapter = true)
data class Profile(

    @Json(name = "first_name") var firstName: String?,
    @Json(name = "avatar") var avatar: String? = null

) : Parcelable

@JsonClass(generateAdapter = true)
data class Likes(

    @Json(name = "profiles") var profiles: List<Profile>? = null,
    @Json(name = "can_see_profile") var canSeeProfile: Boolean? = null,
    @Json(name = "likes_received_count") var likesReceivedCount: Int? = null

)

@Parcelize
@JsonClass(generateAdapter = true)
data class Profiles(
    @Json(name = "photos") var photos: List<Photos>? = null,
    @Json(name = "general_information") var generalInformation: GeneralInformation? = null,
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class Photos(

    @Json(name = "photo") var photo: String? = null,
    @Json(name = "photo_id") var photoId: Int? = null,
    @Json(name = "selected") var selected: Boolean? = null,
    @Json(name = "status") var status: String? = null

) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class GeneralInformation(
    @Json(name = "first_name") var firstName: String? = null,

) : Parcelable