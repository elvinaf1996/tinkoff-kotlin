
import org.example.dto.News
import org.example.dto.Place

@DslMarker
annotation class NewsDslMarker

@NewsDslMarker
class NewsBuilder {
    var id: Long = 0
    var title: String = ""
    var place: PlaceBuilder? = null
    var description: String = ""
    var siteUrl: String = ""
    var favoritesCount: Int = 0
    var commentsCount: Int = 0
    var slug: String = ""
    var publicationDate: Long = 0

    fun place(init: PlaceBuilder.() -> Unit) {
        place = PlaceBuilder().apply(init)
    }

    override fun toString(): String {
        return buildString {
            append("News:\n")
            append("  id: $id\n")
            append("  title: \"$title\"\n")
            place?.let {
                append("  place: $it\n")
            }
            append("  description: \"$description\"\n")
            append("  siteUrl: \"$siteUrl\"\n")
            append("  favoritesCount: $favoritesCount\n")
            append("  commentsCount: $commentsCount\n")
            append("  slug: \"$slug\"\n")
            append("  publicationDate: $publicationDate\n")
        }
    }

    fun build(): News {
        return News(id, title, place?.build() ,description,siteUrl, favoritesCount,commentsCount,slug,publicationDate)
    }

}

@NewsDslMarker
class PlaceBuilder {
    var id: Long = 0

    override fun toString(): String {
        return buildString {
            append("{  id: $id }")
        }
    }

    fun build(): Place {
        return Place(id)
    }
}

fun news(init: NewsBuilder.() -> Unit): NewsBuilder {
    return NewsBuilder().apply(init)
}
