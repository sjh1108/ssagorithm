package month08.week04.pkt;

import java.io.*;
import java.util.*;

public class Main_1157_단어공부_박기택 {
    public static void main(String[] args) throws IOException {

    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	
        // 대문자로 입력받기
        String input = br.readLine().toUpperCase();



        // 카운트를 저장할 배열 초기화 A~Z의 개수를 저장할 배열
        int [] count = new int[26];

        // 문자열을 순회하며 영어 문자 카운트
        for (char c : input.toCharArray()) {
            if ( 'A' <= c && c <= 'Z') {// 영어 대문자라면
                count[c - 'A']++;// 해당 알파벳의 배열 인덱스 증가
            }
        }

        // 최대값 찾기 및 중복 확인
        int maxCount = 0;
        char maxChar ='?';
        boolean isDuplicate = false;

        for (int i = 0; i < 26; i++) {
            if (count[i] > maxCount) {
                maxCount = count[i];
                maxChar = (char) (i + 'A'); // 최대값의 알파벳을 이런식으로 구할 수 있음 오..
                isDuplicate = false; // 중복 초기화
            } else if (count[i] == maxCount) {
                isDuplicate = true; // 중복 발생
            }
        }

        // 결과 출력
        if (isDuplicate) {
            System.out.println('?');
        } else {
            System.out.println(maxChar);
        }

    }
}

