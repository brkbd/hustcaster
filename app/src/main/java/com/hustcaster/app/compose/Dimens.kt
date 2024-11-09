package com.hustcaster.app.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.hustcaster.app.R
import kotlin.time.Duration

object Dimens {
    val DurationIconPaddingVertical:Dp
        @Composable get() = dimensionResource(id = R.dimen.duration_icon_padding_vertical)

    val DurationIconPaddingHorizontal:Dp
        @Composable get() = dimensionResource(id = R.dimen.duration_icon_padding_horizontal)

    val TitleAdditionalInfosPadding:Dp
        @Composable get() = dimensionResource(id = R.dimen.title_additional_infos_padding)

    val FeedItemSurfaceRoundedCornerShape:Dp
        @Composable get() = dimensionResource(id = R.dimen.feed_item_surface_rounded_corner_shape)

    val FeedItemSurfaceShadowElevation:Dp
        @Composable get()= dimensionResource(id = R.dimen.feed_item_surface_shadow_elevation)

    val FeedItemPaddingHorizontal:Dp
        @Composable get() = dimensionResource(id = R.dimen.feed_item_padding_horizontal)

    val FeedItemPaddingBetweenImageText:Dp
        @Composable get() = dimensionResource(id = R.dimen.feed_item_padding_between_image_text)
}