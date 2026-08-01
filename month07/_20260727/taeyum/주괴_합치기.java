package _20260727.taeyum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

// 주괴 N개, 두 개의 주괴를 골라 합치기
// 두 주괴의 합 > 비용
// 합체 후 합체 가능 (퓨우우우젼 + 퓨우우우우젼)
// 음 두 개씩 가져와야할 거 같고 뽑은 거 다시 넣고 더하고 반복...
// 보자마자 그냥 정렬해서 최소값 두개 더하면 되겠노 ㅋㅋ
// 했지만 정말 고맙게도 우선순위 큐 쓰라고 친히 적혀있네요
// 음음 감사합니다 쓸게요 ㅋㅋ 는 양심 없으니 분석이라도 하겠음
// 정렬 후 더한 값 다시 넣고 또 정렬 -> O(N^2)
// 우선 순위 큐 O(log N), N번 반복 -> N O(log N) -> 노드 N 개 완전 이진트리, 노드 수 2배씩 (1,2,4,8...) -> O(log N)
// add -> 트리의 맨 끝에 넣은 뒤, 부모와 비교 후 나보다 크면 자리 바꿔서 부모 노드쪽으로 올라가며 반복 log N 번 -> O(log N)
// poll -> 최소값은 맨 위에 있어 꺼낼땐 O(1), 꺼낸 후 채우기는 마지막 노드를 루트로 옮긴 후 자식과 비교하며 내려감 -> O(log N)

public class 주괴_합치기 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        PriorityQueue<Long> pq = new PriorityQueue<>();
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            pq.add(Long.parseLong(st.nextToken()));
        }

        long jugae = 0;

        while (pq.size() > 1) {
            long a = pq.poll();
            long b = pq.poll();
            long sum = a + b;
            jugae += sum;
            pq.add(sum);
        }

        System.out.println(jugae);
    }
}
