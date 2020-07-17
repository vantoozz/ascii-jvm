package com.github.ascii

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream


internal class ConvertTest {

    private val newline = System.lineSeparator()

    @Test
    fun it_converts_image_to_string() {
        val output = ByteArrayOutputStream()

        Convert(output).parse(
            listOf(
                javaClass.getResource("/lena.png").path.toString(),
                "--width=20"
            )
        )

        val ascii = "\$@|.-&@@*M8k?&\$\$*)/!" + newline +
                "|#|.\"1|o%@%m-oU&k~^f" + newline +
                ".#\\.]1rMB%8\$%BdM~<h\$" + newline +
                "`Mf.+mJv-}J8\$OX<_&\$\$" + newline +
                ".oj\"{{>.1OQ##]l;M\$\$\$" + newline +
                ".av++i_]]M%MW+_m\$\$\$\$" + newline +
                "^oY\"~x)`.\\WWx.[&*o@p" + newline +
                "tdC',+f^`\\@\$8mcWda}." + newline +
                "(*n.^li'(&\$\$\$\$win[`." + newline


        assertEquals(ascii, output.toString())

        output.close()
    }
}