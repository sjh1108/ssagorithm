package week12_01.thdwngjs.BOJ_3207;
import java.io.*;
import java.util.*;

// 백준 3207 - 코딩은 체육과목 입니다
// 메모리 할당(malloc), 해제(free), 출력(print)을 시뮬레이션하는 문제입니다.
// 이중 연결 리스트(Doubly Linked List)를 사용하여 메모리 블록을 관리합니다.
class Main {
    // 명령어 파싱을 위한 상수들 (명령어 길이 등)
    private static final int FREE_SIZE = 5;   // "free(" 의 길이
    private static final int PRINT_SIZE = 6;  // "print(" 의 길이
    private static final int MALLOC_SIZE = 7; // "malloc(" 의 길이

    private static final String MALLOC_TOKEN = "=";
    private static final String FREE_STRING = "free";
    private static final String PRINT_STRING = "print";

    // 메모리 블록을 나타내는 노드 클래스
    static class Node {
        int start, len; // 블록의 시작 주소, 길이
        boolean blank;  // 빈 블록인지 여부 (true: 할당 가능, false: 사용 중)
        Node prev, next; // 이중 연결 리스트를 위한 포인터

        Node(int start, int len) {
            this.start = start;
            this.len = len;
            this.blank = true; // 기본적으로 빈 블록으로 생성
        }
    }

    // 변수명(Key)과 시작 주소(Value)를 매핑하는 맵 (print 명령어용)
    private static Map<String, Integer> map;
    // 변수명(Key)과 해당 메모리 노드(Node)를 매핑하는 맵 (free 명령어용)
    private static Map<String, Node> key2Node;
    
    // 메모리 리스트의 시작(head)과 끝(tail)을 나타내는 더미 노드
    private static Node head, tail;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        map = new HashMap<>();
        key2Node = new HashMap<>();

        // 0번지는 null 포인터 처럼 사용되지 않는 영역으로 가정하거나 더미 헤드로 설정
        head = new Node(0, 0); 
        head.blank = false; // 더미 노드는 할당 불가
        
        // 메모리 끝을 나타내는 더미 테일
        tail = new Node(100_001, 0); 
        tail.blank = false;
        
        // 초기 메모리 상태: 1번지부터 100,000번지까지 하나의 큰 빈 블록이 존재함
        Node tmp = new Node(1, 100_000);
        
        // 리스트 연결: head <-> tmp <-> tail
        head.next = tmp; tmp.prev = head;
        tmp.next = tail; tail.prev = tmp;

        int commandCount = Integer.parseInt(br.readLine());
        while (commandCount-- > 0) {
            String cmd = br.readLine();

            // 명령어 종류에 따라 분기 처리
            if (cmd.startsWith(FREE_STRING)) {
                // free(변수명)
                String key = separateBrackets(cmd, FREE_SIZE);
                free(key);
            } else if (cmd.startsWith(PRINT_STRING)) {
                // print(변수명)
                String key = separateBrackets(cmd, PRINT_SIZE);
                sb.append(print(key)).append('\n');
            } else {
                // 변수명=malloc(크기)
                StringTokenizer st = new StringTokenizer(cmd, MALLOC_TOKEN);
                String key = st.nextToken().trim(); // 변수명 추출
                
                String valStr = st.nextToken().trim(); // malloc(크기) 추출
                int size = Integer.parseInt(separateBrackets(valStr, MALLOC_SIZE));
                
                malloc(size, key);
            }
        }
        System.out.println(sb);
    }

    // "명령어(값)" 형태에서 괄호 안의 "값"만 추출하는 유틸리티 메서드
    private static String separateBrackets(String cmd, int commandSize) {
        // substring을 이용해 앞의 명령어와 괄호를 제거
        // (주의: cmd.length() - 2는 닫는 괄호 ')'와 세미콜론 ';' 등을 처리하기 위함일 수 있으나
        //  일반적인 입력 "func(arg)" 형태라면 length()-1이 적절합니다. 입력 데이터 형식을 확인 필요)
        return cmd.substring(commandSize, cmd.length() - 2);
    }

    // 메모리 할당 (First-Fit 전략 사용)
    private static void malloc(int size, String key) {
        Node cur = head.next;

        // 변수가 이미 존재했다면 덮어씌워지는 개념이므로 초기화 (문제 조건에 따라 다를 수 있음)
        map.put(key, 0); 
        key2Node.remove(key); 
        
        // 리스트를 순회하며 할당 가능한 빈 블록 탐색
        while (cur != tail) {
            // 빈 블록이고(blank == true), 요청한 크기(size)보다 크거나 같다면 할당 가능
            if (cur.blank && cur.len >= size) {
                cur.blank = false; // 사용 중으로 변경
                map.put(key, cur.start); // 시작 주소 저장
                key2Node.put(key, cur);  // 해당 노드 매핑

                // 블록의 크기가 요청 크기보다 크다면, 남은 공간을 분할(Split)해야 함
                if (cur.len > size) {
                    // 남은 공간으로 새로운 빈 노드 생성
                    Node tmp = new Node(cur.start + size, cur.len - size);
                    
                    // 새 노드를 현재 노드 뒤에 연결 (cur <-> tmp <-> cur.next)
                    tmp.next = cur.next;
                    tmp.prev = cur;
                    
                    cur.next.prev = tmp;
                    cur.next = tmp;
                    
                    // 현재 노드의 길이는 요청한 사이즈로 축소
                    cur.len = size;
                }
                return; // 할당 완료 후 종료
            }
            cur = cur.next; // 다음 블록으로 이동
        }
    }

    // 메모리 해제 및 병합 (Coalescing)
    private static void free(String key) {
        Node n = key2Node.get(key); // 해제할 변수의 노드 가져오기
        if (n == null) return; // 존재하지 않는 변수면 무시

        n.blank = true; // 빈 블록으로 표시
        
        // 1. 오른쪽(다음) 블록이 빈 블록이면 병합
        Node next = n.next;
        if (next != tail && next.blank) {
            n.len += next.len; // 길이 합치기
            // 포인터 조정하여 next 노드 삭제
            n.next = next.next;
            next.next.prev = n;
        }

        // 2. 왼쪽(이전) 블록이 빈 블록이면 병합
        Node prev = n.prev;
        if (prev != head && prev.blank) {
            prev.len += n.len; // 이전 블록에 현재 블록 길이 합치기
            // 포인터 조정하여 현재 노드(n) 삭제 (prev가 통합 노드가 됨)
            prev.next = n.next;
            n.next.prev = prev;
        }

        // 매핑 정보 삭제 및 주소값 0으로 초기화
        key2Node.remove(key);
        map.put(key, 0);
    }

    // 변수의 시작 주소 출력
    private static int print(String key) {
        return map.getOrDefault(key, 0); // 없으면 0 반환
    }
}