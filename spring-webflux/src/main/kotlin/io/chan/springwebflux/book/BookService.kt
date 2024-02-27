package io.chan.springwebflux.book

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import java.util.concurrent.atomic.AtomicInteger

@Service
class BookService {
    private final val nextId = AtomicInteger(0)

    fun getAll(): Flux<Book> {
        return books.toFlux()
    }

    fun get(id: Int): Mono<Book> {
        return Mono.justOrEmpty(books.find { it.id == id })
    }

    fun add(req: Map<String, Any>): Mono<Book> {
        return Mono.just(req)
            .map { map ->
                val book = Book(
                    id = nextId.incrementAndGet(),
                    name = map["name"].toString(),
                    price = map["price"].toString().toInt()
                )

                books.add(book)

                book
            }
    }

    fun delete(id: Int): Mono<Void> {
        return Mono.justOrEmpty(books.find {it.id == id})
            .map { books.remove(it) }
            .then()
    }

    val books = mutableListOf(
        Book(id = nextId.incrementAndGet(), name = "코틀린 인 액션", price = 30000),
        Book(id = nextId.incrementAndGet(), name = "HTTP 완벽 가이드", price = 40000)
    )
}
