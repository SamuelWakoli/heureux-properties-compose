package com.heureux.admin.ui.presentation.screens.inquiries_screens.sell_with_us

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.twotone.ArrowBackIos
import androidx.compose.material.icons.twotone.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.heureux.admin.ui.presentation.composables.images.CoilImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SellWithUsImagesScreen(navController: NavController) {

    val pagerState = rememberPagerState(pageCount = { 4 /*TODO: Change this later*/ })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back"
                        )
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Outlined.Image, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "Images")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
    ) { paddingValues ->

        Column(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(state = pagerState) {
                    CoilImage(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .align(Alignment.Center),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (pagerState.currentPage != 0)
                        Card(
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                                /*TODO*/
                            },
                            colors = CardDefaults.cardColors(
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.ArrowBackIos,
                                contentDescription = "Previous Image",
                                modifier = Modifier.padding(8.dp)
                            )

                        } else Spacer(modifier = Modifier)
                    if (pagerState.currentPage != pagerState.pageCount - 1)
                        Card(
                            onClick = {
                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                                /*TODO*/
                            },
                            colors = CardDefaults.cardColors(
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                        ) {
                            Icon(
                                imageVector = Icons.TwoTone.ArrowForwardIos,
                                contentDescription = "Next Image",
                                modifier = Modifier.padding(8.dp)
                            )

                        } else Spacer(modifier = Modifier)
                }
            }
        }
    }
}