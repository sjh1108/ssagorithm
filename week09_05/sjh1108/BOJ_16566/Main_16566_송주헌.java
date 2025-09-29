package week09_05.sjh1108.BOJ_16566;
import java.io.*;
import java.util.*;

class Main {

    static class Tree {
        // 세그먼트 트리의 기저 크기 (리프 노드의 개수)
        // 1 << 22는 2^22으로 약 420만을 의미합니다.
        static final int SZ = 1 << 22;
        int[] tree;

        public Tree() {
            // 전체 트리 크기는 기저 크기의 2배
            this.tree = new int[SZ << 1];
        }

        void update(int i, int val) {
            // 1-based 인덱스를 트리 내부의 리프 노드 인덱스로 변환
            i = (i - 1) | SZ;
            tree[i] = val;
            // 부모 노드들을 루트(1)까지 갱신
            while ((i >>= 1) > 0) {
                tree[i] = tree[i << 1] + tree[(i << 1) | 1];
            }
        }

        /**
         * 구간 [l, r]의 합을 계산합니다.
         * @param l 1-based start index
         * @param r 1-based end index
         * @return 구간의 합
         */
        int query(int l, int r) {
            if (l > r) {
                return 0; // 유효하지 않은 범위는 0을 반환
            }
            int ret = 0;
            // 1-based 인덱스를 트리 내부의 리프 노드 인덱스로 변환
            l = (l - 1) | SZ;
            r = (r - 1) | SZ;

            while (l <= r) {
                // l이 오른쪽 자식이면 현재 노드 값을 더하고 오른쪽으로 한 칸 이동
                if ((l & 1) == 1) ret += tree[l++];
                // r이 왼쪽 자식이면 현재 노드 값을 더하고 왼쪽으로 한 칸 이동
                if ((r & 1) == 0) ret += tree[r--];
                // 부모 노드로 이동
                l >>= 1;
                r >>= 1;
            }
            return ret;
        }
    }

    public static void main(String[] args) throws IOException {
        // 빠른 입출력을 위한 설정
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        Tree segTree = new Tree();
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < m; i++) {
            int t = Integer.parseInt(st.nextToken());
            segTree.update(t, 1);
        }

        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < k; i++) {
            int t = Integer.parseInt(st.nextToken());
            
            // 이진 탐색으로 t보다 큰 가장 작은 수를 찾음
            int lo = t, hi = n;
            // 탐색 범위: (lo, hi]
            while (lo + 1 < hi) {
                int mid = lo + ((hi - lo) >> 1); // 오버플로우 방지
                
                // 구간 [t + 1, mid]에 원소가 없다면, 답은 mid보다 큰 쪽에 있음
                if (segTree.query(t + 1, mid) == 0) {
                    lo = mid;
                } 
                // 원소가 있다면, 답은 mid 또는 그보다 작은 쪽에 있음
                else {
                    hi = mid;
                }
            }
            
            // 찾은 결과를 StringBuilder에 추가
            sb.append(hi).append('\n');
            // 사용한 원소는 트리에서 제거 (값을 0으로 설정)
            segTree.update(hi, 0);
        }

        System.out.print(sb);
    }
}