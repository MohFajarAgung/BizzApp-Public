package com.thegreatdawn.bizzapp.ui


import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.thegreatdawn.bizzapp.R
import com.thegreatdawn.bizzapp.SettingsManager
import com.thegreatdawn.bizzapp.ui.screen.auth.FirstScreen
import com.thegreatdawn.bizzapp.ui.screen.business.BusinessScreen
import com.thegreatdawn.bizzapp.ui.screen.history.HistoryScreen
import com.thegreatdawn.bizzapp.ui.screen.history.TransactionScreen
import com.thegreatdawn.bizzapp.ui.screen.home.HomeScreen
import com.thegreatdawn.bizzapp.viewModel.AuthViewModel
import com.thegreatdawn.bizzapp.viewModel.BriLinkViewModel
import kotlinx.coroutines.launch

@Composable
fun BizzApp(
    authViewModel: AuthViewModel,
    briLinkViewModel: BriLinkViewModel,
    showInertialAd: () -> Unit
) {
    val context = LocalContext.current
    val navController: NavHostController = rememberNavController()

    val items = listOf(
        Screen.Business,
        Screen.History,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val settingsManager = remember { SettingsManager(context) }

    ModalNavigationDrawer(
        drawerContent = {
            authViewModel.fetchUsername()
            val userNode by authViewModel.userNode

            ModalDrawerSheet(drawerContainerColor = Color(0xFFD9D9D9)) {
                userNode?.let {
                    Column( modifier = Modifier.padding(16.dp)) {
                        Text(
                          text = it.username,
                            style = TextStyle(
                                color = Color.Black, // Change text color to white
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp
                            )
                        )
                        Text(it.email,)
                    }
                }
                Divider()

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(10.dp), contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            authViewModel.signOut()
                            navController.navigate("auth") {
                                popUpTo("home") {
                                    inclusive = true
                                }
                            }
                            scope.launch {
                                drawerState.close()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color.Black)
                    ) {
                      Text("Keluar")
                    }
                }
            }
        },
        drawerState = drawerState,
        gesturesEnabled = if (drawerState.isOpen) true else false,

        ) {
        Scaffold(
            topBar = {
                if (currentRoute != "auth" && currentRoute != InHistory.Transaction.route ) {
                    TopAppBar(
                        title = {
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) {
                                        drawerState.open()
                                    } else {
                                        drawerState.close()
                                    }

                                }
                            }) {
                                Icon(Icons.Filled.Menu, contentDescription = "Open Drawer")
                            }
                        },
                        actions = {
                            IconButton(onClick = {
                                val phoneNumber = "6289528983887" // Nomor WhatsApp dengan kode negara (tanpa tanda +)
                                val message = "Berikan Saran anda tentang BizzApp!! atau ada yang ingin ditanyakan?" // Pesan yang ingin Anda kirim

                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = Uri.parse("https://wa.me/$phoneNumber/?text=${Uri.encode(message)}")
                                context.startActivity(intent)
                            }) {
                                Icon(Icons.Default.Info, contentDescription = "Tutorial")
                            }
                        },
                        backgroundColor = Color.Transparent,
                        contentColor = Color.Black,
                        elevation = 0.dp
                    )
                }
                if(currentRoute =="auth"){
                    TopAppBar(
                        title = {
                        },
                        actions = {
                            IconButton(onClick = {
                                val phoneNumber = "6289528983887" // Nomor WhatsApp dengan kode negara (tanpa tanda +)
                                val message = "Berikan Saran anda tentang BizzApp!! atau ada yang ingin ditanyakan?" // Pesan yang ingin Anda kirim

                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = Uri.parse("https://wa.me/$phoneNumber/?text=${Uri.encode(message)}")
                                context.startActivity(intent)
                            }) {
                                Icon(Icons.Default.Info, contentDescription = "Tutorial")
                            }
                        },
                        backgroundColor = Color.Transparent,
                        contentColor = Color.Black,
                        elevation = 0.dp
                    )
                }
                if(currentRoute == InHistory.Transaction.route){
                    TopAppBar(
                        title = {
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                             navController.navigateUp()
                            }) {
                                Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                            }
                        },
                        backgroundColor = Color.Transparent,
                        contentColor = Color.Black,
                        elevation = 0.dp
                    )
                }
            },

            bottomBar = {
                if (currentRoute != "auth" && currentRoute != "home" && currentRoute != InHistory.Transaction.route) {
                    NavigationBar(modifier = Modifier.height(50.dp)) {
                        items.forEach { screen ->
                            NavigationBarItem(
                                icon = {
                                    if(screen.title == "History"){
                                        screen.icon?.let {
                                            Icon(
                                                painterResource(id = R.drawable.solar__history_outline_white),
                                                contentDescription = null,
                                                tint = Color.White,
                                            )
                                        }
                                    }
                                    else{
                                    screen.icon?.let {
                                        Icon(
                                            it,
                                            contentDescription = null,
                                            tint = Color.White,
                                        )
                                    }
                                    }

                                },

                                selected = currentRoute == screen.route,
                                onClick = {
                                    navController.navigate(screen.route) {
                                        popUpTo(navController.graph.startDestinationRoute!!) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },

                                )
                        }
                    }
                }
            },
            content = { innerPadding ->
                // Screen content
                NavHost(
                    navController = navController,
                    startDestination = if (!authViewModel.getLoginStatus()
                    ) "auth" else "business",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("auth") {
                        FirstScreen(authViewModel = authViewModel, navController = navController)
                    }
                    composable("home") {
                        if(settingsManager.businessIsStarted){
                              navController.navigate("business")
                        }else{

                        HomeScreen(authViewModel = authViewModel, navController = navController, agenBriLinkViewModel = briLinkViewModel)
                        }
                    }
                    composable(Screen.History.route) {
                      HistoryScreen(briLinkViewModel = briLinkViewModel,
                          navigateToTransaction = {historyKey ->
                              navController.navigate(InHistory.Transaction.createRoute(historyKey))
                          }
                          )
                    }
                    composable(route = "transaction/{historyKey}",
                        arguments = listOf(navArgument("historyKey"){type = NavType.StringType})
                    ){
                        val key = it.arguments?.getString("historyKey") ?: ""
                        LaunchedEffect(key) {
                            showInertialAd()
                        }
                        TransactionScreen(briLinkViewModel = briLinkViewModel, historyKey = key )
                        
                    }

                    composable("business"){
                        BusinessScreen(briLinkViewModel = briLinkViewModel, navController = navController)

                    }

                }

            }


        )
    }

}

sealed class Screen(val route: String, val title: String, val icon: ImageVector?) {
    object Business : Screen("business", "Business", Icons.Default.Home)
    object History : Screen("history", "History", Icons.Default.Home)
}

sealed class InHistory(val route : String){
    object Transaction : InHistory("transaction/{historyKey}"){
        fun createRoute(historyKey : String) = "transaction/$historyKey"
    }
}
