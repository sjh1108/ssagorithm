package _20260413.thdwngjs.BOJ_2593;

import java.io.*;
import java.util.*;

class Main {
    static int N, M;
    static int[] xs, ys;
    static int[] parent;
    static int[] dist;

    static boolean stopsAt(int i, int floor) {
        if (floor < xs[i] || floor > N) return false;
        return (floor - xs[i]) % ys[i] == 0;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        xs = new int[M + 1];
        ys = new int[M + 1];
        parent = new int[M + 1];
        dist = new int[M + 1];
        Arrays.fill(parent, -1);
        Arrays.fill(dist, -1);

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            xs[i] = Integer.parseInt(st.nextToken());
            ys[i] = Integer.parseInt(st.nextToken());
        }

        st = new StringTokenizer(br.readLine());
        int A = Integer.parseInt(st.nextToken());
        int B = Integer.parseInt(st.nextToken());

        ArrayDeque<Integer> q = new ArrayDeque<>();
        for (int i = 1; i <= M; i++) {
            if (stopsAt(i, A)) {
                q.add(i);
                parent[i] = 0;
                dist[i] = 1;
            }
        }

        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int floor = xs[cur]; floor <= N; floor += ys[cur]) {
                for (int j = 1; j <= M; j++) {
                    if (dist[j] != -1) continue;
                    if (!stopsAt(j, floor)) continue;
                    parent[j] = cur;
                    dist[j] = dist[cur] + 1;
                    q.add(j);
                }
            }
        }

        int best = -1;
        for (int i = 1; i <= M; i++) {
            if (dist[i] == -1) continue;
            if (!stopsAt(i, B)) continue;
            if (best == -1 || dist[i] < dist[best]) {
                best = i;
            }
        }

        if (best == -1) {
            System.out.println(-1);
            return;
        }

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        int cur = best;
        while (cur != 0) {
            stack.push(cur);
            cur = parent[cur];
        }

        StringBuilder sb = new StringBuilder();
        sb.append(dist[best]).append('\n');
        while (!stack.isEmpty()) {
            sb.append(stack.pop()).append('\n');
        }
        System.out.print(sb);
    }
}