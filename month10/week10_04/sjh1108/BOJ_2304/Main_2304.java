package week10_04.sjh1108.BOJ_2304;

import java.io.*;
import java.util.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine()); // 기둥의 개수

        // 기둥의 위치(L)를 인덱스로, 높이(H)를 값으로 저장하는 배열
        // 위치 L은 1부터 1000까지 가능
        int[] location = new int[1001]; 

        StringTokenizer st;
        while(N-- > 0){
            st = new StringTokenizer(br.readLine());
            // location[L] = H
            location[Integer.parseInt(st.nextToken())] = Integer.parseInt(st.nextToken());
        }

        int ans = 0; // 총 면적

        // [핵심 아이디어]
        // 1. 왼쪽 -> 오른쪽으로 훑으면서, "현재까지 본 최대 높이"를 지붕으로 가정하고 면적을 더한다.
        // 2. 오른쪽 -> 왼쪽으로 훑으면서, "가장 높은 기둥"을 만날 때까지,
        //    "현재까지 본 (오른쪽 기준) 최대 높이"를 지붕으로 가정하고,
        //    1번에서 과다 계산된 면적(전체최대높이 - 오른쪽최대높이)을 뺀다.

        // 1. 왼쪽 -> 오른쪽 (0 -> 1000)
        int max = 0; // 왼쪽에서부터 훑으면서 만난 기둥 중 최대 높이
        for(int i = 0; i <= 1000; i++) {
            int l = location[i]; // 현재 위치(i)의 기둥 높이
            
            // 현재 기둥이 지금까지의 최대 높이보다 높으면, 최대 높이 갱신
            if(l > max) max = l;
            
            // 현재 위치(i)의 면적은 "지금까지의 최대 높이(max)"가 된다.
            // (너비가 1이므로 높이 = 면적)
            ans += max;
        }
        
        // 2. 오른쪽 -> 왼쪽 (1000 -> 0), 과다 계산된 부분 수정
        int max2 = 0; // 오른쪽에서부터 훑으면서 만난 기둥 중 최대 높이
        for(int i=1000; i>0; i--) {
            // 'max'는 1번 과정에서 찾은 "전체 기둥 중 가장 높은 높이"
            // 만약 오른쪽에서 훑다가 이 가장 높은 기둥을 만나면,
            // 그 지점부터 왼쪽은 1번 과정에서 이미 올바르게 계산되었으므로 수정 중지
            if(location[i] == max) break;
            
            // 오른쪽에서 훑으면서 최대 높이 갱신
            if(location[i] > max2) max2 = location[i];
            
            // [수정 작업]
            // 1번 과정에서 이 위치(i)에는 'max'(전체 최대 높이)만큼이 더해졌음.
            // 하지만 이 위치의 올바른 지붕 높이는 'max2'(오른쪽 기준 최대 높이)여야 함.
            // 따라서 과다 계산된 (max - max2) 만큼을 총 면적에서 뺀다.
            ans -= max - max2;
        }

        System.out.println(ans);
    }
}