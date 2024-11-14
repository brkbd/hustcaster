package com.hustcaster.app.compose.common

object NavigationGraph {
    const val HOME = "home"
    const val SUBSCRIPTION = "subscription"
    const val RSS = "rss"
    const val RECORD = "record"
    const val PODCAST_LIST = "podcast_list"
    const val PODCAST = "podcast"
    const val PODCAST_INFO = "podcast_info"
    const val LISTEN = "listen"

    fun String.withArgument(argumentName: String): String = "$this/{$argumentName}"

    fun String.toId(id: Long): String = "$this/$id"

    const val EPISODE_ID = "episode_id"
    const val PODCAST_ID = "podcast_id"
}