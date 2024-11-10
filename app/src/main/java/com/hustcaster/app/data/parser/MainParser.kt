package com.hustcaster.app.data.parser

import android.os.Build
import androidx.annotation.RequiresApi
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.Podcast
import com.hustcaster.app.data.model.PodcastAndEpisodes
import com.hustcaster.app.data.repository.EpisodeRepository
import com.hustcaster.app.data.repository.PodcastRepository
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
        episodeRepository: EpisodeRepository,
        podcastRepository: PodcastRepository
    ) {
        try {
            val state = PodcastAndEpisodes(Podcast(rssUrl))
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            val xmlData = fetchRssData(rssUrl)
            parser.setInput(StringReader(xmlData))
            var eventType = parser.eventType
            var currentItem: Episode? = null
            while (eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (parser.namespace.isNotEmpty()) {
                            FeedParserFactory.getParser(parser.namespace)
                                ?.parse(parser, state, currentItem)
                        } else {
                            val nodeName = parser.name
                            when (nodeName) {
                                ITEM -> currentItem = Episode(state.podcast.id)
                                TITLE -> if (currentItem == null) {
                                    state.podcast.title = parser.nextText()
                                } else {
                                    currentItem.title = parser.nextText()
                                }

                                LINK -> if (currentItem == null) {
                                    state.podcast.link = parser.nextText()
                                }

                                DESCRIPTION -> if (currentItem == null) {
                                    state.podcast.description = parser.nextText()
                                } else {
                                    currentItem.description = parser.nextText()
                                }

                                PUB_DATE -> if (currentItem == null) {
                                    state.podcast.pubDate =
                                        convertStringToCalendar(parser.nextText())
                                } else {
                                    currentItem.pubDate = convertStringToCalendar(parser.nextText())
                                    if (state.podcast.pubDate == null || state.podcast.pubDate!!.before(
                                            currentItem.pubDate
                                        )
                                    ) {
                                        state.podcast.pubDate = currentItem.pubDate
                                    }
                                }


                                ENCLOSURE -> currentItem?.audioUrl =
                                    parser.getAttributeValue(null, "url")

                            }
                        }
                    }

                    XmlPullParser.END_TAG -> {
                        if (parser.name == ITEM) {
                            currentItem?.let { episodeRepository.saveEpisode(it) }
                            currentItem = null
                        }
                    }
                }
                eventType = parser.next()
            }
            podcastRepository.savePodcast(state.podcast)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //need to pass updateRepository
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun checkUpdates(
        state: PodcastAndEpisodes,
        episodeRepository: EpisodeRepository,
        podcastRepository: PodcastRepository
    ) {
        try {
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            val data = fetchRssData(state.podcast.rssUrl)
            parser.setInput(StringReader(data))
            var currentItem: Episode? = null
            var eventType = parser.eventType
            val lastPubDate = state.podcast.pubDate
            var flag = true//decide whether to continue parsing or not
            while (flag && eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (parser.namespace.isNotEmpty()) {
                            FeedParserFactory.getParser(parser.namespace)
                                ?.parse(parser, state, currentItem)
                        } else {
                            val nodeName = parser.name
                            when (nodeName) {
                                ITEM -> currentItem = Episode(state.podcast.id)
                                TITLE -> if (currentItem == null) {
                                    state.podcast.title = parser.nextText()
                                } else {
                                    currentItem.title = parser.nextText()
                                }

                                LINK -> if (currentItem == null) {
                                    state.podcast.link = parser.nextText()
                                }

                                DESCRIPTION -> if (currentItem == null) {
                                    state.podcast.description = parser.nextText()
                                } else {
                                    currentItem.description = parser.nextText()
                                }

                                PUB_DATE -> if (currentItem == null) {
                                    //check updates
                                    val pubDate = convertStringToCalendar(parser.nextText())
                                    if (pubDate?.after(lastPubDate) == false) {
                                        flag = false
                                    } else {
                                        state.podcast.pubDate = pubDate
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
                            currentItem?.isUpdated = true
                            currentItem?.let { episodeRepository.saveEpisode(it) }//add item
                            currentItem = null
                        }
                    }
                }
                eventType = parser.next()
            }
            podcastRepository.updatePodcast(state.podcast)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}