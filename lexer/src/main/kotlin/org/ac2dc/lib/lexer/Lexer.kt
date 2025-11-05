package org.ac2dc.lib.lexer

import org.ac2dc.lib.representation.Token
import org.ac2dc.lib.representation.TokenMetadata
import java.io.Reader

internal infix fun<T: TokenMetadata> LexHandler<T>.next(other: LexHandler<T>): LexHandler<T> {
    this.parent = other
    return other
}

internal class LexHandler<T: TokenMetadata>(parent: LexHandler<T>? = null, val handler: (reader: Reader, parent: LexHandler<T>?) -> Token<T>) {
    var parent: LexHandler<T>? = parent
    operator fun invoke(reader: Reader): Token<T> = handler(reader, parent)
}

interface Lexer<T: TokenMetadata> {
    val tokens: Sequence<Token<T>>
}