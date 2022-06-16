package com.dissiapps.crypto.ui.news

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dissiapps.crypto.R
import com.dissiapps.crypto.ui.theme.VeryLightGray

@Composable
fun SearchNewsScreen(){
    Column(Modifier.fillMaxSize()) {
        SearchField(Modifier.fillMaxWidth())
        SearchIcon(Modifier.padding(vertical = 24.dp))
    }
}

@Composable
fun SearchField(modifier: Modifier = Modifier) {
    val focusRequester = remember { FocusRequester() }
    var text by remember { mutableStateOf("") }
    TextField(
        modifier = modifier.focusRequester(focusRequester),
        value = text,
        placeholder = { Text(text = "ex: BTC", fontSize = 18.sp) },
        maxLines = 1,
        leadingIcon = { Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back_24),
            contentDescription = null
        ) },
        trailingIcon = { IconButton(
            modifier = Modifier.padding(end = 8.dp),
            content = { Icon(painter = painterResource(id = R.drawable.ic_baseline_close_24), contentDescription = null) },
            onClick = {
                text = ""
            }
        ) },
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight(300),
            color = Color.Gray
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = Color.Black,
            backgroundColor = Color.White,
            leadingIconColor = Color.Black,
            trailingIconColor = Color.Black,
            cursorColor = Color.Black,
            focusedBorderColor = Color(0x99000000)
        ),
        onValueChange = {
            text = it
        },
    )

    LaunchedEffect(key1 = Unit){
        focusRequester.requestFocus()
    }
}

@Preview
@Composable
fun SearchIcon(modifier: Modifier = Modifier){
    Column(
        modifier = modifier.wrapContentHeight().fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_search_24),
            tint = Color.Black,
            contentDescription = null
        )
        Text(
            text = "Search by code",
            fontFamily = FontFamily.SansSerif,
            fontSize = 20.sp
        )
    }
}