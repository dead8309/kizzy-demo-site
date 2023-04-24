package kizzy.demo

import kizzy.core.DiscordWebSocket

sealed interface PageState {
    object NotConnected: PageState
    object StartConnecting: PageState
    class Connected(val webSocket: DiscordWebSocket): PageState
    class Error(val errorMessage: String): PageState
}