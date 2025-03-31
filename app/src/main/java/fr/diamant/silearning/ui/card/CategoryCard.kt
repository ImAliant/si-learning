package fr.diamant.silearning.ui.card

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.diamant.silearning.R
import fr.diamant.silearning.data.entity.Category

private val PADDING = 8.dp
private val HEIGHT = 150.dp

@Composable
fun CategoryCard(onClick: () -> Unit, category: Category, color: Int) {
    val containerColor = colorResource(id = color)

    Card(
        onClick = onClick,
        modifier = Modifier
            .padding(PADDING)
            .fillMaxHeight()
            .height(HEIGHT),
        colors = CardDefaults.cardColors(containerColor)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.name,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}