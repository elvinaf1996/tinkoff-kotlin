package org.example.dto

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import kotlin.math.exp

@Serializable
data class News(
    val id: Long,
    val title: String,
    val place: Place?,
    val description: String,
    @SerializedName("site_url")
    val siteUrl: String,
    @SerializedName("favorites_count")
    val favoritesCount: Int,
    @SerializedName("comments_count")
    val commentsCount: Int,
    val slug: String,
    @SerializedName("publication_date")
    val publicationDate: Long
) {
    val publicationLocalDate: LocalDate?
        get() {
            return publicationDate.let {
                try {
                    Instant.ofEpochSecond(it).atZone(ZoneId.systemDefault()).toLocalDate()
                } catch (e: NumberFormatException) {
                    null
                }
            }
        }
    val rating: Double
        get() = 1 / (1 + exp(-(favoritesCount / (commentsCount + 1).toDouble())))
}

@Serializable
data class Place(
    val id: Long
)