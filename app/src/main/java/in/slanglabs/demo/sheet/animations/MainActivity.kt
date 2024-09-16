package `in`.slanglabs.demo.sheet.animations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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

@Composable
fun BottomSheetDemo() {
    var showBottomSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {
        Button(
            onClick = { showBottomSheet = true },
            modifier = Modifier.align(Alignment.Center)
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
            BottomSheetContent()
        }
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
