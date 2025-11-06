package algo2025_11_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 아파트를 배정받을 수 있는 지원자의 최대 수 구하기

/**
 * 
 * @author SSAFY
 * 
 * 문제 설명

    총 n명의 지원자(applicants) 와 m개의 빈 아파트(apartments) 가 있습니다.
    당신의 목표는 가능한 한 많은 지원자에게 아파트를 배정하는 것입니다.
    
    각 지원자는 희망하는 아파트 크기가 있으며,
    그들은 희망 크기와의 차이가 k 이하인 아파트라면 수락할 수 있습니다.
    
    입력

    첫 번째 줄에는 세 개의 정수 n, m, k가 주어집니다.
    n = 지원자의 수
    m = 아파트의 수
    k = 허용 가능한 최대 크기 차이
    두 번째 줄에는 n개의 정수 a1, a2, …, an이 주어집니다.
    ai = i번째 지원자가 원하는 아파트의 크기
    즉, 해당 지원자는 [ai - k, ai + k] 범위 안의 아파트라면 수락 가능
    세 번째 줄에는 m개의 정수 b1, b2, …, bm이 주어집니다.
    bi = j번째 아파트의 실제 크기
    
    //
    4 3 5
    60 45 80 60
    30 60 75
    
    2
 *
 */

public class Ps_투포인터_아파트배정 {
    static int n, m, k;
    static int[] arr;
    static int[] size;

    public static void main(String[] args) throws IOException {
    	long cur = System.currentTimeMillis();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine()); 
        
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        
        arr = new int[n];
        size = new int[m];
        
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < m; i++) {
            size[i] = Integer.parseInt(st.nextToken());
        }
        
        Arrays.sort(arr);
        Arrays.sort(size);
        
        int i = 0; // 지원자 인덱스
        int j = 0; // 아파트 인덱스
        int cnt = 0; // 배정된 사람 수
        
        // 투 포인터 탐색
        while(i < n && j < m) {
            if(Math.abs(arr[i] - size[j]) <= k) {
                cnt++;
                i++;
                j++;
            } else if(size[j] < arr[i] - k) {
                j++; // 아파트가 너무 작을때
            } else {
                i++; // 아파트가 너무 클때
            }
        }
        System.out.println(cnt);
        long end = System.currentTimeMillis();
		System.out.println(end-cur);
    }

}