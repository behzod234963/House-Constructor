package com.mr.anonym.houseconstructor.helpers

class CalculateMaterials(
    private val width: Double,
    private val length: Double,
    private val height: Double
) {

    private val lengthWall = length * 0.5 * 0.6
    private val widthWall = width - 1.0 * 0.5 * 0.6
    private val lengthResult = lengthWall * 2
    private val widthResult = widthWall * 2
    private val roomVolume = lengthResult + widthResult

    private val cementVolumeToKg = 225
    private val bag = 50

    private val sandVolume = 0.495

    private val stoneVolume = 0.87

    private val brickVolume = (0.25 + 0.01) * (0.065 + 0.1)

    fun cement(): Double {
        return roomVolume + 0.05
    }

    fun cementWithBag(): Int {
        val result = roomVolume * cementVolumeToKg
        return result.toInt() / bag + 1
    }

    fun sand(): Double {
        val result = roomVolume * sandVolume
        return result
    }

    fun crushedStone(): Double {
        val result = roomVolume * stoneVolume
        return result
    }

    fun brick(): Int {
        val wallVolume = length * height
        val result = wallVolume / brickVolume.times(3)
        return result.toInt()
    }
}