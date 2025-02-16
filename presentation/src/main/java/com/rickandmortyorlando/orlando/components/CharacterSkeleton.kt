package com.rickandmortyorlando.orlando.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CharacterSkeleton(
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Column(
                modifier = Modifier
                    .size(100.dp)
                    .padding(horizontal = 8.dp)
                    .background(shimmerBrush()),
            ) {

            }
            Column(
                modifier = Modifier
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .background(shimmerBrush())
                        .padding(top = 8.dp)
                        .fillMaxWidth(),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    text = ""
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(16.dp)
                            .background(shimmerBrush())
                            .clip(
                                CircleShape
                            )
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(56.dp)
                            .background(shimmerBrush()),
                        text = ""
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(4.dp)
                            .background(shimmerBrush()),
                        text = ""
                    )
                    Spacer(Modifier.width(16.dp))
                    Text(
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .width(56.dp)
                            .background(shimmerBrush()),
                        text = ""
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CharacterSkeletonPreview(modifier: Modifier = Modifier) {
    CharacterSkeleton()
}
