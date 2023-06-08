package kizzy.demo.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import kotlinx.browser.localStorage
import org.w3c.dom.get

@Page
@Composable
fun IndexPage() {
    val ctx = rememberPageContext()
    val token = localStorage["Token"] ?: ""
    if (token.isEmpty()) {
        ctx.router.tryRoutingTo("/login")
    } else
        ctx.router.tryRoutingTo("/home")
}