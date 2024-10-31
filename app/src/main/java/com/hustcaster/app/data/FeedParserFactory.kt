package com.hustcaster.app.data

object FeedParserFactory {
    private const val ITUNES = "http://www.itunes.com/dtds/podcast-1.0.dtd"
    private val parsers = mapOf(
        ITUNES to ItunesParser()
    )

    fun getParser(namespace: String): ItunesParser? = parsers[namespace]
}