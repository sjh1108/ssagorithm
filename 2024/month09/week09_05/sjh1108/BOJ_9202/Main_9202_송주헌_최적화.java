package week09_05.sjh1108.BOJ_9202;

import java.io.*;
// import java.util.*;

class Main {
    // Trie 노드 클래스 (최적화)
    static class Node {
        static Node root;
        static {
            root = new Node();
        }

        // 최적화 1: 자식 노드 저장 방식을 HashMap에서 배열로 변경
        Node[] children;
        boolean terminal;
        // 최적화 2: 단어가 완성되는 노드에 실제 문자열을 저장
        String word;
        // 최적화 3: 각 보드별 중복 탐색을 방지하기 위한 방문 태그
        int visitedBoardId;

        Node() {
            children = new Node[26]; // 'A' ~ 'Z'
            terminal = false;
            word = null;
            visitedBoardId = -1; // 보드 ID는 0부터 시작하므로 -1로 초기화
        }

        public static void insert(String word) {
            Node cur = root;
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                int index = c - 'A';
                if (cur.children[index] == null) {
                    cur.children[index] = new Node();
                }
                cur = cur.children[index];
            }
            cur.terminal = true;
            cur.word = word; // 단어의 끝에 문자열 전체를 저장
        }
    }

    private static char[][] map;
    private static boolean[][] visited;

    private static final int[] dx = {-1, -1, -1, 0, 1, 1, 1, 0};
    private static final int[] dy = {-1, 0, 1, 1, 1, 0, -1, -1};
    
    // 각 보드별 결과를 저장할 전역 변수 (HashSet과 PriorityQueue 대체)
    private static int totalScore;
    private static String longestWord;
    private static int foundWordsCount;


    /**
     * DFS 탐색 메서드 (최적화)
     * @param x 현재 x 좌표
     * @param y 현재 y 좌표
     * @param node 현재 Trie 노드
     * @param boardId 현재 게임 보드의 고유 ID
     */
    private static void search(int x, int y, Node node, int boardId) {
        // 1. 현재 노드가 단어의 끝인지, 그리고 이번 보드에서 처음 찾는 단어인지 확인
        if (node.terminal && node.visitedBoardId != boardId) {
            node.visitedBoardId = boardId; // 이번 보드에서 찾았다고 태그

            totalScore += getScore(node.word);
            foundWordsCount++;

            // 가장 긴 단어 갱신
            // 길이가 같을 경우 사전순으로 앞서는 단어로 선택
            if (longestWord.length() < node.word.length()) {
                longestWord = node.word;
            } else if (longestWord.length() == node.word.length() && longestWord.compareTo(node.word) > 0) {
                longestWord = node.word;
            }
        }

        // 2. 8방향으로 탐색 계속 진행
        for (int d = 0; d < 8; d++) {
            int nx = x + dx[d];
            int ny = y + dy[d];

            if (nx < 0 || ny < 0 || nx >= 4 || ny >= 4 || visited[nx][ny]) {
                continue;
            }

            int nextCharIndex = map[nx][ny] - 'A';
            Node nextNode = node.children[nextCharIndex];

            // 다음 문자로 이어지는 단어가 Trie에 존재할 경우에만 탐색 계속
            if (nextNode != null) {
                visited[nx][ny] = true;
                search(nx, ny, nextNode, boardId);
                visited[nx][ny] = false; // 백트래킹
            }
        }
    }

    private static int getScore(String word) {
        int len = word.length();
        if (len <= 2) return 0;
        if (len <= 4) return 1;
        if (len == 5) return 2;
        if (len == 6) return 3;
        if (len == 7) return 5;
        if (len == 8) return 11;
        return 0;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        while (N-- > 0) {
            Node.insert(br.readLine());
        }
        br.readLine(); // 사전과 보드 사이의 빈 줄 소비

        StringBuilder sb = new StringBuilder();
        int M = Integer.parseInt(br.readLine());
        for (int boardId = 0; boardId < M; boardId++) { // 각 보드에 고유 ID 부여
            visited = new boolean[4][4];
            map = new char[4][4];

            for (int i = 0; i < 4; i++) {
                map[i] = br.readLine().toCharArray();
            }

            // 새 보드를 시작하기 전에 결과 변수 초기화
            totalScore = 0;
            longestWord = "";
            foundWordsCount = 0;

            // 보드의 모든 위치에서 DFS 시작
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    char startChar = map[i][j];
                    int startCharIndex = startChar - 'A';
                    Node startNode = Node.root.children[startCharIndex];

                    // Trie에 시작 문자로 시작하는 단어가 있을 경우 탐색 시작
                    if (startNode != null) {
                        visited[i][j] = true;
                        // 최적화된 search 메서드 호출. String 대신 Trie 노드와 boardId 전달
                        search(i, j, startNode, boardId);
                        visited[i][j] = false;
                    }
                }
            }

            // Set, PriorityQueue를 사용한 후처리 과정 없이 바로 결과 저장
            sb.append(totalScore).append(" ").append(longestWord).append(" ").append(foundWordsCount).append("\n");

            // 마지막 보드가 아닐 경우에만 보드 사이의 빈 줄 소비
            if (boardId < M - 1) {
                br.readLine();
            }
        }
        System.out.print(sb);
    }
}
