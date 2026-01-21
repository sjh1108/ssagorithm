package week10_02.sjh1108.BOJ_18870;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        // 입력
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        // 원본 배열과 정렬을 위한 복사 배열
        int[] origin = new int[N];
        int[] copied = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++){
            int x = Integer.parseInt(st.nextToken());
            
            copied[i] = origin[i] = x;
        }
        Arrays.sort(copied);
        
        // 정렬, 배열 압축을 위한 해쉬맵 선언
        HashMap<Integer, Integer> map = new HashMap<>();
        int idx = 0;
        for(int i = 0; i < N; i++){
            // 맵에 이미 저장된 위치는 배열 압축을 위해서 저장하지 않음
            if(!map.containsKey(copied[i])){
                // 맵에 인덱스 저장
                map.put(copied[i], idx++);
            }
        }

        // 맵에서 인덱스를 가져와 출력력력
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < N; i++){
            sb.append(map.get(origin[i])).append(' ');
        }
        System.out.println(sb);
    }
}