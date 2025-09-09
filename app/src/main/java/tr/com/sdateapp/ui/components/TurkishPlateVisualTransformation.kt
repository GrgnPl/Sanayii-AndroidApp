package tr.com.sdateapp.ui.components

import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

class TurkishPlateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val raw = text.text.uppercase().replace(" ", "") // boşlukları temizle
        val builder = StringBuilder()

        var i = 0
        if (raw.length >= 2) {
            builder.append(raw.substring(0, 2)) // il kodu
            builder.append(" ")
            i = 2
        }

        // Harfleri oku (max 3)
        var letterCount = 0
        while (i < raw.length && raw[i].isLetter() && letterCount < 3) {
            builder.append(raw[i])
            i++
            letterCount++
        }

        if (letterCount > 0) builder.append(" ") // harf varsa boşluk ekle

        // Kalanları sayı olarak ekle
        while (i < raw.length) {
            builder.append(raw[i])
            i++
        }

        val transformed = builder.toString()

        // offset mapping
        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                var transformedOffset = offset
                if (offset > 2) transformedOffset += 1 // il kodundan sonra boşluk
                if (offset > 2 + letterCount) transformedOffset += 1 // harflerden sonra boşluk
                return transformedOffset
            }

            override fun transformedToOriginal(offset: Int): Int {
                var originalOffset = offset
                if (offset > 2) originalOffset -= 1
                if (offset > 3 + letterCount) originalOffset -= 1
                return originalOffset.coerceAtLeast(0)
            }
        }

        return TransformedText(AnnotatedString(transformed), offsetMapping)
    }
}


