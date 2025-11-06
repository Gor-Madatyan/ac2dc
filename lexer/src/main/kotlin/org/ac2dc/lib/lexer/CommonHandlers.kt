package org.ac2dc.lib.lexer

import org.ac2dc.lib.lexer.util.singleCharExtendedHandler
import org.ac2dc.lib.lexer.util.singleCharHandler
import org.ac2dc.lib.lexer.util.skipAll
import org.ac2dc.lib.representation.Token

internal val achandler =
    LexHandler { context, next ->
        var skippedNewLines = 0
        context.skipAll { ch -> (ch == '\n').also{if(it)skippedNewLines++} || ch == ' ' }
        context.line += skippedNewLines
        if (skippedNewLines > 0) return@LexHandler Token(null, ACTokenMetadata.NEW_LINE)
        else return@LexHandler next!!(context)
    }.also { first ->
        first next singleCharHandler(
            '+',
            ACTokenMetadata.SYNTAX_ELEMENT
        ) next singleCharHandler(
            '-',
            ACTokenMetadata.SYNTAX_ELEMENT
        ) next singleCharHandler(
            '/',
            ACTokenMetadata.SYNTAX_ELEMENT
        )next singleCharHandler(
            '*',
            ACTokenMetadata.SYNTAX_ELEMENT
        )next singleCharHandler(
            '%',
            ACTokenMetadata.SYNTAX_ELEMENT
        )next singleCharHandler(
            '.',
            ACTokenMetadata.SYNTAX_ELEMENT
        )next singleCharHandler(
            ',',
            ACTokenMetadata.SYNTAX_ELEMENT
        )next singleCharHandler(
            ';',
            ACTokenMetadata.SYNTAX_ELEMENT
        ) next singleCharExtendedHandler(
            (-1).toChar(),
            null,
            ACTokenMetadata.EOF
        ) next LexHandler { context, _ ->
            System.err.println("Can't handle ${context.curr} at line ${context.line}")
            context.hasError = true
            context.next()
            return@LexHandler first(context)
        }
    }
