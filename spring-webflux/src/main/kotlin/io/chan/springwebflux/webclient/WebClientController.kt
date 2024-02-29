package io.chan.springwebflux.webclient

import io.chan.springwebflux.book.Book
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux

@RestController
class WebClientController {
    val url = "http://localhost:8080/books"

    val log = LoggerFactory.getLogger(javaClass)

    @GetMapping("/books/block")
    fun getBooksBlocking(): List<Book> {
        log.info("Start RestTemplate")

        val restTemplate = RestTemplate()
        val response =
            restTemplate.exchange(url, HttpMethod.GET, null, object : ParameterizedTypeReference<List<Book>>() {})

        val result = response.body!!

        log.info("result: $result")
        log.info("Finish RestTemplate")

        return result
    }

    @GetMapping("/books/nonblock")
    fun getBooksNonBLocking(): Flux<Book> {
        log.info("Start WebClient")

        val flux = WebClient.create()
            .get()
            .uri(url)
            .retrieve()
            .bodyToFlux(Book::class.java)
            .map {
                log.info("result: $it")
                it
            }

        log.info("End WebClient")

        return flux
    }
}
