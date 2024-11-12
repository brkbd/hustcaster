package com.hustcaster.app.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hustcaster.app.R
import com.hustcaster.app.data.model.Episode
import com.hustcaster.app.data.model.EpisodeAndRecord
import com.hustcaster.app.data.model.Record

@Preview
@Composable
fun RecordHomeList(
    modifier: Modifier = Modifier,
    records: List<EpisodeAndRecord> = listOf(
        EpisodeAndRecord(
            Record(episodeId = 1),
            Episode(podcastId = 1, title = "111", description = "111111111111111111111111")
        ),
        EpisodeAndRecord(
            Record(episodeId = 2),
            Episode(podcastId = 1, title = "222", description = "22222222222222222222222222222222")
        ),
        EpisodeAndRecord(
            Record(episodeId = 3),
            Episode(podcastId = 1, title = "333", description = "333")
        )
    ),
    onMoreClick: () -> Unit = {},
    onPlayClick: (Episode) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.record),
                    style = MaterialTheme.typography.titleMedium
                )
                Box(
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .clickable { onMoreClick() }
                ) {
                    Row {
                        Text(
                            text = stringResource(id = R.string.more_records),
                            style = MaterialTheme.typography.titleSmall,
                        )
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

            }
            if (records.size < 3) {
                records.forEach { record ->
                    RecordCard(
                        imageUrl = "",//should call a function in HomeViewModel
                        title = record.episode.title,
                        description = record.episode.description
                    ) {
                        onPlayClick(record.episode)
                    }
                }
            }else{
                RecordCard(
                    imageUrl = "",//should call a function in HomeViewModel
                    title = records[0].episode.title,
                    description = records[0].episode.description
                ) {
                    onPlayClick(records[0].episode)
                }
                RecordCard(
                    imageUrl = "",//should call a function in HomeViewModel
                    title = records[1].episode.title,
                    description = records[1].episode.description
                ) {
                    onPlayClick(records[1].episode)
                }
                RecordCard(
                    imageUrl = "",//should call a function in HomeViewModel
                    title = records[2].episode.title,
                    description = records[2].episode.description
                ) {
                    onPlayClick(records[2].episode)
                }
            }
        }
    }
}