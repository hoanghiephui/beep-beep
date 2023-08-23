package org.thechance.common.presentation.composables.scaffold

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.beepbeep.designSystem.ui.theme.BpTheme

@Composable
fun DashBoardScaffold(
    appbar: @Composable () -> Unit,
    sideBar: @Composable () -> Unit,
    content: @Composable (Dp) -> Unit,
) {
    BpTheme {
        Row(Modifier.fillMaxSize()) {
            sideBar()
            Column {
                appbar()
                content(40.dp)
            }
        }
    }

}