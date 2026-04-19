package platinum_5;

import java.io.*;
import java.util.*;

public class Main_1517_버블_소트 {

    /*
     * BOJ_1517 : 버블 소트 (Platinum_5)
     * 자료구조 및 알고리즘 : 세그먼트 트리
     *
     * [문제 요약]
     * - 수열에 대해 버블 소트를 수행할 때, 실제 swap이 총 몇 번 일어나는지를 구해야 한다.
     * - 배열을 직접 정렬하면서 swap을 세면 O(N^2)이 되어 시간 초과가 발생한다.
     * - 따라서 실제 정렬 없이 swap 횟수만 계산해야 한다.
     *
     * [핵심 아이디어]
     * - 버블 소트에서 어떤 수가 자기보다 뒤에 있는 더 작은 수들과 자리를 바꾸는 횟수만큼 swap이 발생한다.
     * - 이 코드는 "현재 남아 있는 수들 중 최댓값"을 뒤에서부터 하나씩 확정해 가는 방식으로 swap 수를 센다.
     * - 최댓값이 있는 위치 idx를 찾고,
     *   그 idx 뒤에 아직 남아 있는 원소 개수를 세면
     *   그 최댓값이 최종 위치로 가기 위해 필요한 swap 횟수가 된다.
     * - 이후 그 값을 제거하고 같은 작업을 반복하면 전체 swap 횟수를 구할 수 있다.
     *
     * [구현 메모]
     * - maxTree:
     *   - 각 구간의 최댓값을 저장
     *   - findIndex()에서 현재 전체 최댓값이 존재하는 "가장 뒤쪽 인덱스"를 찾는 데 사용
     * - cntTree:
     *   - 각 위치에 아직 남아 있는 원소 수(초기 1)를 저장
     *   - getSwapCnt(idx)에서 idx 뒤쪽에 남아 있는 원소 개수를 구하는 데 사용
     * - findIndex():
     *   - 루트의 최댓값과 같은 값이 오른쪽 자식에 있으면 오른쪽으로 내려간다.
     *   - 이렇게 하면 동일한 최댓값이 여러 개 있어도 가장 뒤의 인덱스를 찾게 된다.
     * - getSwapCnt(left):
     *   - left 위치부터 끝까지 살아 있는 원소 개수를 구한 뒤,
     *   - 자기 자신 1개를 제외하기 위해 마지막에 -1을 한다.
     * - 어떤 값을 사용한 뒤에는
     *   1) maxTree에서는 해당 리프를 Integer.MIN_VALUE로 바꿔 제거
     *   2) cntTree에서는 해당 리프를 0으로 바꿔 제거
     *   한 뒤 부모 노드까지 갱신한다.
     * - swap 횟수는 커질 수 있으므로 total은 long으로 관리한다.
     *
     * [시간 복잡도]
     * - 트리 구축: O(N)
     * - 각 단계마다
     *   - 최댓값 위치 찾기: O(log N)
     *   - 뒤쪽 개수 세기: O(log N)
     *   - 제거 갱신: O(log N)
     * - 총: O(N log N)
     */

    static int n, p = 1;
    static int[] maxTree, cntTree;

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        while(p < n) p <<= 1;

        maxTree = new int[p*2];
        cntTree = new int[p*2];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            maxTree[p+i] = Integer.parseInt(st.nextToken());
            cntTree[p+i] = 1;
        }

        for(int i=n; i<p; i++) maxTree[p+i] = Integer.MIN_VALUE;

        for(int i=p-1; i>0; i--) {
            maxTree[i] = Math.max(maxTree[i << 1], maxTree[i << 1 | 1]);
            cntTree[i] = cntTree[i << 1] + cntTree[i << 1 | 1];
        }
    }

    static int findIndex() {
        int idx = 1;

        // 현재 구간의 최댓값과 같은 값이 오른쪽에도 있으면 오른쪽으로 내려가
        // 최댓값이 존재하는 가장 마지막 인덱스를 찾는다.
        while(idx < p) {
            if(maxTree[idx << 1 | 1] == maxTree[idx]) idx = idx << 1 | 1;
            else idx <<= 1;
        }

        return idx - p;
    }

    static int getSwapCnt(int left) {
        int right = p*2 - 1, cnt = 0;
        left += p;

        // 현재 인덱스부터 끝까지 살아 있는 원소 수를 센다.
        while(left <= right) {
            if(left % 2 == 1) cnt += cntTree[left++];
            if(right % 2 == 0) cnt += cntTree[right--];
            left >>= 1; right >>= 1;
        }

        // 자기 자신은 제외해야 하므로 -1
        return cnt - 1;
    }

    public static void main(String[] args) throws Exception {
        init();

        long total = 0L;
        for(int i=0; i<n-1; i++) {
            int idx = findIndex();
            total += getSwapCnt(idx);

            idx += p;
            // maxTree에서는 제거된 값으로 처리
            maxTree[idx] = Integer.MIN_VALUE;
            // cntTree에서는 살아 있는 개수를 0으로 처리
            cntTree[idx] = 0;
            while(idx > 1) {
                idx >>= 1;
                maxTree[idx] = Math.max(maxTree[idx << 1], maxTree[idx << 1 | 1]);
                cntTree[idx] = cntTree[idx << 1] + cntTree[idx << 1 | 1];
            }
        }

        System.out.println(total);
    }

}