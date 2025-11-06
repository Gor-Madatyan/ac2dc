package org.ac2dc.lib.lexer

import org.ac2dc.lib.lexer.util.DEFAULT_LOOK_AHEAD
import org.ac2dc.lib.representation.Token
import org.ac2dc.lib.representation.TokenMetadata
import java.io.Reader

internal data class LexingContext(
    private val reader: Reader,
    val lookAhead: Int = DEFAULT_LOOK_AHEAD,
    var hasError: Boolean = false,
    var line: Int = 1
) {
    private val buff = CharArray(lookAhead + 1) { reader.read().toChar() }
    val curr
        get() = buff[0]

    fun next() {
        for (i in 0..<lookAhead) {
            buff[i] = buff[i + 1]
        }
        buff[lookAhead] = reader.read().toChar()
    }

    fun lookAhead(ahead: Int): Char {
        if (ahead !in 1..lookAhead) throw IllegalArgumentException("Invalid lookAhead")
        return buff[ahead]
    }

    inline fun use(lambda: (LexingContext) -> Unit) {
        reader.use { lambda(this) }
    }
}

internal infix fun <T : TokenMetadata> LexHandler<T>.next(other: LexHandler<T>): LexHandler<T> {
    this.next = other
    return other
}

internal class LexHandler<T : TokenMetadata>(
    next: LexHandler<T>? = null,
    val handler: (lexingContext: LexingContext, next: LexHandler<T>?) -> Token<T>
) {
    var next: LexHandler<T>? = next
    operator fun invoke(lexingContext: LexingContext): Token<T> = handler(lexingContext, next)
}

interface Lexer<T : TokenMetadata> {
    val tokens: Sequence<Token<T>>
}