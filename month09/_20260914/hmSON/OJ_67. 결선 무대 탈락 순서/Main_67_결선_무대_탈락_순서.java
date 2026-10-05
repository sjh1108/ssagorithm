import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 시뮬레이션, 이분 탐색, 세그먼트 트리
     * 직전 문제와 동일한 형태의 요세푸스 문제
     * N은 최대 200,000으로 동일하므로, 세그먼트 트리를 이용해 문제를 해결한다
     * 직전 문제와 다른 부분은 누가 탈락하느냐에 따라 다음 턴에 건너뛰어야 할 거리와 방향이 달라진다는 것
     * 매 탈락자가 나올 때마다 이 부분만 정확하게 계산한다면 다음 탈락 예정자의 위치는 동일하게 계산할 수 있다
     */

    static int p = 1;
    static int[] tree; // 세그먼트 트리

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long k = Long.parseLong(st.nextToken());

        // 각 참가자의 결선 포인트 등록
        long[] point = new long[n];
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            point[i] = Long.parseLong(st.nextToken());
        }

        // 세그먼트 트리의 노드 수는 n 이상인 2의 제곱수 * 2
        // 200,000 크기 수열을 관리하려면 최소 2^18 = 262,144 개의 리프노드를 가진 이진 트리가 요구됨
        while(p < n) p <<= 1;
        tree = new int[p*2];
        for(int i=0; i<n; i++) tree[p+i] = 1; // 참가자 등록
        for(int i=p-1; i>0; i--) tree[i] = tree[i << 1] + tree[i << 1 | 1]; // 구간합 초기화

        int target = 0, dir = 1; // 탈락 예정자의 현재 순서, 현재 턴의 진행 방향(시계 방향은 1, 반시계는 -1)
        StringBuilder sb = new StringBuilder(); // 탈락 순서 등록
        while(n > 0) {
            // 다음 탈락 예정자의 현재 순서 계산
            // 직전 탈락자의 결선 포인트에 따른 진행 방향을 적용
            target += (int)(k % n) * dir;
            if(target < 1) target += n;
            else if(target > n) target -= n;

            // 탈락자 등록
            int eliminated = eliminate(target);
            sb.append(eliminated + 1).append(" ");

            // 탈락자 발생에 따른 다음 턴의 진행 거리 및 방향 결정
            n--;
            k = point[eliminated]; // 다음 턴의 진행 거리는 탈락자의 결선 포인트
            dir = k % 2 == 0 ? -1 : 1; // 다음 턴의 진행 방향은 탈락자의 결선 포인트가 홀수면 시계 방향, 짝수면 반시계 방향
            if(dir == 1) target--; // 진행 방향에 따른 시작 위치 재정비
        }

        System.out.println(sb);
    }

    // 탈락 예정자 탐색 메서드
    // 구간합 세그먼트 트리 + 이분 탐색 과정을 이용
    static int eliminate(int target) {
        int idx = 1; // 루트 노드
        while(idx < p) {
            // 현재 노드의 생존자 수 미리 차감
            tree[idx]--;
            // 왼쪽 서브노드의 생존자 수에 따라 다음 이동 방향 결정
            if(target > tree[idx << 1]) {
                // 타겟이 오른쪽 서브트리에 있는 경우
                // 오른쪽으로 이동할 경우 왼쪽 서브트리의 생존자 수를 뺀 나머지 인원 수만 취급해야 동일한 로직에 적용 가능
                target -= tree[idx << 1];
                idx = idx << 1 | 1;
            } else {
                // 타겟이 왼쪽 서브트리에 있는 경우
                idx <<= 1;
            }
        }
        // 리프 노드
        tree[idx] = 0;

        return idx - p;
    }

}