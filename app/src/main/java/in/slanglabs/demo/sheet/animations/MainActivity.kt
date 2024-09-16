package `in`.slanglabs.demo.sheet.animations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BottomSheetDemo()
        }
    }
}

private const val SLIDE_DURATION_MS = 500;
private const val FADE_DURATION_MS = 300;

@Composable
fun BottomSheetDemo() {
    var animationType by remember {
        mutableStateOf(AnimationType.SLIDE)
    }
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(top = 120.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = animationType == AnimationType.SLIDE,
                    onClick = { animationType = AnimationType.SLIDE }
                )
                Text("Slide")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = animationType == AnimationType.BOUNCE,
                    onClick = { animationType = AnimationType.BOUNCE }
                )
                Text("Bounce")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = animationType == AnimationType.FADE,
                    onClick = { animationType = AnimationType.FADE }
                )
                Text("Fade")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = animationType == AnimationType.FADE_SLIDE,
                    onClick = { animationType = AnimationType.FADE_SLIDE }
                )
                Text("Fade and Slide")
            }
        }

        Button(
            onClick = { showBottomSheet = true },
            modifier = Modifier
                .align(Alignment.Center)
                .padding(bottom = 120.dp)
        ) {
            Text("Open Bottom Sheet")
        }
        if (showBottomSheet) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)) // Semi-transparent grey
                    .clickable(onClick = {
                        showBottomSheet = false
                    }) // Close sheet on outside click
            )
        }
        when (animationType) {
            AnimationType.SLIDE -> BottomSheetSlideIn(showBottomSheet)
            AnimationType.BOUNCE -> BottomSheetSlideWithBounce(showBottomSheet)
            AnimationType.FADE -> BottomSheetFadeIn(showBottomSheet)
            AnimationType.FADE_SLIDE -> BottomSheetFadeAndSlide(showBottomSheet)
        }
    }
}

@Composable
fun BottomSheetSlideIn(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(SLIDE_DURATION_MS)
        ),
        exit = slideOutVertically(targetOffsetY = { it }, animationSpec = tween(SLIDE_DURATION_MS))
    ) {
        BottomSheetContent()
    }
}

@Composable
fun BottomSheetSlideWithBounce(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it }, animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            )
        ), // Slide in with bounce
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(SLIDE_DURATION_MS)
        )   // Slide out with bounce
    ) {
        BottomSheetContent()
    }
}

@Composable
fun BottomSheetFadeIn(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(SLIDE_DURATION_MS)
        ) + fadeIn(animationSpec = tween(FADE_DURATION_MS)),
        exit = fadeOut(animationSpec = tween(FADE_DURATION_MS))
    ) {
        BottomSheetContent()
    }
}

@Composable
fun BottomSheetFadeAndSlide(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(
            initialOffsetY = { it },
            animationSpec = tween(SLIDE_DURATION_MS)
        ) + fadeIn(animationSpec = tween(FADE_DURATION_MS)),
        exit = slideOutVertically(
            targetOffsetY = { it },
            animationSpec = tween(SLIDE_DURATION_MS)
        ) + fadeOut(animationSpec = tween(FADE_DURATION_MS))
    ) {
        BottomSheetContent()
    }
}

@Composable
fun BottomSheetContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .height(300.dp)
            .padding(16.dp)
            .pointerInput(Unit) {}
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Tab on top of the Bottom Sheet
            Box(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(6.dp)
                    .width(60.dp)
                    .background(Color.Gray, RoundedCornerShape(50))
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Bottom Sheet Content",
                fontSize = 18.sp,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 80.dp)
            )
        }
    }
}

private enum class AnimationType {
    SLIDE, BOUNCE, FADE, FADE_SLIDE
}
