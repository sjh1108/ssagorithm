package week10_04.sjh1108.BOJ_2607;

import java.io.*;

class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 단어의 개수 N (기준이 되는 첫 번째 단어 1개 + 비교할 단어 N-1개)
        int N = Integer.parseInt(br.readLine());

        // 기준이 되는 첫 번째 단어의 알파벳(A-Z) 개수를 세는 배열
        // cnt[0] = 'A'의 개수, cnt[1] = 'B'의 개수, ...
        int[] cnt = new int[26];
        String originString = br.readLine(); // 기준 단어 입력
        char[] origin = originString.toCharArray(); // 문자 배열로 변환
        
        // 기준 단어의 알파벳 개수를 센다
        for(char c: origin){
            cnt[c - 'A']++; // 'A'는 0번, 'B'는 1번 인덱스...
        }
        int len = originString.length(); // 기준 단어의 길이

        int res = 0; // 비슷한 단어의 개수를 셀 변수
        int[] copy; // 기준 단어의 알파벳 개수 배열(cnt)을 복사해서 사용할 배열
        
        // 나머지 N-1개의 단어를 입력받아 비교
        for(int i = 0; i < N-1; i++){
            // 비교 시작 전, 기준 단어의 알파벳 개수 배열(cnt)을 그대로 복사(clone)
            copy = cnt.clone();
            
            // x: 현재 단어(str)와 기준 단어(origin)에서 공통으로 포함된 알파벳의 총 개수
            int x = 0; 
            String str = br.readLine(); // 비교할 단어 입력
            char[] read = str.toCharArray(); // 문자 배열로 변환
            
            // 비교할 단어(read)의 알파벳을 하나씩 확인
            for(char c: read){
                // 만약 현재 알파벳(c)이 copy 배열(기준 단어의 남은 알파벳)에 1개 이상 있다면
                if(copy[c-'A'] > 0){
                    copy[c-'A']--; // 사용했으므로 개수를 1 줄임
                    x++; // 공통 알파벳 개수(x) 1 증가
                }
            }
            
            // 현재 단어가 기준 단어와 "비슷한 단어"인지 판별할 플래그
            boolean flag = false;

            // [비슷한 단어의 조건 판별]
            // x는 '기준 단어'와 '현재 단어'의 구성 중 공통된 알파벳의 총 개수를 의미
            // (copy 배열에는 기준 단어에만 있고 현재 단어에는 없는 알파벳의 개수가 남음)

            // 1. 두 단어의 길이가 같은 경우 (교체)
            //    공통 개수(x)가 (길이) 또는 (길이-1)이어야 함
            //    (x == len) : 두 단어의 구성이 완전히 같음 (예: DOG, DOG)
            //    (x == len-1) : 한 글자만 다름 (예: DOG, DOT) -> D, O가 공통(x=2)
            if(len == str.length() && (x == len || x == len-1)){
                flag = true;
            
            // 2. 기준 단어가 한 글자 더 긴 경우 (삭제)
            //    (예: DOG, DO)
            //    공통 개수(x)가 '현재 단어의 길이'(str.length())와 같아야 함
            //    (D, O가 공통(x=2), str.length()도 2)
            } else if(len == str.length()+1 && x == str.length()){
                flag = true;
            
            // 3. 현재 단어가 한 글자 더 긴 경우 (추가)
            //    (예: DO, DOG)
            //    공통 개수(x)가 '현재 단어의 길이 - 1'과 같아야 함
            //    (D, O가 공통(x=2), str.length()-1도 2)
            } else if(len == str.length()-1 && x == str.length()-1){
                flag = true;
            }

            // 위 3가지 조건 중 하나라도 만족했다면(flag == true)
            if(flag) res++; // 비슷한 단어 개수 증가
        }
        
        // 총 N-1개의 단어 중 비슷한 단어의 총 개수 출력
        System.out.println(res);
    }
}