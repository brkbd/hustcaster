package com.hustcaster.app.network.parser

import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.PodcastAndEpisodes
import org.xmlpull.v1.XmlPullParser

interface FeedParser {
    fun parse(xmlPullParser: XmlPullParser, state: PodcastAndEpisodes, currentItem: Episode?);
}