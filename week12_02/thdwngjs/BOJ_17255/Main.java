package week12_02.thdwngjs.BOJ_17255;

import java.io.*;
import java.util.*;

// 백준 17255 - N으로 만들기
// 숫자 N을 만들기 위해 숫자를 하나씩 붙여나가는 과정을 역추적하거나, 
// 가능한 모든 생성 순서를 탐색하여 고유한 과정의 수를 세는 문제입니다.
class Main {
    static char[] arr; // 목표 숫자 N을 문자 배열로 저장
    static Set<String> set; // 고유한 생성 과정(경로)을 저장하여 중복 제거
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        arr = br.readLine().toCharArray();
        set = new HashSet<>();
        
        // 숫자를 만드는 과정은 N의 구성 숫자 중 하나에서 시작됩니다.
        // 모든 위치(i)를 시작점으로 하여 탐색을 시작합니다.
        for(int i=0; i<arr.length; i++) {
            // dfs(왼쪽 인덱스, 오른쪽 인덱스, 현재 만들어진 문자열, 지금까지의 경로 문자열)
            dfs(i, i, ""+arr[i], ""+arr[i]);
        }
        
        // Set에 저장된 고유한 경로의 개수를 출력
        System.out.println(set.size());
    }
    
    /**
     * DFS를 이용해 가능한 모든 숫자 생성 과정을 탐색합니다.
     * @param L 현재 부분 문자열의 왼쪽 끝 인덱스 (arr 배열 기준)
     * @param R 현재 부분 문자열의 오른쪽 끝 인덱스 (arr 배열 기준)
     * @param s 현재까지 만들어진 문자열
     * @param path 칠판에 적힌 숫자들의 순서 (생성 과정)
     * 예: "1" -> "12" -> "123" 이라면 path는 "1 12 123"
     */
    static void dfs(int L, int R, String s, String path) {
        // 기저 사례: 현재 문자열이 목표 숫자(arr 전체)를 모두 커버했을 때
        if(L==0 && R==arr.length-1) {
            set.add(path); // 완성된 경로를 Set에 추가 (중복 자동 제거)
            return;
        }
        
        // 1. 왼쪽으로 숫자를 붙이는 경우 (L-1 인덱스가 유효할 때)
        if(L-1 >= 0) {
            // 새로운 문자열: arr[L-1] + 현재문자열
            // 경로: 기존경로 + " " + 새로운문자열
            dfs(L-1, R, arr[L-1]+s, path+" "+arr[L-1]+s);
        }
        
        // 2. 오른쪽으로 숫자를 붙이는 경우 (R+1 인덱스가 유효할 때)
        if(R+1 < arr.length) {
            // 새로운 문자열: 현재문자열 + arr[R+1]
            // 경로: 기존경로 + " " + 새로운문자열
            dfs(L, R+1, s+arr[R+1], path+" "+s+arr[R+1]);
        }
    }
}