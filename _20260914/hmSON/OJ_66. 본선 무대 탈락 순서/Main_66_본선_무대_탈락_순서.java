import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 시뮬레이션, 이분 탐색, 세그먼트 트리
     * 건너뛰어야 할 수가 변하는 것 외에 큰 차이가 없는 전형적인 요세푸스 문제
     * 참가자 수가 5,000에서 200,000으로 늘어남에 따라 큐를 이용한 선형 처리를 대신할 알고리즘이 필요함
     *
     * 탈락 예정자를 찾는 과정의 시간 복잡도를 적어도 O(logN) 수준으로 낮출 필요가 있음
     * 이분 탐색의 개념이 적용될 경우 N = 200,000 기준 각 탐색을 18회의 연산으로 처리할 수 있음
     * 정렬되지 않은 수열에서의 이분 탐색이 요구되므로 세그먼트 트리를 이용
     *
     * 각 참가자의 상태를 1(생존), 0(탈락)으로 관리
     * 세그먼트 트리의 각 노드는 구간합을 저장
     * 이후 탈락 예정자의 순서가 주어지면 양쪽 서브트리의 생존자 수 구간합을 이용해 어느 쪽 서브트리로 내려갈 지 결정
     * 예1) 찾아야 할 탈락 예정자의 순서가 4번째인데, 왼쪽 서브트리의 생존자 수가 4명이다 -> 왼쪽 서브트리로
     * 예2) 찾아야 할 탈락 예정자의 순서가 4번째인데, 왼쪽 서브트리의 생존자 수가 3명이다 -> 오른쪽 서브트리로
     *
     * 위 과정을 거쳐 리프 노드까지 도착했을 때의 인덱스가 탈락 예정자의 번호
     * 탈락 처리 이후 다시 리프 노드부터 올라가면서 생존자 수를 차감해야 함
     * 그 대신 탐색 과정에서 지나가는 노드의 생존자 수를 1씩 차감하여 불필요한 재방문 방지
     */

    static int p = 1;
    static int[] tree; // 세그먼트 트리

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long k = Long.parseLong(st.nextToken());

        // 세그먼트 트리의 노드 수는 n 이상인 2의 제곱수 * 2
        // 200,000 크기 수열을 관리하려면 최소 2^18 = 262,144 개의 리프노드를 가진 이진 트리가 요구됨
        while(p < n) p <<= 1;
        tree = new int[p*2];
        for(int i=0; i<n; i++) tree[p+i] = 1; // 참가자 등록
        for(int i=p-1; i>0; i--) tree[i] = tree[i << 1] + tree[i << 1 | 1]; // 구간합 초기화

        int target = 1; // 탈락 예정자의 현재 순서
        StringBuilder sb = new StringBuilder(); // 탈락 순서 등록
        while(n > 0) {
            // 다음 탈락 예정자의 현재 순서를 미리 계산
            target += (int)(k % n) - 1;
            if(target < 1) target += n;
            else if(target > n) target -= n;

            // 탈락자 등록
            sb.append(eliminate(target)).append(" ");

            k++;
            n--;
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

        return idx - p + 1;
    }

}