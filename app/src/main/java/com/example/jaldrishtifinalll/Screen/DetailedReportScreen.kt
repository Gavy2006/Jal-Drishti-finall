package com.example.jaldrishtifinalll.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.NavController

import com.example.jaldrishtifinalll.ViewModel.RainfallViewModel


@Composable
fun DetailedReportScreen(
    navController: NavController,
    rainfallViewModel: RainfallViewModel = viewModel()
) {

    val report by
    rainfallViewModel.detailedReport.collectAsState()

    val reportLoading by
    rainfallViewModel.reportLoading.collectAsState()

    val reportError by
    rainfallViewModel.reportError.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Color(0xFFEAF8FC)
            )
    ) {


        // TOP BAR

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Color(0xFF173B43)
                )
                .padding(
                    horizontal = 12.dp,
                    vertical = 10.dp
                ),
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {

                Icon(
                    imageVector =
                        Icons.Default.ArrowBack,
                    contentDescription =
                        "Back",
                    tint = Color.White
                )
            }


            Column {

                Text(
                    text = "Jal Drishti",
                    color = Color.White,
                    fontSize = 21.sp,
                    fontWeight =
                        FontWeight.Bold
                )

                Text(
                    text =
                        "Detailed Feasibility Report",
                    color =
                        Color.White.copy(
                            alpha = 0.75f
                        ),
                    fontSize = 12.sp
                )
            }
        }


        // LOADING

        when {

            reportLoading -> {

                Box(
                    modifier =
                        Modifier.fillMaxSize(),
                    contentAlignment =
                        Alignment.Center
                ) {

                    Column(
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        CircularProgressIndicator(
                            color =
                                Color(0xFF3FA8BD)
                        )

                        Spacer(
                            modifier =
                                Modifier.height(12.dp)
                        )

                        Text(
                            text =
                                "Generating detailed report...",
                            color =
                                Color(0xFF173B43),
                            fontSize = 14.sp
                        )
                    }
                }
            }


            // ERROR

            reportError.isNotEmpty() -> {

                Box(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text = reportError,
                        color =
                            Color(0xFFC62828)
                    )
                }
            }


            // REPORT

            report != null -> {

                val data = report!!


                Column(
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(
                                rememberScrollState()
                            )
                            .padding(18.dp)
                ) {


                    // HEADER

                    Card(
                        modifier =
                            Modifier.fillMaxWidth(),
                        shape =
                            RoundedCornerShape(24.dp),
                        colors =
                            CardDefaults.cardColors(
                                containerColor =
                                    Color(0xFF173B43)
                            )
                    ) {

                        Column(
                            modifier =
                                Modifier.padding(22.dp)
                        ) {

                            Row(
                                verticalAlignment =
                                    Alignment.CenterVertically
                            ) {

                                Icon(
                                    imageVector =
                                        Icons.Default.ArrowBack,
                                    contentDescription =
                                        null,
                                    tint =
                                        Color(0xFF65C6DA)
                                )

                                Spacer(
                                    modifier =
                                        Modifier.width(10.dp)
                                )

                                Text(
                                    text =
                                        "Feasibility Report",
                                    color =
                                        Color.White,
                                    fontSize = 23.sp,
                                    fontWeight =
                                        FontWeight.Bold
                                )
                            }


                            Spacer(
                                modifier =
                                    Modifier.height(14.dp)
                            )


                            Text(
                                text =
                                    data.executive_summary,
                                color =
                                    Color.White.copy(
                                        alpha = 0.88f
                                    ),
                                fontSize = 14.sp,
                                lineHeight = 21.sp
                            )
                        }
                    }


                    Spacer(
                        modifier =
                            Modifier.height(16.dp)
                    )


                    ReportSection(
                        "Location Analysis",
                        data.location_analysis
                    )

                    ReportSection(
                        "Roof Analysis",
                        data.roof_analysis
                    )

                    ReportSection(
                        "Rainfall Analysis",
                        data.rainfall_analysis
                    )

                    ReportSection(
                        "Harvesting Potential",
                        data.harvesting_potential
                    )

                    ReportSection(
                        "Feasibility",
                        data.feasibility
                    )

                    ReportSection(
                        "Recommended System",
                        data.recommended_system
                    )

                    ReportSection(
                        "Tank Recommendation",
                        data.tank_recommendation
                    )

                    ReportSection(
                        "Filtration Recommendation",
                        data.filtration_recommendation
                    )

                    ReportSection(
                        "Installation Guidance",
                        data.installation_guidance
                    )

                    ReportSection(
                        "Cost Estimation",
                        data.cost_estimation
                    )

                    ReportSection(
                        "Component Cost Breakdown",
                        data.component_cost_breakdown
                    )

                    ReportSection(
                        "Maintenance Cost",
                        data.maintenance_cost
                    )

                    ReportSection(
                        "Government Policies",
                        data.government_policies
                    )

                    ReportSection(
                        "Applicable Subsidies",
                        data.applicable_subsidies
                    )

                    ReportSection(
                        "Policy Year",
                        data.policy_year
                    )

                    ReportSection(
                        "Environmental Benefits",
                        data.environmental_benefits
                    )

                    ReportSection(
                        "Water Savings",
                        data.water_savings
                    )

                    ReportSection(
                        "Recommendations",
                        data.recommendations
                    )


                    // SOURCES

                    if (data.sources.isNotEmpty()) {

                        Spacer(
                            modifier =
                                Modifier.height(8.dp)
                        )


                        Card(
                            modifier =
                                Modifier.fillMaxWidth(),
                            shape =
                                RoundedCornerShape(20.dp),
                            colors =
                                CardDefaults.cardColors(
                                    containerColor =
                                        Color(0xFFDFF3F7)
                                )
                        ) {

                            Column(
                                modifier =
                                    Modifier.padding(18.dp)
                            ) {

                                Text(
                                    text = "Sources",
                                    color =
                                        Color(0xFF173B43),
                                    fontSize = 18.sp,
                                    fontWeight =
                                        FontWeight.Bold
                                )


                                Spacer(
                                    modifier =
                                        Modifier.height(10.dp)
                                )


                                data.sources
                                    .distinct()
                                    .forEachIndexed {
                                            index,
                                            source ->

                                        Text(
                                            text =
                                                "${index + 1}. $source",
                                            color =
                                                Color(0xFF4D5A5D),
                                            fontSize = 12.sp,
                                            lineHeight = 18.sp,
                                            modifier =
                                                Modifier.padding(
                                                    vertical = 3.dp
                                                )
                                        )
                                    }
                            }
                        }
                    }


                    Spacer(
                        modifier =
                            Modifier.height(30.dp)
                    )
                }
            }


            else -> {

                Box(
                    modifier =
                        Modifier.fillMaxSize(),
                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text =
                            "Detailed report is not available yet.",
                        color =
                            Color(0xFF173B43),
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Composable
private fun ReportSection(
    title: String,
    body: String
) {

    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 6.dp
                ),
        shape =
            RoundedCornerShape(18.dp),
        colors =
            CardDefaults.cardColors(
                containerColor =
                    Color.White
            ),
        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 2.dp
            )
    ) {

        Column(
            modifier =
                Modifier.padding(18.dp)
        ) {

            Text(
                text = title,
                color =
                    Color(0xFF173B43),
                fontSize = 17.sp,
                fontWeight =
                    FontWeight.Bold
            )


            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )


            Text(
                text = body,
                color =
                    Color(0xFF4D5A5D),
                fontSize = 14.sp,
                lineHeight = 21.sp
            )
        }
    }
}