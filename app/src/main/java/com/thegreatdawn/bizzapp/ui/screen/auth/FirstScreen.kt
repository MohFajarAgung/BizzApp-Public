package com.thegreatdawn.bizzapp.ui.screen.auth

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.thegreatdawn.bizzapp.ui.screen.auth.section.LoginSection
import com.thegreatdawn.bizzapp.ui.screen.auth.section.SignUpSection
import com.thegreatdawn.bizzapp.viewModel.AuthViewModel

@Composable
fun FirstScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    navController : NavController
) {

    val isLoading by authViewModel.isLoading




    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFF31313D))
    ) {

        drawCircle(
            color = Color(0xFF434367),
            radius = size.minDimension / 1.3f,
            center = center.copy(y = size.height / 6.5f * -1f, x = size.width)
        )
        drawCircle(
            color = Color(0xFF4991B9),
            radius = size.minDimension / 3f,
            center = center.copy(x = size.width / 6, y = 0f)
        )
        drawCircle(
            color = Color(0xFF434367),
            radius = size.minDimension,
            center = center.copy(x = size.width / 2.15f, y = size.height + size.height / 4.5f)
        )
        drawCircle(
            color = Color(0xFF4991B9),
            radius = size.minDimension,
            center = center.copy(x = size.width / 2f, y = size.height + size.height / 4f)
        )

    }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Column(modifier = modifier
            .padding(40.dp)
            .fillMaxWidth()) {
            Row {
                Text(
                    text = "BizApp",
                    style = TextStyle(
                        color = Color.White, // Change text color to white
                        fontWeight = FontWeight.Bold,
                        fontSize = 70.sp
                    )
                )
                Text(
                    text = ".",
                    style = TextStyle(
                        color = Color(0xFF4991B9), // Change text color to white
                        fontWeight = FontWeight.Bold,
                        fontSize = 70.sp
                    )
                )
            }
            Spacer(modifier = modifier.height(20.dp))
            Text(
                modifier = modifier.width(200.dp),
                text = "Kelola Bisnismu dengan Lebih Mudah",
                style = TextStyle(
                    color = Color.White, // Change text color to white
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            )
            Spacer(modifier = modifier.height(50.dp))
        }
    }


    val isLoginVisible by authViewModel.isLoginVisible
    val isSingUpVisible by authViewModel.isSignUpVisible
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
        // Menempatkan konten di bagian bawah layar
    ) {

        Button(
            onClick = {
                if (isLoginVisible) {
                    authViewModel.setLoginVisible(false)
                } else {
                    authViewModel.setLoginVisible(true)
                }

            },
            colors = ButtonDefaults.buttonColors(Color.Black),
            modifier = Modifier
                .padding(bottom = 50.dp)
                .width(200.dp)
                .height(50.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Text("Mulai")
        }

        AnimatedVisibility(visible = isLoginVisible) {
            LoginSection(authViewModel, navController)
//            SignUpSection(authViewModel = authViewModel)
        }
        AnimatedVisibility(visible = isSingUpVisible) {
            SignUpSection(authViewModel = authViewModel)
        }
    }
    if (isLoading) {

        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

}


@Composable
fun AnimatedVisibility(
    visible: Boolean,
    content: @Composable () -> Unit
) {
    val targetOffset = if (visible) 0.dp else 800.dp // Menentukan pergeseran vertikal
    val animatedOffset by animateDpAsState(
        targetValue = targetOffset,
        animationSpec = tween(durationMillis = 300)
    )

    Box(
        modifier = Modifier.offset(y = animatedOffset),
    ) {
        content()
    }
}

