package org.ac2dc.lib.lexer

import org.ac2dc.lib.lexer.util.DEFAULT_LOOK_AHEAD
import org.ac2dc.lib.representation.Token
import org.ac2dc.lib.representation.TokenMetadata
import java.io.Reader

internal data class LexingContext(
    val reader: Reader,
    val lookAhead: Int = DEFAULT_LOOK_AHEAD,
    var hasError: Boolean = false,
    var line: Int = 1
) {
    fun mark() {
        reader.mark(lookAhead)
    }
    fun reset(){
        reader.reset()
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