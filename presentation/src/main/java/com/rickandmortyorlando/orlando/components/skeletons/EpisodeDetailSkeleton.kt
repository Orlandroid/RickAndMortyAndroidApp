package com.rickandmortyorlando.orlando.components.skeletons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.shimmerBrush

@Composable
fun EpisodeDetailSkeleton() {
    Column(Modifier.fillMaxWidth()) {
        Spacer(Modifier.height(8.dp))
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .background(shimmerBrush())
                .height(200.dp)
        )
        Spacer(Modifier.height(32.dp))

        Column(Modifier.padding(start = 32.dp, end = 16.dp)) {
            Row(Modifier.fillMaxWidth()) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .height(16.dp)
                        .background(shimmerBrush())
                )
                Spacer(Modifier.weight(1f))
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .height(16.dp)
                        .background(shimmerBrush())
                )
            }
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth()) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .height(16.dp)
                        .background(shimmerBrush())
                )
                Spacer(Modifier.weight(1f))
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .height(16.dp)
                        .background(shimmerBrush())
                )
            }
            Spacer(Modifier.height(16.dp))
            Row(Modifier.fillMaxWidth()) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .height(16.dp)
                        .background(shimmerBrush())
                )
                Spacer(Modifier.weight(1f))
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                        .height(16.dp)
                        .background(shimmerBrush())
                )
            }
            Spacer(Modifier.height(16.dp))
            Spacer(
                Modifier
                    .width(40.dp)
                    .height(16.dp)
                    .background(shimmerBrush())
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(
            modifier =
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                color = Color.Transparent,
                fontSize = 32.sp,
                modifier = Modifier
                    .background(shimmerBrush()),
                text = stringResource(R.string.characters),
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
        Spacer(Modifier.height(32.dp))
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                count = 5
            ) {
                CharacterSkeleton()
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EpisodeDetailSkeletonPreview(modifier: Modifier = Modifier) {
    EpisodeDetailSkeleton()
}