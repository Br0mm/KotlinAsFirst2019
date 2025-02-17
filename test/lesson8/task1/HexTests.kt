package lesson8.task1

import lesson8.task1.Direction.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.lang.IllegalArgumentException

class HexTests {

    @Test
    @Tag("Normal")
    fun hexPointDistance() {
        assertEquals(5, HexPoint(6, 1).distance(HexPoint(1, 4)))
    }

    @Test
    @Tag("Normal")
    fun hexagonDistance() {
        assertEquals(2, Hexagon(HexPoint(1, 3), 1).distance(Hexagon(HexPoint(6, 2), 2)))
    }

    @Test
    @Tag("Trivial")
    fun hexagonContains() {
        assertTrue(Hexagon(HexPoint(3, 3), 1).contains(HexPoint(2, 3)))
        assertFalse(Hexagon(HexPoint(3, 3), 1).contains(HexPoint(4, 4)))
    }

    @Test
    @Tag("Easy")
    fun hexSegmentValid() {
        assertTrue(HexSegment(HexPoint(1, 3), HexPoint(5, 3)).isValid())
        assertTrue(HexSegment(HexPoint(3, 1), HexPoint(3, 6)).isValid())
        assertTrue(HexSegment(HexPoint(1, 5), HexPoint(4, 2)).isValid())
        assertFalse(HexSegment(HexPoint(3, 1), HexPoint(6, 2)).isValid())
    }

    @Test
    @Tag("Normal")
    fun hexSegmentDirection() {
        assertEquals(RIGHT, HexSegment(HexPoint(1, 3), HexPoint(5, 3)).direction())
        assertEquals(UP_RIGHT, HexSegment(HexPoint(3, 1), HexPoint(3, 6)).direction())
        assertEquals(DOWN_RIGHT, HexSegment(HexPoint(1, 5), HexPoint(4, 2)).direction())
        assertEquals(LEFT, HexSegment(HexPoint(5, 3), HexPoint(1, 3)).direction())
        assertEquals(DOWN_LEFT, HexSegment(HexPoint(3, 6), HexPoint(3, 1)).direction())
        assertEquals(UP_LEFT, HexSegment(HexPoint(4, 2), HexPoint(1, 5)).direction())
        assertEquals(INCORRECT, HexSegment(HexPoint(3, 1), HexPoint(6, 2)).direction())
    }

    @Test
    @Tag("Easy")
    fun oppositeDirection() {
        assertEquals(LEFT, RIGHT.opposite())
        assertEquals(DOWN_LEFT, UP_RIGHT.opposite())
        assertEquals(UP_LEFT, DOWN_RIGHT.opposite())
        assertEquals(RIGHT, LEFT.opposite())
        assertEquals(DOWN_RIGHT, UP_LEFT.opposite())
        assertEquals(UP_RIGHT, DOWN_LEFT.opposite())
        assertEquals(INCORRECT, INCORRECT.opposite())
    }

    @Test
    @Tag("Normal")
    fun nextDirection() {
        assertEquals(UP_RIGHT, RIGHT.next())
        assertEquals(UP_LEFT, UP_RIGHT.next())
        assertEquals(RIGHT, DOWN_RIGHT.next())
        assertEquals(DOWN_LEFT, LEFT.next())
        assertEquals(LEFT, UP_LEFT.next())
        assertEquals(DOWN_RIGHT, DOWN_LEFT.next())
        assertThrows(IllegalArgumentException::class.java) {
            INCORRECT.next()
        }
    }

    @Test
    @Tag("Easy")
    fun isParallelDirection() {
        assertTrue(RIGHT.isParallel(RIGHT))
        assertTrue(RIGHT.isParallel(LEFT))
        assertFalse(RIGHT.isParallel(UP_LEFT))
        assertFalse(RIGHT.isParallel(INCORRECT))
        assertTrue(UP_RIGHT.isParallel(UP_RIGHT))
        assertTrue(UP_RIGHT.isParallel(DOWN_LEFT))
        assertFalse(UP_RIGHT.isParallel(UP_LEFT))
        assertFalse(INCORRECT.isParallel(INCORRECT))
        assertFalse(INCORRECT.isParallel(UP_LEFT))
    }

    @Test
    @Tag("Normal")
    fun hexPointMove() {
        assertEquals(HexPoint(3, 3), HexPoint(0, 3).move(RIGHT, 3))
        assertEquals(HexPoint(3, 5), HexPoint(5, 3).move(UP_LEFT, 2))
        assertEquals(HexPoint(5, 0), HexPoint(5, 4).move(DOWN_LEFT, 4))
        assertEquals(HexPoint(1, 1), HexPoint(1, 1).move(DOWN_RIGHT, 0))
        assertEquals(HexPoint(4, 2), HexPoint(2, 2).move(LEFT, -2))
        assertEquals(HexPoint(769393590, -769393671), HexPoint(477, -558).move(UP_LEFT, -769393113))
        assertThrows(IllegalArgumentException::class.java) {
            HexPoint(0, 0).move(INCORRECT, 0)
        }
    }

    @Test
    @Tag("Hard")
    fun pathBetweenHexes() {
        assertEquals(
            listOf(
                HexPoint(y = 2, x = 2),
                HexPoint(y = 2, x = 3),
                HexPoint(y = 3, x = 3),
                HexPoint(y = 4, x = 3),
                HexPoint(y = 5, x = 3)
            ), pathBetweenHexes(HexPoint(y = 2, x = 2), HexPoint(y = 5, x = 3))
        )
        assertEquals(
            443, pathBetweenHexes(HexPoint(y = -557, x = -561), HexPoint(y = -999, x = -557)).size
        )
    }

    @Test
    @Tag("Impossible")
    fun hexagonByThreePoints() {
        assertEquals(
            1177,
            hexagonByThreePoints(HexPoint(-557, -724), HexPoint(-557, 453), HexPoint(-557, -347))?.radius
        )
        assertEquals(
            442,
            hexagonByThreePoints(HexPoint(-999, -1000), HexPoint(-999, -558), HexPoint(-558, -558))?.radius
        )
        assertEquals(
            819,
            hexagonByThreePoints(HexPoint(-744, 6), HexPoint(-558, -485), HexPoint(-558, -999))?.radius
        )
        assertEquals(
            Hexagon(HexPoint(4, 2), 2),
            hexagonByThreePoints(HexPoint(3, 1), HexPoint(2, 3), HexPoint(4, 4))
        )
        assertNull(
            hexagonByThreePoints(HexPoint(3, 1), HexPoint(2, 3), HexPoint(5, 4))
        )
        assertEquals(
            3,
            hexagonByThreePoints(HexPoint(2, 3), HexPoint(3, 3), HexPoint(5, 3))?.radius
        )
        assertEquals(
            253,
            hexagonByThreePoints(HexPoint(-296, -805), HexPoint(-558, -557), HexPoint(-797, -558))?.radius
        )
        assertEquals(
            1493,
            hexagonByThreePoints(HexPoint(-999, -791), HexPoint(-1000, 494), HexPoint(-999, -1000))?.radius
        )
        assertEquals(
            981,
            hexagonByThreePoints(HexPoint(567, -1000), HexPoint(-959, -558), HexPoint(-557, -455))?.radius
        )
        assertEquals(
            446,
            hexagonByThreePoints(HexPoint(-558, -557), HexPoint(-1000, 331), HexPoint(-558, -522))?.radius
        )
        assertEquals(
            1389,
            hexagonByThreePoints(HexPoint(-999, -558), HexPoint(-1000, -1000), HexPoint(389, -558))?.radius
        )
    }

    @Test
    @Tag("Impossible")
    fun minContainingHexagon() {
        val points = arrayOf(HexPoint(3, 1), HexPoint(3, 2), HexPoint(5, 4), HexPoint(8, 1))
        val result = minContainingHexagon(*points)
        assertEquals(3, result.radius)
        assertTrue(points.all { result.contains(it) })
        val points1 = arrayOf(HexPoint(-557, -557), HexPoint(815, 94), HexPoint(-414, -662), HexPoint(157, 486)
            , HexPoint(-1000, -317), HexPoint(-999, -557), HexPoint(-1000, -584), HexPoint(-999, -74), HexPoint(-999, -1000), HexPoint(-999,-558)
            , HexPoint(-557,-999), HexPoint(-999,-599), HexPoint(791,-745), HexPoint(-835,-558)
            , HexPoint(-58, -557), HexPoint(-999, 387), HexPoint(758, -999), HexPoint(-557, -999), HexPoint(-557, -1000)
            , HexPoint(552, -557), HexPoint(-890, 144), HexPoint(-579, 473), HexPoint(901, -744), HexPoint(-558, -372), HexPoint(608, -557)
            , HexPoint(421, -124), HexPoint(-184, 439), HexPoint(-557, -999), HexPoint(-374, -1000), HexPoint(-1000, -999), HexPoint(460, -25)
            , HexPoint(-1000, -1000), HexPoint(-999, -1000) , HexPoint(-757, 86) , HexPoint(491, 431), HexPoint(-558, -999)
            , HexPoint(-570, -171), HexPoint(-1000, 65), HexPoint(-1000, 117), HexPoint(208, 501), HexPoint(-557, -999)
            , HexPoint(-557, -12), HexPoint(486, 891), HexPoint(245, -1000), HexPoint(-557, -945), HexPoint(533, 884))
        val result1 = minContainingHexagon(*points1)
        assertEquals(1709, result1.radius)
        assertTrue(points1.all { result1.contains(it) })
        val points2 = arrayOf(HexPoint(-557, -558), HexPoint(-558, -999), HexPoint(613, -558), HexPoint(-97, -557)
            , HexPoint(-558, 719), HexPoint(731, -557))
        val result2 = minContainingHexagon(*points2)
        assertEquals(1003, result2.radius)
        assertTrue(points2.all { result2.contains(it) })
    }

}