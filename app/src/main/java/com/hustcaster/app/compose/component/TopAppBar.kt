package com.hustcaster.app.compose.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun CustomizedTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "首页",
    actionIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = null,
            modifier = Modifier.padding(end = 10.dp)
        )
    },
    navigationIcon: @Composable () -> Unit = {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier.padding(start = 10.dp)
        )
    },
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    androidx.compose.material3.TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 10.dp)
            )
        },
        modifier = modifier,
        actions = {
            actionIcon()
        },
        navigationIcon = {
            navigationIcon()
        },
        scrollBehavior = scrollBehavior
    )
}