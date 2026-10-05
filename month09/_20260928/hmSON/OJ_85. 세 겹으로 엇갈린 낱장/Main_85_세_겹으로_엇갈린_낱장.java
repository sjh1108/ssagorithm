import java.io.*;
import java.util.*;

public class Main {

    // 자료구조 및 알고리즘 : 정렬, 세그먼트 트리, 누적 합
    // 직전 문제와 n의 최대값은 동일, 대신 세 숫자가 역순으로 존재하는 경우만 카운트해야 함
    // i < j < k인 세 낱장의 번호가 ai > aj > ak를 만족하면 세 겹 엇갈림이라 칭함
    // 구간합 세그먼트 트리를 이용해 각 번호의 인덱스를 j로 고정하고 조건을 만족하는 i와 k의 개수를 저장하기
    // i -> leftCnt[j] : 최소값부터 오름차순(동일하면 인덱스 오름차순)으로 호출하여 왼쪽에 있는 모든 번호 개수 카운트
    // k -> rightCnt[j] : 최대값부터 내림차순(동일하면 인덱스 내림차순)으로 호출하여 오른쪽에 있는 모든 번호 개수 카운트
    // 각 인덱스를 j로 고정했을 때 조건을 만족하는 조합의 개수 : leftCnt[j] * rightCnt[j]의 누적 합

    static int n, p = 1;
    static int[] tree; // 세그먼트 트리

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());

        // 정렬 이후 각 번호의 인덱스 확인을 위해 int[n][2] 크기로 선언
        int[][] arr = new int[n][2];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            arr[i][0] = i;
            arr[i][1] = Integer.parseInt(st.nextToken());
        }

        // 배열 정렬(1: 번호 크기 오름차순, 2: 번호 크기 동일하면 인덱스 오름차순)
        // 어차피 양방향으로 한 번씩 다 호출할 것이므로 오름차순/ 내림차순 선택은 알아서
        Arrays.sort(arr, (a, b) -> a[1] != b[1] ? a[1] - b[1] : a[0] - b[0]);

        while(p < n) p <<= 1;
        tree = new int[p*2];

        // 각 번호 기준으로 엇갈린 위치에 있는 다른 번호 개수를 저장
        // leftCnt[] : 각 번호 기준 왼쪽에 있는 큰 번호 개수
        // rightCnt[] : 각 번호 기준 오른쪽에 있는 작은 번호 개수
        int[] leftCnt = new int[n], rightCnt = new int[n];

        // leftCnt
        build();
        for(int i=0; i<n; i++) {
            int idx = arr[i][0]; // 가장 작은 번호의 인덱스
            leftCnt[idx] = getCnt(0, idx); // 가장 작은 번호 앞에 있는 모든 번호의 개수를 카운트
            update(idx); // 사용한 번호는 세그먼트 트리에서 제거
        }

        // rightCnt
        build();
        for(int i=n-1; i>=0; i--) {
            int idx = arr[i][0]; // 가장 큰 번호의 인덱스
            rightCnt[idx] = getCnt(idx, n-1); // 가장 큰 번호 뒤에 있는 모든 번호의 개수를 카운트
            update(idx); // 사용한 번호는 세그먼트 트리에서 제거
        }

        // 누적 합
        // 개수가 int 범위를 초과할 수 있음
        long total = 0;
        for(int i=0; i<n; i++) {
            total += (long)leftCnt[i] * rightCnt[i];
        }

        System.out.println(total);
    }

    // 세그 트리 초기화 메서드. 동일한 형태로 2번 사용해야 해서 별도의 메서드로 분리
    // 리프 노드는 번호가 존재하면 1, 없으면 0으로 처리
    // 상위 노드는 구간합
    static void build() {
        for(int i=0; i<n; i++) tree[p+i] = 1;
        for(int i=p-1; i>0; i--) tree[i] = tree[i << 1] + tree[i << 1 | 1];
    }

    // 구간합 반환 메서드
    // (0, idx) -> idx 왼쪽 번호 개수 카운트
    // (idx, n-1) -> idx 오른쪽 번호 개수 카운트
    static int getCnt(int left, int right) {
        left += p; right += p;

        int cnt = 0;
        while(left <= right) {
            if(left % 2 == 1) cnt += tree[left++];
            if(right % 2 == 0) cnt += tree[right--];

            left >>= 1; right >>= 1;
        }

        // idx 위치의 값도 카운트에 포함되었으므로 반환시 1을 빼줘야 함
        return cnt - 1;
    }

    // 구간합 갱신 메서드
    // 해당 문제에서는 이미 사용한 번호를 제거하고 모든 상위 노드의 구간합을 1씩 감소시키는 데 활용
    static void update(int idx) {
        idx += p;
        tree[idx] = 0;

        while(idx > 1) {
            idx >>= 1;
            tree[idx]--;
        }
    }

}