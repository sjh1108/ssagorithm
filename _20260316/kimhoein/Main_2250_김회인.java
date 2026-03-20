package study;

import java.io.*;
import java.util.StringTokenizer;
import java.util.Arrays;

public class Main_2250_김회인 {
    
    static class Node {
        int root; // 현재 노드 번호
        int left;
        int right;
        
        Node(int root, int left, int right) {
            this.root = root;
            this.left = left;
            this.right = right;
        }
    }
    
    static int N;
    static Node[] Node_arr;
    
    // 각 레벨(row)의 가장 왼쪽(min)과 오른쪽(max) 열(col) 번호를 저장할 배열
    static int[] Node_min_col;
    static int[] Node_max_col;
    
    // 중위 순회를 하면서 1부터 차례대로 부여할 열 번호 (전역 변수)
    static int current_col = 1;
    // 트리의 최대 깊이(row)를 저장할 변수
    static int max_depth = 0;
    
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        
        N = Integer.parseInt(br.readLine());
        
        Node_arr = new Node[N + 1];
        Node_min_col = new int[N + 1];
        Node_max_col = new int[N + 1];
        
        // 1단계: 루트 노드를 찾기 위한 배열
        boolean[] hasParent = new boolean[N + 1];
        
        // 최솟값/최댓값 비교를 위해 배열 초기화
        Arrays.fill(Node_min_col, Integer.MAX_VALUE);
        Arrays.fill(Node_max_col, Integer.MIN_VALUE);
        
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int root = Integer.parseInt(st.nextToken());
            int left = Integer.parseInt(st.nextToken());
            int right = Integer.parseInt(st.nextToken());
            
            Node_arr[root] = new Node(root, left, right);
            
            // 자식으로 등장한 노드는 부모가 있다고 체크
            if (left != -1) hasParent[left] = true;
            if (right != -1) hasParent[right] = true;
        }
       
        // 부모가 없는 노드를 진짜 루트로 찾기
        int real_root = 0;
        for (int i = 1; i <= N; i++) {
            if (!hasParent[i]) {
                real_root = i;
                break;
            }
        }
        
        // 2단계: 찾은 진짜 루트부터 깊이 1로 중위 순회 시작
        dfs(real_root, 1);
       
        // 3단계: 1층부터 최대 깊이까지 돌면서 가장 넓은 너비 찾기
        int max_width = 0;
        int max_width_row = 1;
       
        for (int i = 1; i <= max_depth; i++) {
            // 해당 레벨의 너비 = (가장 오른쪽 열 - 가장 왼쪽 열) + 1
            int width = Node_max_col[i] - Node_min_col[i] + 1;
            
            if (width > max_width) {
                max_width = width;
                max_width_row = i;
            }
        }
       
        bw.write(max_width_row + " " + max_width);
        bw.flush();
    }
    
    // 두 개로 나뉘었던 함수를 하나로 합친 중위 순회(In-order) DFS
    static void dfs(int n, int row) { // 현재 노드 번호, 현재 깊이(행)
        // 자식이 없어서 -1이 들어오면 종료
        if (n == -1) return;
        
        // 최대 깊이 갱신
        max_depth = Math.max(max_depth, row);
        
        // 1. 왼쪽 자식 탐색
        dfs(Node_arr[n].left, row + 1);
        
        // 2. 현재 노드 처리 (내 위치의 열 번호 지정)
        // 현재 레벨(row)의 최소, 최대 열 번호를 갱신
        Node_min_col[row] = Math.min(Node_min_col[row], current_col);
        Node_max_col[row] = Math.max(Node_max_col[row], current_col);
        
        // 내 열 번호를 썼으니, 다음 노드를 위해 열 번호 1 증가
        current_col++;
        
        // 3. 오른쪽 자식 탐색
        dfs(Node_arr[n].right, row + 1);
    }
}