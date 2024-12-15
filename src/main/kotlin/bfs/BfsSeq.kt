package bfs

class BfsSeq(
    private val edges: Array<Array<Int>>
) {

    private val dist = Array(edges.size) { -1 }
    private val queue = ArrayDeque<Int>()
    private val used = Array(edges.size) { false }

    fun startBfs(v: Int) {
        dist[v] = 0
        queue.add(v)
        bfs()
    }

    fun distArray(): Array<Int> {
        return dist
    }

    private fun bfs() {
        while (queue.isNotEmpty()) {
            val v = queue.first()
            queue.removeFirst()
            for (u in edges[v]) {
                if (u < 0) {
                    continue
                }
                if (!used[u]) {
                    queue.add(u)
                    used[u] = true
                    dist[u] = dist[v] + 1
                }
            }
        }
    }
}