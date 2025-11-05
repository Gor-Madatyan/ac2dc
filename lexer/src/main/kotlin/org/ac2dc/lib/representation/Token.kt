package org.ac2dc.lib.representation

interface TokenMetadata{
    val isEOF: Boolean
}

val <T: TokenMetadata> Iterable<Token<T>>.asLine: String
    get() = this.joinToString(separator = " ")

data class Token<T: TokenMetadata>(val lexeme: String?, val metadata: T) {
    override fun toString() = lexeme ?: metadata.toString()
}
