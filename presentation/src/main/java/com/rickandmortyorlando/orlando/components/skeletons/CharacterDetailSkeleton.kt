package com.rickandmortyorlando.orlando.components.skeletons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rickandmortyorlando.orlando.R
import com.rickandmortyorlando.orlando.components.shimmerBrush


@Composable
fun CharacterDetailSkeleton() {
    Column(
        modifier = Modifier.testTag("CharacterDetailSkeleton")
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(32.dp))
        Spacer(
            Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(shimmerBrush())
        )
        Spacer(Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
        ) {
            Row {
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent,
                    text = stringResource(R.string.status),
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.width(8.dp))
                Spacer(
                    Modifier
                        .size(14.dp)
                        .clip(CircleShape)
                        .background(shimmerBrush())
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent, text = stringResource(R.string.alive)
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent,
                    text = stringResource(R.string.species),
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent, text = stringResource(R.string.human)
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent,
                    text = stringResource(R.string.gender),
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent, text = stringResource(R.string.male)
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent,
                    text = stringResource(R.string.number_of_episodes),
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent, text = stringResource(R.string._5)
                )
            }
            Spacer(Modifier.height(32.dp))
            Text(
                modifier = Modifier.background(shimmerBrush()),
                color = Color.Transparent,
                text = stringResource(R.string.last_seen_location),
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent,
                    text = stringResource(R.string.name),
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent, text = stringResource(R.string.citadel_of_ricks)
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent,
                    text = stringResource(R.string.type),
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent,
                    text = stringResource(R.string.planet2)
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent,
                    text = stringResource(R.string.dimension),
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent, text = stringResource(R.string.earth_c_137)
                )
            }
            Spacer(Modifier.height(16.dp))
            Row {
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent,
                    text = stringResource(R.string.numbers_of_residents),
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    modifier = Modifier.background(shimmerBrush()),
                    color = Color.Transparent, text = stringResource(R.string._5)
                )
            }
            Spacer(Modifier.height(32.dp))
            Text(
                modifier = Modifier.background(shimmerBrush()),
                color = Color.Transparent,
                text = stringResource(R.string.last_seen_location_residents),
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(16.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                items(8) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .wrapContentWidth()
                            .clickable { }
                    ) {
                        Spacer(
                            Modifier
                                .size(112.dp)
                                .clip(CircleShape)
                                .background(shimmerBrush())
                        )
                        Text(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .background(shimmerBrush()),
                            color = Color.Transparent,
                            text = stringResource(R.string.rick_sanchez)
                        )
                    }
                }
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
private fun CharacterDetailSkeletonPreview(modifier: Modifier = Modifier) {
    CharacterDetailSkeleton()
}