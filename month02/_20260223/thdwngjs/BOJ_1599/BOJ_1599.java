package _20260223.thdwngjs.BOJ_1599;

import java.io.*;
import java.util.*;

// 백준 1599 - 민식어 (문자열, 정렬)
class Main {
    static class Node {
        String first;  // 원본 문자열 (출력용)
        String second; // 치환된 문자열 (정렬 비교용)
        
        Node(String first, String second) {
            this.first = first;
            this.second = second;
        }
    }

    private static Map<String, String> map;
    private static Queue<Node> pq;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        
        // 민식어 -> 일반 알파벳 매핑 및 우선순위 큐 초기화
        init();

        for (int i = 0; i < N; i++) {
            String str = br.readLine();
            StringBuilder order = new StringBuilder();

            // 입력받은 민식어 문자열을 순회하며 일반 알파벳(a~t)으로 치환
            for (int j = 0; j < str.length(); j++) {
                // 'ng' 처리: 'n' 다음에 'g'가 오면 하나의 알파벳 'ng'로 취급
                if (str.charAt(j) == 'n' && j + 1 < str.length() && str.charAt(j + 1) == 'g') {
                    order.append(map.get("ng"));
                    j++; // 'g'까지 처리했으므로 인덱스 1 증가
                } else {
                    // 그 외 일반 단일 알파벳 처리
                    order.append(map.get(String.valueOf(str.charAt(j))));
                }
            }
            
            // 우선순위 큐에 (원본 문자열, 치환된 문자열) 객체 삽입
            // 삽입과 동시에 치환된 문자열(second) 기준으로 자동 정렬됨
            pq.add(new Node(str, order.toString()));
        }

        // 정렬된 순서대로 원본 문자열 출력
        while(!pq.isEmpty()){
            System.out.println(pq.poll().first);
        }
    }
    
    // 민식어 알파벳 순서를 자바의 기본 사전순 정렬로 쉽게 처리하기 위해
    // 민식어의 각 알파벳을 영어 알파벳 'a'부터 't'까지 순서대로 1:1 매핑
    private static void init() {
        map = new HashMap<>();
        
        // 치환된 문자열(second)을 기준으로 사전순(compareTo) 오름차순 정렬하는 큐
        pq = new PriorityQueue<>((o1, o2) -> o1.second.compareTo(o2.second));

        // 민식어 순서: a b k d e g h i l m n ng o p r s t u w y
        // 치환 알파벳: a b c d e f g h i j k l  m n o p q r s t
        map.put("a", "a"); map.put("b", "b"); map.put("k", "c"); map.put("d", "d");
        map.put("e", "e"); map.put("g", "f"); map.put("h", "g"); map.put("i", "h");
        map.put("l", "i"); map.put("m", "j"); map.put("n", "k"); map.put("ng", "l");
        map.put("o", "m"); map.put("p", "n"); map.put("r", "o"); map.put("s", "p");
        map.put("t", "q"); map.put("u", "r"); map.put("w", "s"); map.put("y", "t");
    }
}