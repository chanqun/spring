package io.chan.springwebflux.book

import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import java.util.concurrent.atomic.AtomicInteger

@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun getAll(): Flux<Book> {
        return bookRepository.findAll()
    }

    fun getByName(name:String): Mono<Book> {
        return bookRepository.findByName(name)
    }

    fun create(map: Map<String, Any>): Mono<Book> {
        val book = Book(name = map["name"].toString(), price = map["price"].toString().toInt())

        return bookRepository.save(book)
    }
}
