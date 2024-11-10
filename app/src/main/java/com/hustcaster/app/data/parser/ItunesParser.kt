package com.hustcaster.app.data.parser

import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.PodcastAndEpisodes
import org.xmlpull.v1.XmlPullParser

private const val IMAGE = "image"
private const val IMAGE_HREF = "href"
private const val AUTHOR = "author"
private const val DURATION = "duration"

class ItunesParser : FeedParser {
    override fun parse(
        xmlPullParser: XmlPullParser,
        state: PodcastAndEpisodes,
        currentItem: Episode?
    ) {
        val nodeName = xmlPullParser.name
        when (nodeName) {
            DURATION -> currentItem?.duration = xmlPullParser.nextText()
            AUTHOR -> if (currentItem == null) {
                state.podcast.author = xmlPullParser.nextText()
            }

            IMAGE -> if (currentItem == null) {
                state.podcast.imageUrl = xmlPullParser.getAttributeValue(null, IMAGE_HREF)
            }

        }
    }
}