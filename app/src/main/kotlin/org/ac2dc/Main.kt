package org.ac2dc

import org.ac2dc.lib.lexer.LexerFactory
import org.ac2dc.lib.representation.asLine
import java.nio.file.Files
import java.nio.file.Paths

fun main(args: Array<String>) {
    if(args.size != 2 ) throw IllegalArgumentException("usage:executable <input file> <output directory>")
    val lexer = LexerFactory.createAClexer(Files.newBufferedReader(Paths.get(args[0])))
    println(lexer.tokens.asIterable().asLine)
}