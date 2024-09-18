package org.example

import org.example.steps.NewsService
import org.example.steps.getMostRatedNews
import java.io.IOException
import java.time.LocalDate

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
suspend fun main() {
    val newsService = NewsService();

    val news = newsService.getNews()

    val startDate = LocalDate.of(2024, 1, 1)
    val endDate = LocalDate.of(2024, 9, 1)

    val period: ClosedRange<LocalDate> = startDate..endDate
    val mostRatedNews = news.getMostRatedNews(5, period)

    val path = "src/main/resources/news.csv"

    try {
        newsService.saveNews(path, mostRatedNews)
        println("Новости успешно сохранены в файл: $path")
    } catch (e: IOException) {
        println("Ошибка при сохранении новостей: ${e.message}")
    }

//    mostRatedNews.forEach { println(it) }

//    newsPrinter {
//        mostRatedNews
//    }

//    val newsItem: News = dsl.news {
//        id = 1
//        title = "Kotlin DSLs are Awesome!"
//        place = "Online"
//        description = "Learn how to create DSLs in Kotlin."
//        siteUrl = "https://example.com/kotlin-dsl"
//        favoritesCount = 100
//        commentsCount = 20
//        date = "2023-10-01"
//        publicationDate = 1696123200 // Example timestamp
//    }
//
//    println(newsItem)
}