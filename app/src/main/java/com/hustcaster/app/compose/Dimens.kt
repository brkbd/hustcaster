package com.hustcaster.app.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.hustcaster.app.R

object Dimens {
    val DurationIconPaddingVertical:Dp
        @Composable get() = dimensionResource(id = R.dimen.duration_icon_padding_vertical)

    val DurationIconPaddingHorizontal:Dp
        @Composable get() = dimensionResource(id = R.dimen.duration_icon_padding_horizontal)

    val TitleAdditionalInfosPadding:Dp
        @Composable get() = dimensionResource(id = R.dimen.title_additional_infos_padding)

    val EpisodeSurfaceRoundedCornerShape:Dp
        @Composable get() = dimensionResource(id = R.dimen.episode_surface_rounded_corner_shape)

    val EpisodeSurfaceShadowElevation:Dp
        @Composable get()= dimensionResource(id = R.dimen.episode_surface_shadow_elevation)

    val EpisodePaddingHorizontal:Dp
        @Composable get() = dimensionResource(id = R.dimen.episode_padding_horizontal)

    val EpisodePaddingBetweenImageText:Dp
        @Composable get() = dimensionResource(id = R.dimen.episode_padding_between_image_text)
}