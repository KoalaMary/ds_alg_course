package week_4.task_2


import java.io.File

fun main(args: Array<String>) {
    val queue = Queue()
    val n = Reader.lineAsInt(0)
    for (i in 1..n) {
        val operation = Reader.lineAsString(i)
        when (operation[0]) {
            '+' -> queue.push(operation.split(" ").last().toInt())
            '-' -> Writer.writeLines(queue.pop())
        }
    }
    Writer.persist()
}

class Queue {

    private val array = arrayOfNulls<Int>(1_000_000)

    private var head = 0
    private var tail = 0

    fun push(num: Int) {
        array[tail] = num
        tail++
    }

    fun pop(): Int {
        val removed = array[head]
        head++
        return removed!!
    }
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
