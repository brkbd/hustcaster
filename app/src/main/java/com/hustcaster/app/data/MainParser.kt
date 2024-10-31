package com.hustcaster.app.data

import android.icu.text.CaseMap.Title
import kotlinx.coroutines.delay
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

private const val ITEM = "item"
private const val TITLE = "title"
private const val LINK = "link"
private const val DESCRIPTION = "description"
private const val PUB_DATE = "pubDate"


object MainParser {
    private var state: FeedState = FeedState()
    private val factory = XmlPullParserFactory.newInstance()

    fun parse(xmlData: String) {
        try {
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            parser.setInput(StringReader(xmlData))
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (parser.namespace != null) {
                            FeedParserFactory.getParser(parser.namespace)?.parse(parser, state)
                        } else {
                            val nodeName = parser.name
                            when (nodeName) {
                                ITEM -> state.currentFeedItem = FeedItem()
                                TITLE -> if (state.currentFeedItem == null) {
                                    state.feed.title = parser.nextText()
                                } else {
                                    state.currentFeedItem?.title = parser.nextText()
                                }

                                LINK -> if (state.currentFeedItem == null) {
                                    state.feed.link = parser.nextText()
                                }

                                DESCRIPTION -> if (state.currentFeedItem == null) {
                                    state.feed.description = parser.nextText()
                                } else {
                                    state.currentFeedItem?.description = parser.nextText()
                                }

                                PUB_DATE -> if (state.currentFeedItem == null) {
                                    state.feed.pubDate = parser.nextText()
                                } else {
                                    state.currentFeedItem?.pubDate = parser.nextText()
                                }

                            }
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        if (parser.name == ITEM) {
                            state.currentFeedItem?.let { state.feed.items.add(it) }
                            state.currentFeedItem = null
                        }
                    }
                }
                eventType = parser.next()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}