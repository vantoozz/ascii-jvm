package com.github.ascii

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.validate
import com.github.ajalt.clikt.parameters.types.float
import com.github.ajalt.clikt.parameters.types.int
import com.github.ajalt.clikt.parameters.types.path
import com.sksamuel.scrimage.ImmutableImage
import com.sksamuel.scrimage.filter.InvertFilter
import com.sksamuel.scrimage.filter.ThresholdFilter
import com.sksamuel.scrimage.pixels.Pixel
import java.io.OutputStream
import kotlin.math.round

internal class Convert(private val output: OutputStream) : CliktCommand() {

    private val imagePath
            by argument(help = "Image Path")
                .path(mustExist = true, mustBeReadable = true)

    private val width
            by option("-w", "--width", help = "Set characters per line")
                .int().default(80).validate {
                    require(it > 0) { "Width must be greater than zero" }
                }

    private val ratio
            by option("-r", "--ratio", help = "Width/height ratio")
                .float().default(.45f).validate {
                    require(it > 0) { "Ratio must be greater than zero" }
                }

    private val invert
            by option("-i", "--invert", help = "Invert colors")
                .flag(default = false)

    override fun run() {
        ImmutableImage.loader()
            .fromPath(imagePath)
            .filter(ThresholdFilter())
            .let {
                it
                    .invertIf(invert)
                    .scaleTo(width, round((it.height * ratio * width) / it.width).toInt())
            }
            .apply {
                pixels().toList().zipWithNext().forEach {
                    output.write(it.first.toChar())
                    if (it.first.y != it.second.y) {
                        output.write(System.lineSeparator().toByteArray())
                    }
                }

                output.write(pixels().last().toChar())
                output.write(System.lineSeparator().toByteArray())
            }
    }
}

private fun ImmutableImage.invertIf(invert: Boolean) = if (invert) filter(InvertFilter()) else this

private fun Pixel.toChar() = chars[round(average().toDouble() * (chars.size - 1) / 255).toInt()].toInt()

private val chars = listOf(
    '.', '\'', '`', '^', '"', ',', ':', ';', 'I', 'l', '!', 'i', '>', '<', '~',
    '+', '_', '-', '?', ']', '[', '}', '{', '1', ')', '(', '|', '\\', '/', 't',
    'f', 'j', 'r', 'x', 'n', 'u', 'v', 'c', 'z', 'X', 'Y', 'U', 'J', 'C', 'L',
    'Q', '0', 'O', 'Z', 'm', 'w', 'q', 'p', 'd', 'b', 'k', 'h', 'a', 'o', '*',
    '#', 'M', 'W', '&', '8', '%', 'B', '@', '$'
)
