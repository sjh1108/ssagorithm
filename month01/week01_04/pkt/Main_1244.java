package week01_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

// 스위치 켜고 끄기 실버4
public class Main_1244 {
    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());

        int[] sw = new int[n+1];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= n; i++) {
            sw[i] = Integer.parseInt(st.nextToken());
        }

        int m = Integer.parseInt(br.readLine());

        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int gender = Integer.parseInt(st.nextToken());
            int k = Integer.parseInt(st.nextToken());

            if(gender == 1){ // 남학생
                for(int idx = k; idx <= n; idx += k){ // k의 배수, n크기 전까지.
                    // 상태변화: 1 <-> 0 바꾸기
                    sw[idx] = 1 - sw[idx];
                }
            } else {
                int l = k;
                int r = k;

                // 여학생은 받은 스위치를 중심으로 대칭 구간의 범위를 찾는다.
                while(l - 1 >= 1 && r + 1 <= n && sw[l - 1] == sw[r + 1]){
                    l--;
                    r++;
                }

                // 찾은 범위 내에서 상태 변화 -> 범위 내 스위치만 전부 바꾸기.
                for (int idx = l; idx <= r; idx++) {
                    sw[idx] = 1 - sw[idx];
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            sb.append(sw[i]).append(" ");
            if(i % 20 == 0) sb.append("\n");
        }
        System.out.print(sb);
    }
}


