package com.hustcaster.app.data

object FeedParserFactory {
    private val parsers = mapOf(
        "http://www.itunes.com/dtds/podcast-1.0.dtd" to ItunesParser()
    )

    fun getParser(namespace: String): ItunesParser? = parsers[namespace]
}