package cubeGraph

import kotlin.math.pow

class Cube{
    fun makeEdgesArray(len: Int): Array<Array<Int>> {
        val numVertexes = (len + 1).toDouble().pow(3).toInt()
        val result = Array(numVertexes) { Array(6) { -1 } }
        for (i in 0..len) {
            for (j in 0..len) {
                for (q in 0..len) {
                    val v = i * (len + 1) * (len + 1) + j * (len + 1) + q
                    var num = 0
                    if (i < len) {
                        result[v][num] = (v + (len + 1) * (len + 1))
                        num++
                    }
                    if (j < len) {
                        result[v][num] = (v + (len + 1))
                        num++
                    }
                    if (q < len) {
                        result[v][num] = (v + 1)
                        num++
                    }
                    if (i > 0) {
                        result[v][num] = (v - (len + 1) * (len + 1))
                        num++
                    }
                    if (j > 0) {
                        result[v][num] = (v - (len + 1))
                        num++
                    }
                    if (q > 0) {
                        result[v][num] = (v - 1)
                    }
                }
            }
        }

        return result
    }
}
