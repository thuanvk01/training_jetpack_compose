package com.example.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.compose.ui.theme.ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun SimpleAnnotatedStringText() {
    Text(
        text = "Hiiiiiiiiiiii",
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            fontFamily = FontFamily.Cursive,
            color = Color(color = R.color.purple_700),
            fontStyle = FontStyle.Normal,
            letterSpacing = 0.sp
        ),

        textDecoration = TextDecoration.Underline,
        textAlign = TextAlign.Start,
        lineHeight = 0.em,
        overflow = TextOverflow.Ellipsis,
        softWrap = false,
        maxLines = 1,
        modifier = Modifier.size(50.dp)
//    inlineContent: Map<String, InlineTextContent> = mapOf(),
//    onTextLayout: (TextLayoutResult) -> Unit = {}
    )

    Text(buildAnnotatedString {
        withStyle(style = SpanStyle(color = Color.Blue)) {
            append("H")
        }
        append("ello ")

        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)) {
            append("W")
        }
        append("orld")
    })
}


@Composable
fun MainScreen() {
    Column {
        // Nhiều kiểu chữ trong văn bản
        SimpleAnnotatedStringText()

        // Bôi đen để copy text
        SelectionContainer() {
            Text(
                text = stringResource(id = R.string.text_test),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        DemoTextField()

        AnnotatedClickableText()

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DemoTextField() {
    var textField by remember {
        mutableStateOf("")
    }
    TextField(
        value = textField,
        onValueChange = { newValue ->
            textField = newValue
        },
        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp, fontWeight = FontWeight.Medium),
        label = {Text(text = "TextField:")}
    )
    OutlinedTextField(
        value = textField,
        onValueChange = {value ->
            textField = value
        },
        label = { Text(text = "Outlinded TextField")}
    )
}

@Composable
fun AnnotatedClickableText() {
    val annotatedText = buildAnnotatedString {
        append("Clickable ")

        pushStringAnnotation(tag = "VKT",
            annotation = "oooooooooo")
        withStyle(style = SpanStyle(color = Color.Blue,
            fontWeight = FontWeight.Bold)) {
            append("Text")
        }

        pop()
    }

    ClickableText(
        text = annotatedText,
        onClick = { offset ->

            annotatedText.getStringAnnotations(tag = "VKT", start = offset,
                end = offset)
                .firstOrNull()?.let { annotation ->
                    // If yes, we log its value
                    Log.d("Clicked", annotation.item)
                }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserPasswordTextField() {
    var password by remember {
        mutableStateOf("")
    }
    var user by remember {
        mutableStateOf("")
    }
    Column {
        TextField(
            value = user,
            onValueChange = { inputUser ->
                user = inputUser
            }
        )
        TextField(
            value = password,
            onValueChange = { inputPass ->
                password = inputPass
            },
            label = { Text(text = "Enter Password:") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    ComposeTheme {
        MainScreen()
    }
}