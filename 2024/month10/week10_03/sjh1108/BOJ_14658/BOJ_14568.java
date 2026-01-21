package week10_03.sjh1108.BOJ_14658;

import java.io.*;
import java.util.*;

class Main {
    // 트램펄린의 한 변의 길이 (L x L)
    private static int L;

    // 별똥별의 위치 {x, y}를 저장할 리스트
    private static List<int[]> list;

    /**
     * (r, c)를 트램펄린의 '왼쪽 위' 모서리 좌표로 가정했을 때,
     * 해당 트램펄린 내부에 포함되는 별똥별의 개수를 세는 함수
     * @param r 트램펄린의 왼쪽 위 x좌표
     * @param c 트램펄린의 왼쪽 위 y좌표
     * @return [r, r+L], [c, c+L] 범위 내에 포함된 별똥별의 개수
     */
    private static int max(int r, int c){
        int cnt = 0; // 트램펄린 내부의 별똥별 개수 카운터

        // 저장된 모든 별똥별의 위치를 순회
        for(int[] s: list){
            int x = s[0]; // 별똥별의 x좌표
            int y = s[1]; // 별똥별의 y좌표

            // 현재 별똥별(x, y)이 트램펄린 범위 내에 있는지 확인
            // 트램펄린의 x범위: r <= x <= r+L
            // 트램펄린의 y범위: c <= y <= c+L
            if(x >= r && x <= r+L && y >= c && y <= c+L){
                cnt++; // 범위 내에 있다면 카운트 증가
            }
        }

        return cnt; // 범위 내 총 별똥별 개수 반환
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        // N (가로), M (세로)는 이 풀이에서 사용되지 않으므로 읽고 버림
        st.nextToken(); // N
        st.nextToken(); // M
        L = Integer.parseInt(st.nextToken()); // 트램펄린의 한 변 길이
        int K = Integer.parseInt(st.nextToken()); // 별똥별의 개수

        list = new ArrayList<>(); // 별똥별 좌표 리스트 초기화
        
        // K개의 별똥별 좌표를 입력받아 리스트에 저장
        for(int i = 0; i < K; i++){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken()); // 별똥별 x좌표
            int b = Integer.parseInt(st.nextToken()); // 별똥별 y좌표

            list.add(new int[]{a, b}); // 리스트에 {x, y} 배열 형태로 추가
        }

        int max = 0; // 트램펄린으로 막을 수 있는 별똥별의 *최대* 개수

        // [핵심 아이디어]
        // 가장 많은 별똥별을 잡는 '최적의' 트램펄린 위치는
        // 반드시 그 트램펄린의 *왼쪽* 경계선에 어떤 별똥별(i)이 걸치고,
        // 동시에 *위쪽* 경계선에 어떤 별똥별(j)이 걸치게 놓을 수 있다.
        // (i와 j는 같을 수도 있음)
        
        // 따라서, 모든 별똥별 i와 j의 조합 (K*K가지)에 대해,
        // i의 x좌표를 트램펄린의 왼쪽 경계로 (r = i.x)
        // j의 y좌표를 트램펄린의 위쪽 경계로 (c = j.y)
        // 하는 경우만 모두 테스트해보면 최적의 해를 찾을 수 있다.

        // 모든 별 i
        for(int[] a : list){
            // 모든 별 j
            for(int[] b: list){
                // i번째 별의 x좌표(a[0])와 j번째 별의 y좌표(b[1])를
                // 트램펄린의 '왼쪽 위' 모서리(r, c) 후보로 삼는다.
                int x = a[0]; // i번째 별의 x좌표
                int y = b[1]; // j번째 별의 y좌표

                // (x, y)를 왼쪽 위 모서리로 할 때 잡을 수 있는 별의 개수를 계산
                // 현재까지의 최대값(max)과 비교하여 더 큰 값으로 갱신
                max = Math.max(max, max(x, y));
            }
        }

        // 문제에서 요구하는 답: "지구에 부딪히는 별똥별의 *최소* 개수"
        // (총 별똥별 개수 K) - (트램펄린이 막는 최대 개수 max)
        System.out.println(K - max);
    }
}