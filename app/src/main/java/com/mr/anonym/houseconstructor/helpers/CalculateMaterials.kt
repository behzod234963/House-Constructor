package com.mr.anonym.houseconstructor.helpers

class CalculateMaterials(
    private val width: Double,
    private val length: Double,
    private val height: Double
) {

    private val lengthWall = length * 0.5 * 0.5
    private val widthWall = width - 1.0 * 0.5 * 0.5
    private val lengthResult = lengthWall * 2
    private val widthResult = widthWall * 2
    private val roomVolume = lengthResult + widthResult

    private val cementVolumeToKg = 225
    private val bag = 50
    private val sandVolume = 0.495
    private val stoneVolume = 0.87
    private val brickVolume = 0.26 * 0.13

    fun cement(): Double {
        return roomVolume + 0.05
    }

    fun cementWithBag(): Int {
        val wallVolume = width + length
        val volume = wallVolume * 0.5 * 0.6
        val result = volume * cementVolumeToKg
        return result.toInt() / bag + 1
    }

    fun sand(): Double {
        val wallVolume = width + length
        val volume = wallVolume * 2 * 0.5 * 0.6
        val result = volume * sandVolume
        return result
    }

    fun crushedStone(): Double {
        val wallVolume = width + length
        val volume = wallVolume * 2 * 0.5 * 0.6
        val result = volume * stoneVolume
        return result
    }

    fun brick(): Int {
        val lengthVolume = height * length
        val widthVolume = height * width
        val result = lengthVolume * widthVolume / brickVolume.times(1.5)

        return result.toInt()
    }
}