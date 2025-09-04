package com.example.animeseries.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.animeseries.model.Data

//@Preview
@Composable
fun AnimeRow(item: Data, onItemClick: (Int) -> Unit) {
    Card(modifier = Modifier.padding(4.dp)
        .fillMaxWidth()
        .clickable {
            onItemClick(item.mal_id)
        },
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start) {
            Surface(modifier = Modifier.padding(12.dp)
                .size(100.dp),
                shape = RectangleShape,
                shadowElevation =4.dp) {
                Image(painter = rememberImagePainter(data = item.images.jpg.image_url,
                    builder = {
                        crossfade(true)
                        transformations(CircleCropTransformation())
                    }),
                    contentDescription = "Anime Poster")
            }
            Column(modifier = Modifier.padding(4.dp)) {
                Text(text = item.title, style = MaterialTheme.typography.headlineSmall)
                Text(text = "Number of Episodes: ${item.episodes}", style = MaterialTheme.typography.labelSmall)
                Text(text = "Rating: ${item.rating}", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}