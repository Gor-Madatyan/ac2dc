package org.ac2dc.lib.lexer

import org.ac2dc.lib.representation.Token
import org.ac2dc.lib.representation.TokenMetadata
import java.io.Reader

internal class LexerImpl<T : TokenMetadata>(
    source: Reader,
    lookAhead: Int,
    private val handler: LexHandler<T>
) : Lexer<T> {
    private val context = LexingContext(source.buffered(), lookAhead)
    override val tokens: Sequence<Token<T>> by lazy(::tokenStream)

    private fun tokenStream(): Sequence<Token<T>> {
        return sequence {
            context.use {
                var token: Token<T>
                do {
                    token = handler(context).also { yield(it) }
                } while (!token.metadata.isEOF)
            }
        }
    }

}
