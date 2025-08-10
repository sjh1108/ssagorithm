import java.io.*;
import java.util.*;


public class Main_1991_박기택 {
    static Node head = new Node('A', null, null); // 전역 정적 노드를 생성.

    public static  void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out)); // 출력 성능을 높이기 위한 출력 버퍼 설정
        StringTokenizer st = new StringTokenizer(br.readLine(), " "); //	"스페이스 하나"만 구분자로 씀

        int N = Integer.parseInt(st.nextToken()); // 노드 갯수 설정

        for(int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            char root = st.nextToken().charAt(0); // 문자 하나 입력받기 = 노드값
            char left = st.nextToken().charAt(0); // 왼쪽
            char right = st.nextToken().charAt(0); //오른쪽

            insertNode(head, root, left, right); // 매서드 호출에서 넣기
        }

        // 출력
        preOrder(head);
        System.out.println();
        inOrder(head);
        System.out.println();
        postOrder(head);
        System.out.println();
    }

    // 2. Node 클래스 생성: 값, 왼쪽, 오른쪽 노드 구조로
    static class Node {
        char value;
        Node left;
        Node right;

        Node(char value, Node left, Node right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }

    // 3. Node 클래스에 insertNode매서드를 활용해 값을 입력한다.
    public static void insertNode(Node current, char root, char left, char right) {
        // 1. 현재 노드가 우리가 찾는 루트라면?
        if (current.value == root) {
            if (left != '.') { // 왼쪽노드가 있다면, * . 노드 정보가 없다는 뜻
                current.left = new Node(left, null, null);
            }
            if (right != '.') {
                current.right = new Node(right, null, null);
            }
            return; // 추가했으니 더 이상 탐색 안 함
        }

        // 2. 왼쪽 서브트리 탐색
        if (current.left != null) {
            insertNode(current.left, root, left, right);
        }

        // 3. 오른쪽 서브트리 탐색
        if (current.right != null) {
            insertNode(current.right, root, left, right);
        }
    }

    // 전위, 중위, 후위 매서드 각각 사용하여 출력한다.
    private static void preOrder(Node node) {
        if (node == null) { // 더 이상 값이 없을 때 까지
            return;
        }
        System.out.print(node.value); // 출력
        preOrder(node.left); // 왼쪽 노드 탐색
        preOrder(node.right); // 오른쪽 노드 탐색
    }

    private static void inOrder(Node node) {
        if (node == null) { // 더 이상 값이 없을 때 까지
            return;
        }
        inOrder(node.left); // 왼쪽 노드 탐색
        System.out.print(node.value); // 출력
        inOrder(node.right); // 오른쪽 노드 탐색
    }

    private static void postOrder(Node node) {
        if (node == null) { // 더 이상 값이 없을 때 까지
            return;
        }

        postOrder(node.left); // 왼쪽 노드 탐색
        postOrder(node.right); // 오른쪽 노드 탐색
        System.out.print(node.value); // 출력
    }
}
