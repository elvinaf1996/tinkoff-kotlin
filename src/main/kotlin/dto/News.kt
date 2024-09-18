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
    val place: String,
    val description: String,
    val siteUrl: String,
    @SerializedName("favorites_count")
    val favoritesCount: Int,
    @SerializedName("comments_count")
    val commentsCount: Int,
    val date: String,
    @SerializedName("publication_date")
    val publicationDate: Long
) {
    val publicationLocalDate: LocalDate?
        get() {
            return publicationDate.let {
                try {
                    // Преобразуем временную метку в LocalDate
                    Instant.ofEpochSecond(it).atZone(ZoneId.systemDefault()).toLocalDate()
                } catch (e: NumberFormatException) {
                    // Обработка ошибки, если строка не может быть преобразована в Long
                    null
                }
            }
        }
    val rating: Double
        get() = 1 / (1 + exp(-(favoritesCount / (commentsCount + 1).toDouble())))
}