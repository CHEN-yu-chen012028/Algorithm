// GraphSearch.java
import java.util.*;

public class GraphSearch {
    private int V; // 頂點數量
    private LinkedList<Integer>[] adj; // 鄰接清單

    // 初始化圖形
    public GraphSearch(int v) {
        V = v;
        adj = new LinkedList[v];
        for (int i = 0; i < v; ++i)
            adj[i] = new LinkedList();
    }

    // 新增邊 (無向圖)
    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
    }

    // --- 1. DFS (使用遞迴實作) ---
    public void DFS(int s) {
        boolean[] visited = new boolean[V];
        System.out.print("DFS 走訪結果: ");
        DFSRecursive(s, visited);
        System.out.println();
    }

    private void DFSRecursive(int v, boolean[] visited) {
        visited[v] = true;
        System.out.print(v + " ");

        for (int n : adj[v]) {
            if (!visited[n])
                DFSRecursive(n, visited);
        }
    }

    // --- 2. BFS (使用佇列 Queue 實作) ---
    public void BFS(int s) {
        boolean[] visited = new boolean[V];
        LinkedList<Integer> queue = new LinkedList<Integer>();

        visited[s] = true;
        queue.add(s);

        System.out.print("BFS 走訪結果: ");
        while (queue.size() != 0) {
            s = queue.poll();
            System.out.print(s + " ");

            for (int n : adj[s]) {
                if (!visited[n]) {
                    visited[n] = true;
                    queue.add(n);
                }
            }
        }
        System.out.println();
    }

    public static void main(String args[]) {
        GraphSearch g = new GraphSearch(6);

        // 建立圖形結構
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 5);

        System.out.println("從頂點 0 開始搜尋:");
        g.DFS(0);
        g.BFS(0);
    }
}