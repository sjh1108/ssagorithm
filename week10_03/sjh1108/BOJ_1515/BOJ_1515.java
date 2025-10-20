package week10_03.sjh1108.BOJ_1515;

import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        // 입력을 받기 위한 BufferedReader 설정
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        // 지워진 숫자가 포함된 문자열 S 입력받기
        String str = br.readLine();
        
        // 문자열 S(str)의 몇 번째 문자를 찾고 있는지 가리키는 포인터(인덱스)
        int pt = 0; 

        // N이 될 수 있는 수(base)를 1부터 (최대 30000까지) 증가시키며 탐색
        // while(base++ <= 30000) : 
        // 1. base(0) <= 30000 인지 비교 (true)
        // 2. base를 1 증가시킴 (base = 1)
        // 3. 루프 실행 (base는 1부터 시작)
        int base = 0; 
        while (base++ <= 30000) { 
            // 현재 수(base)를 문자열로 변환 (예: base가 123이면 tmp는 "123")
            String tmp = String.valueOf(base);

            // 현재 수(tmp)의 각 자리 숫자를 하나씩 확인
            for (int i = 0; i < tmp.length(); i++) {
                
                // 현재 수(tmp)의 i번째 자리 숫자(char)가
                // 우리가 찾고 있는 문자열(str)의 pt번째 숫자(char)와 일치하는지 확인
                if (tmp.charAt(i) == str.charAt(pt)) {
                    // 일치한다면, 이제 str의 다음 문자를 찾아야 하므로 포인터(pt)를 1 증가
                    pt++;
                }

                // 만약 포인터(pt)가 str의 길이와 같아졌다면,
                // str의 모든 문자를 순서대로 다 찾았다는 의미
                if (pt == str.length()) {
                    // 이때의 base가 우리가 찾는 최소 N이므로 출력
                    System.out.println(base);
                    // 프로그램을 종료
                    return;
                }
            }
        }
        
        // (문제 조건상 30000 이내에 항상 답이 존재하므로 이 부분은 실행되지 않음)
    }
}