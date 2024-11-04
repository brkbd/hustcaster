package com.hustcaster.app.data

import android.os.Build
import android.util.Log
import android.util.Xml
import androidx.annotation.RequiresApi
import com.hustcaster.app.network.fetchRssData
import com.hustcaster.app.utils.convertStringToCalendar
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

    private val factory = XmlPullParserFactory.newInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun parse(
        rssUrl: String,
        feedItemRepository: FeedItemRepository,
        feedRepository: FeedRepository
    ) {
        try {
            val state = FeedAndFeedItems(Feed(rssUrl))
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
                                    state.feed.pubDate = convertStringToCalendar(parser.nextText())
                                } else {
                                    currentItem.pubDate = convertStringToCalendar(parser.nextText())
                                    if (state.feed.pubDate == null || state.feed.pubDate!!.before(
                                            currentItem.pubDate
                                        )
                                    ) {
                                        state.feed.pubDate = currentItem.pubDate
                                    }
                                }


                                ENCLOSURE -> currentItem?.audioUrl =
                                    parser.getAttributeValue(null, "url")

                            }
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        if (parser.name == ITEM) {
                            currentItem?.let { feedItemRepository.saveFeedItem(it) }
                            currentItem = null
                        }
                    }
                }
                eventType = parser.next()
            }
            feedRepository.saveFeed(state.feed)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun checkUpdates(
        state: FeedAndFeedItems,
        feedItemRepository: FeedItemRepository,
        feedRepository: FeedRepository
    ) {
        try {
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            val data = fetchRssData(state.feed.rssUrl)
            parser.setInput(StringReader(data))
            var currentItem: FeedItem? = null
            var eventType = parser.eventType
            val lastPubDate = state.feed.pubDate
            var flag = true//decide whether to continue parsing or not
            while (flag && eventType != XmlPullParser.END_DOCUMENT) {
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
                                    //check updates
                                    val pubDate = convertStringToCalendar(parser.nextText())
                                    if (pubDate?.after(lastPubDate) == false) {
                                        flag = false
                                    } else {
                                        state.feed.pubDate = pubDate
                                    }
                                } else {
                                    currentItem.pubDate = convertStringToCalendar(parser.nextText())
                                    if (currentItem.pubDate?.after(lastPubDate) == false) {
                                        flag = false
                                    }
                                }

                                ENCLOSURE -> currentItem?.audioUrl =
                                    parser.getAttributeValue(null, "url")

                            }
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        if (parser.name == ITEM) {
                            currentItem?.let { feedItemRepository.saveFeedItem(it) }//add item
                            currentItem = null
                        }
                    }
                }
                eventType = parser.next()
            }
            feedRepository.updateFeed(state.feed)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}