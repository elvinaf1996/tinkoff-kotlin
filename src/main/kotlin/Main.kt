package org.example

import news
import org.example.dsl.NewsCollection
import org.example.steps.NewsService
import org.example.steps.getMostRatedNews
import java.io.IOException
import java.time.LocalDate

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

    val article = news {
        id = 51430
        title = "Индекс счастья россиян упал до минимальных показателей с 2014 года"
        place {
            id = 123
        }
        description = "<p>Уровень медленно снижался в течение лета.</p>"
        siteUrl = "https://kudago.com/all/news/indeks-schastya-upal-do/"
        favoritesCount = 1
        commentsCount = 2
        slug = "indeks-schastya-upal-do"
        publicationDate = 1726670429
    }

    println(article)

    val newsList = NewsCollection().newsList {
        news {
            id = 51429
            title = "Появился первый трейлер комедии «Братья» с Питером Динклейджем и Джошем Бролином"
            place {
                id = 101
            }
            description = "<p>Её выход намечен на начало октября.</p>"
            siteUrl = "https://kudago.com/all/news/trejler-komedii-bratya/"
            commentsCount = 1
            slug = "trejler-komedii-bratya"
            publicationDate = 1726670316
        }
        news {
            id = 51428
            title = "Вышел трейлер фантастического фильма «Микки 17» с Робертом Паттинсоном"
            description = "<p>Премьера состоится в начале следующего года.</p>"
            siteUrl = "https://kudago.com/all/news/trejler-mikki-17-s-robertom-pattinsonom/"
            favoritesCount = 1
            slug = "trejler-mikki-17-s-robertom-pattinsonom"
            publicationDate = 1726670191
        }
    }
    println(newsList)
}