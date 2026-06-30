package _20260625.thdwngjs.폴더_검사_순서;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        // [문제 요약]
        //  루트(0)에서 시작하는 트리를 전위순회(preorder)로 검사한다. 같은 부모 아래
        //  자식들은 "번호가 작은 순"으로 방문한다고 가정. target 폴더가 몇 번째에 검사되는지가
        //  자연스러운 위치(naturalPos). 이 위치가 deadline 이내가 되도록 일부 노드를 마킹하여
        //  순서를 앞당길 수 있고, 필요한 최소 마킹 수를 구한다(불가능하면 -1).
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());        // 노드 수
        int target = Integer.parseInt(st.nextToken());   // 목표 폴더 번호
        int deadline = Integer.parseInt(st.nextToken()); // target이 검사돼야 하는 마감 순번

        // 무방향 인접리스트로 트리 구성 (간선 n-1개)
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int i = 0; i < n - 1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()), b = Integer.parseInt(st.nextToken());
            adj.get(a).add(b); adj.get(b).add(a);
        }

        // BFS로 각 노드의 부모(parent)와 방문순서(order)를 구한다.
        // (재귀 DFS 대신 BFS를 써서 노드가 많아도 스택오버플로를 피함)
        int[] parent = new int[n], order = new int[n];
        boolean[] vis = new boolean[n];
        int idx = 0;
        ArrayDeque<Integer> q = new ArrayDeque<>();
        q.add(0); vis[0] = true; parent[0] = -1;          // 루트의 부모는 -1
        while (!q.isEmpty()) {
            int cur = q.poll();
            order[idx++] = cur;
            for (int nx : adj.get(cur)) if (!vis[nx]) { vis[nx] = true; parent[nx] = cur; q.add(nx); }
        }
        // 서브트리 크기: BFS 방문순서를 거꾸로 훑으며 자식 크기를 부모로 누적
        // (잎에서 위로 올라가는 효과 -> 자식이 항상 부모보다 먼저 처리됨)
        int[] size = new int[n];
        Arrays.fill(size, 1);
        for (int i = idx - 1; i >= 1; i--) size[parent[order[i]]] += size[order[i]];

        // 루트 -> target 경로를 parent를 따라 거슬러 올라가 만든 뒤 뒤집어 정방향으로
        List<Integer> path = new ArrayList<>();
        for (int v = target; v != -1; v = parent[v]) path.add(v);
        Collections.reverse(path);
        int k = path.size() - 1; // target의 깊이(= 경로상 간선 수)

        // [자연 위치 계산]
        //  전위순회에서 target보다 먼저 방문되는 노드 = 경로상의 조상들(k개) +
        //  "각 경로 노드의 형제 중 번호가 더 작은 것들의 서브트리 전체".
        //  base_B = 그 작은 형제들의 서브트리 크기 합. 이 값이 마킹으로 건너뛸 수 있는 단위.
        List<Long> red = new ArrayList<>();   // 마킹으로 줄일 수 있는 양들의 목록
        long naturalPos = 1 + k;              // 루트 1 + 조상 k개를 기본으로 깔고 시작
        for (int i = 1; i <= k; i++) {
            int node = path.get(i), par = path.get(i - 1);
            long baseB = 0;
            for (int ch : adj.get(par)) {
                if (ch == parent[par]) continue;   // 조부모(par의 부모) 제외 -> 나머지는 par의 자식
                if (ch < node) baseB += size[ch];   // node보다 먼저 방문되는 형제 서브트리 (내림차순 정렬이면 ch > node 로 변경)
            }
            naturalPos += baseB;
            if (baseB > 0) red.add(baseB);
        }

        if (naturalPos <= deadline) { System.out.println(0); return; } // 이미 마감 안에 들어옴
        if (1 + k > deadline) { System.out.println(-1); return; }      // 모두 건너뛴 최소 위치(1+k)조차 초과 -> 불가능

        // [그리디] 한 번 마킹하면 base_B 하나를 통째로 줄일 수 있으니, 큰 것부터 빼면 마킹 수가 최소.
        red.sort(Collections.reverseOrder());
        long pos = naturalPos; int marks = 0;
        for (long r : red) {
            if (pos <= deadline) break; // 마감 안으로 들어오면 중단
            pos -= r; marks++;
        }
        System.out.println(marks);
    }
}