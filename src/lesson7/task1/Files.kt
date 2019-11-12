@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import java.lang.StringBuilder
import kotlin.math.pow

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val answer = mutableMapOf<String, Int>()
    val setOfSubstrings = substrings.toSet()
    for (str in setOfSubstrings) {
        answer.getOrPut(str, { 0 })
        for (line in File(inputName).readLines()) {
            for (i in 0..line.length - str.length) {
                if (line.substring(i, i + str.length).toLowerCase().contains(str.toLowerCase()))
                    answer[str] = answer[str]!! + 1
            }
        }
    }
    return answer
}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val dictionary = setOf('ж', 'ч', 'ш', 'щ')
    File(outputName).bufferedWriter().use {
        var needChange: Boolean
        for (line in File(inputName).readLines()) {
            needChange = false
            for (char in line) {
                var str = char
                if (needChange) {
                    str = when (char) {
                        'Я' -> 'А'
                        'я' -> 'а'
                        'Ы' -> 'И'
                        'ы' -> 'и'
                        'Ю' -> 'У'
                        'ю' -> 'у'
                        else -> char
                    }
                    needChange = false
                }
                needChange = dictionary.contains(char.toLowerCase())
                it.write("$str")
            }
            it.newLine()
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use {
        var biggestLength = 0
        var str: String
        for (line in File(inputName).readLines()) {
            if (line.trim().length > biggestLength) biggestLength = line.trim().length
        }
        for (line in File(inputName).readLines()) {
            str = line.trim()
            for (i in 1..(biggestLength - str.length) / 2) it.write(" ")
            it.write(str)
            it.newLine()
        }
    }
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    File(outputName).bufferedWriter().use {
        val wordsInLine = mutableListOf<Int>()
        val lengthOfLine = mutableListOf<Int>()
        var counterOfWords: Int
        var index = 0
        var counterOfAddedSpaces: Int
        val biggestLength: Int
        var str: String
        for (line in File(inputName).readLines()) {
            wordsInLine.add(index, 0)
            lengthOfLine.add(index, 0)
            for (word in line.split(" ")) {
                if (word.isEmpty()) continue
                lengthOfLine[index] += word.length + 1
                wordsInLine[index]++
            }
            if (lengthOfLine[index] > 0) lengthOfLine[index]--
            index++
        }
        biggestLength = lengthOfLine.max() ?: 0
        index = 0
        for (line in File(inputName).readLines()) {
            str = line.trim()
            counterOfAddedSpaces = 0
            counterOfWords = 0
            for (word in str.split(" ")) {
                if (word.isEmpty()) continue
                counterOfWords++
                it.write(word)
                if (counterOfWords < wordsInLine[index]) {
                    it.write(" ")
                    for (i in 1..(biggestLength - lengthOfLine[index]) / (wordsInLine[index] - 1))
                        it.write(" ")
                    if ((biggestLength - lengthOfLine[index]) % (wordsInLine[index] - 1) > counterOfAddedSpaces) {
                        it.write(" ")
                        counterOfAddedSpaces++
                    }
                }
            }
            it.newLine()
            index++
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    var allWords = mutableMapOf<String, Int>()
    for (line in File(inputName).readLines()) {
        for (word in line.split(Regex("""[^a-zA-ZА-Яа-яёЁ]"""))) {
            if (word.isEmpty()) continue
            allWords[word.toLowerCase()] = allWords.getOrPut(word.toLowerCase(), { 0 }) + 1
        }
    }
    if (allWords.size < 20) return allWords
    allWords = allWords.toList().sortedByDescending { it.second }.toMap().toMutableMap()
    return allWords.toList().subList(0, 20).toMap()
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val newDictionary = mutableMapOf<Char, String>()
    File(outputName).bufferedWriter().use {
        for ((key, value) in dictionary)
            newDictionary[key.toLowerCase()] = value.toLowerCase()
        for ((index, line) in File(inputName).readLines().withIndex()) {
            for (char in line) {
                if (newDictionary.containsKey(char.toLowerCase())) {
                    if (char.isUpperCase()) it.write(newDictionary[char.toLowerCase()]!!.capitalize())
                    else it.write(newDictionary[char]!!)
                } else it.write(char.toString())
            }
            if (newDictionary.containsKey('\n') && index != File(inputName).readLines().size - 1) it.write(newDictionary['\n']!!)
            else it.newLine()
        }
    }
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val dictionary = mutableMapOf<Char, Int>()
    val outputFile = File(outputName).bufferedWriter()
    var maxLength = -1
    val str = StringBuilder()
    for (line in File(inputName).readLines()) {
        if (line.toLowerCase().toSet().size == line.toLowerCase().toList().size)
            when {
                line.length == maxLength -> str.append(", $line")
                line.length > maxLength -> {
                    maxLength = line.length
                    str.clear()
                    str.append(line)
                }
            }
        dictionary.clear()
    }
    outputFile.write(str.toString())
    outputFile.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val outputFile = File(outputName).bufferedWriter()
    outputFile.write("<html>\n<body>\n<p>\n")
    val str = StringBuilder()
    val input = File(inputName).readLines()
    val newLine = StringBuilder()
    for ((index, line) in input.withIndex()) {
        if ((index == 0 || input[index - 1].isEmpty()) && line.isEmpty()) continue
        str.append(line)
        str.append("\n")
    }
    newLine.append(subMarkdownToHtmlSimple(str.toString().removeSuffix("\n"), "~~", "<s>", "</s>"))
    str.clear()
    str.append(subMarkdownToHtmlSimple(newLine.toString(), "**", "<b>", "</b>"))
    newLine.clear()
    newLine.append(subMarkdownToHtmlSimple(str.toString(), "*", "<i>", "</i>"))
    for (line in newLine.split("\n")) {
        if (line.isEmpty()) outputFile.write("\n</p>\n<p>\n")
        outputFile.write(line)
    }
    outputFile.write("\n</p>\n</body>\n</html>")
    outputFile.close()
}

fun subMarkdownToHtmlSimple(str: String, splitter: String, openTag: String, closeTag: String): StringBuilder {
    val newLine = StringBuilder()
    var counterOfTags = 0
    var flagOfTag = true
    val a: String
    for (line in str.split(splitter)) {
        newLine.append(line)
        counterOfTags++
        flagOfTag = if (flagOfTag) {
            newLine.append(openTag)
            false
        } else {
            newLine.append(closeTag)
            true
        }
    }
    a = newLine.toString()
    newLine.clear()
    newLine.append(a.removeSuffix(openTag))
    return newLine
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Фрукты
<ol>
<li>Бананы</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val outputFile = File(outputName).bufferedWriter()
    val lhvString = lhv.toString()
    val rhvString = rhv.toString()
    var digit: Int
    val lengthOfMultiplication = (rhv * lhv).toString().length
    var number = rhv
    for (i in 0..lengthOfMultiplication - lhvString.length) {
        outputFile.write(" ")
    }
    outputFile.write("$lhvString\n*")
    for (i in 1..lengthOfMultiplication - rhvString.length) {
        outputFile.write(" ")
    }
    outputFile.write("$rhvString\n")
    for (i in 0..lengthOfMultiplication) {
        outputFile.write("-")
    }
    outputFile.write("\n")
    for (i in 0 until rhvString.length) {
        digit = number % 10 * lhv
        for (j in 0 until lengthOfMultiplication - (digit.toString().length - 1 + i)) {
            when {
                i == 0 -> outputFile.write(" ")
                j == 0 -> outputFile.write("+")
                else -> outputFile.write(" ")
            }
        }
        outputFile.write("$digit\n")
        number /= 10
    }
    for (i in 0..lengthOfMultiplication) {
        outputFile.write("-")
    }
    outputFile.write("\n ${lhv * rhv}")
    outputFile.close()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) { // переписать получше
    File(outputName).bufferedWriter().use {
        var digit = lhv
        var counter = 0
        var remainder: Int
        var positionOfDigit = 1
        while (digit / 10 >= rhv) {
            counter++
            digit /= 10
        }
        remainder = digit % rhv

        fun firstSubPrint(flag: Boolean): String { // обработка первых 3 строк выходного файла
            val str = StringBuilder()
            val counterOfMinuses = if (flag) (digit - remainder).toString().length + 1
            else digit.toString().length
            if (flag) str.append(" ")
            str.append("$lhv | $rhv\n")
            if (!flag) str.append(" ".repeat(digit.toString().length - (digit - remainder).toString().length - 1))
            str.append(
                "-${digit - remainder}" +
                        " ".repeat(lhv.toString().length + 3 - digit.toString().length) +
                        "${lhv / rhv}\n"
            )
            str.append("-".repeat(counterOfMinuses) + "\n")
            return str.toString()
        }

        fun secondSubPrint(flag: Boolean): String { // нахожу сколько мне нужно пробелов, в зависимости от вычитаемой части
            val str = StringBuilder()
            if (flag) str.append(" ".repeat(positionOfDigit + (digit.toString().length - (digit - remainder).toString().length) - 1))
            else str.append(" ".repeat(positionOfDigit - 1))
            str.append("-${digit - remainder}\n")
            if (flag) {
                str.append(" ".repeat(positionOfDigit))
                str.append("-".repeat(digit.toString().length) + "\n")
            } else {
                str.append(" ".repeat(positionOfDigit - 1))
                str.append("-".repeat((digit - remainder).toString().length + 1) + "\n")
            }
            return str.toString()
        }

        var flag = digit.toString().length == (digit - remainder).toString().length
        if (flag) it.write(firstSubPrint(flag))
        else {
            it.write(firstSubPrint(flag))
            positionOfDigit--
        }
        positionOfDigit += digit.toString().length - remainder.toString().length //нахожу сколько мне нужно пробелов, чтобы выводить digit в правильном месте
        for (i in digit.toString().length until lhv.toString().length) {
            counter--
            it.write(" ".repeat(positionOfDigit) + "$remainder${lhv / 10.0.pow(counter).toInt() % 10}\n")
            if (remainder == 0) positionOfDigit++ // добавляю к positionOfDigit 1, если остаток от деления был 0
            digit =
                remainder * 10 + lhv / 10.0.pow(counter).toInt() % 10 //нахожу часть числа, которя будет делиться на rhv
            remainder = digit % rhv // нахожу остаток после деления части числа на rhv
            flag = digit.toString().length > (digit - remainder).toString().length
            if (flag) it.write(secondSubPrint(flag)) // нахожу сколько мне нужно пробелов, в зависимости от вычитаемой части
            else it.write(secondSubPrint(flag))
            positionOfDigit += digit.toString().length - remainder.toString().length //нахожу сколько мне нужно пробелов, чтобы выводить digit в правильном месте
        }
        it.write(" ".repeat(positionOfDigit) + "$remainder")
    }
}