@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import kotlin.math.pow

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val monthNames = listOf(
    "января", "февраля", "марта", "апреля", "мая", "июня",
    "июля", "августа", "сентября", "октября", "ноября", "декабря"
)

fun dateStrToDigit(str: String): String {
    if (!str.matches(Regex("""^\d+\s([а-я]{3,8})\s\d+$"""))) return ("")
    val parts = str.split(" ")
    val days = parts[0].toInt()
    val years = parts[2].toInt()
    val month: Int
    if (!monthNames.contains(parts[1])) return "" else month = monthNames.indexOf(parts[1]) + 1
    if (days > daysInMonth(month, years)) return ""
    return String.format("%02d.%02d.%d", days, month, years)
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    if (!digital.matches(Regex("""^\d+\.\d+\.\d+$"""))) return ("")
    val parts = digital.split(".")
    val days = parts[0].toInt()
    val numberOfMonth = parts[1].toInt()
    val month: String
    if (numberOfMonth in 1..12) month = monthNames[numberOfMonth - 1] else return ""
    if (days > daysInMonth(numberOfMonth, parts[2].toInt())) return ""
    return String.format("%d %s %s", days, month, parts[2])
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    if (phone.matches(Regex("""^\+?\s*$"""))) return ""
    if (!phone.matches(Regex("""^\+?\s*\d*\s*(\(?[ 0-9\-]+\)?)?[ 0-9\-]*$"""))) return ""
    if (phone.contains("(") && !phone.contains(")") ||
        !phone.contains("(") && phone.contains(")")
    ) return ""
    return Regex("""[ ()\-]""").split(phone).joinToString(separator = "")
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (!"$jumps ".matches(Regex("""^((\d+|%|-)\s)*$"""))) return -1
    val parts = Regex("""[ %\-]""").split(jumps)
    var max = -1
    for (i in 0 until parts.size) {
        if (parts[i] != "" && parts[i].toInt() > max)
            max = parts[i].toInt()
    }
    return max
}

/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    if (!"$jumps ".matches(Regex("""^(\d+\s([+\-%])+\s)*$"""))) return -1
    val parts = Regex("""[ ]""").split(jumps)
    var max = -1
    for (i in 0 until parts.size - 1) {
        if (parts[i].matches(Regex("""^\d+$""")) && parts[i + 1].contains("+") && parts[i].toInt() > max)
            max = parts[i].toInt()
    }
    return max
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    require("$expression + ".matches(Regex("""^(\d+\s[+-]\s)*$""")))
    val parts = Regex("""[ ]""").split(expression)
    var result = parts[0].toInt()
    for (i in 1 until parts.size - 1 step 2) {
        if (parts[i + 1].contains("+") && parts[i + 1].contains("-"))
            throw IllegalArgumentException(expression)
        when {
            parts[i] == "+" -> result += parts[i + 1].toInt()
            parts[i] == "-" -> result -= parts[i + 1].toInt()
            else -> throw IllegalArgumentException(expression)
        }
    }
    return result
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val parts = Regex("""[ ]""").split(str)
    var result = 0
    for (i in 0 until parts.size - 1) {
        if (parts[i].toLowerCase() == parts[i + 1].toLowerCase()) return result
        else result += parts[i].length + 1
    }
    return -1
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    if (description.isEmpty()) return ""
    val line = "$description; "
    if (!line.matches(Regex("""^(.+\s\d+\.?\d*;\s)*$"""))) return ""
    val parts = Regex("""[; ]""").split(description)
    var maxPrice = parts[1].toDouble()
    var nameOfProduct = parts[0]
    for (i in 3 until parts.size - 1 step 3) {
        if (parts[i + 1].toDouble() > maxPrice) {
            maxPrice = parts[i + 1].toDouble()
            nameOfProduct = parts[i]
        }
    }
    return nameOfProduct
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if (roman.isEmpty()) return -1
    if (!roman.matches(Regex("""^(M{0,4})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$"""))) return -1
    var result = 0
    val parts =
        Regex("""^(M{0,4})(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$""").find(roman)!!.groupValues
    for (i in 1 until parts.size) {
        result += if (parts[i].contains("M") && !parts[i].contains("C"))
            parts[i].length * 1000 else subFromRoman(parts[i])
    }
    return result
}

fun subFromRoman(str: String): Int {
    if (str.isEmpty()) return 0
    val listOfNumbers1 = listOf("IX", "XC", "CM")
    val listOfNumbers2 = listOf("IV", "XL", "CD")
    val listOfNumbers3 = listOf("V", "L", "D")
    val listOfNumbers4 = listOf("I", "X", "C")
    val numbers = StringBuilder().append(str)
    var result = 0
    if (str in listOfNumbers1) {
        return (10.0.pow(listOfNumbers1.indexOf(str))).toInt() * 9
    }
    if (str in listOfNumbers2) {
        return (10.0.pow(listOfNumbers2.indexOf(str))).toInt() * 4
    }
    if (str[0].toString() in listOfNumbers3) {
        result += (10.0.pow(listOfNumbers3.indexOf(str[0].toString()))).toInt() * 5
        numbers.deleteCharAt(0)
        if (numbers.isEmpty()) return result
    }
    return result + (10.0.pow(listOfNumbers4.indexOf(numbers[0].toString()))).toInt() * numbers.length
}


/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    val tape = MutableList(cells) { 0 }
    if (commands.isEmpty()) return tape
    if (!commands.matches(Regex("""^[ ><+-\[\]]*[^=]$"""))) throw IllegalArgumentException()
    var bracketCounter = 0
    for (char in commands) {
        if (char == '[') bracketCounter++
        if (char == ']') bracketCounter--
        if (bracketCounter < 0) throw IllegalArgumentException()
    }
    if (bracketCounter != 0) throw IllegalArgumentException()
    var positionOfSensor = cells / 2
    var positionOfCommand = 0
    var numberOfCommands = 0

    fun cycles(symbol: Char): Int { // подфункция для выполненния операций, когда соманда '[' или ']'
        var iterator = 0
        if (symbol == '[' && tape[positionOfSensor] != 0) return positionOfCommand
        if (symbol == ']' && tape[positionOfSensor] == 0) return positionOfCommand
        if (symbol == '[' && tape[positionOfSensor] == 0) {
            for (i in positionOfCommand + 1 until commands.length) {
                if (commands[i] == '[') iterator++
                if (commands[i] == ']') {
                    if (iterator == 0) return i
                    else iterator--
                }
            }
        }
        if (symbol == ']' && tape[positionOfSensor] != 0) {
            for (i in (positionOfCommand - 1) downTo 0) {
                if (commands[i] == ']') iterator++
                if (commands[i] == '[') {
                    if (iterator == 0) return i
                    else iterator--
                }
            }
        }
        return 0
    }

    while (numberOfCommands < limit && positionOfCommand < commands.length) {
        numberOfCommands++
        when (commands[positionOfCommand]) {
            '>' -> positionOfSensor++
            '<' -> positionOfSensor--
            '+' -> tape[positionOfSensor]++
            '-' -> tape[positionOfSensor]--
            ' ' -> numberOfCommands
            else -> positionOfCommand =
                cycles(commands[positionOfCommand])
        }
        positionOfCommand++
        if (positionOfSensor > tape.size - 1 || positionOfSensor < 0) throw IllegalStateException()
    }
    return tape
}
