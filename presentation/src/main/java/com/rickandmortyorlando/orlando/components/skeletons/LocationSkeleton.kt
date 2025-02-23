package com.rickandmortyorlando.orlando.components.skeletons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rickandmortyorlando.orlando.components.shimmerBrush

@Composable
fun LocationSkeleton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier
                        .width(56.dp)
                        .padding(bottom = 1.dp)
                        .background(shimmerBrush()), text = ""
                )
                Text(
                    modifier = Modifier
                        .width(96.dp)
                        .padding(bottom = 1.dp)
                        .background(shimmerBrush()), text = ""
                )
            }
            Spacer(
                Modifier
                    .size(24.dp)
                    .background(shimmerBrush())
            )
            Spacer(Modifier.width(16.dp))
        }
        HorizontalDivider(
            modifier = Modifier
                .background(shimmerBrush())
        )
    }

}

@Composable
@Preview(showBackground = true)
private fun LocationSkeletonPreview(modifier: Modifier = Modifier) {
    LocationSkeleton()
}