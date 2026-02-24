package _20260223.thdwngjs.BOJ_7453;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

// 백준 7453 - 합이 0인 네 정수 (투 포인터, 이분 탐색, Meet in the Middle)
class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());

        // map[i][0] = A, map[i][1] = B, map[i][2] = C, map[i][3] = D
        long[][] map = new long[N][4];
        for(int i = 0; i < N; i++){
            StringTokenizer st = new StringTokenizer(br.readLine());

            int idx = 0;
            map[i][idx++] = Long.parseLong(st.nextToken());
            map[i][idx++] = Long.parseLong(st.nextToken());
            map[i][idx++] = Long.parseLong(st.nextToken());
            map[i][idx++] = Long.parseLong(st.nextToken());
        }

        // 4개의 배열을 A+B, C+D 2개의 그룹으로 묶음
        // N이 최대 4000이므로, N*N은 16,000,000
        long[] ab = new long[N*N];
        long[] cd = new long[N*N];

        int idx = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                // 배열 생성 시 같은 인덱스를 공유하도록 idx++의 위치 주의
                ab[idx] = map[i][0] + map[j][1];
                cd[idx++] = map[i][2] + map[j][3];
            }
        }

        // 투 포인터 탐색을 위해 두 배열 정렬
        Arrays.sort(ab);
        Arrays.sort(cd);

        long cnt = 0; // 정답의 개수 (최대 N^4 이므로 반드시 long 타입 사용)
        
        // 투 포인터 설정: l은 ab의 왼쪽 끝(가장 작은 값)부터, r은 cd의 오른쪽 끝(가장 큰 값)부터 시작
        int l = 0;
        int r = N*N - 1;
        
        while(l < N*N && r >= 0){
            long sum = ab[l] + cd[r];

            if(sum < 0){
                // 합이 0보다 작으면, 값을 키우기 위해 l을 우측으로 이동
                l++;
            } else if(sum > 0){
                // 합이 0보다 크면, 값을 줄이기 위해 r을 좌측으로 이동
                r--;
            } else {
                // 합이 0일 때: 중복된 값들이 연속해서 나타날 수 있으므로 개수를 세어 곱해줌
                long cnt1 = 1, cnt2 = 1;

                // ab 배열에서 현재와 같은 값을 가지는 원소의 개수 카운트
                while (l + 1 < N*N && (ab[l] == ab[l+1])) {
                    cnt1++;
                    l++;
                }

                // cd 배열에서 현재와 같은 값을 가지는 원소의 개수 카운트
                while (r > 0 && (cd[r] == cd[r-1])) {
                    cnt2++;
                    r--;
                }
                
                // 두 중복 개수를 곱하여 총 조합의 수를 더해줌
                cnt += cnt1 * cnt2;
                
                // 한 묶음의 처리가 끝났으므로 다음 다른 값을 확인하기 위해 포인터 이동
                l++;
                // r--; 는 위에서 sum > 0 조건이나 다음 루프에서 자연스럽게/혹은 추가 이동이 필요하지만,
                // l이 바뀌면 sum이 0이 아니게 되어 다음 분기에서 자연스럽게 알맞은 포인터가 이동함.
                // (일반적으로는 여기서 l++, r-- 둘 다 해주는 것이 조금 더 안전하고 빠름)
            }
        }

        System.out.println(cnt);
    }
}