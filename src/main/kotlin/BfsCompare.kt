import bfs.BfsPar
import bfs.BfsSeq
import cubeGraph.Cube
import kotlin.system.measureTimeMillis

fun main() {
    val edges = Cube().makeEdgesArray(300)
    lateinit var distSeq: Array<Int>
    lateinit var distPar: Array<Int>
    var sumSeq = 0L
    var sumPar = 0L
    for (i in 0 until 5) {
        if (true) {
            val bfsSeq = BfsSeq(edges)
            val timeSeq = measureTimeMillis { bfsSeq.startBfs(0) }
            distSeq = bfsSeq.distArray()
            sumSeq += timeSeq
            println("Time for sequential bfs: $timeSeq")
        }
        Thread.sleep(1000)
        if (true) {
            val bfsPar = BfsPar(edges)
            val timePar = measureTimeMillis { bfsPar.startBfs(0) }
            distPar = bfsPar.distArray()
            sumPar += timePar
            println("Time for parallel bfs: $timePar")
        }
        println(distPar.contentEquals(distSeq))
    }
    println("Average sequential time: ${sumSeq / 4}")
    println("Average parallel time: ${sumPar / 4}")
}