package org.thechance.common.presentation.composables.table

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.Theme
import org.thechance.common.presentation.composables.modifier.noRipple
import org.thechance.common.presentation.uistate.UserScreenUiState


operator fun <E> List<E>.times(i: Int): List<E> {
    return (0 until i).flatMap { this }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RowScope.UserRow(
    onClickEditUser: (userId: String) -> Unit,
    position: Int,
    user: UserScreenUiState.UserUiState,
    firstColumnWeight: Float = 1f,
    otherColumnsWeight: Float = 3f,
) {
    Text(
        position.toString(),
        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentTertiary),
        modifier = Modifier.weight(firstColumnWeight),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )

    Row(Modifier.weight(otherColumnsWeight), verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource("dummy_img.png"), contentDescription = null)
        Text(
            user.fullName,
            style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
            modifier = Modifier.padding(start = 16.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }

    Text(
        user.username,
        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.weight(otherColumnsWeight),
        maxLines = 1,
    )
    Text(
        user.email,
        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.weight(otherColumnsWeight),
        maxLines = 1,
    )
    Text(
        user.country,
        style = Theme.typography.titleMedium.copy(color = Theme.colors.contentPrimary),
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.weight(otherColumnsWeight),
        maxLines = 1,
    )
    FlowRow(
        Modifier.weight(otherColumnsWeight),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        user.permissions.forEach {
            Icon(
                painter = painterResource(it.iconPath),
                contentDescription = null,
                tint = Theme.colors.contentPrimary.copy(alpha = 0.87f),
                modifier = Modifier.size(24.dp)
            )
        }
    }

    Image(
        painter = painterResource("horizontal_dots.xml"),
        contentDescription = null,
        modifier = Modifier.noRipple { onClickEditUser(user.fullName) }
            .weight(firstColumnWeight),
        colorFilter = ColorFilter.tint(color = Theme.colors.contentPrimary)
    )
}