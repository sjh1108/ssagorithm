import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 트리, DFS, ETT(오일러 투어 테크닉)
     * 본부인 1번 기지를 루트로 하는 트리 구조의 조사 기지
     * 질의로 두 기지 번호 u, v가 주어졌을 때 u가 v의 상위 기지인지 판단해야 함
     * 최악의 경우 경사 트리가 주어질 수 있으므로 매 질의마다 경로 탐색을 수행하는 것은 시간 초과를 유발할 수 있음
     *
     * ETT(오일러 투어 테크닉)
     * 트리에 DFS를 적용했을 때 각 정점별 진입 시각과 탈출 시각이 존재함
     * 상위 노드의 진입 시각과 탈출 시각 내에 하위 트리 내부의 모든 정점의 진입 및 탈출 시각이 포함됨
     * 이 점을 이용하여 각 기지별로 탐색시의 진입 시각과 탈출 시각을 기록하고,
     * 두 기지 번호가 주어졌을 때 기지 v의 진입 및 탈출 시각이 기지 u의 진입 및 탈출 시각 내부에 존재하는 지 검증
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int q = Integer.parseInt(st.nextToken());

        // 양방향 인접 리스트
        List<Integer>[] graph = new ArrayList[n];
        for(int i=0; i<n; i++) graph[i] = new ArrayList<>();

        for(int i=0; i<n-1; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken()) - 1;
            int b = Integer.parseInt(st.nextToken()) - 1;
            graph[a].add(b);
            graph[b].add(a);
        }

        // 각각 기지별 진입 시각과 탈출 시각 기록
        // 진입 시에는 시각을 카운트, 탈출 시에는 시각을 카운트하지 않음
        int[] in = new int[n], out = new int[n];
        int[] stack = new int[n]; // DFS를 재귀 없이 처리하기 위해 stack 사용
        int top = 1, t = 1; // 각각 stack의 진입점 위치, 현재 시각
        while(top > 0) {
            int idx = stack[top-1];

            // 아직 진입 시각이 없는 상태면 진입해야 함, 아니라면 탈출해야 함
            if(in[idx] == 0) {
                // 진입 시 진입 시각 기록 및 현재 시각 카운트. 하위 기지 번호도 stack에 추가
                in[idx] = t++;
                for(int next : graph[idx]) {
                    if(in[next] != 0) continue;
                    stack[top++] = next;
                }
            } else {
                out[idx] = t;
                top--;
            }
        }

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<q; i++) {
            st = new StringTokenizer(br.readLine());
            int sup = Integer.parseInt(st.nextToken()) - 1;
            int inf = Integer.parseInt(st.nextToken()) - 1;
            // sup이 inf의 상위 기지면 YES, 아니면 NO
            sb.append(in[sup] < in[inf] && out[sup] >= out[inf] ? "YES" : "NO").append("\n");
        }
        System.out.println(sb);
    }

}