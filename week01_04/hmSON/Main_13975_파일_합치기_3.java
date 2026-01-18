import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_13975_파일_합치기_3 {

    /*
     * BOJ_13975 : 파일 합치기 3 (Gold_4)
     * 자료구조 및 알고리즘 : 그리디, 우선순위 큐
     *
     * [문제 요약]
     * - 소설의 각 장(Chapter) 파일 크기가 주어질 때, 두 파일을 합치는 비용은 두 파일 크기의 합이다.
     * - 모든 파일을 하나로 합칠 때까지 발생하는 비용의 총합을 최소화하라.
     *
     * [핵심 아이디어]
     * - 가장 작은 비용을 만들기 위해서는 매 순간 가장 작은 크기의 두 파일을 골라 합쳐야 한다. (허프만 코딩 원리)
     * - 가장 작은 값을 반복적으로 꺼내고, 합친 값을 다시 넣어야 하므로 '최소 힙(Min Heap)'이 필요하다.
     * - Java.Util의 PriorityQueue를 사용하는 대신 배열을 이용해 직접 최소 힙을 구현하여 해결한다.
     *
     * [구현 메모]
     * - heap[] : 1-based index를 사용하는 최소 힙 배열.
     * - insert() : 힙의 끝에 데이터를 넣고 부모와 비교하며 올라가는(Up-Heap) 연산.
     * - poll() : 루트(최솟값)를 제거하고 마지막 노드를 루트로 올린 뒤 자식과 비교하며 내려가는(Down-Heap) 연산.
     * - sum 변수 및 힙 내부 값은 파일 크기의 합이 int 범위를 넘을 수 있으므로 long 타입을 사용한다.
     *
     * [시간 복잡도]
     * - 힙 삽입/삭제 연산 : O(log N)
     * - N개의 파일에 대해 합치기 과정을 N-1번 반복하므로 전체 시간 복잡도는 O(N log N).
     */

    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();

    static int n;
    static long sum;
    static long[] heap;

    static void init() throws Exception {
        n = Integer.parseInt(br.readLine()); // 파일 개수
        heap = new long[n+1]; // 1-based indexing을 위해 크기 +1
        sum = 0;

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=1; i<=n; i++) {
            int value = Integer.parseInt(st.nextToken());
            insert(i, value); // 초기 힙 구성
        }
    }

    // 최소 힙 삽입 (Up-Heap)
    static void insert(int key, long value) {
        heap[key] = value;

        int child = key;
        while(child > 1) {
            int parent = child/2;

            // 부모가 더 작으면 힙 성질 만족하므로 중단
            if(heap[parent] <= heap[child]) break;

            swap(parent, child);
            child = parent;
        }
    }

    static void swap(int p, int c) {
        long temp = heap[p];
        heap[p] = heap[c];
        heap[c] = temp;
    }

    // 최소 힙 삭제 및 반환 (Down-Heap)
    static long poll() {
        if(n == 0) return -1;

        long target = heap[1]; // 최솟값(루트) 저장
        heap[1] = heap[n]; // 마지막 노드를 루트로 이동
        n--; // 힙 크기 감소

        int parent = 1;
        while(parent * 2 <= n) {
            int child = parent * 2; // 왼쪽 자식

            // 오른쪽 자식이 존재하고 더 작다면 오른쪽 자식 선택
            if(child+1 <= n && heap[child+1] < heap[child]) child++;

            // 부모가 자식보다 작으면 힙 성질 만족하므로 중단
            if(heap[child] >= heap[parent]) break;

            swap(parent, child);
            parent = child;
        }

        return target;
    }

    public static void main(String[] args) throws Exception {
        int t = Integer.parseInt(br.readLine());
        while(t-- > 0) {
            init();

            // 파일이 하나만 남을 때까지 반복 (N-1번 수행)
            while(n > 1) {
                // 가장 작은 두 파일 꺼내기
                long cur = poll() + poll();
                sum += cur; // 비용 누적

                // 합친 파일 다시 힙에 삽입 (n 증가 후 삽입)
                insert(++n, cur);
            }

            sb.append(sum).append("\n");
        }

        System.out.println(sb);
    }

}