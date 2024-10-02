package com.emma.emmaandroidexample.ui.nativeAd.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Scale
import com.emma.emmaandroidexample.R
import io.emma.android.model.EMMANativeAd
import kotlin.math.absoluteValue

@Composable
fun NativeAdCarousel(
    nativeAds: List<EMMANativeAd>
) {
    val pagerState = rememberPagerState(initialPage = 0) {
        nativeAds.size
    }
    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(50.dp)
    ) {index ->
        CardContent(
            index = index,
            nativeAds = nativeAds,
            pagerState = pagerState
        )
    }
}

@Composable
fun CardContent(
    index: Int,
    nativeAds: List<EMMANativeAd>,
    pagerState: PagerState
) {
    val content = nativeAds[index].nativeAdContent
    val title = content["Title"]?.fieldValue
    val subtitle = content["Subtitle"]?.fieldValue
    val image = content["Main picture"]?.fieldValue
    val cta = content["CTA"]?.fieldValue

    val pageOffset = (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.padding(2.dp).graphicsLayer {
            lerp(
                start = 0.85f.dp,
                stop = 1f.dp,
                fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
            ).also { scale ->
                scaleX = scale.value
                scaleY = scale.value
            }
            alpha = lerp(
                start = 0.5f.dp,
                stop = 1f.dp,
                fraction = 1f - pageOffset.absoluteValue.coerceIn(0f, 1f)
            ).value
        }
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image ?: "https://picsum.photos/200/300")
                    .crossfade(true)
                    .scale(Scale.FILL)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Black.copy(alpha = 0.7f), Color.Transparent),
                                startY = 0f,
                                endY = size.height
                            ),
                            size = this.size
                        )
                    }
            )
            Column {
                Text(
                    text = title ?: "Title",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1
                )
                Text(
                    text = subtitle ?: "Subtitle",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    textAlign = TextAlign.Start,
                    fontSize = 13.sp,
                    color = Color.White,
                    maxLines = 2
                )
            }
        }
    }
}