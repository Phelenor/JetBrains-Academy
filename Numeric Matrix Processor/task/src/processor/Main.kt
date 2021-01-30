package processor

import java.util.*

val reader = Scanner(System.`in`)

fun main() {
    run()
}

fun run() {
    while(true) {
        println("1. Add matrices\n" +
                "2. Multiply matrix to a constant\n" +
                "3. Multiply matrices\n" +
                "4. Transpose matrix\n" +
                "5. Calculate a determinant\n" +
                "6. Inverse matrix\n" +
                "0. Exit")

        print("Your choice: ")
        when (readLine()!!.toInt()) {
            1 -> {
                val m1 = createMatrix()
                val m2 = createMatrix("second")
                val addition = addMatrices(m1, m2)
                if(addition != null) {
                    println("The addition result is: ")
                    printMatrix(addition)
                }
            }
            2 -> {
                val m1 = createMatrix()
                print("Enter constant: ")
                val n = reader.nextInt()
                val multiplication = multiplyByScalar(m1, n.toDouble())
                println("The multiplication result is: ")
                printMatrix(multiplication)
                println()
            }
            3 -> {
                val m1 = createMatrix()
                val m2 = createMatrix("second")
                val multiplication = multiplyMatrices(m1, m2)
                if(multiplication != null) {
                    println("The multiplication result is: ")
                    printMatrix(multiplication)
                }
                println()
            }
            4 -> {
                println("1. Main diagonal\n" +
                        "2. Side diagonal\n" +
                        "3. Vertical line\n" +
                        "4. Horizontal line")
                print("Your choice: ")
                val choice = reader.nextInt()
                val m1 = createMatrix()
                val transposed = transpose(m1, choice)
                println("The result is: ")
                printMatrix(transposed)
            }
            5 -> {
                val m1 = createMatrix()
                println("The result is:")
                println(determinant(m1))
                println()
            }
            6 -> {
                val m1 = createMatrix()
                println("The result is:")
                printMatrix(inverse(m1))
            }
            0 -> return
        }
    }
}

fun createMatrix(num: String = "first"): Array<DoubleArray> {
    print("Enter size of $num matrix: ")
    val rows = reader.nextInt()
    val columns = reader.nextInt()
    val matrix = Array(rows) {DoubleArray(columns)}

    println("Enter $num matrix: ")
    for(i in 0 until rows) {
        for(j in 0 until columns) {
            matrix[i][j] = reader.nextDouble()
        }
    }
    return matrix
}

fun addMatrices(m1: Array<DoubleArray>, m2: Array<DoubleArray>): Array<DoubleArray>?{
    if(m1.size != m2.size || m1[0].size != m2[0].size) {
        println("ERROR")
        return null
    }
    val matrix = Array(m1.size) {DoubleArray(m1[0].size)}

    for(i in m1.indices) {
        for(j in m1[0].indices) {
            matrix[i][j] = m1[i][j] + m2[i][j]
        }
    }
    return matrix
}

fun transpose(m1: Array<DoubleArray>, option: Int = 1):Array<DoubleArray> {
    val matrix = Array(m1[0].size) { DoubleArray(m1.size) }
    val matrix2 = Array(m1.size) { DoubleArray(m1[0].size) }
    when (option) {
        1 -> {
            for (i in m1.indices) {
                for (j in m1[0].indices) {
                    matrix[i][j] = m1[j][i]
                }
            }
        }
        2 -> {
            for (i in m1.indices) {
                for (j in m1[0].indices) {
                    matrix[i][j] = m1[m1[0].lastIndex - j][m1.lastIndex - i]
                }
            }
        }
        3 -> {
            for (i in m1.indices) {
                for (j in m1[0].indices) {
                    matrix2[i][j] = m1[i][m1[0].lastIndex - j]
                }
            }
            return matrix2
        }
        4 -> {
            for (i in m1.indices) {
                for (j in m1[0].indices) {
                    matrix2[i][j] = m1[m1.lastIndex - i][j]
                }
            }
            return matrix2
        }
    }
    return matrix
}

fun multiplyByScalar(m1: Array<DoubleArray>, n: Double): Array<DoubleArray> {
    for(i in m1.indices) {
        for(j in m1[0].indices) {
            m1[i][j] = m1[i][j] * n
        }
    }
    return m1
}

fun multiplyMatrices(m1: Array<DoubleArray>, m2: Array<DoubleArray>): Array<DoubleArray>? {
    if(m1[0].size != m2.size) {
        println("ERROR\n")
        return null
    }
    val m = m1.size
    val n = m2[0].size
    val diff = m2.size
    val matrix = Array(m) {DoubleArray(n) {0.0} }

    for(i in 0 until m) {
        for(j in 0 until n) {
            for(x in 0 until diff) {
                matrix[i][j] += m1[i][x] * m2[x][j]
            }
        }
    }
    return matrix
}

fun printMatrix(matrix: Array<DoubleArray>) {
    var output = ""
    for (i in matrix.indices) {
        for(j in matrix[0].indices) {
            var x = String.format("%5.2f ", matrix[i][j])
            output += x
        }
        output += "\n"
    }
    println(output)
}

fun findCofactor(m1: Array<DoubleArray>, column: Int, row: Int = 0): Array<DoubleArray> {
    val cofactor = Array(m1.size - 1) {DoubleArray (m1.size - 1)}
    var i2 = 0
    for(i in 0..m1.lastIndex) {
        if(i == row) continue
        var j2 = 0
        for(j in 0..m1.lastIndex) {
            if(j == column) continue
            cofactor[i2][j2] = m1[i][j]
            j2++
        }
        i2++
    }
    return cofactor
}

fun determinant(m1: Array<DoubleArray>): Double {
    var sum = 0.0
    if(m1.size == 1)
        return m1[0][0]
    else if(m1.size == 2)
        return (m1[0][0]*m1[1][1] - m1[0][1]*m1[1][0])
    else {
        for(j in m1.indices) {
            var sign = if(j % 2 == 0) 1
                        else -1
            var cofactor = findCofactor(m1, j)
            sum += sign * m1[0][j] * determinant(cofactor)
        }
    }
    return sum
}

fun inverse(m1: Array<DoubleArray>): Array<DoubleArray> {
    var inverse = Array(m1.size) {DoubleArray(m1.size)}
    for(i in m1.indices) {
        for(j in m1.indices) {
            var sign = if((i + j) % 2 == 0) 1
                    else -1
            inverse[i][j] = sign * determinant(findCofactor(m1, j, i))
            if(inverse[i][j] == -0.0) inverse[i][j] = 0.0
        }
    }
    inverse = multiplyByScalar(transpose(inverse), 1.0 / determinant(m1))
    for(i in m1.indices) {
        for (j in m1.indices) {
            if(inverse[i][j] == -0.0) inverse[i][j] = 0.0
        }
    }

    return inverse
}