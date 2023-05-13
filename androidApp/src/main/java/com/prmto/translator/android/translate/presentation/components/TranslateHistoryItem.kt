package com.prmto.translator.android.translate.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.prmto.translator.android.core.theme.LightBlue
import com.prmto.translator.translate.presentation.UiHistoryItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TranslateHistoryItem(
    item: UiHistoryItem,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberDismissState(
        confirmStateChange = {
            if (it == DismissValue.DismissedToStart) {
                onDelete()
            }
            true
        }
    )

    SwipeToDismiss(
        state = dismissState,
        directions = setOf(
            DismissDirection.EndToStart
        ),
        dismissThresholds = {
            FractionalThreshold(0.5f)
        },
        background = {
            SwipeBackground(dismissState = dismissState)
        },
        dismissContent = {
            Column(
                modifier = modifier
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clip(RoundedCornerShape(20.dp))
                    .gradientSurface()
                    .clickable(onClick = onClick)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SmallLanguageIcon(language = item.fromLanguage)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = item.fromText,
                        color = LightBlue,
                        style = MaterialTheme.typography.body2
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SmallLanguageIcon(language = item.toLanguage)
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = item.toText,
                        color = MaterialTheme.colors.onSurface,
                        style = MaterialTheme.typography.body1,
                        fontWeight = FontWeight.Medium
                    )
                }

            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SwipeBackground(dismissState: DismissState) {


    val color = animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            DismissValue.Default -> MaterialTheme.colors.surface
            DismissValue.DismissedToStart -> MaterialTheme.colors.error
            DismissValue.DismissedToEnd -> MaterialTheme.colors.error
        }, label = "",
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clip(RoundedCornerShape(20.dp))
            .background(color.value)
    ) {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Localized description",
            tint = Color.White,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterEnd)
        )
    }
}