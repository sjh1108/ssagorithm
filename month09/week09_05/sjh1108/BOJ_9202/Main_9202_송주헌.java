package week09_05.sjh1108.BOJ_9202;

import java.io.*;
import java.util.*;

/**
 * 백준 9202번 Boggle 문제 풀이 클래스
 */
class Main {

    /**
     * Trie 자료구조를 표현하는 정적 내부 클래스
     * 각 노드는 문자를 키로 자식 노드를 가지며, 단어의 끝인지 여부를 저장합니다.
     */
    static class Node {
        // Trie의 시작점인 루트 노드
        static Node root;

        // static 초기화 블록을 사용하여 클래스 로드 시 루트 노드를 생성합니다.
        static {
            root = new Node();
        }

        // 자식 노드들을 저장하는 맵. '문자'를 key로, 해당 문자에 해당하는 '자식 노드'를 value로 가집니다.
        // ※ 성능 최적화 포인트: 알파벳 대소문자가 정해진 경우, Map보다 크기 26의 배열(Node[] children = new Node[26])을 사용하는 것이 더 빠릅니다.
        // 배열은 해시 계산 없이 인덱스로 O(1) 접근이 가능하기 때문입니다.
        Map<Character, Node> children;
        // 해당 노드에서 끝나는 단어가 있는지를 나타내는 플래그
        boolean terminal;

        /**
         * Node 생성자. 자식 맵을 초기화합니다.
         */
        Node() {
            children = new HashMap<>();
        }

        /**
         * Trie에 단어를 추가하는 메소드
         *
         * @param word 추가할 단어
         */
        public static void insert(String word) {
            Node cur = root; // 루트 노드에서 시작

            // 단어의 각 문자를 순회하며 Trie에 노드를 추가하거나 이동합니다.
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);

                // 현재 노드의 자식 중에 해당 문자가 없으면 새로 노드를 생성하여 추가합니다.
                cur.children.putIfAbsent(c, new Node());
                // 자식 노드로 이동합니다.
                cur = cur.children.get(c);

                // 단어의 마지막 문자라면, 해당 노드를 단어의 끝으로 표시합니다.
                if (i == word.length() - 1) {
                    cur.terminal = true;
                }
            }
        }

        /**
         * 주어진 문자로 시작하는 단어가 Trie에 있는지 확인하는 메소드 (DFS 시작 전 최적화)
         *
         * @param init 시작 문자
         * @return 루트의 자식으로 해당 문자가 있으면 true, 없으면 false
         */
        public static boolean isInitWord(char init) {
            return root.children.containsKey(init);
        }

        /**
         * Trie에 특정 문자열(key)이 존재하는지 확인하는 메소드
         *
         * @param key  확인할 문자열
         * @param flag true이면 완전한 단어인지(terminal), false이면 접두사인지 확인
         * @return 존재 여부
         */
        public static boolean contains(String key, boolean flag) {
            // 보글 게임에서 만들 수 있는 최대 길이는 8입니다.
            if (key.length() > 8) return false;

            Node cur = root; // 루트에서부터 탐색 시작
            for (int i = 0; i < key.length(); i++) {
                char c = key.charAt(i);
                cur = cur.children.get(c);

                // 다음 노드가 없으면 해당 문자열은 Trie에 존재하지 않습니다.
                if (cur == null) return false;
            }

            // flag 값에 따라 단어의 끝인지(terminal) 또는 그냥 존재하는지(접두사) 여부를 반환합니다.
            if (flag) return cur.terminal; // 단어가 완전히 일치하는지 확인
            else return true; // 접두사로 존재하는지 확인
        }
    }

    // 4x4 크기의 보글 게임 보드
    private static char[][] map;
    // DFS 탐색 중 방문 여부를 체크하기 위한 배열
    private static boolean[][] visited;

    // 8방향(상하좌우, 대각선) 이동을 위한 x, y 좌표 변화량
    private static int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0};
    private static int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};

    // 각 보드에서 찾은 단어들을 중복 없이 저장하기 위한 Set
    private static Set<String> result;

    /**
     * 깊이 우선 탐색(DFS)으로 단어를 찾는 재귀 함수
     *
     * @param x   현재 위치의 x 좌표
     * @param y   현재 위치의 y 좌표
     * @param str 현재까지 만들어진 문자열
     */
    private static void search(int x, int y, String str) {
        // 현재까지 만들어진 문자열이 Trie에 완전한 단어로 존재하는지 확인합니다.
        if (Node.contains(str, true)) {
            result.add(str); // 존재한다면 결과 Set에 추가 (Set이므로 중복은 자동 제거)
        }

        // 8방향 탐색
        for (int d = 0; d < 8; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            // 보드 범위를 벗어나는 경우 건너뜁니다.
            if (nx < 0 || ny < 0 || nx > 3 || ny > 3) continue;
            // 이미 방문한 위치인 경우 건너뜁니다.
            if (visited[nx][ny]) continue;

            // ※ 성능 저하의 주된 원인: 문자열 합치기
            // 재귀 호출마다 `str + char` 연산은 새로운 String 객체를 생성합니다.
            // 이는 많은 메모리 할당과 가비지 컬렉션을 유발하여 성능을 크게 떨어뜨립니다.
            // 해결책: StringBuilder를 파라미터로 넘겨 append()와 setLength()를 사용하거나,
            //         다른 코드처럼 Trie 노드 자체를 파라미터로 넘겨 탐색하는 것이 좋습니다.
            String nxt = str + map[nx][ny];

            // 다음 문자열이 Trie에 접두사로 존재하는 경우에만 탐색을 계속합니다. (가지치기, Pruning)
            if (Node.contains(nxt, false)) {
                visited[nx][ny] = true;   // 방문 처리
                search(nx, ny, nxt);      // 재귀 호출
                visited[nx][ny] = false;  // 백트래킹: 탐색이 끝나면 방문 표시 해제
            }
        }
    }

    /**
     * 단어 길이에 따라 점수를 반환하는 메소드
     *
     * @param word 점수를 계산할 단어
     * @return 계산된 점수
     */
    private static int getScore(String word) {
        int len = word.length();
        if (len <= 2) return 0;
        if (len <= 4) return 1;
        if (len == 5) return 2;
        if (len == 6) return 3;
        if (len == 7) return 5;
        if (len == 8) return 11;
        return 0; // 이 경우는 발생하지 않음
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 1. 단어 사전을 입력받아 Trie를 구성합니다.
        int N = Integer.parseInt(br.readLine());
        while (N-- > 0) {
            Node.insert(br.readLine());
        }
        br.readLine(); // 단어 목록과 보드 목록 사이의 빈 줄을 읽어들입니다.

        StringBuilder sb = new StringBuilder();
        int M = Integer.parseInt(br.readLine());
        while (M-- > 0) {
            // 2. 각 보드마다 초기화 작업을 수행합니다.
            visited = new boolean[4][4];
            map = new char[4][4];
            for (int i = 0; i < 4; i++) {
                map[i] = br.readLine().toCharArray();
            }

            // 3. DFS를 통해 단어를 찾습니다.
            result = new HashSet<>(); // 이번 보드에서 찾은 단어를 저장할 Set 초기화
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    // Trie에 시작 문자가 존재하는 경우에만 탐색을 시작합니다. (작은 최적화)
                    if (Node.isInitWord(map[i][j])) {
                        visited[i][j] = true;
                        search(i, j, "" + map[i][j]);
                        visited[i][j] = false; // 시작점에 대한 백트래킹
                    }
                }
            }

            // 4. 찾은 단어들을 바탕으로 점수, 가장 긴 단어, 찾은 단어의 개수를 계산합니다.
            
            // ※ PriorityQueue를 사용하면 사전순으로 정렬되지만, 이 문제에서는 가장 긴 단어를 찾는 것이 목표이므로
            //   단순히 Set을 순회하며 비교해도 충분합니다.
            PriorityQueue<String> pq = new PriorityQueue<>(result);
            int score = 0;
            String maxString = "";

            while (!pq.isEmpty()) {
                String cur = pq.poll();

                // 가장 긴 단어를 찾습니다. 길이가 같으면 사전순으로 앞서는 단어가 선택됩니다.
                // PriorityQueue가 사전순으로 정렬해주지만, 이 로직은 길이를 우선으로 봅니다.
                // 문제의 요구사항(길이가 같으면 사전순)을 정확히 맞추려면 비교 로직을 수정해야 합니다.
                // (예: `if (cur.length() > maxString.length() || (cur.length() == maxString.length() && cur.compareTo(maxString) < 0))`)
                // 하지만 현재 코드처럼 `result`를 `PriorityQueue`에 넣고 순서대로 뽑으면,
                // 길이가 같은 단어 중 사전순으로 가장 늦은 단어가 `maxString`이 될 가능성이 있습니다.
                // 이 문제를 해결하려면, `result`를 그냥 순회하면서 longest/lexicographically smallest를 찾는 로직이 더 명확합니다.
                if (maxString.length() < cur.length()) {
                    maxString = cur;
                } else if (maxString.length() == cur.length() && cur.compareTo(maxString) < 0){
                    maxString = cur; // 길이가 같은 경우 사전순으로 앞서는 것으로 교체
                }

                score += getScore(cur);
            }

            // 결과를 StringBuilder에 추가합니다.
            sb.append(score).append(" ").append(maxString).append(" ").append(result.size()).append("\n");

            // 마지막 보드가 아니면 보드 사이의 빈 줄을 읽어들입니다.
            if (M == 0) continue;
            br.readLine();
        }
        // 최종 결과를 한 번에 출력합니다.
        System.out.println(sb);
    }
}