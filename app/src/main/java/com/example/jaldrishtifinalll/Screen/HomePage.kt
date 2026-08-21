package com.example.jaldrishtifinalll.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// -------------------------------------------------------------------------
// HOME PAGE COLORS
// These colors are kept local to this screen.
// Existing BottomBar / other screens are NOT changed.
// -------------------------------------------------------------------------

private val HomeBackground = Color(0xFF071F27)
private val CardBackground = Color(0xFF103742)
private val CardBorder = Color(0xFF1B5260)

private val Cyan = Color(0xFF35C7D0)
private val Green = Color(0xFF61D69A)
private val Gold = Color(0xFFD8B65A)
private val Coral = Color(0xFFE87561)

private val PrimaryText = Color(0xFFF4FAFB)
private val SecondaryText = Color(0xFF83A4AB)
private val MutedText = Color(0xFF67888F)


// -------------------------------------------------------------------------
// HOME PAGE
// Only the content of Home screen.
// Do NOT put BottomAppBar here.
// -------------------------------------------------------------------------

@Composable
fun HomePage(
    navController: NavController
) {

    val quickActions = listOf(
        HomeAction(
            title = "Roof Calculator",
            subtitle = "Rainfall & runoff estimate",
            icon = Icons.Default.Home,
            iconBackground = Color(0xFF0E5660),
            iconTint = Cyan,
            route = "calculate"
        ),

        HomeAction(
            title = "Feasibility Score",
            subtitle = "Harvest potential",
            icon = Icons.Default.Home,
            iconBackground = Color(0xFF145347),
            iconTint = Green,
            route = "calculate"
        ),

        HomeAction(
            title = "Cost Analysis",
            subtitle = "Savings & maintenance",
            icon = Icons.Default.Home,
            iconBackground = Color(0xFF4E4C35),
            iconTint = Gold,
            route = "calculate"
        ),

        HomeAction(
            title = "PDF Report",
            subtitle = "Detailed report with maps & charts",
            icon = Icons.Default.Home,
            iconBackground = Color(0xFF52383A),
            iconTint = Coral,
            route = "reports"
        ),

        HomeAction(
            title = "WhatsApp Alerts",
            subtitle = "Smart notifications",
            icon = Icons.Default.Home,
            iconBackground = Color(0xFF0E5660),
            iconTint = Cyan,
            route = "profile"
        ),

        HomeAction(
            title = "Policy Guide",
            subtitle = "Compliance rules",
            icon = Icons.Default.Home,
            iconBackground = Color(0xFF145347),
            iconTint = Green,
            route = "reports"
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomeBackground)
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = 18.dp,
                vertical = 16.dp
            )
    ) {

        // -----------------------------------------------------------------
        // GREETING HEADER
        // -----------------------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = "GOOD MORNING",
                    color = MutedText,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.5.sp
                )

                Spacer(
                    modifier = Modifier.height(5.dp)
                )

                Text(
                    text = "Namaste, Asha 👋",
                    color = PrimaryText,
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            // Notification button
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(15.dp))
                    .background(CardBackground)
                    .clickable {
                        // Notification action can be added later.
                    },
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Notifications",
                    tint = PrimaryText,
                    modifier = Modifier.size(25.dp)
                )

                // Small notification dot
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(Gold)
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier.height(22.dp)
        )

        // -----------------------------------------------------------------
        // MAIN HARVEST SUMMARY CARD
        // -----------------------------------------------------------------

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = CardBackground
            ),
            border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                color = CardBorder
            )
        ) {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                // Top row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "ESTIMATED ANNUAL HARVEST",
                        color = SecondaryText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 1.2.sp
                    )

                    // Roof scanned badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(50.dp))
                            .background(
                                Color(0xFF173F3D)
                            )
                            .padding(
                                horizontal = 12.dp,
                                vertical = 7.dp
                            )
                    ) {

                        Text(
                            text = "Roof Scanned ✓",
                            color = Green,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                // Harvest amount
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {

                    Text(
                        text = "1.28",
                        color = Cyan,
                        fontSize = 42.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.width(5.dp)
                    )

                    Text(
                        text = "lakh L/yr",
                        color = PrimaryText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(
                            bottom = 8.dp
                        )
                    )
                }

                Spacer(
                    modifier = Modifier.height(14.dp)
                )

                // Statistics
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    SummaryStat(
                        value = "96 m²",
                        label = "Roof area",
                        modifier = Modifier.weight(1f)
                    )

                    SummaryStat(
                        value = "1,120 mm",
                        label = "Annual rainfall",
                        modifier = Modifier.weight(1f)
                    )

                    SummaryStat(
                        value = "82/100",
                        label = "Feasibility",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        // -----------------------------------------------------------------
        // SECTION HEADER
        // -----------------------------------------------------------------

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Assess & Plan",
                color = PrimaryText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "View all",
                color = Cyan,
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable {
                    navController.navigate("calculate")
                }
            )
        }

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        // -----------------------------------------------------------------
        // QUICK ACTION CARDS
        // Screenshot uses 2-column grid.
        // -----------------------------------------------------------------

        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // Row 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                ActionCard(
                    action = quickActions[0],
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.navigate(
                            quickActions[0].route
                        )
                    }
                )

                ActionCard(
                    action = quickActions[1],
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.navigate(
                            quickActions[1].route
                        )
                    }
                )
            }

            // Row 2
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                ActionCard(
                    action = quickActions[2],
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.navigate(
                            quickActions[2].route
                        )
                    }
                )

                ActionCard(
                    action = quickActions[3],
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.navigate(
                            quickActions[3].route
                        )
                    }
                )
            }

            // Row 3
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                ActionCard(
                    action = quickActions[4],
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.navigate(
                            quickActions[4].route
                        )
                    }
                )

                ActionCard(
                    action = quickActions[5],
                    modifier = Modifier.weight(1f),
                    onClick = {
                        navController.navigate(
                            quickActions[5].route
                        )
                    }
                )
            }
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )
    }
}


// -------------------------------------------------------------------------
// SUMMARY STAT COMPONENT
// -------------------------------------------------------------------------

@Composable
private fun SummaryStat(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
    ) {

        Text(
            text = value,
            color = PrimaryText,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(
            modifier = Modifier.height(3.dp)
        )

        Text(
            text = label,
            color = SecondaryText,
            fontSize = 11.sp
        )
    }
}


// -------------------------------------------------------------------------
// ACTION CARD DATA
// -------------------------------------------------------------------------

private data class HomeAction(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val iconBackground: Color,
    val iconTint: Color,
    val route: String
)


// -------------------------------------------------------------------------
// PROFESSIONAL QUICK ACTION CARD
// -------------------------------------------------------------------------

@Composable
private fun ActionCard(
    action: HomeAction,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    Card(
        modifier = modifier
            .height(145.dp)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = CardBackground
        ),
        border = androidx.compose.foundation.BorderStroke(
            width = 1.dp,
            color = CardBorder
        )
    ) {

        Column(
            modifier = Modifier.padding(17.dp)
        ) {

            // Icon container
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(13.dp))
                    .background(action.iconBackground),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = action.icon,
                    contentDescription = action.title,
                    tint = action.iconTint,
                    modifier = Modifier.size(23.dp)
                )
            }

            Spacer(
                modifier = Modifier.height(17.dp)
            )

            Text(
                text = action.title,
                color = PrimaryText,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = action.subtitle,
                color = SecondaryText,
                fontSize = 11.sp,
                lineHeight = 15.sp
            )
        }
    }
}