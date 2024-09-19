package org.example.dsl

import NewsBuilder
import org.example.dto.News

class NewsCollection {

    private val newsList = arrayListOf<NewsBuilder>()

    operator fun NewsBuilder.unaryPlus() {
        newsList += this
    }

    override fun toString(): String {
        val builder = StringBuilder()
        for (news in newsList) {
            builder.append(news.toString())
        }
        return builder.toString()
    }

    fun build(): List<News> {
        val list = arrayListOf<News>()
        for (news in newsList) {
            list += news.build()
        }
        return list
    }

    fun newsList(function: NewsCollection.() -> Unit): NewsCollection {
        this.function()
        return this
    }

    fun news(function: NewsBuilder.() -> Unit) {
        val newsBuilder = NewsBuilder()
        newsBuilder.function()
        newsList += newsBuilder
    }
}
