package com.thegreatdawn.bizzapp.ui.screen.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.thegreatdawn.bizzapp.ui.screen.component.ListRiwayatHarian
import com.thegreatdawn.bizzapp.viewModel.BriLinkViewModel

@Composable
fun HistoryScreen(
    modifier: Modifier = Modifier,
    briLinkViewModel: BriLinkViewModel,
    navigateToTransaction:(String) -> Unit
){

    Column(
        modifier = modifier.padding(20.dp)
            .fillMaxSize()
    ) {
        Column(Modifier.padding(bottom = 20.dp, start = 20.dp)) {
            Text(
                text = "Riwayat Harian",
                style = TextStyle(
                    color = Color.Black, // Change text color to white
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
        }
        ListRiwayatHarian(briLinkViewModel = briLinkViewModel, navigateToTransaction = navigateToTransaction )
    }

}
