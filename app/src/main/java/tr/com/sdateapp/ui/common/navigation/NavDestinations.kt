package tr.com.sdateapp.ui.common.navigation

sealed class NavDestinations(val route: String) {
    object SplashScreen : NavDestinations(Routes.SPLASH.name)
    object MainScreen : NavDestinations(Routes.MAIN.name)
    object MenuScreen : NavDestinations(Routes.MENU.name)
    object LoginScreen : NavDestinations(Routes.LOGIN.name)

    object SignUpScreen : NavDestinations(Routes.SIGNUP.name)
}
