# Crypto HeadLines

Crypto headlines is a fully functional Android Application that was created by me for studying purposes (play ground).
Purose of study: play a little with Jetpack Compose

## Screenshots

![Screenshot_20220623_162053](https://user-images.githubusercontent.com/52013876/175310529-be8d106c-596d-42da-9c95-87b87545e161.png)


## Video

## Features

- Access to BTC and ETH prices Live in the header of news section.
- News headlines for quick read or you can click on them for the full articles.
- Search for news of a specifc coin using its code (ex: BTC).
- Crypto market closing times.
- Crypto Fear Greed Index.

## Tech Stack
- Kotlin, MVVM
- Api requests: Retrofit2, OkHttp WebSockets
- Caching: Room DB, Shared Preferences
- Dependency Injection: dagger-hilt
- UI: Jetpack Compose
- others: Coroutines, Flow, Paging3, navigation compose, viewmodel

## Used APIs
- Fear & Greed Index by [https://alternative.me/crypto/fear-and-greed-index/]
- News by [https://cryptopanic.com/]
- Bitcoin & Etherium prices ticker, websocket provided by [https://docs.cloud.coinbase.com/exchange/docs/websocket-overview]

## Building and using the App
After cloning the app to Android studio build will work fine BUT inorder for news api to work you need to put api key in you `local.properties` file.

You can get the api key from [https://cryptopanic.com/]
And then paste it in your `local.properties` file like this:
`NEWS_AUTH_TOKEN="you-api-key-value"`

## License

**Free Software, For Study or personal uses only**

