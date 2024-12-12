package com.hustcaster.app.compose.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.hustcaster.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizedTopAppBar(
    title: String,
    navigationIcon: ImageVector?,
    onNavigationIconClick: () -> Unit,
    actionIcon: ImageVector?,
    onActionIconClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.top_app_bar_title_padding_start))
            )
        },
        actions = {
            if (actionIcon != null) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onActionIconClick() }
                        .padding(end = dimensionResource(id = R.dimen.top_app_bar_icon_padding_horizontal))
                )
            }
        },
        navigationIcon = {
            if (navigationIcon != null) {
                Icon(imageVector = navigationIcon, contentDescription = null,
                    modifier = Modifier
                        .clickable { onNavigationIconClick() }
                        .padding(start = dimensionResource(id = R.dimen.top_app_bar_icon_padding_horizontal))
                        .size(30.dp)
                )
            }

        },
        scrollBehavior = scrollBehavior
    )
}