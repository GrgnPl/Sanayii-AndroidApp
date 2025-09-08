package tr.com.sdateapp.ui.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
// Image importunu kaldırabiliriz eğer hiç kullanılmayacaksa
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
// import androidx.compose.foundation.layout.size // Image için kullanılıyordu, kaldırılabilir
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale // Metin için de ölçek animasyonu kullanabiliriz
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalInspectionMode
// import androidx.compose.ui.res.painterResource // Image için kullanılıyordu, kaldırılabilir
import androidx.compose.ui.text.font.FontFamily // Özel font kullanmak isterseniz
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import tr.com.sdateapp.ui.common.navigation.NavDestinations
import tr.com.sdateapp.ui.theme.AppLightGray
import tr.com.sdateapp.ui.theme.AppOrange
import tr.com.sdateapp.ui.theme.SdateAppTheme




@Composable
fun SplashScreen(navController: NavController) {
    val scaleAppName = remember { Animatable(0.5f) }
    val alphaAppName = remember { Animatable(0f) }
    val alphaSlogan = remember { Animatable(0f) }
    val isInPreview = LocalInspectionMode.current

    LaunchedEffect(key1 = true) {
        scaleAppName.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 900,
                easing = { OvershootInterpolator(1.5f).getInterpolation(it) }
            )
        )
        alphaAppName.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 700, delayMillis = 200)
        )
        alphaSlogan.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 600, delayMillis = 700)
        )

        if (!isInPreview) {
            delay(2800L)
            navController.navigate(NavDestinations.LoginScreen.route) {
                popUpTo(NavDestinations.SplashScreen.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        AppOrange.copy(alpha = 0.1f),
                        AppLightGray.copy(alpha = 0.5f),
                        AppLightGray
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        ) {
            Text(
                text = "Sanayii",
                fontSize = 52.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.SansSerif,
                color = AppOrange,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .scale(scaleAppName.value)
                    .alpha(alphaAppName.value)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Servis ve bakım işlerinizin en kolay yolu.",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .alpha(alphaSlogan.value)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenSanayiiPreview() {
    SdateAppTheme {
        SplashScreen(navController = rememberNavController())
    }
}
