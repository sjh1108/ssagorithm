/**
 * BOJ 2313 - 보석 구매하기 (골드 5)
 *
 * [풀이] 최대 부분 배열 합 (카데인 알고리즘 변형)
 * 각 행에서 최대 부분 배열 합을 구하되, 합이 같을 경우 더 짧은 구간을 선택한다.
 * 전체 보석 가치의 합 = 각 행의 최대 부분 배열 합의 합
 */
package _20260330.thdwngjs.BOJ_2313;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine()); // 행의 수

        long max = 0; // 전체 보석 가치 합

        StringBuilder sb = new StringBuilder();
        while(N-- > 0){
            int L = Integer.parseInt(br.readLine()); // 현재 행의 보석 수

            int ans = -1_000_000_000, sum = -1_000_000_000;
            int al, l, ar, r;          // al~ar: 최종 답 구간, l~r: 현재 탐색 구간
            al = l = ar = r = 1;

            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i = 1; i <= L; i++){
                int d = Integer.parseInt(st.nextToken());

                // 카데인 알고리즘: 이전 합이 음수면 새로 시작
                if(sum < 0){
                    sum = d;
                    l = r = i;
                } else{
                    if(sum != 0) r++;    // 합이 양수면 구간 확장
                    else l = r = i;      // 합이 0이면 새 구간 시작 (더 짧은 구간 우선)
                    sum += d;
                }

                // 합이 더 크거나, 같으면 더 짧은 구간으로 갱신
                if(ans < sum || (ans == sum && r-l < ar - al)){
                    ans = sum;
                    al = l; ar = r;
                }
            }

            sb.append(al).append(" ").append(ar);
            sb.append('\n');

            max += ans;
        }
        System.out.println(max);
        System.out.println(sb);
    }
}