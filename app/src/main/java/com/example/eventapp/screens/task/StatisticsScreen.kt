package com.example.eventapp.screens.task


import android.graphics.Typeface
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eventapp.data.entity.TagWithTaskLists
import com.example.eventapp.ui.theme.Navy
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.decoration.rememberHorizontalLine
import com.patrykandpatrick.vico.compose.cartesian.fullWidth
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.core.cartesian.HorizontalLayout
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.cartesian.decoration.HorizontalLine
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.shape.Shape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext


@Composable
fun StatisticsScreen(viewModel: TaskViewModel) {
    val tags = viewModel.tagWithTasks.collectAsState()
    Column(Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Graphic", color = Navy, fontSize = 25.sp, fontWeight = FontWeight.Bold, 
            modifier = Modifier.padding(20.dp))
        LazyColumn {
            item {
                Text(text = "Tasks per tag")
                Chart2(tags)
            }

        }
    }
}

//@Composable
//internal fun Chart2(tags: State<List<TagWithTaskLists>>) {
//    val modelProducer = remember { CartesianChartModelProducer.build() }
//    LaunchedEffect(tags) {
//        withContext(Dispatchers.Default) {
//            while (isActive) {
//                modelProducer.tryRunTransaction { columnSeries { repeat(1){ series(y = tags.value.map { it.tasks.size }) }} }
//            }
//        }
//    }
//        CartesianChartHost(
//            rememberCartesianChart(
//                rememberColumnCartesianLayer(),
//                startAxis = rememberStartAxis(),
//                bottomAxis = rememberBottomAxis(
//                    label = rememberAxisLabelComponent(),
//                    valueFormatter = { x, _, _ ->
//                        tags.value[x.toInt()].tag.name
//                    }
//                ),
//            ),
//            modelProducer,
//        )
//    }

@Composable
internal fun Chart2(tags: State<List<TagWithTaskLists>>) {
    val modelProducer = remember { CartesianChartModelProducer.build() }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.Default) {
            while (isActive) {
                modelProducer.tryRunTransaction {
                    /* Learn more:
                    https://patrykandpatrick.com/vico/wiki/cartesian-charts/layers/column-layer#data. */
                    columnSeries { series(y = tags.value.map { it.tasks.size }) }
                }

            }
        }
    }
    ComposeChart2(modelProducer, tags)
    }


@Composable
private fun ComposeChart2(modelProducer: CartesianChartModelProducer, tags: State<List<TagWithTaskLists>>) {
    CartesianChartHost(
        chart =
        rememberCartesianChart(
            rememberColumnCartesianLayer(
                ColumnCartesianLayer.ColumnProvider.series(
                    rememberLineComponent(
                        color = Color(0xffff5500),
                        thickness = 16.dp,
                        shape = remember { Shape.rounded(allPercent = 40) },
                    )
                )
            ),
            startAxis = rememberStartAxis(),
            bottomAxis =
            rememberBottomAxis(
                valueFormatter = { x, _, _ ->
                        tags.value[x.toInt()].tag.name
                    },
            ),
            decorations = listOf(rememberComposeHorizontalLine()),
        ),
        modelProducer = modelProducer,
        horizontalLayout = HorizontalLayout.fullWidth(),
    )
}


@Composable
private fun rememberComposeHorizontalLine(): HorizontalLine {
    val color = Color(HORIZONTAL_LINE_COLOR)
    return rememberHorizontalLine(
        y = { HORIZONTAL_LINE_Y },
        line = rememberLineComponent(color, HORIZONTAL_LINE_THICKNESS_DP.dp),
        labelComponent =
        rememberTextComponent(
            background = rememberShapeComponent(Shape.Pill, color),
            padding =
            Dimensions.of(
                HORIZONTAL_LINE_LABEL_HORIZONTAL_PADDING_DP.dp,
                HORIZONTAL_LINE_LABEL_VERTICAL_PADDING_DP.dp,
            ),
            margins = Dimensions.of(HORIZONTAL_LINE_LABEL_MARGIN_DP.dp),
            typeface = Typeface.MONOSPACE,
        ),
    )
}

private fun getViewHorizontalLine() =
    HorizontalLine(
        y = { HORIZONTAL_LINE_Y },
        line = LineComponent(HORIZONTAL_LINE_COLOR, HORIZONTAL_LINE_THICKNESS_DP),
        labelComponent =
        TextComponent.build {
            background = ShapeComponent(Shape.Pill, HORIZONTAL_LINE_COLOR)
            padding =
                Dimensions(
                    HORIZONTAL_LINE_LABEL_HORIZONTAL_PADDING_DP,
                    HORIZONTAL_LINE_LABEL_VERTICAL_PADDING_DP,
                )
            margins = Dimensions(HORIZONTAL_LINE_LABEL_MARGIN_DP)
            typeface = Typeface.MONOSPACE
        },
    )

private const val HORIZONTAL_LINE_Y = 14f
private const val HORIZONTAL_LINE_COLOR = -2893786
private const val HORIZONTAL_LINE_THICKNESS_DP = 2f
private const val HORIZONTAL_LINE_LABEL_HORIZONTAL_PADDING_DP = 8f
private const val HORIZONTAL_LINE_LABEL_VERTICAL_PADDING_DP = 2f
private const val HORIZONTAL_LINE_LABEL_MARGIN_DP = 4f

