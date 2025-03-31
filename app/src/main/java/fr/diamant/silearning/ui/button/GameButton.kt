package fr.diamant.silearning.ui.button

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val WIDTH = 150.dp
private val SHAPE_SIZE = 8.dp
private val ELEVATION = 4.dp
private val PRESSED_ELEVATION = 8.dp
private val HOVERED_ELEVATION = 6.dp
private val FOCUSED_ELEVATION = 6.dp

private val CONTAINER_COLOR = Color(0xFF6200EE)
private val CONTENT_COLOR = Color.White
private val DISABLED_CONTAINER_COLOR = Color(0xFFBB86FC)
private val DISABLED_CONTENT_COLOR = Color.White

@Composable
fun GameButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = CONTAINER_COLOR,
        contentColor = CONTENT_COLOR,
        disabledContainerColor = DISABLED_CONTAINER_COLOR,
        disabledContentColor = DISABLED_CONTENT_COLOR
    ),
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.width(WIDTH),
        colors = colors,
        shape = RoundedCornerShape(SHAPE_SIZE),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = ELEVATION,
            pressedElevation = PRESSED_ELEVATION,
            hoveredElevation = HOVERED_ELEVATION,
            focusedElevation = FOCUSED_ELEVATION
        )
    ) {
        Text(text = text)
    }
}