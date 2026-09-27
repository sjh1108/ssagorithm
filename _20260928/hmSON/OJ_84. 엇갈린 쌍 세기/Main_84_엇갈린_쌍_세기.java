import java.io.*;
import java.util.*;

public class Main {

    // 자료구조 및 알고리즘 : 정렬, 세그먼트 트리
    // 수열의 최대 크기가 20만으로 증가함에 따라 구간합 세그먼트 트리를 이용해 카운트하는 방식으로 변경
    // 큰 수부터 내림차순으로 각 번호의 위치를 확인하고 자신보다 뒤에 있는 모든 번호의 개수를 한꺼번에 카운트
    // 사용한 번호는 세그먼트 트리에서 제거

    static int p = 1;
    static int[] tree; // 세그먼트 트리

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 정렬 이후 각 번호의 인덱스 확인을 위해 int[n][2] 크기로 선언
        int[][] arr = new int[n][2];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            arr[i][0] = i;
            arr[i][1] = Integer.parseInt(st.nextToken());
        }

        // 배열 정렬(1: 숫자 크기 내림차순, 2: 숫자 크기 동일하면 인덱스 내림차순)
        // 2번 조건을 인덱스 오름차순으로 하면 동일한 크기의 숫자쌍을 엇갈린 쌍으로 처리하게 됨
        Arrays.sort(arr, (a, b) -> a[1] != b[1] ? b[1] - a[1] : b[0] - a[0]);

        while(p < n) p <<= 1;
        tree = new int[p*2];
        // 리프 노드는 번호가 존재하면 1, 없으면 0으로 처리
        for(int i=0; i<n; i++) tree[p+i] = 1;
        // 상위 노드는 구간합
        for(int i=p-1; i>0; i--) tree[i] = tree[i << 1] + tree[i << 1 | 1];

        // 모든 순서쌍이 엇갈린 쌍일 경우 개수가 int 범위를 초과할 수 있음
        long total = 0;
        for(int i=0; i<n; i++) {
            int idx = arr[i][0]; // 가장 큰 번호의 인덱스
            total += getCnt(idx, n-1); // 가장 큰 번호 뒤에 있는 모든 번호의 개수를 카운트
            update(idx); // 사용한 번호는 세그먼트 트리에서 제거
        }

        System.out.println(total);
    }

    // 구간합 반환 메서드
    // left는 현재 가장 큰 번호의 인덱스, right는 항상 마지막 인덱스
    // 현재 가장 큰 번호의 뒤에 있는 모든 번호의 개수를 반환
    static int getCnt(int left, int right) {
        left += p; right += p;

        int cnt = 0;
        while(left <= right) {
            if(left % 2 == 1) cnt += tree[left++];
            if(right % 2 == 0) cnt += tree[right--];

            left >>= 1; right >>= 1;
        }

        // left 위치의 값도 카운트에 포함되었으므로 반환시 1을 빼줘야 함
        return cnt - 1;
    }

    // 구간합 갱신 메서드
    // 해당 문제에서는 이미 사용한 숫자를 제거하고 모든 상위 노드의 구간합을 1씩 감소시키는 데 활용
    static void update(int idx) {
        idx += p;
        tree[idx] = 0;

        while(idx > 1) {
            idx >>= 1;
            tree[idx]--;
        }
    }

}