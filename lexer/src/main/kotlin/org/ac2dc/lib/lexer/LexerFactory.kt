package org.ac2dc.lib.lexer

import org.ac2dc.lib.representation.Token
import org.ac2dc.lib.representation.TokenMetadata
import java.io.Reader

object LexerFactory {
    fun createAClexer(source: Reader): Lexer<ACTokenMetadata> = LexerImpl(source, achandler)
}

enum class ACTokenMetadata : TokenMetadata {
    KEYWORD,
    IDENTIFIER,
    LITERAL,
    NEW_LINE,
    EOF;

    override val isEOF: Boolean
        get() = this == EOF
}

private val achandler =
    LexHandler { reader, parent ->
        Token(null, ACTokenMetadata.EOF)
    }

