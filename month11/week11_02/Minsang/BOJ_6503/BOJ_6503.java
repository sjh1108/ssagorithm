package week11_02.Minsang.BOJ_6503;

import java.io.BufferedReader;
import java.util.HashMap;

class Main {
    // 구하고자 하는 것 : 연속하는 문자의 최댓값을 구하기
    static int m;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new java.io.InputStreamReader(System.in));

        while(true) {
            m = Integer.parseInt(br.readLine());
            if (m == 0) break;
            
                String line = br.readLine();

                int left = 0;
                int max = 0;
                // 각 문자의 개수를 세기 위한 HashMap 사용
                HashMap<Character, Integer> map = new HashMap<>();

                // 입력받은 문자열을 한 글자씩 확인
                for(int i = 0; i < line.length(); i++){
                    char c = line.charAt(i);

                    // 현재 문자의 개수 갱신
                    if(map.containsKey(c)){
                        map.put(c, map.get(c)+1);
                    }
                    // 처음 나오는 문자라면 1로 초기화
                    else {
                        map.put(c, 1);
                    }

                    // 현재 문자의 개수가 m을 초과한다면, left 포인터를 이동시키며 개수를 줄임
                    while(map.size() > m) {
                        char lChar = line.charAt(left);
                        map.put(lChar, map.get(lChar)-1);
                        // 왼쪽 문자의 개수가 0이 되면 맵에서 제거
                        if(map.get(lChar) == 0) {
                            map.remove(lChar);
                        }
                        left++;
                    }

                    // 현재 부분 문자열의 길이 계산 및 최댓값 갱신
                    int len = i - left + 1;
                    if(len > max) {
                        max = len;
                    }
                }
                System.out.println(max);

        }
    }

}