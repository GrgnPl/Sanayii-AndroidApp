package tr.com.sdateapp.ui.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel
import tr.com.sdateapp.ui.screens.SplashScreen
import tr.com.sdateapp.ui.common.navigation.NavDestinations
import tr.com.sdateapp.ui.common.navigation.NavigationController
import tr.com.sdateapp.ui.screens.LoginScreen
import tr.com.sdateapp.ui.screens.SignupScreen
import tr.com.sdateapp.ui.theme.SdateAppTheme
import tr.com.sdateapp.ui.viewmodels.AuthViewModel
import tr.com.sdateapp.ui.viewmodels.LoginViewModel
import tr.com.sdateapp.ui.viewmodels.SignupViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SdateAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationController(
                        startDestination = NavDestinations.SplashScreen.route
                    ) { navController ->
                        composable(NavDestinations.SplashScreen.route) {
                            SplashScreen(navController = navController)
                        }
                        composable(NavDestinations.MainScreen.route) {
                            MainScreen()
                        }
                        composable(NavDestinations.LoginScreen.route) {
                            val context = LocalContext.current
                            val coroutineScope = rememberCoroutineScope()
                            val loginViewModel: LoginViewModel = getViewModel()
                            val authViewModel: AuthViewModel = getViewModel()
                            LaunchedEffect(Unit) {
                                loginViewModel.toastMessage.collect { message ->
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            }
                            LoginScreen(
                                onLoginClick = { username, password ->
                                    coroutineScope.launch {
                                        loginViewModel.login(username, password)
                                    }
                                },
                                onSignupNavigate = {
                                    navController.navigate(NavDestinations.SignUpScreen.route)
                                },
                                onRememberMeChecked = { isChecked ->
                                    if (isChecked)
                                    {
                                        authViewModel.saveToken()
                                    }
                                }
                            )
                        }

                        composable(NavDestinations.SignUpScreen.route) {
                            val context = LocalContext.current
                            val coroutineScope = rememberCoroutineScope()
                            val signupViewModel: SignupViewModel = getViewModel()

                            LaunchedEffect(Unit) {
                                signupViewModel.signUptoastMessage.collect { message ->
                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                                }
                            }
                            SignupScreen { username, password, aracPlakasi ->
                                coroutineScope.launch {
                                    val success = signupViewModel.signUp(username, password, aracPlakasi)
                                    if (success) {
                                        navController.navigate(NavDestinations.LoginScreen.route) {
                                            popUpTo(NavDestinations.SignUpScreen.route) { inclusive = true }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Ana Ekran")
    }
}
