package _20260303.sjh1108.BOJ_2668;

import java.io.*;
import java.util.*;

class Main {
    private static int num;
    private static int[] arr;
    private static boolean[] visited;
    private static List<Integer> list;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        arr = new int[N + 1];
        visited = new boolean[N + 1];
        for (int i = 1; i <= N; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }
        list = new LinkedList<>();

        for (int i = 1; i <= N; i++) {
            // i에서 시작하는 사이클 여부 확인
            visited[i] = true;
            num = i;
            dfs(i);
            visited[i] = false;
        }

        Collections.sort(list);
        System.out.println(list.size());
        for (int i : list) {
            System.out.println(i);
        }
    }

    public static void dfs(int i) {
        // 시작 숫자로 되돌아오면 사이클 성립
        if (arr[i] == num) list.add(num);
        
        // 다음 정점을 방문
        if (!visited[arr[i]]) {
            visited[arr[i]] = true;
            dfs(arr[i]);
            visited[arr[i]] = false;
        }
    }
}
