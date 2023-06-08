package kizzy.demo.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.components.layout.numColumns
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.components.text.Text
import kizzy.core.DiscordWebSocket
import kizzy.core.DiscordWebSocketImpl
import kizzy.core.entities.LogLevel
import kizzy.core.entities.presence.Activity
import kizzy.core.entities.presence.Assets
import kizzy.core.entities.presence.Presence
import kizzy.core.entities.presence.Timestamps
import kizzy.demo.PageState
import kizzy.demo.components.layouts.PageLayout
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.s
import org.jetbrains.compose.web.dom.Form
import org.jetbrains.compose.web.dom.H2
import org.jetbrains.compose.web.dom.Input
import org.jetbrains.compose.web.dom.P
import org.w3c.dom.get
import org.w3c.dom.set
import kotlin.js.Date

@Page
@Composable
fun HomePage() {
    var state: PageState by remember { mutableStateOf(PageState.NotConnected) }
    PageLayout("Welcome to Kizzy Demo!") {
        Text("Please enter your token")
        var token by remember { mutableStateOf(window.localStorage["Token"] ?: "") }
        Input(
            InputType.Text,
            attrs = InputModifier.margin(0.px, 5.px).toAttrs {
                onInput { e -> token = e.value }
                defaultValue(token)
            }
        )
        P()
        when (state) {
            is PageState.Connected -> {
                window.localStorage["Token"] = token
                ActivityScreen((state as PageState.Connected).webSocket)
            }

            is PageState.Error -> {
                Text((state as PageState.Error).errorMessage, Modifier.color(Color.red))
            }

            PageState.NotConnected -> {
                Button(
                    onClick = {
                        if (state == PageState.NotConnected) {
                            state = PageState.StartConnecting
                        }
                    },
                    modifier = Modifier.fontWeight(700)
                        .color(Color.black)
                        .backgroundColor(Color("#fff"))
                        .padding(14.px,48.px)
                        .fontSize(18.px)
                        .margin(top = 25.px)
                        .borderRadius(0.px)
                        .position(Position.Relative)
                        .transition(CSSTransition("ease-in-out",0.3.s))
                ) {
                    Text("Connect")
                }
            }

            PageState.StartConnecting -> {
                Text("Connecting")
                LaunchedEffect(Unit) {
                    val webSocket = object : DiscordWebSocketImpl(token) {
                        override fun log(message: Any?, logLevel: LogLevel) {
                            super.log(message, logLevel)
                            when (logLevel) {
                                LogLevel.INFO -> console.info(message.toString())
                                LogLevel.DEBUG -> console.log(message.toString())
                                LogLevel.WARN -> console.warn(message.toString())
                                LogLevel.ERROR -> {
                                    state = PageState.Error(message.toString())
                                    console.error(message.toString())
                                }
                            }
                        }
                    }
                    webSocket.connect()
                    state = PageState.Connected(webSocket)
                }
            }
        }
    }
}

@Composable
fun ActivityScreen(webSocket: DiscordWebSocket) {
    var name by remember { mutableStateOf("") }
    var state by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(0) }
    var timestampsStart by remember { mutableStateOf(0L) }
    var timestampsStop by remember { mutableStateOf(0L) }
    /*var button1 by remember { mutableStateOf("") }
    var button1Url by remember { mutableStateOf("") }
    var button2 by remember { mutableStateOf("") }
    var button2Url by remember { mutableStateOf("") }*/
    var largeImage by remember { mutableStateOf("") }
    var smallImage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()
    Box(
        ActivityScreenFormStyle.toModifier(),
        contentAlignment = Alignment.Center
    ) {
        Column(Modifier.fillMaxWidth()) {
            H2 {
                Text("Custom Rpc")
            }
            Form(attrs = formStyle.toModifier().toAttrs()) {
                SimpleGrid(
                    numColumns(1, md = 2),
                    Modifier.fillMaxWidth().columnGap(8.px)
                ) {
                    Input(InputType.Text) {
                        placeholder("Activity Name")
                        onInput { e -> name = e.value }
                    }
                    Input(InputType.Text) {
                        placeholder("Activity Details")
                        onInput { e -> details = e.value }
                    }
                    Input(InputType.Text) {
                        placeholder("Activity State")
                        onInput { e -> state = e.value }
                    }
                    Input(InputType.DateTimeLocal) {
                        placeholder("Activity Start Timestamps")
                        onInput { e ->
                            val date = Date(e.value)
                            timestampsStart = date.getTime().toLong()
                        }
                    }
                    Input(InputType.DateTimeLocal) {
                        placeholder("Activity Stop Timestamps")
                        onInput { e ->
                            val date = Date(e.value)
                            timestampsStop = date.getTime().toLong()
                        }
                    }
                    /* Input(InputType.Text) {
                         placeholder("Button 1")
                         onInput { e ->
                             button1 = e.value
                         }
                     }
                     Input(InputType.Text) {
                         placeholder("Button 1 Url")
                         onInput { e ->
                             button1Url = e.value
                         }
                     }
                     Input(InputType.Text) {
                         placeholder("Button 2")
                         onInput { e ->
                             button2 = e.value
                         }
                     }
                     Input(InputType.Text) {
                         placeholder("Button 2 Url")
                         onInput { e ->
                             button2Url = e.value
                         }
                     }*/
                    Input(InputType.Text) {
                        placeholder("Activity Large Image")
                        onInput { e ->
                            largeImage = e.value
                        }
                    }
                    Input(InputType.Text) {
                        placeholder("Activity Small Image")
                        onInput { e ->
                            smallImage = e.value
                        }
                    }

                    Input(InputType.Tel) {
                        placeholder("Activity Type")
                        onInput { e -> type = e.value.toInt() }
                    }
                }
                Column(Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            scope.launch {
                                webSocket.sendActivity(
                                    Presence(
                                        activities = listOf(
                                            Activity(
                                                name = name,
                                                details = details.ifBlank { null },
                                                state = state.ifBlank { null },
                                                type = 0,
                                                timestamps = Timestamps(
                                                    start = timestampsStart.takeIf { it != 0L },
                                                    end = timestampsStop.takeIf { it != 0L }
                                                ).takeIf { it.start  != null || it.end  != null},
                                                assets = Assets(
                                                    largeImage = "mp:$largeImage".takeIf { largeImage.isNotBlank() },
                                                    smallImage = "mp:$smallImage".takeIf { smallImage.isNotBlank() }
                                                ).takeIf { !it.largeImage.isNullOrBlank() || !it.smallImage.isNullOrBlank() }
                                            )
                                        ),
                                        since = Date.now().toLong(),
                                        status = "dnd",
                                        afk = true
                                    )
                                )
                            }
                        }
                    ) {
                        SpanText("Update")
                    }
                }
            }
        }
    }
}