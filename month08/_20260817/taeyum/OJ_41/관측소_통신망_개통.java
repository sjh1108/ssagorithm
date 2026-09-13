package _20260817.taeyum.OJ_41;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/*
    관측소 N개, 통신 케이블 M개
    i 번쨰로 설치하는 케이블은 관측소 ui 와 vi 를 잇는다.
    관측소 a,b로 신호를 보낼 수 있으면 두 관측소는 같은 무리에 속한다고 본다.
    아무런 케이블도 없는 처음에는 무리가 N개 이다.
    두 관측소가 이미 같은 무리에 있는경우, 자기 자신에게 케이블 공사하는 경우 무리가 줄어들지 않는다.
    N개의 관측소 전체가 "처음으로" 하나의 무리가 되는  순간, 그때 설치한 케이블의 번호를 구한다.
    케이블 M개 모두 설치 후에도 하나의 무리가 되지 못하면 -1 출력

    끼잉끼잉...
    간만에 하니 진짜 하나도 모르겠어서...
    AI 도움을 받았습니다...
    최대한 이해하는 걸 목표로 진행했습니다.
    진짜로 뇌 굳었노 ㅋㅋ

    풀이 : Union-Find
    무리 개수를 N에서 시작해서, union 으로 실제 합쳐졌을 때만 1씩 줄인다.
    무리 개수가 1이 되는 순간의 케이블 번호가 답. 끝까지 1이 안되면 -1.
 */
public class 관측소_통신망_개통 {
    static int N, M;
    static int[] parent;
    static int[] size;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        parent = new int[N + 1];
        size = new int[N + 1];

        for (int i = 1; i <= N; i++) {
            parent[i] = i;
            size[i] = 1;
        }

        // parent에 1,2,3,4,5,6 으로 초기화 (각 그룹)
        // size 각 그룹 size(크기) 표시

        int groups = N;
        int answer = -1;

        for (int i = 1; i <= M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());

            // 연결 되면 그룹 크기가 줄어드는 거니 줄임
            if (union(u, v)) {
                groups--;
            }

            // 그룹이 하나로 전부 연결되면 해당 회차 표시
            if (groups == 1) {
                answer = i;
                break;
            }
        }

        System.out.println(answer);
    }


    // 어떤 그룹이었는지 찾기 위함
    static int find(int x) {
        // 찾음
        if (parent[x] == x) {
            return x;
        }
        return parent[x] = find(parent[x]); // 계속해서 올라감
    }

    // 실제로 합쳐졌으면 true, 이미 같은 무리였으면 false
    // a,b 의 그룹 찾기
    static boolean union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);

        // 같은 그룹이면 그냥 넘어감
        if (rootA == rootB) {
            return false;
        }

        // 작은 무리를 큰 무리에 붙인다
        if (size[rootA] < size[rootB]) {
            int tmp = rootA;
            rootA = rootB;
            rootB = tmp;
        }

        parent[rootB] = rootA;
        size[rootA] += size[rootB];

        return true;
    }
}
