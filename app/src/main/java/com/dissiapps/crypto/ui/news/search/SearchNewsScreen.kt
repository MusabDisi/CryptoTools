package com.dissiapps.crypto.ui.news.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.dissiapps.crypto.R
import com.dissiapps.crypto.data.local.news.search_history.SearchQuery
import com.dissiapps.crypto.ui.navigation.NavigationItem
import com.dissiapps.crypto.ui.theme.iconTint
import com.dissiapps.crypto.ui.theme.text
import com.dissiapps.crypto.utils.CryptoCodesSearch

@Composable
fun SearchNewsScreen(
    navController: NavController,
    searchNewsViewModel: SearchNewsViewModel = hiltViewModel()
) {
//    val context = LocalContext.current
//    val trie = remember { CryptoCodesSearch(context) }
//    var suggestions by remember { mutableStateOf<List<String>>(emptyList()) }

    val searchHistory by remember { searchNewsViewModel.searchHistory }

    Column(Modifier.fillMaxSize()) {
        SearchField(
            Modifier.fillMaxWidth(),
            onBackClicked = {
                navController.popBackStack()
            },
            onSearch = { string ->
                searchNewsViewModel.insertValueToHistory(string)
                searchString(navController, string)
            },
            onValueChanged = {
//                    suggestions = trie.search(it)
            }
        )

        /*if (suggestions.isNotEmpty()){
            Suggestions(suggestionsList = suggestions, onItemClicked = {})
        }else */
        if (searchHistory.isEmpty()) {
            SearchIcon(Modifier.padding(vertical = 24.dp))
        } else {
            SearchHistory(
                history = searchHistory,
                onItemClicked = {
                    searchString(navController, it)
                },
                onItemDeleteClicked = {
                    searchNewsViewModel.deleteQuery(it)
                },
                onDeleteAllClicked = {
                    searchNewsViewModel.deleteAllQueries()
                }
            )
        }
    }
}

fun searchString(navController: NavController, string: String) {
    navController.navigate(NavigationItem.NewsScreenNav.createNavigationRoute(string)) {
        popUpTo(navController.graph.startDestinationId)
    }
}

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    onSearch: (String) -> Unit,
    onValueChanged: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    var text by remember { mutableStateOf("") }
    TextField(
        modifier = modifier.focusRequester(focusRequester),
        value = text,
        placeholder = { Text(text = stringResource(R.string.search_example), fontSize = 18.sp) },
        maxLines = 1,
        leadingIcon = {
            IconButton(
                content = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
                    )
                },
                onClick = {
                    onBackClicked()
                }
            )
        },
        trailingIcon = {
            IconButton(
                modifier = Modifier.padding(end = 8.dp),
                content = {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = null
                    )
                },
                onClick = {
                    text = ""
                }
            )
        },
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight(400)
        ),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            textColor = MaterialTheme.colors.text,
            backgroundColor = MaterialTheme.colors.background,
            leadingIconColor = MaterialTheme.colors.iconTint,
            trailingIconColor = MaterialTheme.colors.iconTint,
            cursorColor = MaterialTheme.colors.text,
            focusedBorderColor = Color(0x99000000)
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            capitalization = KeyboardCapitalization.Characters
        ),
        keyboardActions = KeyboardActions(onSearch = {
            onSearch(text)
        }),
        onValueChange = {
            text = it
            onValueChanged(it)
        },
    )

    LaunchedEffect(key1 = Unit) {
        focusRequester.requestFocus()
    }
}

@Preview(showBackground = true)
@Composable
fun SearchIcon(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            tint = Color.Black,
            contentDescription = null
        )
        Text(
            text = stringResource(id = R.string.search_currency_code),
            fontFamily = FontFamily.SansSerif,
            fontSize = 20.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchHistoryPreview() {
    SearchHistory(
        history = listOf(SearchQuery("BTC", 0L), SearchQuery("BTC", 0L)),
        onItemClicked = {},
        onItemDeleteClicked = {},
        onDeleteAllClicked = {}
    )
}

@Composable
fun SearchHistory(
    modifier: Modifier = Modifier,
    history: List<SearchQuery>,
    onItemClicked: (String) -> Unit,
    onItemDeleteClicked: (SearchQuery) -> Unit,
    onDeleteAllClicked: () -> Unit
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.search_history).uppercase(),
                fontSize = 18.sp,
                letterSpacing = 1.2.sp,
                color = Color.Gray
            )
            Text(
                modifier = Modifier.clickable {
                    onDeleteAllClicked()
                },
                text = stringResource(R.string.clear_all),
                fontSize = 18.sp,
                letterSpacing = 1.2.sp,
                color = Color.Gray,
                textDecoration = TextDecoration.Underline
            )
        }

        history.forEach {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onItemClicked(it.query) },
                horizontalArrangement = SpaceBetween
            ) {
                Text(
                    modifier = Modifier.padding(start = 12.dp),
                    text = it.query,
                    fontSize = 18.sp,
                    letterSpacing = 1.2.sp,
                    fontWeight = FontWeight.W400
                )
                Icon(
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clickable {
                            onItemDeleteClicked(it)
                        },
                    imageVector = Icons.Filled.Close,
                    contentDescription = null
                )
            }
            Divider(modifier = Modifier.padding(vertical = 4.dp), thickness = 1.dp)
        }
    }
}

//@Composable
//fun Suggestions(
//    modifier: Modifier = Modifier,
//    suggestionsList: List<String>,
//    onItemClicked: (String) -> Unit
//) {
//    Column(
//        modifier = modifier
//            .wrapContentHeight()
//            .fillMaxWidth()
//    ) {
//
//        suggestionsList.forEach {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp)
//                    .clickable { onItemClicked(it) },
//                horizontalArrangement = SpaceBetween
//            ) {
//                Text(
//                    modifier = Modifier.padding(start = 12.dp),
//                    text = it,
//                    fontSize = 18.sp,
//                    letterSpacing = 1.2.sp,
//                    fontWeight = FontWeight.W400
//                )
//            }
//            Divider(modifier = Modifier.padding(vertical = 4.dp), thickness = 1.dp)
//        }
//    }
//}