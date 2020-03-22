package week_2.task_2


import java.io.File

fun main(args: Array<String>) {
    val n = Reader.lineAsInt(0)
    val a = Reader.lineAsIntList(1)
    val elements = a.mapIndexed { i, v -> Element(i + 1, v) }
    mergeSort(elements)

    Writer.writeLines(Counter.count)
    Writer.persist()
}

private fun merge(a: List<Element>, b: List<Element>): List<Element> {
    var i = 0
    var j = 0
    val n = a.size
    val m = b.size
    val c = mutableListOf<Element>()

    val l = a[0].index
    val r = b.last().index

    while (i < n || j < m) {
        if (j == m || i < n && a[i].value <= b[j].value) {
            c.add(a[i])
            i++
        } else {
            c.add(b[j])
//            if (a[i].index < b[j].index) {
                Counter.count++
//            }
            j++
        }
    }

//    Writer.writeLines(listOf(l, r, c[0].value, c.last().value))
    return c
}

private fun mergeSort(a: List<Element>): List<Element> {
    val n = a.size
    if (n == 1) {
        return a
    }
    val l = a.subList(0, n / 2)
    val r = a.subList(n / 2, n)
    return merge(mergeSort(l), mergeSort(r))
}

private data class Element(var index: Int, val value: Int)

private object Counter {
    var count = 0
}


private object Reader {

    private val inputFile = File("input.txt")

    private val lines = inputFile.readLines().map { it.trim() }

    fun lineAsString(index: Int) = lines[index]

    fun lineAsInt(index: Int) = lineAsString(index).toInt()

    fun lineAsStringList(index: Int, delimiter: String = " ") = lines[index]
        .split(delimiter)
        .filterNot { it.isBlank() }

    fun lineAsIntList(index: Int, delimiter: String = " ") = lineAsStringList(
        index,
        delimiter
    )
        .map { it.toInt() }

    fun lineAsDoubleList(index: Int, delimiter: String = " ") = lineAsStringList(
        index,
        delimiter
    )
        .map { it.toDouble() }

}

private object Writer {

    val outputFile: File = File("output.txt")

    private val linesForWriting = mutableListOf<String>()

    fun <T> writeToLine(vararg values: T) = linesForWriting.add(
        values.joinToString(separator = " ")
    )

    fun <T> writeLines(vararg lines: T) = linesForWriting.addAll(
        lines.map {
            when (it) {
                is Collection<*> -> it.joinToString(separator = " ")
                is Array<*> -> it.joinToString(separator = " ")
                else -> it.toString()
            }
        }
    )

    fun persist() = outputFile.printWriter().use { out ->
        linesForWriting.forEach { out.println(it) }
    }

}
