package com.willyweather.assignment.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "repos")
data class Repo(
    @PrimaryKey
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("html_url")
    val url: String,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int,
    @Embedded(prefix = "license_")
    @SerializedName("license")
    val license: License?,
    @Embedded(prefix = "permissions_")
    @SerializedName("permissions")
    val permissions: Permissions?,
) : Parcelable {

    @Parcelize
    data class License(
        @SerializedName("key")
        val key: String,
        @SerializedName("name")
        val name: String,
        @SerializedName("url")
        val url: String?
    ) : Parcelable

    @Parcelize
    data class Permissions(
        @SerializedName("admin")
        val admin: Boolean,
        @SerializedName("push")
        val push: Boolean,
        @SerializedName("pull")
        val pull: Boolean,
    ) : Parcelable

}
