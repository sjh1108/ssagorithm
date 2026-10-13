import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 구간 DP, 팰린드롬
     * 구슬이 한 줄로 놓여있고, 팰린드롬이 성립하는 구간을 한꺼번에 제거하는 주문을 사용할 수 있음
     * 모든 구슬을 제거하기 위해 최소 몇 번의 주문을 사용해야 하는지 구해야 함
     *
     * 각 구간을 전부 제거하기 위해 사용해야 할 주문의 횟수를 기록하는 2차원 구간 DP 배열 사용
     * 각 구간의 구슬을 제거하는 방법은 기본 선택지와 조건부 선택지가 있음
     * -> 기본 선택지 : 현재 처리하려는 구간을 두 그룹으로 분리해 별도로 처리
     * -> 조건부 선택지 : 양 끝이 같은 값이면 가운데 그룹의 주문 사용 횟수를 그대로 계승
     * 위 방법 중 주문 사용 횟수가 최소인 경우를 선택하면 됨
     *
     * 두 그룹으로 분리하여 처리하는 DP 점화식 : dp[j][j+i] = Math.min(dp[j][j+i], dp[j][j+k] + dp[j+k+1][j+i]);
     * 현재 처리하는 그룹의 크기가 4일 경우, 두 그룹을 (1, 3), (2, 2), (3, 1)의 크기로 분리했을 때의 주문 사용 횟수를 비교하게 됨
     *
     * 양 끝 구슬의 값(j, j+i)이 같은 경우의 DP 점화식 : dp[j][j+i] = Math.min(dp[j][j+i], i == 1 ? 1 : dp[j+1][j+i-1]);
     * 양 끝이 같으면 가운데 그룹이 어떤 형태든 마지막 주문 사용시 양 끝의 구슬을 붙여도 반드시 팰린드롬이 성립함
     * 따라서 양 끝이 같은 값이면 가운데 그룹의 주문 사용 횟수를 계승한다는 선택지가 추가됨
     * 크기가 2인 구간의 경우 계승할 가운데 그룹이 없는 상태이므로 주문 사용 횟수는 1
     * 단, 이것이 최선의 선택지는 아닐 가능성이 존재하므로 기존의 최소값과 비교가 불가피하다는 점에 유의
     */

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int[] arr = new int[n];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 구간 DP 배열 초기화
        int[][] dp = new int[n][n];
        for(int i=0; i<n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE); // 최소값 갱신이 요구되므로 초기값은 Integer.MAX_VALUE
            dp[i][i] = 1; // 구슬 1개도 하나의 그룹으로 취급하므로 주문 사용 횟수 = 1
        }

        // 구간 DP를 이용한 팰린드롬
        // i: 양 끝 구슬의 간격(구간 크기 - 1)
        // j: 구간 내 첫 구슬의 위치
        // 작은 구간부터 처리해야 큰 구간의 처리를 간결하게 처리할 수 있으므로 첫번째 반복문이 구간 크기를 관리해야 함
        for(int i=1; i<n; i++) {
            for(int j=0; j<n-i; j++) {
                // 기본 선택지 : 두 그룹으로 분리하여 처리
                for(int k=0; k<i; k++) {
                    dp[j][j+i] = Math.min(dp[j][j+i], dp[j][j+k] + dp[j+k+1][j+i]);
                }

                // 조건부 선택지 : 양 끝 구슬의 값이 같은 경우
                // 단, 양 끝의 간격(i)이 1인 경우 참조할 가운데 값이 없는 상태이므로 주문 1회 사용과 동일
                if(arr[j] == arr[j+i]) {
                    dp[j][j+i] = Math.min(dp[j][j+i], i == 1 ? 1 : dp[j+1][j+i-1]);
                }
            }
        }

        // 전체 구간에 대한 주문 사용 횟수 출력
        System.out.println(dp[0][n-1]);
    }

}