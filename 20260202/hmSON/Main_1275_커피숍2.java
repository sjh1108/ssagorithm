import java.io.*;
import java.util.*;

public class Main_1275_커피숍2 {

    /*
     * BOJ_1275 : 커피숍2 (Gold_1)
     * 자료구조 및 알고리즘 : 세그먼트 트리
     *
     * [문제 요약]
     * - N개의 정수가 주어진다.
     * - Q번의 턴 동안 다음을 수행한다:
     * 1. 주어진 구간 [x, y]의 합을 구하여 출력한다. (단, x > y일 수 있음)
     * 2. a번째 정수를 b로 바꾼다.
     * - 모든 정수와 합은 long 범위를 가질 수 있다.
     *
     * [핵심 아이디어]
     * - 수열의 값이 수시로 변경(Update)되고, 특정 구간의 합(Query)을 구해야 하므로 '세그먼트 트리'가 적합하다.
     * - 재귀 방식이 아닌 반복문(Iterative) 방식을 사용하여 트리를 구현했다.
     * - 입력받은 구간 [x, y]가 항상 x <= y가 아니므로, x > y인 경우 swap 처리가 필요하다.
     * - 값의 범위가 크므로 합계 저장 배열은 long 타입을 사용한다.
     *
     * [구현 메모]
     * - p : 리프 노드가 시작되는 인덱스 (N보다 크거나 같은 2의 거듭제곱).
     * - getSum : 구간 [left, right]의 합을 구하는 함수.
     * - update : 특정 인덱스의 값을 변경하고 부모 노드들을 갱신하는 함수.
     * - 비트 연산(<<, >>, &, |)을 활용하여 인덱스 이동 및 홀/짝 판별 속도를 높임.
     *
     * [시간 복잡도]
     * - 트리 구축(Build): O(N)
     * - 쿼리(Query) 및 업데이트(Update): O(log N)
     * - 총 시간 복잡도: O(N + Q log N)
     */



    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int n, turn, p = 1;
    static long[] tree;

    static void build() throws Exception {
        st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        turn = Integer.parseInt(st.nextToken());

        // 리프 노드의 시작점(p) 계산 (N보다 큰 가장 작은 2의 거듭제곱)
        while(p < n) p <<= 1;
        tree = new long[p*2];

        // 리프 노드 데이터 입력 및 세팅
        st = new StringTokenizer(br.readLine());
        for(int i=p; i<p+n; i++) {
            tree[i] = Integer.parseInt(st.nextToken());
        }

        // 내부 노드(구간합) 계산 (Bottom-Up)
        for(int i=p-1; i>=1; i--) {
            tree[i] = tree[i*2] + tree[i*2 + 1];
        }
    }

    static long getSum(int left, int right) {
        long sum = 0;
        // 리프 노드 인덱스로 변환
        left += p; right += p;

        while(left <= right) {
            // left가 오른쪽 자식이면 더하고 오른쪽으로 한 칸 이동
            if((left & 1) == 1) sum += tree[left++];
            // right가 왼쪽 자식이면 더하고 왼쪽으로 한 칸 이동
            if((right & 1) == 0) sum += tree[right--];

            // 부모 노드로 이동 (/2)
            left >>= 1; right >>= 1;
        }

        return sum;
    }

    static void update(int idx, int value) {
        int target = p + idx; // 변경할 노드의 실제 인덱스
        tree[target] = value;

        // 루트까지 올라가며 구간합 갱신
        while(true) {
            target >>= 1; // 부모 노드로 이동
            if(target == 0) return; // 루트 도달 시 종료
            tree[target] = tree[target << 1] + tree[target << 1 | 1]; // 왼쪽 자식 + 오른쪽 자식
        }
    }

    public static void main(String[] args) throws Exception {
        build();

        StringBuilder sb = new StringBuilder();
        for(int i=0; i<turn; i++) {
            st = new StringTokenizer(br.readLine());
            int left = Integer.parseInt(st.nextToken()) - 1;
            int right = Integer.parseInt(st.nextToken()) - 1;

            // 입력된 구간의 순서가 뒤바뀐 경우 swap 처리
            if(left > right) { int temp = left; left = right; right = temp; }

            sb.append(getSum(left, right)).append("\n");

            int idx = Integer.parseInt(st.nextToken()) - 1;
            int value = Integer.parseInt(st.nextToken());
            update(idx, value);
        }

        System.out.println(sb);
    }

}