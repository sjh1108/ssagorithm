import java.io.*;
import java.util.*;

public class Main {

    /*
     * 문제 : https://jungol.co.kr/problem/5461
     *
     * 자료구조 및 알고리즘 : 이분 탐색, 매개변수 탐색
     * 매개변수 탐색 문제의 핵심은 "구해야 할 해"를 기준으로 문제 설명을 재구성하는 것
     * 기존 : 숫자 구슬을 M개 이하 그룹으로 나누었을 때 각 그룹의 합 중 최대값이 최소가 되어야 함
     * 재구성 : 각 그룹의 합 상한치를 특정 값 X로 제한했을 때 M개 이하의 그룹으로 구성할 수 있어야 함
     * 각 그룹의 합의 상한치가 X일 때
     * -> 총 그룹 수가 M개 이하라면 X보다 작은 값을 새 상한치로 재설정
     * -> 총 그룹 수가 M개 초과라면 X보다 큰 값을 새 상한치로 재설정
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int group = Integer.parseInt(st.nextToken());

        int[] arr = new int[n];
        // 각 구슬의 최대값은 1억, 구슬의 개수는 최대 10만 -> int 범위 초과
        long sum = 0L;
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            int x = Integer.parseInt(st.nextToken());
            sum += x;
            arr[i] = x;
        }

        // 최대 그룹 수가 1이면 구슬 값 합계를 출력하는 것과 결과 동일
        if(group == 1) {
            System.out.println(sum);
            return;
        }

        // 해의 범위는 1 ~ 모든 구슬의 합(group == 1)
        long left = 1, right = sum;
        while(left <= right) {

            // mid를 그룹의 합 상한치로 제한
            long mid = left + (right - left) / 2;
            int groupCnt = 1;
            long curSum = 0L;

            // 그룹 구성 실패 가능성에 대한 플래그
            // 사유 : 구슬 하나의 값이 mid보다 크면 사용할 수 없는 구슬이 됨
            boolean flag = false;
            for(int val : arr) {
                if(val > mid) {
                    flag = true;
                    break;
                }

                // 각 구슬을 확인할 때마다 curSum에 구슬 값을 더함
                // 더한 결과가 mid보다 크면 이 구슬을 하나의 그룹으로 만들 수 없어 새 그룹을 생성하고 여기에 구슬을 포함해야 함
                // 그룹 카운트 추가 및 그룹의 값 초기화
                curSum += val;
                if(curSum > mid) {
                    curSum = val;
                    groupCnt++;
                    if(groupCnt > group) break;
                }
            }

            // 사용 불가능한 구슬이 있거나 총 그룹 수가 제한 그룹 수를 초과함 -> 상한치를 올려서 재검증
            // 총 그룹 수가 제한 그룹 수 이하 -> 상한치를 내려서 재검증
            if(flag || groupCnt > group) left = mid + 1;
            else right = mid - 1;
        }

        System.out.println(left);
    }

}