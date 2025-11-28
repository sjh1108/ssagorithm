package week11_03.sjh1108.BOJ_15961;

import java.io.*;
import java.util.*;

// 백준 15961 - 회전 초밥 (슬라이딩 윈도우)
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        // n: 회전 초밥 벨트에 놓인 접시의 총 수
        int n = Integer.parseInt(st.nextToken());
        // d: 초밥의 총 종류 (번호 1 ~ d)
        int d = Integer.parseInt(st.nextToken());
        // k: 연속해서 먹는 접시의 수 (슬라이딩 윈도우의 크기)
        int k = Integer.parseInt(st.nextToken());
        // c: 쿠폰으로 무료로 받는 초밥의 종류
        int c = Integer.parseInt(st.nextToken());

        // 벨트 위의 초밥들을 저장할 배열
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        // 현재 윈도우(k개)에 포함된 초밥의 종류별 개수를 세는 배열 (빈도수 체크)
        // eaten[i] = i번 초밥을 현재 윈도우에서 몇 개 먹었는지
        int[] eaten = new int[d + 1];
        
        // 현재 윈도우 내의 '서로 다른' 초밥 종류의 수
        int uniqueCount = 0;

        // --- 1. 첫 번째 윈도우 (인덱스 0 ~ k-1) 설정 ---
        // 먼저 0번부터 k-1번까지 k개의 초밥으로 초기 윈도우를 설정
        for (int i = 0; i < k; i++) {
            // 이 초밥(arr[i])을 윈도우에서 처음 본다면 (eaten[arr[i]] == 0)
            if (eaten[arr[i]] == 0) {
                uniqueCount++; // 서로 다른 종류의 수 1 증가
            }
            eaten[arr[i]]++; // 해당 초밥의 개수를 1 증가시킴
        }

        // 최대 가짓수의 초기값은 첫 번째 윈도우의 가짓수
        int maxCount = uniqueCount;
        // 만약 이 윈도우에 쿠폰 초밥(c)이 포함되어 있지 않다면
        if (eaten[c] == 0) {
            maxCount++; // 쿠폰으로 1가지 종류를 추가할 수 있으므로 1 증가
        }

        // --- 2. 슬라이딩 윈도우 실행 (i는 윈도우의 '시작' 인덱스) ---
        // i=1 (윈도우 [1, k]) 부터 i=n-1 (윈도우 [n-1, k-2])까지 n-1번 더 실행
        // (총 n개의 윈도우를 검사하게 됨)
        for (int i = 1; i < n; i++) {
            
            // 2-1. 윈도우에서 빠지는 초밥 처리
            // 윈도우의 맨 앞(이전 시작점, i-1)에 있던 초밥
            int sushiToRemove = arr[i - 1];
            eaten[sushiToRemove]--; // 빠지는 초밥의 개수를 1 감소
            // 만약 이 초밥이 윈도우에서 0개가 되었다면 (더 이상 이 종류가 없다면)
            if (eaten[sushiToRemove] == 0) {
                uniqueCount--; // 서로 다른 종류의 수 1 감소
            }

            // 2-2. 윈도우에 새로 들어오는 초밥 처리
            // 윈도우의 맨 뒤에 새로 추가될 초밥
            // 인덱스 (i + k - 1) % n : 
            // i=1일 때 (1+k-1)%n = k%n 
            // i=n-1일 때 (n-1+k-1)%n = (n+k-2)%n
            // %n을 통해 배열의 끝에서 다시 앞으로 돌아오는 '회전'을 구현
            int sushiToAdd = arr[(i + k - 1) % n];
            // 만약 새로 들어온 초밥이 윈도우에 0개였다면 (새로운 종류라면)
            if (eaten[sushiToAdd] == 0) {
                uniqueCount++; // 서로 다른 종류의 수 1 증가
            }
            eaten[sushiToAdd]++; // 새로 들어온 초밥의 개수를 1 증가

            // 2-3. 최대 가짓수 갱신
            // 현재 윈도우의 가짓수로 초기화
            int currentCount = uniqueCount;
            // 이 윈도우에도 쿠폰 초밥(c)이 없다면
            if (eaten[c] == 0) {
                currentCount++; // 쿠폰으로 1가지 종류 추가
            }
            
            // 기존 최대값(maxCount)과 현재 윈도우의 가짓수(currentCount)를 비교하여 갱신
            maxCount = Math.max(maxCount, currentCount);
        }

        // 모든 윈도우를 탐색한 후의 최종 최대값 출력
        System.out.println(maxCount);
    }
}