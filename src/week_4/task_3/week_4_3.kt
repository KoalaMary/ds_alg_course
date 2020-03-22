package week_4.task_3


import java.io.File

fun main(args: Array<String>) {
    val n = Reader.lineAsInt(0)
    for (i in 1..n) {
        val stack = Stack()
        var success = true
        Reader.lineAsString(i).toCharArray().forEach {
            if (it == '(' || it == '[') {
                stack.push(it)
            } else {
                val last = stack.last()
                if (last == null) {
                    success = false
                    return@forEach
                }
                if (it == ']' && last == '(' || it == ')' && last == '[') {
                    success = false
                    return@forEach
                }
                stack.pop()
            }
        }
        if (!stack.isEmpty()) success = false
        Writer.writeLines(if (success) "YES" else "NO")

    }
    Writer.persist()
}

class Stack {

    private val list = mutableListOf<Char>()

    private var top = 0

    fun push(num: Char) {
        if (list.isNotEmpty()) top++
        list.add(num)
    }

    fun last() = if (isEmpty()) null else list.last()

    fun pop(): Char {
        val removed = list.removeAt(top)
        if (top != 0) top--
        return removed
    }

    fun isEmpty() = list.isEmpty()
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
