package _20260323.thdwngjs.BOJ_28277;

import java.io.*;
import java.util.*;

/**
 * BOJ 28277 - 뭉쳐야 산다
 * 알고리즘: Small-to-Large (작은 집합을 큰 집합에 합치기)
 *
 * N개의 집합에 대해 Q개의 쿼리를 처리하는 문제.
 * - 쿼리 1 (a, b): 집합 b의 모든 원소를 집합 a로 합침 (b는 비움)
 * - 쿼리 2 (a): 집합 a의 크기 출력
 *
 * 핵심: 합칠 때 항상 작은 집합을 큰 집합에 합쳐 시간복잡도를 최적화 (swap trick)
 */
class Main {

    private static Set<Integer>[] set;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        // 각 집합을 HashSet으로 관리
        set = new Set[N+1];
        for(int i = 1; i <= N; i++){
            set[i] = new HashSet<>();

            st = new StringTokenizer(br.readLine());
            int size = Integer.parseInt(st.nextToken());

            while(size-- > 0){
                set[i].add(Integer.parseInt(st.nextToken()));
            }
        }

        StringBuilder sb = new StringBuilder();
        while(Q-- > 0){
            st = new StringTokenizer(br.readLine());

            int op = Integer.parseInt(st.nextToken());

            int a = Integer.parseInt(st.nextToken());
            if(op == 1){
                // 합치기 연산: set[b]를 set[a]로 합침
                int b = Integer.parseInt(st.nextToken());

                // Small-to-Large: a가 더 작으면 swap하여 항상 큰 쪽에 합침
                if(set[a].size() < set[b].size()){
                    swap(a, b);
                }

                // 작은 집합(b)의 원소를 큰 집합(a)에 추가
                for(int i: set[b]){
                    set[a].add(i);
                }

                set[b].clear();
            } else{
                // 크기 조회 연산
                sb.append(set[a].size()).append('\n');
            }
        }

        System.out.println(sb);
    }

    // 집합 참조를 교환하여 항상 큰 집합 기준으로 합칠 수 있게 함
    private static void swap(int a, int b){
        Set<Integer> tmp = set[b];
        set[b] = set[a];
        set[a] = tmp;
    }
}