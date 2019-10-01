@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson5.task1

import kotlin.math.max


/**
 * Пример
 *
 * Для заданного списка покупок `shoppingList` посчитать его общую стоимость
 * на основе цен из `costs`. В случае неизвестной цены считать, что товар
 * игнорируется.
 */
fun shoppingListCost(
    shoppingList: List<String>,
    costs: Map<String, Double>
): Double {
    var totalCost = 0.0

    for (item in shoppingList) {
        val itemCost = costs[item]
        if (itemCost != null) {
            totalCost += itemCost
        }
    }

    return totalCost
}

/**
 * Пример
 *
 * Для набора "имя"-"номер телефона" `phoneBook` оставить только такие пары,
 * для которых телефон начинается с заданного кода страны `countryCode`
 */
fun filterByCountryCode(
    phoneBook: MutableMap<String, String>,
    countryCode: String
) {
    val namesToRemove = mutableListOf<String>()

    for ((name, phone) in phoneBook) {
        if (!phone.startsWith(countryCode)) {
            namesToRemove.add(name)
        }
    }

    for (name in namesToRemove) {
        phoneBook.remove(name)
    }
}

/**
 * Пример
 *
 * Для заданного текста `text` убрать заданные слова-паразиты `fillerWords`
 * и вернуть отфильтрованный текст
 */
fun removeFillerWords(
    text: List<String>,
    vararg fillerWords: String
): List<String> {
    val fillerWordSet = setOf(*fillerWords)

    val res = mutableListOf<String>()
    for (word in text) {
        if (word !in fillerWordSet) {
            res += word
        }
    }
    return res
}

/**
 * Пример
 *
 * Для заданного текста `text` построить множество встречающихся в нем слов
 */
fun buildWordSet(text: List<String>): MutableSet<String> {
    val res = mutableSetOf<String>()
    for (word in text) res.add(word)
    return res
}


/**
 * Простая
 *
 * По заданному ассоциативному массиву "студент"-"оценка за экзамен" построить
 * обратный массив "оценка за экзамен"-"список студентов с этой оценкой".
 *
 * Например:
 *   buildGrades(mapOf("Марат" to 3, "Семён" to 5, "Михаил" to 5))
 *     -> mapOf(5 to listOf("Семён", "Михаил"), 3 to listOf("Марат"))
 */
fun buildGrades(grades: Map<String, Int>): Map<Int, List<String>> {
    val a = mutableMapOf<Int, MutableList<String>>()
    for ((name, grade) in grades) {
        if (a[grade] == null) a[grade] = mutableListOf(name)
        else a[grade]!!.add(name)
    }
    return a
}

/**
 * Простая
 *
 * Определить, входит ли ассоциативный массив a в ассоциативный массив b;
 * это выполняется, если все ключи из a содержатся в b с такими же значениями.
 *
 * Например:
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "z", "b" to "sweet")) -> true
 *   containsIn(mapOf("a" to "z"), mapOf("a" to "zee", "b" to "sweet")) -> false
 */
fun containsIn(a: Map<String, String>, b: Map<String, String>): Boolean {
    for ((key, value) in a) {
        if (b[key] == null) return false
        else if (b[key] != value) return false
    }
    return true
}

/**
 * Простая
 *
 * Удалить из изменяемого ассоциативного массива все записи,
 * которые встречаются в заданном ассоциативном массиве.
 * Записи считать одинаковыми, если и ключи, и значения совпадают.
 *
 * ВАЖНО: необходимо изменить переданный в качестве аргумента
 *        изменяемый ассоциативный массив
 *
 * Например:
 *   subtractOf(a = mutableMapOf("a" to "z"), mapOf("a" to "z"))
 *     -> a changes to mutableMapOf() aka becomes empty
 */
fun subtractOf(a: MutableMap<String, String>, b: Map<String, String>) {
    for ((key, value) in b) {
        if (a[key] != null && a[key] == value) a.remove(key)
    }
}

/**
 * Простая
 *
 * Для двух списков людей найти людей, встречающихся в обоих списках.
 * В выходном списке не должно быть повторяюихся элементов,
 * т. е. whoAreInBoth(listOf("Марат", "Семён, "Марат"), listOf("Марат", "Марат")) == listOf("Марат")
 */
fun whoAreInBoth(a: List<String>, b: List<String>): List<String> = a.intersect(b).toList()

/**
 * Средняя
 *
 * Объединить два ассоциативных массива `mapA` и `mapB` с парами
 * "имя"-"номер телефона" в итоговый ассоциативный массив, склеивая
 * значения для повторяющихся ключей через запятую.
 * В случае повторяющихся *ключей* значение из mapA должно быть
 * перед значением из mapB.
 *
 * Повторяющиеся *значения* следует добавлять только один раз.
 *
 * Например:
 *   mergePhoneBooks(
 *     mapOf("Emergency" to "112", "Police" to "02"),
 *     mapOf("Emergency" to "911", "Police" to "02")
 *   ) -> mapOf("Emergency" to "112, 911", "Police" to "02")
 */
fun mergePhoneBooks(mapA: Map<String, String>, mapB: Map<String, String>): Map<String, String> {
    val b = mapB.toMutableMap()
    val k = mapA.toMutableMap()
    subtractOf(b, mapA)
    for ((key, value) in b) {
        if (mapA[key] == null) k[key] = value
        else k[key] += ", $value"
    }
    return k
}

/**
 * Средняя
 *
 * Для заданного списка пар "акция"-"стоимость" вернуть ассоциативный массив,
 * содержащий для каждой акции ее усредненную стоимость.
 *
 * Например:
 *   averageStockPrice(listOf("MSFT" to 100.0, "MSFT" to 200.0, "NFLX" to 40.0))
 *     -> mapOf("MSFT" to 150.0, "NFLX" to 40.0)
 */
fun averageStockPrice(stockPrices: List<Pair<String, Double>>): Map<String, Double> {
    val k = mutableMapOf<String, Pair<Int, Double>>()
    val m = mutableMapOf<String, Double>()
    for ((key, value) in stockPrices) {
        if (k[key] == null) k[key] = Pair(1, value)
        else k[key] = Pair(k[key]!!.first + 1, value + k[key]!!.second)
    }
    for ((key, value) in k) {
        m[key] = value.second / value.first
    }
    return m
}

/**
 * Средняя
 *
 * Входными данными является ассоциативный массив
 * "название товара"-"пара (тип товара, цена товара)"
 * и тип интересующего нас товара.
 * Необходимо вернуть название товара заданного типа с минимальной стоимостью
 * или null в случае, если товаров такого типа нет.
 *
 * Например:
 *   findCheapestStuff(
 *     mapOf("Мария" to ("печенье" to 20.0), "Орео" to ("печенье" to 100.0)),
 *     "печенье"
 *   ) -> "Мария"
 */
fun findCheapestStuff(stuff: Map<String, Pair<String, Double>>, kind: String): String? {
    var min = -1.0
    var l = ""
    for ((name, product) in stuff) {
        if (product.first == kind && (min > product.second || min == -1.0)) {
            min = product.second
            l = name
        }
    }
    return if (min == -1.0) null else l
}

/**
 * Средняя
 *
 * Для заданного набора символов определить, можно ли составить из него
 * указанное слово (регистр символов игнорируется)
 *
 * Например:
 *   canBuildFrom(listOf('a', 'b', 'o'), "baobab") -> true
 */
fun canBuildFrom(chars: List<Char>, word: String): Boolean {
    for (i in word.indices)
        if (!chars.contains(word[i].toLowerCase()) && !chars.contains(word[i].toUpperCase()))
            return false
    return true
}

/**
 * Средняя
 *
 * Найти в заданном списке повторяющиеся элементы и вернуть
 * ассоциативный массив с информацией о числе повторений
 * для каждого повторяющегося элемента.
 * Если элемент встречается только один раз, включать его в результат
 * не следует.
 *
 * Например:
 *   extractRepeats(listOf("a", "b", "a")) -> mapOf("a" to 2)
 */
fun extractRepeats(list: List<String>): Map<String, Int> {
    val rightMap = mutableMapOf<String, Int>()
    val wrongMap = mutableMapOf<String, Int>()
    for (letter in list) {
        if (rightMap[letter] != null) rightMap[letter] = rightMap[letter]!! + 1
        if (wrongMap[letter] == null) wrongMap[letter] = 1
        else {
            if (rightMap[letter] == null) rightMap[letter] = 2
            wrongMap.remove(letter)
        }
    }
    return rightMap
}

/**
 * Средняя
 *
 * Для заданного списка слов определить, содержит ли он анаграммы
 * (два слова являются анаграммами, если одно можно составить из второго)
 *
 * Например:
 *   hasAnagrams(listOf("тор", "свет", "рот")) -> true
 */
fun subHasAnagrams(line1: String, line2: String): Boolean {
    if (line1 == "" && line2 == "") return true
    if (line1 == "" || line2 == "") return false
    val rightMap = mutableMapOf<Char, Int>()
    for (char in line1) {
        if (rightMap[char] != null) rightMap[char] = rightMap[char]!! + 1
        else rightMap[char] = 1
    }
    for (char in line2) {
        when {
            rightMap[char] == null -> return false
            rightMap[char]!! > 0 -> rightMap[char] = rightMap[char]!! - 1
            else -> return false
        }
    }
    return true
}

fun hasAnagrams(words: List<String>): Boolean { // переписать
    for (i in 0 until words.size - 1)
        for (j in (i + 1) until words.size)
            if (subHasAnagrams(words[i], words[j])) return true
    return false
}

/**
 * Сложная
 *
 * Для заданного ассоциативного массива знакомых через одно рукопожатие `friends`
 * необходимо построить его максимальное расширение по рукопожатиям, то есть,
 * для каждого человека найти всех людей, с которыми он знаком через любое
 * количество рукопожатий.
 * Считать, что все имена людей являются уникальными, а также что рукопожатия
 * являются направленными, то есть, если Марат знает Свету, то это не означает,
 * что Света знает Марата.
 *
 * Например:
 *   propagateHandshakes(
 *     mapOf(
 *       "Marat" to setOf("Mikhail", "Sveta"),
 *       "Sveta" to setOf("Marat"),
 *       "Mikhail" to setOf("Sveta")
 *     )
 *   ) -> mapOf(
 *          "Marat" to setOf("Mikhail", "Sveta"),
 *          "Sveta" to setOf("Marat", "Mikhail"),
 *          "Mikhail" to setOf("Sveta", "Marat")
 *        )
 */
fun propagateHandshakes(friends: Map<String, Set<String>>): Map<String, Set<String>> {
    if (friends.isEmpty()) return friends
    var map = friends as MutableMap<String, MutableSet<String>> // присваиваю изначальный лист
    var changes = true
    var k: Int
    while (changes) {
        changes = false
        for ((key, value) in friends) {
            k = map[key]!!.size
            for (name in value) { // проверяю имена людей
                if (map[name] == null) map = (map + Pair(name, mutableSetOf()))
                        as MutableMap<String, MutableSet<String>> // если не существует пар с этим человеком добавляю
                map[key] = (map[name]!! + map[key]!!) as MutableSet<String>
            }
            if (map[key]!!.contains(key)) map[key]!!.remove(key) // убираю имена чтобы не дружили сами с собой
            if (map[key]!!.size > k) changes = true
        }
    }
    return map
}


/**
 * Сложная
 *
 * Для заданного списка неотрицательных чисел и числа определить,
 * есть ли в списке пара чисел таких, что их сумма равна заданному числу.
 * Если да, верните их индексы в виде Pair<Int, Int>;
 * если нет, верните пару Pair(-1, -1).
 *
 * Индексы в результате должны следовать в порядке (меньший, больший).
 *
 * Постарайтесь сделать ваше решение как можно более эффективным,
 * используя то, что вы узнали в данном уроке.
 *
 * Например:
 *   findSumOfTwo(listOf(1, 2, 3), 4) -> Pair(0, 2)
 *   findSumOfTwo(listOf(1, 2, 3), 6) -> Pair(-1, -1)
 */
fun findSumOfTwo(list: List<Int>, number: Int): Pair<Int, Int> {
    if (list.isEmpty()) return Pair(-1, -1)
    val index1 = list.indexOf(number / 2)
    val index2 = list.lastIndexOf(number / 2)
    val k = list.toSet()
    var i = 0
    while (i < k.size) {
        if (k.contains(number - k.elementAt(i)))
            if (list.indexOf(k.elementAt(i)) != list.indexOf(number - k.elementAt(i)))
                return Pair(list.indexOf(k.elementAt(i)), list.indexOf(number - k.elementAt(i)))
        i += 1
    }
    if (list.contains(number / 2) && number % 2 == 0) if (index1 != index2) return Pair(index1, index2)
    return Pair(-1, -1)
}

/**
 * Очень сложная
 *
 * Входными данными является ассоциативный массив
 * "название сокровища"-"пара (вес сокровища, цена сокровища)"
 * и вместимость вашего рюкзака.
 * Необходимо вернуть множество сокровищ с максимальной суммарной стоимостью,
 * которые вы можете унести в рюкзаке.
 *
 * Перед решением этой задачи лучше прочитать статью Википедии "Динамическое программирование".
 *
 * Например:
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     850
 *   ) -> setOf("Кубок")
 *   bagPacking(
 *     mapOf("Кубок" to (500 to 2000), "Слиток" to (1000 to 5000)),
 *     450
 *   ) -> emptySet()
 */
fun bagPacking(treasures: Map<String, Pair<Int, Int>>, capacity: Int): Set<String> {
    val answer = mutableSetOf<String>() // ответ
    val weight = mutableListOf<Int>() // вес сокровищ
    val price = mutableListOf<Int>() // цена сокровищ
    val keysOfMap = mutableListOf<String>() // названия сокровищ
    var k = 0
    val items =
        MutableList(treasures.size + 1) { MutableList(capacity + 1) { 0 } } // таблица цен
    for ((key, value) in treasures) {
        weight.add(value.first)
        price.add(value.second)
        keysOfMap.add(key)
        k += 1
    }
    for (i in 1..weight.size)
        for (j in 1..capacity) {
            if (weight[i - 1] <= j)
                items[i][j] = max(items[i - 1][j], items[i - 1][j - weight[i - 1]] + price[i - 1])
            else items[i][j] = items[i - 1][j]
        }

    var n = treasures.size
    k = capacity
    while (n > 0 && k > 0) { // возвращение названий сокровищ
        if (items[n][k] == items[n - 1][k]) n -= 1
        else {
            answer.add(keysOfMap[n - 1])
            n -= 1
            k -= weight[n]
        }
    }
    return answer
}