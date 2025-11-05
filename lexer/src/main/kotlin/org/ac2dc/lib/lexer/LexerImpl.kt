package org.ac2dc.lib.lexer

import org.ac2dc.lib.representation.Token
import org.ac2dc.lib.representation.TokenMetadata
import java.io.Reader

internal class LexerImpl<T: TokenMetadata>(source: Reader, val handler: LexHandler<T>) : Lexer<T> {
    private val reader = source.buffered()
    override val tokens: Sequence<Token<T>> by lazy(::tokenStream)

    private fun tokenStream(): Sequence<Token<T>> {
        return sequence {
            reader.use {
                var token: Token<T>
               do {
                   reader.mark(10)
                   token = handler(reader).also { yield(it) }
               } while (!token.metadata.isEOF)
            }
        }
    }

}
