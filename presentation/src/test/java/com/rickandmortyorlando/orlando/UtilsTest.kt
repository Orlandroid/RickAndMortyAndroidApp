package com.rickandmortyorlando.orlando

import com.rickandmortyorlando.orlando.utils.getColorStatusResource
import org.junit.Test

class UtilsTest {

    @Test
    fun `get color status Resource`() {
        val aliveColor = R.color.alive
        val deadColor = R.color.dead
        val unknownColor = R.color.unknown
        val defaultColor = R.color.unknown

        val defaultColorStatus = getColorStatusResource()
        val aliveColorStatus = getColorStatusResource("Alive")
        val deadColorStatus = getColorStatusResource("Dead")
        val unknownColorStatus = getColorStatusResource("unknown")


        assert(defaultColorStatus == defaultColor)
        assert(aliveColorStatus == aliveColor)
        assert(deadColorStatus == deadColor)
        assert(unknownColorStatus == unknownColor)
    }

}