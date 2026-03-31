import java.io.*;
import java.util.*;

public class Main_2250_트리의_높이와_너비 {

    /*
     * BOJ_2250 : 트리의 높이와 너비 (Gold_2)
     * 자료구조 및 알고리즘 : 트리 순회, DFS
     *
     * [문제 요약]
     * - N개의 노드로 이루어진 이진 트리가 주어진다. (각 노드의 왼쪽/오른쪽 자식 정보)
     * - 같은 레벨(깊이)에 있는 노드들 중,
     *   가장 오른쪽 노드와 가장 왼쪽 노드 간의 거리를 그 레벨의 '너비'로 정의한다.
     * - 너비가 가장 큰 레벨과 그 너비를 출력한다. (레벨은 1부터)
     *
     * [핵심 아이디어]
     * - 중위 순회를 수행하면 "왼쪽 서브트리 -> 현재 -> 오른쪽 서브트리" 순서로 방문한다.
     * - 방문할 때마다 열 번호(cnt)를 1씩 증가시키면, 각 노드의 "가로 위치(열)"가 자연스럽게 결정된다.
     * - 레벨별로
     *   - 처음 방문한 노드의 열(start[level])
     *   - 마지막으로 방문된 노드의 열을 이용해 너비(dist[level])
     *   를 갱신하면 된다.
     * - 최종적으로 dist[level]이 최대인 레벨을 찾는다.
     *
     * [구현 메모]
     * - 입력에서 부모가 한 번도 등장하지 않은 노드가 루트이므로 isParentExist로 루트를 찾는다.
     * - start[level]은 해당 레벨에서 처음 등장한 열 번호를 저장(-1이면 아직 미등장).
     * - dist[level]은 (현재 열 - start[level] + 1)로 계속 갱신되어,
     *   레벨의 최종 너비(왼쪽~오른쪽 범위)가 된다.
     * - inorder(root, level):
     *   - 왼쪽 방문 -> cnt 증가 및 레벨 기록 -> 오른쪽 방문
     * - level은 0-based로 관리하고, 출력 시 +1 해서 1-based 레벨로 출력한다.
     *
     * [시간 복잡도]
     * - 트리 구성: O(N)
     * - 중위 순회: 각 노드 1번 방문 -> O(N)
     * - 레벨 너비 탐색: O(N)
     * - 총: O(N)
     */

    static int v, cnt = 0;
    // 상위 노드 존재 여부 확인 배열
    // 부모 노드가 입력에서 따로 주어지지 않으므로 직접 구해야 함.
    static boolean[] isParentExist;
    static int[] start, dist; // 각 레벨별 시작 위치 배열, 각 레벨별 너비 배열
    static gold_2.Main_2250.Node[] tree; // 트리의 노드 배열

    static class Node {
        int id;
        gold_2.Main_2250.Node left, right;

        public Node(int id) {
            this.id = id;
        }
    }

    static void init() throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        v = Integer.parseInt(br.readLine());
        isParentExist = new boolean[v];
        tree = new gold_2.Main_2250.Node[v];

        for(int i=0; i<v; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int root = Integer.parseInt(st.nextToken()) - 1;
            if(tree[root] == null) tree[root] = new gold_2.Main_2250.Node(root);

            int leftIdx = Integer.parseInt(st.nextToken()) - 1;
            int rightIdx = Integer.parseInt(st.nextToken()) - 1;

            // 입력에서 -1이 들어오면 -2가 되므로(-1 - 1), 그 경우 자식 없음 처리
            if(leftIdx != -2) {
                isParentExist[leftIdx] = true;
                if(tree[leftIdx] == null) tree[leftIdx] = new gold_2.Main_2250.Node(leftIdx);
                tree[root].left = tree[leftIdx];
            }

            if(rightIdx != -2) {
                isParentExist[rightIdx] = true;
                if(tree[rightIdx] == null) tree[rightIdx] = new gold_2.Main_2250.Node(rightIdx);
                tree[root].right = tree[rightIdx];
            }
        }

        // 레벨을 인덱스로 하는 배열도 크기는 v로 고정 -> 최악의 경우 경사 이진트리일 수 있음
        start = new int[v];
        dist = new int[v];
        Arrays.fill(start, -1); // 레벨 등장 여부 체크용
    }

    static void inorder(gold_2.Main_2250.Node root, int level) {
        if(root == null) return;

        inorder(root.left, level+1);

        // 중위 순회 방문 순서대로 열 번호 부여
        cnt++;

        // 해당 레벨의 첫 방문 열(start)과 현재까지의 너비(dist)를 갱신
        if(start[level] == -1) {
            start[level] = cnt;
            dist[level] = 1;
        }
        else dist[level] = cnt - start[level] + 1;

        inorder(root.right, level+1);
    }

    public static void main(String[] args) throws Exception {
        init();

        // 부모가 없는 노드가 루트
        int root = -1;
        for(int i=0; i<v; i++) {
            if(!isParentExist[i]) {
                root = i;
                break;
            }
        }

        inorder(tree[root], 0);

        // 가장 너비가 큰 레벨 찾기 (동률이면 더 작은 레벨이 유지됨)
        int max = 1, targetLevel = 0;
        for(int i=0; i<v; i++) {
            if(start[i] == -1) break; // 더 깊은 레벨은 존재하지 않음
            if(dist[i] > max) {
                max = dist[i];
                targetLevel = i;
            }
        }

        System.out.println(targetLevel+1 + " " + max);
    }

}