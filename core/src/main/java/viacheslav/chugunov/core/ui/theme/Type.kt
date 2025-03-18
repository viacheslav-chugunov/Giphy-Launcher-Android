package viacheslav.chugunov.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal val Typography = Typography(
    titleMedium = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 28.sp
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        fontWeight = FontWeight.W400
    ),
    bodySmall = TextStyle(
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.25.sp,
        fontWeight = FontWeight.W400
    ),
    labelMedium = TextStyle(
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.4.sp,
        fontWeight = FontWeight.W400
    ),
    labelLarge = TextStyle( // buttons
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.W500
    ),
    headlineSmall = TextStyle( // dialogs titles
        fontSize = 24.sp,
        lineHeight = 32.sp,
        fontWeight = FontWeight.W400
    )
)