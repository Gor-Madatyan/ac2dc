package org.ac2dc.lib.lexer.util

import org.ac2dc.lib.lexer.LexHandler
import org.ac2dc.lib.lexer.LexingContext
import org.ac2dc.lib.representation.Token
import org.ac2dc.lib.representation.TokenMetadata

internal const val DEFAULT_LOOK_AHEAD = 3

internal fun <T : TokenMetadata> singleCharExtendedHandler(ch: Char, emit: Char?, metadata: T) =
    LexHandler { context, next ->
        if (context.isChar(ch)) return@LexHandler Token(emit?.toString(), metadata)
        return@LexHandler next!!(context)

    }

internal fun <T : TokenMetadata> singleCharHandler(ch: Char, metadata: T) = singleCharExtendedHandler(ch, ch, metadata)

internal fun LexingContext.skipAll(predicate: (Char) -> Boolean) {
    var c = reader.read().toChar()
    while (predicate(c)) {
        mark()
        c = reader.read().toChar()
    }
    reset()
}

internal fun LexingContext.isChar(c: Char): Boolean {
    val ch = reader.read().toChar()
    if (ch == c) {
        mark()
        return true
    }
    reset()
    return false
}