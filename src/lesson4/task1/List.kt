@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
import kotlin.math.floor
import kotlin.math.min
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
    when {
        y < 0 -> listOf()
        y == 0.0 -> listOf(0.0)
        else -> {
            val root = sqrt(y)
            // Результат!
            listOf(-root, root)
        }
    }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) =
    list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double {
    var sum = 0.0
    for (element in v) sum += sqr(element)
    return sqrt(sum)
}

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double {
    if (list.isEmpty()) return 0.0
    return list.sum() / list.size
}

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    if (list.isEmpty()) return list
    val k = mean(list)
    for (i in 0 until list.size) {
        list[i] = list[i] - k
    }
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.
 */
fun times(a: List<Int>, b: List<Int>): Int {
    var sum = 0
    if (a.isEmpty() || b.isEmpty()) return 0
    val k = min(a.size, b.size)
    for (i in 0 until k) {
        sum += a[i] * b[i]
    }
    return sum
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0 при любом x.
 */
fun polynom(p: List<Int>, x: Int): Int {
    if (p.isEmpty()) return 0
    var sum = 0
    var k = 1
    for (element in p) {
        sum += element * k
        k *= x
    }
    return sum
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Int>): MutableList<Int> {
    if (list.isEmpty()) return list
    var sum = list[0]
    for (i in 1 until list.size) {
        sum += list[i]
        list[i] = sum
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var k = 2
    var a = n
    val m = floor(sqrt(a.toDouble())).toInt()
    val list = mutableListOf<Int>()
    while (a > 1) {
        if (a % k == 0) {
            list.add(k)
            a /= k
            k -= 1
        }
        k += 1
        if (k > m) break
    }
    if (a > 1) list.add(a)
    return list
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    val list = mutableListOf<Int>()
    var a = n
    while (a >= base) {
        list.add(0, a % base)
        a /= base
    }
    list.add(0, a % base)
    return list
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, n.toString(base) и подобные), запрещается.
 */
fun convertToString(n: Int, base: Int): String {
    var a = n
    var numbers = ("")
    while (a >= base) {
        numbers = if (a % base < 10) "${a % base}" + numbers
        else (a % base + 87).toChar() + numbers
        a /= base
    }
    numbers = if (a % base < 10) "${a % base}" + numbers
    else (a % base + 87).toChar() + numbers
    return numbers
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    val k = base.toDouble()
    var a = 0.0
    for (i in 0 until digits.size) {
        a += k.pow(digits.size - i - 1) * digits[i]
    }
    return a.toInt()
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 *
 * Использовать функции стандартной библиотеки, напрямую и полностью решающие данную задачу
 * (например, str.toInt(base)), запрещается.
 */
fun decimalFromString(str: String, base: Int): Int {
    val k = base.toDouble()
    var a = 0.0
    for (i in 0 until str.length) {
        a += if (str[i].toInt() < 58) k.pow(str.length - i - 1) * (str[i].toInt() - 48)
        else k.pow(str.length - i - 1) * (str[i].toInt() - 87)
    }
    return a.toInt()
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun subRoman(n: Int, k: Int): String {
    val listOfNumbers1 = listOf("D", "L", "V")
    val listOfNumbers2 = listOf("CM", "XC", "IX")
    val listOfNumbers3 = listOf("CD", "XL", "IV")
    var numbers = ("")
    if (n / 10.0.pow(2 - k).toInt() >= 5 && n / 10.0.pow(2 - k).toInt() != 9)
        numbers += listOfNumbers1[k]
    when {
        n / 10.0.pow(2 - k).toInt() == 9 -> numbers += listOfNumbers2[k]
        n / 10.0.pow(2 - k).toInt() == 4 -> numbers += listOfNumbers3[k]
        else -> for (i in 1..(n % (5 * 10.0.pow(2 - k)) / 10.0.pow(2 - k)).toInt())
            numbers += listOfNumbers3[k].substring(0, 1)
    }
    return numbers
}

fun roman(n: Int): String {
    var a = n
    var numbers = ("")
    for (i in 1..a / 1000) numbers += "M"
    a %= 1000
    for (i in 0..2) {
        numbers += subRoman(a, i)
        a %= (10.0.pow(2 - i)).toInt()
    }
    return numbers + subRoman(a, 2)
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun end(k: Int): String { // Функция, определяющая название числа
    return when (k) {
        0 -> ""
        1 -> " один"
        2 -> " два"
        3 -> " три"
        4 -> " четыре"
        5 -> " пять"
        6 -> " шесть"
        7 -> " семь"
        8 -> " восемь"
        else -> " девять"
    }
}

fun teens(k: Int): String { // Функция, определяющая название от 10 до 19 или числа
    val l = "надцать"
    return when (k % 100) {
        10 -> "десять"
        11 -> end(k % 10) + l
        12 -> end(k % 10).substring(0, 3) + "е" + l
        13 -> end(k % 10) + l
        14 -> end(k % 10).substring(0, 6) + l
        15 -> end(k % 10).substring(0, 4) + l
        16 -> end(k % 10).substring(0, 5) + l
        17 -> end(k % 10).substring(0, 4) + l
        18 -> end(k % 10).substring(0, 6) + l
        19 -> end(k % 10).substring(0, 6) + l
        else -> end(k % 10)
    }
}

fun dictionary1(a: Int, k: Int): String { // Функция определяющая склонение разрядов числа
    val number = a % 10
    val dictionary = listOf("десят", "сот", " тысяч", " тысячи", "ста")
    if (k == 0) return teens(a)
    if (k == 3 && a in 10..19) return teens(a) + dictionary[2]
    if (number == 3) when (k) { // Определение разрядов 3 (тридцать, триста, три тысячи)
        1 -> return end(number) + "дцать"
        2 -> return end(number) + dictionary[4]
        3 -> return end(number) + " тысячи"
    }
    if (number == 2) when (k) { // Определение разрядов 2
        1 -> return end(number) + "дцать"
        2 -> return end(number).substring(0, 3) + "ести"
        3 -> return end(number).substring(0, 3) + "е" + dictionary[3]
    }
    if (number == 4) when (k) { // Определение разрядов 4
        1 -> return " сорок"
        2 -> return end(number) + dictionary[4]
        3 -> return end(number) + dictionary[3]
    }
    return when { // Определение разрядов оставшихся цифр
        number == 0 && k == 3 -> dictionary[2]
        number == 1 && k == 2 -> " сто"
        number == 1 && k == 3 -> end(number).substring(0, 3) + "на тысяча"
        number == 5 -> end(number) + dictionary[k - 1]
        number == 6 -> end(number) + dictionary[k - 1]
        number == 7 -> end(number) + dictionary[k - 1]
        number == 8 -> end(number) + dictionary[k - 1]
        number == 9 && k == 1 -> end(number).substring(0, 5) + "носто"
        number == 9 && k != 1 -> end(number) + dictionary[k - 1]
        else -> ""
    }
}

fun russian(n: Int): String { // Функция переводящая число в строку
    var number = n
    var k = 0
    var line = ("")
    while (number > 0) {
        line = dictionary1(number % 100, k) + line
        if (k == 0 || k == 3) {
            if (number % 100 in 10..19) {
                number /= 10
                k = 1
            } else if (k == 3) k = 0
        }
        k += 1
        number /= 10
    }
    return line.trim()
}