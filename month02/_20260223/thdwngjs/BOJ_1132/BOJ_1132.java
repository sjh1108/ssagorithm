package _20260223.thdwngjs.BOJ_1132;

import java.io.*;
import java.util.*;

// 백준 1132 - 합 (그리디 알고리즘)
class Main {
    private static long ans = 0;

    // alpha: A~J까지 각 알파벳이 차지하는 자릿수의 가중치 총합
    private static long[] alpha = new long[10];
    
    // notZero: 문자열의 맨 앞에 등장하여 '0'이 될 수 없는 알파벳 체크
    private static boolean[] notZero = new boolean[10];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            String input = br.readLine();
            
            // 문자열의 맨 앞자리에 오는 알파벳은 0이 될 수 없으므로 true 처리
            notZero[input.charAt(0) - 'A'] = true;
            
            long k = 1; // 1, 10, 100, 1000 ... (자릿수 가중치)
            
            // 뒤에서부터(일의 자리부터) 읽으면서 가중치를 더해줌
            for (int j = input.length() - 1; j >= 0; j--) {
                alpha[input.charAt(j) - 'A'] += k;
                k *= 10;
            }
        }

        // 10개의 알파벳(A~J)이 모두 한 번 이상 등장했는지 확인
        // 만약 등장하지 않은 알파벳이 있다면 가중치가 0일 것임.
        boolean flag = false;
        for (int i = 0; i < 10; i++) {
            if (alpha[i] == 0) {
                flag = true;
                break;
            }
        }

        // 모든 알파벳(A~J 10개)이 다 등장했다면(flag == false), 
        // 반드시 누군가는 '0'을 가져가야 함.
        if (!flag) {
            long min = Long.MAX_VALUE;
            int idx = -1;

            // 0이 될 수 있는 알파벳(!notZero) 중에서 가중치가 가장 작은 것을 찾음
            for (int i = 0; i < 10; i++) {
                if (!notZero[i] && alpha[i] < min) {
                    min = alpha[i];
                    idx = i;
                }
            }
            
            // 해당 알파벳의 가중치를 0으로 만들어버림 (나중에 숫자 배정 시 0을 곱하는 것과 같은 효과)
            if (idx != -1) {
                alpha[idx] = 0;
            }
        }

        // 가중치 배열 정렬 (오름차순)
        Arrays.sort(alpha);

        // 가중치가 작은 것부터 큰 것 순서대로 0 ~ 9 숫자를 곱해줌
        // 앞서 0을 강제 배정한 알파벳의 가중치는 이미 0이 되었거나, 
        // 아예 등장하지 않은 알파벳이 맨 앞에 와서 알아서 0이 곱해지게 됨.
        int mul = 0;
        for (int i = 0; i < 10; i++) {
            ans += alpha[i] * mul++;
        }

        System.out.println(ans);
    }
}