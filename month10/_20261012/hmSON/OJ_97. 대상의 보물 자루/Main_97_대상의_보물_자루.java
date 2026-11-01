import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 백트래킹, 중간에서 만나기, 정렬, 투 포인터, 누적 최대값
     * 겉보기에는 전형적인 Knapsack 알고리즘을 사용해야 하는 문제
     * 그러나 입력으로 주어지는 무게와 가치 모두 int 범위를 초과할 수 있는 수준이므로 DP 배열을 만들 수 없음
     * 백트래킹을 시도할 경우 최대 2^36(약 687억)개의 조합이 생성될 수 있으므로 시간 초과 발생
     *
     * 단, 보물을 두 그룹으로 나누어 각 그룹에 대한 백트래킹을 시도하면 최대 2^18(약 26만)개의 조합이 생성됨
     * 따라서 두 그룹에서 생성되는 조합의 무게와 가치를 관리하는 배열을 만들어두고 백트래킹으로 이를 채운다
     * 이후 두 그룹의 조합을 각각 하나씩 선택하여 만들어지는 최종 조합의 무게가 무게 제한을 충족하면 가치 최대값의 후보가 될 수 있다
     * 두 그룹의 조합을 하나씩 선택하는 과정은 투 포인터로 진행한다
     *
     * 아무것도 선택하지 않는 경우(0, 0)도 하나의 조합이므로 한 그룹의 조합만 선택하는 경우도 확인할 수 있다
     */

    static int combiCnt = 0; // 각 그룹의 조합 수를 카운트
    static long limit; // 무게 제한

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        limit = Long.parseLong(st.nextToken());

        // 보물 전체를 두 그룹으로 분할(홀수일 경우 오른쪽에 1 추가)
        int nl = n/2, nr = n/2 + (n%2 == 1 ? 1 : 0);

        long[] weightLeft = new long[nl], weightRight = new long[nr];
        int[] valueLeft = new int[nl], valueRight = new int[nr];

        // 두 그룹에서 생성될 수 있는 조합의 무게와 가치를 담을 배열
        // 최대 2^n개가 생성될 수 있음
        long[][] arrLeft = new long[(int)Math.pow(2, nl)][2];
        long[][] arrRight = new long[(int)Math.pow(2, nr)][2];

        for(int i=0; i<nl; i++) {
            st = new StringTokenizer(br.readLine());
            weightLeft[i] = Long.parseLong(st.nextToken());
            valueLeft[i] = Integer.parseInt(st.nextToken());
        }

        for(int i=0; i<nr; i++) {
            st = new StringTokenizer(br.readLine());
            weightRight[i] = Long.parseLong(st.nextToken());
            valueRight[i] = Integer.parseInt(st.nextToken());
        }

        // 그룹 1 백트래킹
        backtrack(nl, 0, 0, 0, 0, arrLeft, weightLeft, valueLeft);
        int cntLeft = combiCnt;
        combiCnt = 0;

        // 그룹 2 백트래킹
        backtrack(nr, 0, 0, 0, 0, arrRight, weightRight, valueRight);
        int cntRight = combiCnt;

        // 두 그룹의 조합 배열을 무게 오름차순으로 정렬
        Arrays.sort(arrLeft, 0, cntLeft, Comparator.comparingLong(a -> a[0]));
        Arrays.sort(arrRight, 0, cntRight, Comparator.comparingLong(a -> a[0]));

        // 그룹 2의 배열은 누적 최대값 적용
        // 해당 무게 이하의 조합 중 가치 최대값은 얼마인지 전처리함으로써 추가 순회 없이 결과 확인 가능
        for(int i=1; i<cntRight; i++) {
            if(arrRight[i-1][1] > arrRight[i][1]) arrRight[i][1] = arrRight[i-1][1];
        }

        // 투 포인터
        // 왼쪽 포인터는 그룹 1에 붙어 오름차순으로, 오른쪽 포인터는 그룹 2에 붙어 내림차순으로
        int left = 1, right = cntRight - 1;
        long max = arrRight[right][1]; // 그룹 1의 조합이 (0, 0)인 경우의 최대값을 미리 기록(굳이 안해도 left = 0으로 두면 첫 루프에서 기록됨)
        while(left < cntLeft && right >= 0) {
            if(arrLeft[left][0] + arrRight[right][0] > limit) {
                // 두 그룹의 조합의 무게 합이 제한 초과
                // 그룹 2의 현재 처리하는 조합은 더 이상 그룹 1과 조합 불가
                right--;
            } else {
                // 두 그룹의 조합의 무게 합이 제한 충족
                // 두 그룹의 조합을 하나씩 선택하여 가치를 더한 결과 확인
                long sum = arrLeft[left][1] + arrRight[right][1];
                if(sum > max) max = sum;
                left++;
            }
        }

        System.out.println(max);
    }

    /**
     * 백트래킹 메서드
     * @param n: 그룹 크기
     * @param idx: 시작 인덱스, 직전 단계에서 선택된 인덱스의 다음 번호
     * @param w: 무게 합
     * @param v: 가치 합
     * @param visited: 방문 처리 변수. 각 그룹의 최대 크기는 18이므로 비트 연산 활용 가능
     * @param arr: 그룹 내 생성 가능한 조합 배열
     * @param warr: 그룹 내 각 보물의 무게 배열
     * @param varr: 그룹 내 각 보물의 가치 배열
     */
    static void backtrack(int n, int idx, long w, long v, int visited, long[][] arr, long[] warr, int[] varr) {
        // 현재 조합의 무게가 제한을 충족하는 경우 조합 등록을 먼저 처리
        // 첫 호출 시점에도 이 조건문을 먼저 방문하므로 (0, 0) 조합도 포함됨
        if(w <= limit) {
            arr[combiCnt][0] = w;
            arr[combiCnt][1] = v;
            combiCnt++;
        }

        // 배열 끝까지 방문 or 현 조합이 무게 제한 초과 -> 가지치기
        if(idx == n || w > limit) return;

        for(int i=idx; i<n; i++) {
            // 방문 처리
            if((visited & (1 << i)) != 0) continue;
            // 현재 처리하는 보물의 무게와 가치를 합하고 재귀
            backtrack(n, i+1, w + warr[i], v + varr[i], visited | (1 << i), arr, warr, varr);
        }
    }

}