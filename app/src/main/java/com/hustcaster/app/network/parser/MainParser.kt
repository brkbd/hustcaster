package com.hustcaster.app.network.parser

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.Podcast
import com.hustcaster.app.data.repository.EpisodeRepository
import com.hustcaster.app.data.repository.PodcastRepository
import com.hustcaster.app.network.fetchRssData
import com.hustcaster.app.utils.convertStringToCalendar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.StringReader
import java.util.Calendar

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
    ): ParseResult {
        return withContext(Dispatchers.IO) {
            try {
                val podcast = Podcast(rssUrl)
                factory.isNamespaceAware = true
                val parser = factory.newPullParser()
                Log.d("debug", rssUrl)
                val xmlData = fetchRssData(rssUrl)
                if (xmlData == null) {
                    ParseResult.FAILURE
                }
                parser.setInput(StringReader(xmlData))
                var eventType = parser.eventType
                var currentItem: Episode? = null
                val episodes = mutableListOf<Episode>()
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    when (eventType) {
                        XmlPullParser.START_TAG -> {
                            if (parser.namespace.isNotEmpty()) {
                                FeedParserFactory.getParser(parser.namespace)
                                    ?.parse(parser, podcast, currentItem)
                            } else {
                                val nodeName = parser.name

                                when (nodeName) {
                                    ITEM -> currentItem = Episode()
                                    TITLE -> if (currentItem == null) {
                                        podcast.title = parser.nextText()
                                    } else {
                                        currentItem.title = parser.nextText()
                                    }

                                    LINK -> if (currentItem == null) {
                                        podcast.link = parser.nextText()
                                    }

                                    DESCRIPTION -> if (currentItem == null) {
                                        podcast.description = parser.nextText()
                                    } else {
                                        currentItem.description = parser.nextText()
                                    }

                                    PUB_DATE -> if (currentItem == null) {
                                        podcast.pubDate =
                                            convertStringToCalendar(parser.nextText())
                                    } else {
                                        currentItem.pubDate =
                                            convertStringToCalendar(parser.nextText())
                                        if (podcast.pubDate == null || podcast.pubDate!!.before(
                                                currentItem.pubDate
                                            )
                                        ) {
                                            podcast.pubDate = currentItem.pubDate
                                        }
                                    }


                                    ENCLOSURE -> currentItem?.audioUrl =
                                        parser.getAttributeValue(null, "url")

                                }
                            }
                        }

                        XmlPullParser.END_TAG -> {
                            if (parser.name == ITEM) {
                                currentItem?.let { episodes.add(it.copy()) }
                                currentItem = null
                            }
                        }
                    }
                    eventType = parser.next()
                }
                val date=Calendar.getInstance()
                date.set(2024,Calendar.OCTOBER,30)
                podcast.pubDate= date
                podcastRepository.savePodcast(podcast)
                val podcastId = podcastRepository.getPodcastIdByRssUrl(rssUrl)
                episodes.forEach {
                    it.podcastId = podcastId
                    if(it.pubDate?.before(date) == true){
                        episodeRepository.saveEpisode(it)
                    }
                }
                ParseResult.SUCCESS

            } catch (e: Exception) {
                ParseResult.FAILURE
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun checkUpdates(
        podcast: Podcast,
        episodeRepository: EpisodeRepository,
        podcastRepository: PodcastRepository
    ) {
        try {
            factory.isNamespaceAware = true
            val parser = factory.newPullParser()
            val data = fetchRssData(podcast.rssUrl)
            parser.setInput(StringReader(data))
            var currentItem: Episode? = null
            var eventType = parser.eventType
            val lastPubDate = podcast.pubDate
            var flag = true
            val episodes = mutableListOf<Episode>()

            while (flag && eventType != XmlPullParser.END_DOCUMENT) {
                when (eventType) {
                    XmlPullParser.START_TAG -> {
                        if (parser.namespace.isNotEmpty()) {
                            FeedParserFactory.getParser(parser.namespace)
                                ?.parse(parser, podcast, currentItem)
                        } else {
                            val nodeName = parser.name
                            when (nodeName) {
                                ITEM -> currentItem = Episode(podcast.id)
                                TITLE -> if (currentItem == null) {
                                    podcast.title = parser.nextText()
                                } else {
                                    currentItem.title = parser.nextText()
                                }

                                LINK -> if (currentItem == null) {
                                    podcast.link = parser.nextText()
                                }

                                DESCRIPTION -> if (currentItem == null) {
                                    podcast.description = parser.nextText()
                                } else {
                                    currentItem.description = parser.nextText()
                                }

                                PUB_DATE -> if (currentItem == null) {
                                    //check updates
                                    val pubDate = convertStringToCalendar(parser.nextText())
                                    if (pubDate?.after(lastPubDate) == false) {
                                        flag = false
                                    } else {
                                        podcast.pubDate = pubDate
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
                            currentItem?.let { episodes.add(it.copy()) }
                            currentItem = null
                        }
                    }
                }
                eventType = parser.next()
            }
            podcastRepository.updatePodcast(podcast)
            Log.d("UpdateWorker","here")
            val podcastId = podcast.id
            episodes.forEach {
                it.podcastId = podcastId
                episodeRepository.saveEpisode(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

enum class ParseResult {
    SUCCESS,
    FAILURE
}