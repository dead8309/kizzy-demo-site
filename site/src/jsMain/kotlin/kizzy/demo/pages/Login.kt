package kizzy.demo.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.CSSTransition
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.before
import com.varabyte.kobweb.silk.components.style.hover
import com.varabyte.kobweb.silk.components.style.toModifier
import com.varabyte.kobweb.silk.components.text.SpanText
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.placeholder
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Form
import org.jetbrains.compose.web.dom.Input

@Page
@OptIn(ExperimentalComposeWebApi::class)
@Composable
fun LoginPage() {
    var email by remember { mutableStateOf("") }
    var password  by remember { mutableStateOf("") }
    Form(attrs = Modifier
        .fillMaxWidth(80.percent)
        .position(Position.Absolute)
        .top(50.percent)
        .left(50.percent)
        .transform {
            translate(-50.percent,-50.percent)
        }.toAttrs()
    ) {
        Column(
            Modifier.fillMaxWidth().columnGap(8.px)
        ) {
            Input(InputType.Text, attrs = InputModifier.toAttrs {
                placeholder("Email")
                onInput { e -> email = e.value }
            })
            Input(InputType.Text, attrs = InputModifier.toAttrs {
                placeholder("Password")
                onInput { e -> password= e.value }
            })
            Div(attrs = {
                this.classes("h-captcha")
                this.attr("data-sitekey","10000000-ffff-ffff-ffff-000000000001")
                this.attr("data-callback","done")
            })

            Button(onClick = {},
                ButtonStyle.toModifier()) {
                SpanText("Login")
            }
        }
    }
}
@OptIn(ExperimentalJsExport::class)
@JsExport
fun onSubmit(token: String) {
    console.log("Your Token is $token")
}
val ButtonStyle by ComponentStyle {
    base {
        Modifier.fontWeight(700)
            .color(Color.black)
            .backgroundColor(Color("#fff"))
            .padding(14.px,48.px)
            .fontSize(18.px)
            .margin(top = 25.px)
            .borderRadius(0.px)
            .position(Position.Relative)
            .transition(CSSTransition("ease-in-out",0.3.s))
    }
    cssRule(" span"){
        Modifier.zIndex(1)
            .position(Position.Relative)
    }
    hover {
        Modifier.color(Color("#fff"))
    }
    before {
        Modifier.content("")
            .backgroundColor(Color("#121212"))
            .width(0.px)
            .fillMaxHeight()
            . position (Position.Absolute)
            .top(0.px)
            .left(0.px)
            .zIndex(0)
            .transition(CSSTransition("ease-in-out",0.3.s))
    }
    (hover + before) {
        Modifier.fillMaxWidth()
    }
}