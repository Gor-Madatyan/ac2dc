package org.ac2dc.lib.lexer

import org.ac2dc.lib.lexer.util.DEFAULT_LOOK_AHEAD
import org.ac2dc.lib.representation.TokenMetadata
import java.io.Reader

object LexerFactory {
    fun createAClexer(source: Reader): Lexer<ACTokenMetadata> = LexerImpl(source, DEFAULT_LOOK_AHEAD, achandler)
}

enum class ACTokenMetadata : TokenMetadata {
    SYNTAX_ELEMENT,
    NEW_LINE,
    EOF;

    override val isEOF: Boolean
        get() = this == EOF
}


