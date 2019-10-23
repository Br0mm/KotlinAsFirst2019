package lesson8.task1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import java.lang.Math.ulp
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.sqrt

class Tests {
    @Test
    @Tag("Example")
    fun pointDistance() {
        assertEquals(0.0, Point(0.0, 0.0).distance(Point(0.0, 0.0)), 1e-5)
        assertEquals(5.0, Point(3.0, 0.0).distance(Point(0.0, 4.0)), 1e-5)
        assertEquals(50.0, Point(0.0, -30.0).distance(Point(-40.0, 0.0)), 1e-5)
    }

    @Test
    @Tag("Example")
    fun halfPerimeter() {
        assertEquals(6.0, Triangle(Point(0.0, 0.0), Point(0.0, 3.0), Point(4.0, 0.0)).halfPerimeter(), 1e-5)
        assertEquals(2.0, Triangle(Point(0.0, 0.0), Point(0.0, 1.0), Point(0.0, 2.0)).halfPerimeter(), 1e-5)
    }

    @Test
    @Tag("Example")
    fun triangleArea() {
        assertEquals(6.0, Triangle(Point(0.0, 0.0), Point(0.0, 3.0), Point(4.0, 0.0)).area(), 1e-5)
        assertEquals(0.0, Triangle(Point(0.0, 0.0), Point(0.0, 1.0), Point(0.0, 2.0)).area(), 1e-5)
    }

    @Test
    @Tag("Example")
    fun triangleContains() {
        assertTrue(Triangle(Point(0.0, 0.0), Point(0.0, 3.0), Point(4.0, 0.0)).contains(Point(1.5, 1.5)))
        assertFalse(Triangle(Point(0.0, 0.0), Point(0.0, 3.0), Point(4.0, 0.0)).contains(Point(2.5, 2.5)))
    }

    @Test
    @Tag("Example")
    fun segmentEquals() {
        val first = Segment(Point(1.0, 2.0), Point(3.0, 4.0))
        val second = Segment(Point(1.0, 2.0), Point(3.0, 4.0))
        val third = Segment(Point(3.0, 4.0), Point(1.0, 2.0))
        assertEquals(first, second)
        assertEquals(second, third)
        assertEquals(third, first)
    }

    private fun approxEquals(expected: Line, actual: Line, delta: Double): Boolean =
        abs(expected.angle - actual.angle) <= delta && abs(expected.b - actual.b) <= delta

    private fun assertApproxEquals(expected: Line, actual: Line, delta: Double = ulp(10.0)) {
        assertTrue(approxEquals(expected, actual, delta))
    }

    private fun assertApproxNotEquals(expected: Line, actual: Line, delta: Double = ulp(10.0)) {
        assertFalse(approxEquals(expected, actual, delta))
    }

    @Test
    @Tag("Example")
    fun lineEquals() {
        run {
            val first = Line(Point(0.0, 0.0), 0.0)
            val second = Line(Point(3.0, 0.0), 0.0)
            val third = Line(Point(-5.0, 0.0), 0.0)
            val fourth = Line(Point(3.0, 1.0), 0.0)
            assertApproxEquals(first, second)
            assertApproxEquals(second, third)
            assertApproxEquals(third, first)
            assertApproxNotEquals(fourth, first)
        }
        run {
            val first = Line(Point(0.0, 0.0), PI / 2)
            val second = Line(Point(0.0, 3.0), PI / 2)
            val third = Line(Point(0.0, -5.0), PI / 2)
            val fourth = Line(Point(1.0, 3.0), PI / 2)
            assertApproxEquals(first, second)
            assertApproxEquals(second, third)
            assertApproxEquals(third, first)
            assertApproxNotEquals(fourth, first)
        }
        run {
            val first = Line(Point(0.0, 0.0), PI / 4)
            val second = Line(Point(3.0, 3.0), PI / 4)
            val third = Line(Point(-5.0, -5.0), PI / 4)
            val fourth = Line(Point(3.00001, 3.0), PI / 4)
            assertApproxEquals(first, second)
            assertApproxEquals(second, third)
            assertApproxEquals(third, first)
            assertApproxNotEquals(fourth, first)
        }
    }

    @Test
    @Tag("Example")
    fun triangleEquals() {
        val first = Triangle(Point(0.0, 0.0), Point(3.0, 0.0), Point(0.0, 4.0))
        val second = Triangle(Point(0.0, 0.0), Point(0.0, 4.0), Point(3.0, 0.0))
        val third = Triangle(Point(0.0, 4.0), Point(0.0, 0.0), Point(3.0, 0.0))
        val fourth = Triangle(Point(0.0, 4.0), Point(0.0, 3.0), Point(3.0, 0.0))
        assertEquals(first, second)
        assertEquals(second, third)
        assertEquals(third, first)
        assertNotEquals(fourth, first)
    }

    @Test
    @Tag("Easy")
    fun circleDistance() {
        assertEquals(0.0, Circle(Point(0.0, 0.0), 1.0).distance(Circle(Point(1.0, 0.0), 1.0)), 1e-5)
        assertEquals(0.0, Circle(Point(0.0, 0.0), 1.0).distance(Circle(Point(0.0, 2.0), 1.0)), 1e-5)
        assertEquals(1.0, Circle(Point(0.0, 0.0), 1.0).distance(Circle(Point(-4.0, 0.0), 2.0)), 1e-5)
        assertEquals(2.0 * sqrt(2.0) - 2.0, Circle(Point(0.0, 0.0), 1.0).distance(Circle(Point(2.0, 2.0), 1.0)), 1e-5)
    }

    @Test
    @Tag("Trivial")
    fun circleContains() {
        val center = Point(1.0, 2.0)
        assertTrue(Circle(center, 1.0).contains(center))
        assertFalse(Circle(center, 2.0).contains(Point(0.0, 0.0)))
        assertTrue(Circle(Point(0.0, 3.0), 5.01).contains(Point(-4.0, 0.0)))
    }

    @Test
    @Tag("Normal")
    fun diameter() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(1.0, 4.0)
        val p3 = Point(-2.0, 2.0)
        val p4 = Point(3.0, -1.0)
        val p5 = Point(-3.0, -2.0)
        val p6 = Point(0.0, 5.0)
        assertEquals(Segment(p5, p6), diameter(p1, p2, p3, p4, p5, p6))
        assertEquals(Segment(p4, p6), diameter(p1, p2, p3, p4, p6))
        assertEquals(Segment(p3, p4), diameter(p1, p2, p3, p4))
        assertEquals(Segment(p2, p4), diameter(p1, p2, p4))
        assertEquals(Segment(p1, p4), diameter(p1, p4))
    }

    @Test
    @Tag("Easy")
    fun circleByDiameter() {
        assertEquals(Circle(Point(0.0, 1.0), 1.0), circleByDiameter(Segment(Point(0.0, 0.0), Point(0.0, 2.0))))
        assertEquals(Circle(Point(2.0, 1.5), 2.5), circleByDiameter(Segment(Point(4.0, 0.0), Point(0.0, 3.0))))
    }

    @Test
    @Tag("Normal")
    fun crossPoint() {
        assertTrue(
            Point(2.0, 3.0).distance(
                Line(Point(2.0, 0.0), PI / 2).crossPoint(
                    Line(Point(0.0, 3.0), 0.0)
                )
            ) < 1e-5
        )
        assertTrue(
            Point(2.0, 2.0).distance(
                Line(Point(0.0, 0.0), PI / 4).crossPoint(
                    Line(Point(0.0, 4.0), 3 * PI / 4)
                )
            ) < 1e-5
        )
        val p = Point(1.0, 3.0)
        assertTrue(p.distance(Line(p, 1.0).crossPoint(Line(p, 2.0))) < 1e-5)
    }

    @Test
    @Tag("Normal")
    fun lineBySegment() {
        assertApproxEquals(Line(Point(0.0, 0.0), 0.0), lineBySegment(Segment(Point(0.0, 0.0), Point(7.0, 0.0))))
        assertApproxEquals(Line(Point(0.0, 0.0), PI / 2), lineBySegment(Segment(Point(0.0, 0.0), Point(0.0, 8.0))))
        assertApproxEquals(Line(Point(1.0, 1.0), PI / 4), lineBySegment(Segment(Point(1.0, 1.0), Point(3.0, 3.0))))
    }

    @Test
    @Tag("Normal")
    fun lineByPoint() {
        assertApproxEquals(Line(Point(0.0, 0.0), PI / 2), lineByPoints(Point(0.0, 0.0), Point(0.0, 2.0)))
        assertApproxEquals(Line(Point(1.0, 1.0), PI / 4), lineByPoints(Point(1.0, 1.0), Point(3.0, 3.0)))
    }

    @Test
    @Tag("Hard")
    fun bisectorByPoints() {
        assertApproxEquals(Line(Point(2.0, 0.0), PI / 2), bisectorByPoints(Point(0.0, 0.0), Point(4.0, 0.0)))
        assertApproxEquals(Line(Point(1.0, 2.0), 0.0), bisectorByPoints(Point(1.0, 5.0), Point(1.0, -1.0)))
        assertApproxEquals(Line(Point(0.0, 1.0), 0.0), bisectorByPoints(Point(0.0, 0.0), Point(0.0, 2.0)))
        assertApproxEquals(Line(Point(2.0, 2.0), 3 * PI / 4), bisectorByPoints(Point(1.0, 1.0), Point(3.0, 3.0)))
    }

    @Test
    @Tag("Normal")
    fun findNearestCirclePair() {
        val c1 = Circle(Point(0.0, 0.0), 1.0)
        val c2 = Circle(Point(3.0, 0.0), 5.0)
        val c3 = Circle(Point(-5.0, 0.0), 2.0)
        val c4 = Circle(Point(0.0, 7.0), 3.0)
        val c5 = Circle(Point(0.0, -6.0), 4.0)
        assertEquals(Pair(c1, c5), findNearestCirclePair(c1, c3, c4, c5))
        assertEquals(Pair(c2, c4), findNearestCirclePair(c2, c4, c5))
        assertEquals(Pair(c1, c2), findNearestCirclePair(c1, c2, c4, c5))
    }

    @Test
    @Tag("Hard")
    fun circleByThreePoints() {
        val result = circleByThreePoints(Point(5.0, 0.0), Point(3.0, 4.0), Point(0.0, -5.0))
        assertTrue(result.center.distance(Point(0.0, 0.0)) < 1e-5)
        assertEquals(5.0, result.radius, 1e-5)
        val result2 = circleByThreePoints(Point(-632.0, 0.8750309638156738), Point(-632.0, -632.0), Point(0.0, -632.0))
        assertTrue(result2.center.distance(Point(-316.0, -315.56248451809216)) < 1e-5)
        assertEquals(447.2009628839843, result2.radius, 1e-5)
        val result3 = circleByThreePoints(Point(0.9596800656722041, -632.0), Point(0.4276079793575096, 0.42917041708900383), Point(-632.0, -632.0))
        assertTrue(result3.center.distance(Point(-315.5201599671639, -316.05145017736174)) < 1e-5)
        assertEquals(447.19456088176844, result3.radius, 1e-5)
    }

    @Test
    @Tag("Impossible")
    fun minContainingCircle() {
        val p1 = Point(0.0, 0.0)
        val p2 = Point(1.0, 4.0)
        val p3 = Point(-2.0, 2.0)
        val p4 = Point(3.0, -1.0)
        val p5 = Point(-3.0, -2.0)
        val p6 = Point(0.0, 5.0)
        val p7 = Point(0.4222382059399671, 0.5408588215642615)
        val p8 = Point(0.0, 0.6568979111122544)
        val p9 = Point(-632.0, -632.0)
        val p10 = Point(0.034953694026517934, 0.4133661838367574)
        val p11 = Point(5e-324, -632.0)
        val p12 = Point(0.08167235368510883, 0.4016887672933893)
        val p13 = Point(0.44462722003081234, -632.0)
        val p14 = Point(0.4344918662559768, -632.0)
        val k1 = Point(0.03128417932649208, -632.0)
        val k2 = Point(-632.0, -632.0)
        val k3 = Point(0.019549680403975978, -632.0)
        val k4 = Point(0.19543566049188765, -5e-324)
        val k5 = Point(0.0, 0.4466509554000647)
        val k6 = Point(0.7321531495669465, 0.7717831569880458)
        val k7 = Point(0.2095033591332569, 0.9140841180298453)
        val k8 = Point(-632.0, -632.0)
        val k9 = Point(-5e-324, 0.0)
        val k10 = Point(0.14716733037626106, 0.0)
        val k11 = Point(0.7953580459169863, 0.936341682399648)
        val k12 = Point(-632.0, 0.5162637720026494)
        val k13 = Point(0.46647976441109884, 0.7043756958623997)
        val k14 = Point(0.0, 0.28258360374231173)
        val k15 = Point(-632.0, 0.9847032973143528)
        val k16 = Point(0.5980526910769118, 0.13667053742500612)
        val k17 = Point(0.6199548702410832, -632.0)
        val l1 = Point(-632.0, 0.7791602553551887)
        val l2 = Point(-2.220446049250313e-16, 0.15523431403017574)
        val l3 = Point(-2.220446049250313e-16, 5e-324)
        val l4 = Point(0.0, 0.3806595574287366)
        val l5 = Point(0.0972150294192391, 0.5429851045999374)
        val l6 = Point(0.035100267215476544, 0.6015781700543198)
        val l7 = Point(0.5951486769203157, -632.0)
        val l8 = Point(-632.0, -2.220446049250313e-16)
        val l9 = Point(0.3272243779785877, 0.9258844735408748)
        val l10 = Point(0.04015295899133631, 0.20725159709070462)
        val l11 = Point(-2.220446049250313e-16, 0.10455107167550148)
        val l12 = Point(0.9818710793443822, 0.19663688386649303)
        val l13 = Point(0.3647748641788858, 0.791063801087922)
        val l14 = Point(-2.220446049250313e-16, 0.13413954859567823)
        val result = minContainingCircle(p1, p2, p3, p4, p5, p6)
        assertEquals(4.0, result.radius, 0.02)
        for (p in listOf(p1, p2, p3, p4, p5, p6)) {
            assertTrue(result.contains(p))
        }
        val result1 = minContainingCircle(p7, p8, p9, p10, p11, p12, p13, p14)
        assertEquals(447.23199417597584, result1.radius, 0.02)
        for (p in listOf(p7, p8, p9, p10, p11, p12, p13, p14)) {
            assertTrue(result1.contains(p))
        }
        val result2 = minContainingCircle(k1, k2, k3, k4, k5, k6, k7, k8, k9, k10, k11, k12, k13, k14, k15, k16, k17)
        assertEquals(447.50373810324015, result2.radius, 0.02)
        for (p in listOf(k1, k2, k3, k4, k5, k6, k7, k8, k9, k10, k11, k12, k13, k14, k15, k16, k17)) {
            assertTrue(result2.contains(p))
        }
        val result3 = minContainingCircle(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14)
        assertEquals(447.3773820230096, result3.radius, 2e-6)
        for (p in listOf(l1, l2, l3, l4, l5, l6, l7, l8, l9, l10, l11, l12, l13, l14)) {
            assertTrue(result3.contains(p))
        }
    }
}