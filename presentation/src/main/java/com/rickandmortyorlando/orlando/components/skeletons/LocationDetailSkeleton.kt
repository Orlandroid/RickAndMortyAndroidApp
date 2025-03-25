package com.rickandmortyorlando.orlando.components.skeletons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.models.characters.Character
import com.example.domain.models.location.Location
import com.example.domain.models.location.getPairInfoLocation
import com.rickandmortyorlando.orlando.components.shimmerBrush

@Composable
fun LocationDetailSkeleton(modifier: Modifier = Modifier) {
    Column(Modifier.fillMaxWidth()) {
        Location.mockLocation().getPairInfoLocation().forEach { _ ->
            Card(
                modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(
                        Modifier
                            .weight(1f)
                            .height(16.dp)
                            .background(shimmerBrush())
                    )
                    Spacer(
                        Modifier
                            .weight(1f)
                            .height(16.dp)
                    )
                    Spacer(
                        Modifier
                            .weight(1f)
                            .height(16.dp)
                            .background(shimmerBrush())
                    )
                }
            }
        }
        Character.getCharacters(5).forEach { _ ->
            CharacterSkeleton()
        }
    }

}

@Composable
@Preview(showBackground = true)
fun LocationDetailSkeletonPreview(modifier: Modifier = Modifier) {
    LocationDetailSkeleton()
}