package com.ktnotes

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.server.testing.*
import kotlin.test.*
import io.ktor.http.*
import com.ktnotes.plugins.*
import java.time.LocalDate

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
//        application {
////            configureRouting()
//
//        }
//        client.get("/").apply {
//            assertEquals(HttpStatusCode.OK, status)
//            assertEquals("Hello World!", bodyAsText())
//        }

        println(LocalDate.now())
    }
}
