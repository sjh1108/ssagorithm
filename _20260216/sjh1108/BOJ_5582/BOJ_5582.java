package _20260216.sjh1108.BOJ_5582;

import java.io.*;

// 백준 5582 - 공통 부분 문자열 (DP)
// 두 문자열의 "최장 공통 부분 문자열(Longest Common Substring)"의 길이를 구하는 문제
// 주의: 최장 공통 부분 수열(LCS, Subsequence)과 다르게 문자가 '연속'되어야 함.
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
 
        String str1 = br.readLine();
        String str2 = br.readLine();

        int len1 = str1.length();
        int len2 = str2.length();

        int max = 0; // 최장 길이 저장
        
        // map[i][j]: str1의 i번째 문자와 str2의 j번째 문자를 '끝'으로 하는 공통 부분 문자열의 길이
        int[][] map = new int[len1 + 1][len2 + 1];

        char[] array1 = str1.toCharArray();
        char[] array2 = str2.toCharArray();
        
        // 1부터 시작하여 패딩을 둠 (i-1, j-1 인덱스 접근 용이)
        for(int i = 1; i <= len1; i++){
            for(int j = 1; j <= len2; j++){
                // 두 문자가 같다면, 이전 대각선(직전 문자들)의 공통 길이 + 1
                if(array1[i-1] == array2[j-1]){
                    map[i][j] = map[i-1][j-1] + 1;
                    max = Math.max(max, map[i][j]);
                }
                // 두 문자가 다르다면, 연속성이 끊기므로 길이는 0 (배열 초기값이므로 생략 가능)
                // else { map[i][j] = 0; } 
            }
        }

        System.out.println(max);
    }
}