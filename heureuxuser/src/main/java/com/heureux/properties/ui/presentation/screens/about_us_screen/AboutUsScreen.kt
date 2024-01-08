package com.heureux.properties.ui.presentation.screens.about_us_screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Web
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.heureux.properties.ui.presentation.screens.contact_us_screen.ContactListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutUsScreen(navController: NavController) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                scrollBehavior = scrollBehavior,
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Navigate back",
                        )
                    }
                },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Outlined.Info, contentDescription = null)
                        Spacer(modifier = Modifier.size(8.dp))
                        Text(text = "About us")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = 600.dp)
                    .padding(horizontal = 8.dp)
            ) {
                ListItem(
                    headlineContent = {
                        Text(text = "Buy, sell, rent or lease your Land or Plot with Heureux Properties.")
                    },
                    supportingContent = {
                        Text(text = "Our prices are not exaggerated and locations depend on where you want, ready for sale")
                    },
                    colors = ListItemDefaults.colors(
                        headlineColor = MaterialTheme.colorScheme.primary
                    ),
                )
                ListItem(
                    headlineContent = {
                        Text(text = "Who we are")
                    },
                    supportingContent = {
                        Text(text = "We are a real estate company that assists in the acquisition of good and well priced land/property")
                    },
                    colors = ListItemDefaults.colors(
                        headlineColor = MaterialTheme.colorScheme.primary
                    ),
                )
                ListItem(
                    headlineContent = {
                        Text(text = "Why are we unique?")
                    },
                    supportingContent = {
                        Text(text = "We have a track record of pleasing our clients by providing land/property that suites their wants at a good and favourable prices comparable to the current market")
                    },
                    colors = ListItemDefaults.colors(
                        headlineColor = MaterialTheme.colorScheme.primary
                    ),
                )
                ListItem(
                    headlineContent = {
                        Text(text = "Our Goal")
                    },
                    supportingContent = {
                        Text(text = "In the current real estate market, it's hard to find a company that focuses on their clients' needs. We are focusing on making our clients happy by providing property that suites their needs at a good and favourable prices.")
                    },
                    colors = ListItemDefaults.colors(
                        headlineColor = MaterialTheme.colorScheme.primary
                    ),
                )
                Spacer(modifier = Modifier.size(8.dp))
                Divider()
                Spacer(modifier = Modifier.size(8.dp))
                ContactListItem(
                    title = "Email",
                    description = "info@heureuxproperties.co.ke",
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Email, contentDescription = null)
                    },
                    onClick = {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:info@heureuxproperties.co.ke")
                        }
                        try {
                            context.startActivity(emailIntent)
                        } catch (e: ActivityNotFoundException) {
                            // Handle error if no email app is found
                            Toast.makeText(context, "No email app found", Toast.LENGTH_SHORT).show()
                        }
                    },
                )
                ContactListItem(
                    title = "Website",
                    description = "www.heureuxproperties.co.ke",
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Web, contentDescription = null)
                    },
                    onClick = {
                        val emailIntent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                            data = Uri.parse("https://www.heureuxproperties.co.ke")
                        }
                        try {
                            context.startActivity(emailIntent)
                        } catch (e: ActivityNotFoundException) {
                            // Handle error if no email app is found
                            Toast.makeText(context, "No web app found", Toast.LENGTH_SHORT).show()
                        }
                    },
                )
                ContactListItem(
                    title = "Visit our office",
                    description = "Jetro Chambers(4th Floor), Westlands, Nairobi",
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = null)
                    },
                    onClick = {
                        val emailIntent = Intent(Intent.ACTION_VIEW)
                        emailIntent.data = Uri.parse("geo:-1.2650562,36.802304?q=Jetro+Chambers")
                        try {
                            context.startActivity(emailIntent)
                        } catch (e: ActivityNotFoundException) {
                            // Handle error if no email app is found
                            Toast.makeText(context, "Google Maps not found", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                )
            }
        }
    }
}

@Preview
@Composable
private fun AboutUsScreenPreview() {
    AboutUsScreen(navController = rememberNavController())
}