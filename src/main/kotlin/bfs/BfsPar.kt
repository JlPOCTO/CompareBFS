package bfs

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.RecursiveTask

class BfsPar(
    private val edges: Array<Array<Int>>
) {

    private val dist = Array(edges.size) { -1 }
    private val used = Array(edges.size) { false }
    private val maxThreads = 4
    private val queue = Array<ArrayDeque<Int>>(2 * maxThreads * maxThreads) { ArrayDeque() }

    fun startBfs(v: Int) {
        dist[v] = 0
        queue[0].add(v)
        val forkJoinPool = ForkJoinPool(maxThreads)
        forkJoinPool.invoke(object : RecursiveTask<Unit>() {
            override fun compute() {
                bfs()
            }
        })
        forkJoinPool.shutdown()
    }

    fun distArray(): Array<Int> {
        return dist
    }

    private fun bfs() {
        var iter = 0
        while (queue.any { q -> q.isNotEmpty() }) {
            val jobs = ArrayList<RecursiveTask<Unit>>()
            val countVertexes = Array(maxThreads) { Array(maxThreads) {0} }
            for (i in 0 until maxThreads) {
                for (j in 0 until maxThreads) {
                    if (iter % 2 == 0) {
                        countVertexes[i][j] = queue[i + j * maxThreads].size
                    } else {
                        countVertexes[i][j] = queue[i + j * maxThreads + maxThreads * maxThreads].size
                    }
                }
            }
            for (i in 0 until maxThreads) {
                jobs.add(object : RecursiveTask<Unit>() {
                    override fun compute() {
                        checkNeighbours(i, countVertexes[i], iter % 2 == 0)
                    }
                })
            }
            jobs.map { it.fork() }.map { it.join() }
            iter++
        }
    }

    private fun checkNeighbours(i: Int, batchSizes: Array<Int>, isLeftPart: Boolean) {
        for (qInd in 0 until maxThreads) {
            var c = 0
            for (j in 0 until batchSizes[qInd]) {
                val index = if (isLeftPart) {
                    i + qInd * maxThreads
                } else {
                    i + qInd * maxThreads + maxThreads * maxThreads
                }
                val v = queue[index].first()
                if (v == -1) {
                    queue[i + qInd * maxThreads].removeFirst()
                    continue
                }
                queue[index].removeFirst()
                for (q in 0 until edges[v].size) {
                    val u = edges[v][q]
                    if (u == -1) continue
                    if (!used[u]) {
                        used[u] = true
                        if (isLeftPart) {
                            queue[c % maxThreads + i * maxThreads + maxThreads * maxThreads].add(u)
                        } else {
                            queue[c % maxThreads + i * maxThreads].add(u)
                        }
                        c++
                        dist[u] = dist[v] + 1
                    }
                }
            }
        }
    }
}