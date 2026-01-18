import java.util.*;
import java.io.*;

// 백준 2263 - 트리의 순회
// 인오더(In-Order)와 포스트오더(Post-Order)가 주어졌을 때, 프리오더(Pre-Order)를 구하는 문제입니다.
class Main {

    static int idx = 0;
    static int[] inOrder, postOrder; // 인오더와 포스트오더 저장 배열
    static List<Integer> preOrder = new ArrayList<>(); // 결과(프리오더)를 저장할 리스트

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine()); // 정점의 개수 n

        inOrder = new int[N];
        postOrder = new int[N];

        // 인오더 입력 (Left -> Root -> Right)
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            inOrder[i] = Integer.parseInt(st.nextToken());
        }

        // 포스트오더 입력 (Left -> Right -> Root)
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            postOrder[i] = Integer.parseInt(st.nextToken());
        }

        // 재귀 함수 호출 (인오더 범위, 포스트오더 범위)
        getPreOrder(0, N-1, 0, N-1);

        // 결과 출력
        StringBuilder sb = new StringBuilder();
        for(int n : preOrder) {
            sb.append(n + " ");
        }
        System.out.println(sb);
    }

    /**
     * 분할 정복을 이용해 프리오더를 구하는 함수
     * @param is 인오더 시작 인덱스
     * @param ie 인오더 끝 인덱스
     * @param ps 포스트오더 시작 인덱스
     * @param pe 포스트오더 끝 인덱스
     */
    static void getPreOrder(int is, int ie, int ps, int pe) {
        // 기저 사례: 범위가 유효하지 않으면 종료
        if(is > ie || ps > pe) return;

        // [핵심 로직]
        // 포스트오더의 가장 마지막 원소(postOrder[pe])가 현재 서브트리의 '루트(Root)'입니다.
        // 프리오더는 (Root -> Left -> Right) 순서이므로, 찾은 루트를 바로 저장합니다.
        int rootValue = postOrder[pe];
        preOrder.add(rootValue);

        // 인오더에서 루트의 위치(인덱스)를 찾습니다.
        // 인오더는 (Left -> Root -> Right) 구조이므로, 루트를 기준으로 왼쪽과 오른쪽 서브트리가 나뉩니다.
        int rootIdx = 0;
        for(int i = is; i <= ie; i++) {
            if(inOrder[i] == rootValue) {
                rootIdx = i;
                break;
            }
        }

        // 왼쪽 서브트리의 크기 계산
        // (인오더에서 루트 위치 - 인오더 시작 위치)
        int leftSize = rootIdx - is;

        // 1. 왼쪽 서브트리 재귀 호출 (Pre-Order: Root 다음 Left 방문)
        // - 인오더 범위: [is, rootIdx - 1] (루트 왼쪽 부분)
        // - 포스트오더 범위: [ps, ps + leftSize - 1] (시작점부터 왼쪽 서브트리 크기만큼)
        getPreOrder(is, rootIdx - 1, ps, ps + leftSize - 1);

        // 2. 오른쪽 서브트리 재귀 호출 (Pre-Order: Left 다음 Right 방문)
        // - 인오더 범위: [rootIdx + 1, ie] (루트 오른쪽 부분)
        // - 포스트오더 범위: [ps + leftSize, pe - 1] (왼쪽 서브트리 다음부터, 루트(pe) 제외한 끝까지)
        getPreOrder(rootIdx + 1, ie, ps + leftSize, pe - 1);
    }
}