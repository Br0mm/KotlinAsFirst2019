@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import kotlin.math.*

/**
 * Точка (гекс) на шестиугольной сетке.
 * Координаты заданы как в примере (первая цифра - y, вторая цифра - x)
 *
 *       60  61  62  63  64  65
 *     50  51  52  53  54  55  56
 *   40  41  42  43  44  45  46  47
 * 30  31  32  33  34  35  36  37  38
 *   21  22  23  24  25  26  27  28
 *     12  13  14  15  16  17  18
 *       03  04  05  06  07  08
 *
 * В примерах к задачам используются те же обозначения точек,
 * к примеру, 16 соответствует HexPoint(x = 6, y = 1), а 41 -- HexPoint(x = 1, y = 4).
 *
 * В задачах, работающих с шестиугольниками на сетке, считать, что они имеют
 * _плоскую_ ориентацию:
 *  __
 * /  \
 * \__/
 *
 * со сторонами, параллельными координатным осям сетки.
 *
 * Более подробно про шестиугольные системы координат можно почитать по следующей ссылке:
 *   https://www.redblobgames.com/grids/hexagons/
 */
data class HexPoint(val x: Int, val y: Int) {
    /**
     * Средняя
     *
     * Найти целочисленное расстояние между двумя гексами сетки.
     * Расстояние вычисляется как число единичных отрезков в пути между двумя гексами.
     * Например, путь межу гексами 16 и 41 (см. выше) может проходить через 25, 34, 43 и 42 и имеет длину 5.
     */
    fun distance(other: HexPoint): Int =
        if (((x - other.x) < 0 && (y - other.y) < 0) || ((x - other.x) > 0 && (y - other.y) > 0))
            abs(x - other.x) + abs(y - other.y)
        else max(abs(x - other.x), abs(y - other.y))

    override fun toString(): String = "$y.$x"
}

/**
 * Правильный шестиугольник на гексагональной сетке.
 * Как окружность на плоскости, задаётся центральным гексом и радиусом.
 * Например, шестиугольник с центром в 33 и радиусом 1 состоит из гексов 42, 43, 34, 24, 23, 32.
 */
data class Hexagon(val center: HexPoint, val radius: Int) {

    /**
     * Средняя
     *
     * Рассчитать расстояние между двумя шестиугольниками.
     * Оно равно расстоянию между ближайшими точками этих шестиугольников,
     * или 0, если шестиугольники имеют общую точку.
     *
     * Например, расстояние между шестиугольником A с центром в 31 и радиусом 1
     * и другим шестиугольником B с центром в 26 и радиуоом 2 равно 2
     * (расстояние между точками 32 и 24)
     */
    fun distance(other: Hexagon): Int {
        val answer = center.distance(other.center) - (radius + other.radius)
        return if (answer > 0) answer
        else 0
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если заданная точка находится внутри или на границе шестиугольника
     */
    fun contains(point: HexPoint): Boolean = center.distance(point) - radius <= 0
}

/**
 * Прямолинейный отрезок между двумя гексами
 */
class HexSegment(val begin: HexPoint, val end: HexPoint) {
    /**
     * Простая
     *
     * Определить "правильность" отрезка.
     * "Правильным" считается только отрезок, проходящий параллельно одной из трёх осей шестиугольника.
     * Такими являются, например, отрезок 30-34 (горизонталь), 13-63 (прямая диагональ) или 51-24 (косая диагональ).
     * А, например, 13-26 не является "правильным" отрезком.
     */
    fun isValid(): Boolean {
        var flag = (begin.y == end.y) && (begin.x != end.x) || (begin.x == end.x) && (begin.y != end.y)
        if (!flag) {
            if (((begin.x - end.x) > 0 && (begin.y - end.y) < 0) || ((begin.x - end.x) < 0 && (begin.y - end.y) > 0))
                flag = (abs(begin.x - end.x) == abs(begin.y - end.y))
        }
        return flag
    }

    /**
     * Средняя
     *
     * Вернуть направление отрезка (см. описание класса Direction ниже).
     * Для "правильного" отрезка выбирается одно из первых шести направлений,
     * для "неправильного" -- INCORRECT.
     */
    fun direction(): Direction {
        if (!isValid()) return Direction.INCORRECT
        val differenceX = end.x - begin.x
        val differenceY = end.y - begin.y
        return when {
            differenceX == 0 -> {
                if (differenceY > 0) Direction.UP_RIGHT
                else Direction.DOWN_LEFT
            }
            differenceY == 0 -> {
                if (differenceX > 0) Direction.RIGHT
                else Direction.LEFT
            }
            differenceX > differenceY -> Direction.DOWN_RIGHT
            else -> Direction.UP_LEFT
        }
    }

    override fun equals(other: Any?) =
        other is HexSegment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
        begin.hashCode() + end.hashCode()
}

/**
 * Направление отрезка на гексагональной сетке.
 * Если отрезок "правильный", то он проходит вдоль одной из трёх осей шестугольника.
 * Если нет, его направление считается INCORRECT
 */
enum class Direction {
    RIGHT,      // слева направо, например 30 -> 34
    UP_RIGHT,   // вверх-вправо, например 32 -> 62
    UP_LEFT,    // вверх-влево, например 25 -> 61
    LEFT,       // справа налево, например 34 -> 30
    DOWN_LEFT,  // вниз-влево, например 62 -> 32
    DOWN_RIGHT, // вниз-вправо, например 61 -> 25
    INCORRECT;  // отрезок имеет изгиб, например 30 -> 55 (изгиб в точке 35)

    /**
     * Простая
     *
     * Вернуть направление, противоположное данному.
     * Для INCORRECT вернуть INCORRECT
     */
    fun opposite(): Direction {
        return when (ordinal) {
            0 -> LEFT
            1 -> DOWN_LEFT
            2 -> DOWN_RIGHT
            3 -> RIGHT
            4 -> UP_RIGHT
            5 -> UP_LEFT
            else -> INCORRECT
        }
    }

    /**
     * Средняя
     *
     * Вернуть направление, повёрнутое относительно
     * заданного на 60 градусов против часовой стрелки.
     *
     * Например, для RIGHT это UP_RIGHT, для UP_LEFT это LEFT, для LEFT это DOWN_LEFT.
     * Для направления INCORRECT бросить исключение IllegalArgumentException.
     * При решении этой задачи попробуйте обойтись без перечисления всех семи вариантов.
     */
    fun next(): Direction {
        if (ordinal == 6) throw IllegalArgumentException()
        return values()[(ordinal + 1) % 6]
    }

    /**
     * Простая
     *
     * Вернуть true, если данное направление совпадает с other или противоположно ему.
     * INCORRECT не параллельно никакому направлению, в том числе другому INCORRECT.
     */
    fun isParallel(other: Direction): Boolean {
        if (ordinal == 6 || other.ordinal == 6) return false
        return ordinal == other.ordinal || ordinal == (other.ordinal + 3) % 6
    }
}

/**
 * Средняя
 *
 * Сдвинуть точку в направлении direction на расстояние distance.
 * Бросить IllegalArgumentException(), если задано направление INCORRECT.
 * Для расстояния 0 и направления не INCORRECT вернуть ту же точку.
 * Для отрицательного расстояния сдвинуть точку в противоположном направлении на -distance.
 *
 * Примеры:
 * 30, direction = RIGHT, distance = 3 --> 33
 * 35, direction = UP_LEFT, distance = 2 --> 53
 * 45, direction = DOWN_LEFT, distance = 4 --> 05
 */
fun HexPoint.move(direction: Direction, distance: Int): HexPoint {
    if (direction == Direction.INCORRECT) throw IllegalArgumentException()
    if (distance == 0) return HexPoint(x, y)
    var way = distance
    if (direction.ordinal > 2) way *= -1
    return when (direction.ordinal % 3) {
        0 -> HexPoint(x + way, y)
        1 -> HexPoint(x, y + way)
        else -> HexPoint(x - way, y + way)
    }
}

/**
 * Сложная
 *
 * Найти кратчайший путь между двумя заданными гексами, представленный в виде списка всех гексов,
 * которые входят в этот путь.
 * Начальный и конечный гекс также входят в данный список.
 * Если кратчайших путей существует несколько, вернуть любой из них.
 *
 * Пример (для координатной сетки из примера в начале файла):
 *   pathBetweenHexes(HexPoint(y = 2, x = 2), HexPoint(y = 5, x = 3)) ->
 *     listOf(
 *       HexPoint(y = 2, x = 2),
 *       HexPoint(y = 2, x = 3),
 *       HexPoint(y = 3, x = 3),
 *       HexPoint(y = 4, x = 3),
 *       HexPoint(y = 5, x = 3)
 *     )
 */
fun pathBetweenHexes(from: HexPoint, to: HexPoint): List<HexPoint> {
    var x = from.x
    var y = from.y
    val answer = mutableListOf(from)
    while (x != to.x || y != to.y) {
        if (((to.x - x) > 0 && (to.y - y) < 0) || ((to.x - x) < 0 && (to.y - y) > 0)) {
            x += (to.x - x) / abs(to.x - x)
            y += (to.y - y) / abs(to.y - y)
            answer.add(HexPoint(x, y))
            continue
        }
        if ((to.x - x) != 0) {
            x += (to.x - x) / abs(to.x - x)
            answer.add(HexPoint(x, y))
            continue
        } else {
            y += (to.y - y) / abs(to.y - y)
            answer.add(HexPoint(x, y))
            continue
        }
    }
    return answer
}

/**
 * Очень сложная
 *
 * Дано три точки (гекса). Построить правильный шестиугольник, проходящий через них
 * (все три точки должны лежать НА ГРАНИЦЕ, а не ВНУТРИ, шестиугольника).
 * Все стороны шестиугольника должны являться "правильными" отрезками.
 * Вернуть null, если такой шестиугольник построить невозможно.
 * Если шестиугольников существует более одного, выбрать имеющий минимальный радиус.
 *
 * Пример: через точки 13, 32 и 44 проходит правильный шестиугольник с центром в 24 и радиусом 2.
 * Для точек 13, 32 и 45 такого шестиугольника не существует.
 * Для точек 32, 33 и 35 следует вернуть шестиугольник радиусом 3 (с центром в 62 или 05).
 *
 * Если все три точки совпадают, вернуть шестиугольник нулевого радиуса с центром в данной точке.
 */
fun hexagonByThreePoints(a: HexPoint, b: HexPoint, c: HexPoint): Hexagon? {
    var maxR = maxOf(a.distance(b), b.distance(c), a.distance(c))
    var minR = maxOf(a.distance(b), b.distance(c), a.distance(c)) / 2
    val hexagonOfPoints = mutableMapOf<HexPoint, Int>()
    var hexagonOfA: MutableSet<HexPoint>
    var hexagonOfB: MutableSet<HexPoint>
    var hexagonOfC: MutableSet<HexPoint>
    val hexPoints = listOf(a, b, c)
    var currentHexPoint: HexPoint
    var center = HexPoint(-10, -10)
    var radius: List<Int>

    fun circleOfHexes(
        i: Int,
        currentHexPoint: HexPoint
    ): MutableSet<HexPoint> { //функция, находящая какие гексы составляют окружность
        val hexes = mutableSetOf<HexPoint>()
        var point = currentHexPoint
        for (j in 0..5) {
            for (k in 0 until i) {
                point = point.move(Direction.values()[j], 1)
                hexes.add(point)
            }
        }
        return hexes
    }

    fun discoveringTheRadius(minR: Int, maxR: Int): List<Int> { //функция, уменьшающая дистанцию между радиусами
        val test1: Int
        val test2: Int
        val test3: Int
        val test4: Set<HexPoint>
        val test5: Set<HexPoint>
        val test6: Set<HexPoint>
        val test7: Int
        val test8: Int
        val test9: Int
        val test10: HexPoint
        val test11: HexPoint
        val test12: HexPoint
        val i = minR + ((maxR - minR) / 2)
        currentHexPoint = a.move(Direction.DOWN_LEFT, i)
        hexagonOfA = circleOfHexes(i, currentHexPoint)
        currentHexPoint = b.move(Direction.DOWN_LEFT, i)
        hexagonOfB = circleOfHexes(i, currentHexPoint)
        currentHexPoint = c.move(Direction.DOWN_LEFT, i)
        hexagonOfC = circleOfHexes(i, currentHexPoint)
        test4 = hexagonOfA.intersect(hexagonOfB)
        test7 = a.distance(b)
        test5 = hexagonOfA.intersect(hexagonOfC)
        test8 = a.distance(c)
        test6 = hexagonOfB.intersect(hexagonOfC)
        test9 = b.distance(c)


        if (a.distance(test6.elementAt(0)) > a.distance(test6.elementAt(1))) test10 = test6.elementAt(1)
        else test10 = test6.elementAt(0)
        if (b.distance(test5.elementAt(0)) > b.distance(test5.elementAt(1))) test11 = test5.elementAt(1)
        else test11 = test5.elementAt(0)
        if (c.distance(test4.elementAt(0)) > c.distance(test4.elementAt(1))) test12 = test4.elementAt(1)
        else test12 = test4.elementAt(0)
        when {
            test4.size <= 2 && test5.size <= 2 && test6.size <= 2 -> {
                return if (Hexagon(a, i).contains(test10) && Hexagon(b, i).contains(test11) && Hexagon(c, i).contains(test12)) listOf(minR, i)
                else listOf(i, maxR)
            }
            test4.size <= 2 && test5.size <= 2 && test6.size > 2 -> {
                return if (Hexagon(b, i).contains(test11) && Hexagon(c, i).contains(test12)) listOf(minR, i)
                else listOf(i, maxR)
            }
            test4.size <= 2 && test5.size > 2 && test6.size <= 2 -> {
                return if (Hexagon(a, i).contains(test10) && Hexagon(c, i).contains(test12)) listOf(minR, i)
                else listOf(i, maxR)
            }
            test4.size > 2 && test5.size <= 2 && test6.size <= 2 -> {
                return if (Hexagon(a, i).contains(test10) && Hexagon(b, i).contains(test11)) listOf(minR, i)
                else listOf(i, maxR)
            }
            else -> return listOf(i, maxR)
        }
    }

    fun finalAnswer(minR: Int, maxR: Int): Hexagon? {
        for (k in minR..maxR) {
            hexagonOfPoints.clear()
            for (point in hexPoints) {
                currentHexPoint = point.move(Direction.DOWN_LEFT, k)
                for (j in 0..5) {
                    for (n in 0 until k) {
                        currentHexPoint = currentHexPoint.move(Direction.values()[j], 1)
                        hexagonOfPoints[currentHexPoint] = hexagonOfPoints.getOrPut(currentHexPoint, { 0 }) + 1
                    }
                }
            }
            if (hexagonOfPoints.containsValue(3)) {
                for ((key, value) in hexagonOfPoints)
                    if (value == 3) center = key
                return Hexagon(center, center.distance(a))
            }
        }
        return null
    }

    while (maxR - minR > 3) {
        radius = discoveringTheRadius(minR, maxR)
        minR = radius[0]
        maxR = radius[1]
    }

    return finalAnswer(minR, maxR)
}

/**
 * Очень сложная
 *
 * Дано множество точек (гексов). Найти правильный шестиугольник минимального радиуса,
 * содержащий все эти точки (безразлично, внутри или на границе).
 * Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит один гекс, вернуть шестиугольник нулевого радиуса с центром в данной точке.
 *
 * Пример: 13, 32, 45, 18 -- шестиугольник радиусом 3 (с центром, например, в 15)
 */
fun minContainingHexagon(vararg points: HexPoint): Hexagon = TODO()



