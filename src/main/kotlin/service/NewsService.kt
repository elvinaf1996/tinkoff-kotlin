package org.example.steps

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.gson.*
import org.example.dto.News
import org.example.dto.NewsResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDate

class NewsService {
    private val logger: Logger = LoggerFactory.getLogger(NewsService::class.java)

    suspend fun getNews(count: Int = 100): List<News> {
        logger.info("Запрос новостей с количеством: $count")

        val client = HttpClient(CIO) {
            install(ContentNegotiation) {
                gson()
            }
        }

        val httpResponse: HttpResponse = client.get("https://kudago.com/public-api/v1.4/news/") {
            parameter("page", 1)
            parameter("page_size", count)
            parameter("order_by", "-date_posted")
            parameter("location", "kzn")
            parameter("actual_only", true)
            parameter("fields", "id,title,slug,publication_date,description,favorites_count,comments_count,place,description,site_url")
        }

        if (!httpResponse.status.isSuccess()) {
            logger.error("Не удалось получить новости: ${httpResponse.status}")
            throw RuntimeException("Что-то пошло не так: ${httpResponse.status}")
        }

        val body = httpResponse.body<NewsResponse>()
        logger.info("Успешно получено ${body.results.size} новостей.")

        return body.results
    }

    fun saveNews(path: String, news: List<News>) {
        logger.info("Сохранение новостей по пути: $path")
        val file = File(path)

        if (file.exists()) {
            logger.error("Файл уже существует по пути: $path")
            throw IOException("Файл уже существует по пути: $path")
        }

        file.parentFile?.mkdirs()

        FileWriter(file).use { writer ->
            writer.append("id,rating,title,publicationLocalDate,place,description,siteUrl,favoritesCount,commentsCount\n")

            for (newsItem in news) {
                writer.append("${newsItem.id},${newsItem.rating},${newsItem.publicationLocalDate},${newsItem.title},${newsItem.place},${newsItem.description},${newsItem.siteUrl},${newsItem.favoritesCount},${newsItem.commentsCount}\n")
            }
        }

        logger.info("Успешно сохранено ${news.size} новостей в $path")
    }
}

fun List<News>.getMostRatedNews(count: Int, period: ClosedRange<LocalDate>): List<News> {
    return this.filter { news ->
        val publicationLocalDate = news.publicationLocalDate
        publicationLocalDate != null && publicationLocalDate in period
    }.sortedByDescending { it.rating }
        .take(count)
}

