package com.hustcaster.app.data

import android.util.Log
import android.util.Xml
import com.hustcaster.app.network.fetchRssData
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader

private const val ITEM = "item"
private const val TITLE = "title"
private const val LINK = "link"
private const val DESCRIPTION = "description"
private const val PUB_DATE = "pubDate"
private const val ENCLOSURE = "enclosure"

object MainParser {
    private lateinit var state: FeedAndFeedItems
    private val factory = XmlPullParserFactory.newInstance()

    fun parse(rssUrl: String) {
        try {
            state = FeedAndFeedItems(Feed(rssUrl))
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            val xmlData = fetchRssData(rssUrl)
            parser.setInput(StringReader(xmlData))
            var eventType = parser.eventType
            var currentItem: FeedItem? = null
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (parser.namespace.isNotEmpty()) {
                            FeedParserFactory.getParser(parser.namespace)?.parse(parser, state)
                        } else {
                            val nodeName = parser.name
                            when (nodeName) {
                                ITEM -> currentItem = FeedItem(state.feed.id)
                                TITLE -> if (currentItem == null) {
                                    state.feed.title = parser.nextText()
                                } else {
                                    currentItem.title = parser.nextText()
                                }

                                LINK -> if (currentItem == null) {
                                    state.feed.link = parser.nextText()
                                }

                                DESCRIPTION -> if (currentItem == null) {
                                    state.feed.description = parser.nextText()
                                } else {
                                    currentItem.description = parser.nextText()
                                }

                                PUB_DATE -> if (currentItem == null) {
                                    state.feed.pubDate = parser.nextText()
                                } else {
                                    currentItem.pubDate = parser.nextText()
                                }


                                ENCLOSURE -> currentItem?.audioUrl =
                                    parser.getAttributeValue(null, "url")

                            }
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        if (parser.name == ITEM) {
                            currentItem?.let { state.items.add(it) }
                            currentItem = null
                        }
                    }
                }
                eventType = parser.next()
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun checkUpdates(url: String) {
        try {
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            val data = fetchRssData(url)
            parser.setInput(StringReader(data))
            var currentItem: FeedItem? = null
            var eventType = parser.eventType
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (parser.namespace.isNotEmpty()) {
                            FeedParserFactory.getParser(parser.namespace)?.parse(parser, state)
                        } else {
                            val nodeName = parser.name
                            when (nodeName) {
                                ITEM -> currentItem = FeedItem(state.feed.id)
                                TITLE -> if (currentItem == null) {
                                    state.feed.title = parser.nextText()
                                } else {
                                    currentItem.title = parser.nextText()
                                }

                                LINK -> if (currentItem == null) {
                                    state.feed.link = parser.nextText()
                                }

                                DESCRIPTION -> if (currentItem == null) {
                                    state.feed.description = parser.nextText()
                                } else {
                                    currentItem.description = parser.nextText()
                                }

                                PUB_DATE -> if (currentItem == null) {
                                    //check updates logic
                                    state.feed.pubDate = parser.nextText()
                                } else {
                                    currentItem.pubDate = parser.nextText()
                                }


                                ENCLOSURE -> currentItem?.audioUrl =
                                    parser.getAttributeValue(null, "url")

                            }
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