package com.ktnotes.helper

import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType



public suspend inline fun HttpClient.postWithJsonContent(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {}
): HttpResponse = post {
    url(urlString);
    contentType(ContentType.Application.Json)
    block()
}

public suspend inline fun HttpClient.putWithJsonContent(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {}
): HttpResponse = put {
    url(urlString);
    contentType(ContentType.Application.Json)
    block()
}

