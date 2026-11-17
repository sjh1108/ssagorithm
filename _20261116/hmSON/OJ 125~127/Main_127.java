import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 누적 XOR, 비트 마스킹, 이진 트라이(Binary Trie)
     *
     * 구간 내 모든 값을 XOR 한 결과가 K 이상인 모든 구간의 개수를 카운트
     *
     * 구간 [l, r]의 공명값은 누적 XOR 두 값으로 표현됨 -> P[r] ^ P[l-1]
     * -> 누적값을 하나씩 트라이에 넣으며, 넣기 전에 앞선 누적값들 중 조건을 만족하는 개수를 조회
     *
     * 조회는 상위 비트부터 XOR을 1로 만드는 쪽(반대 비트)을 우선 확인
     * - 이 비트를 1로 만들어 K 이상이 확정되면, 그 아래 서브트리 전체를 한 번에 카운트하고 반대쪽으로 진행
     * - 확정되지 않으면 이 비트를 1로 둔 채 계속 내려감
     */

    // MAX_BITS : 최대 비트 수, MASK : 하위 30비트가 모두 1인 반전용 마스크
    static final int MAX_BITS = 30, MASK = (1 << MAX_BITS) - 1;
    static int[][] trie;
    static int[] shareCnt; // 각 노드를 지나간 누적값의 수 = 그 노드 아래 서브트리의 크기
    static int k, triePointer = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        // XOR 결과는 항상 0 이상이므로 모든 구간이 해당
        if(k == 0) {
            System.out.println((long)n * (n+1) / 2);
            return;
        }

        // 이진 트라이 배열
        // trie[x][y] : x번 노드에서 비트 y로 갈 때의 노드 번호 (0이면 없음)
        // 수 하나당 최대 30개 노드 + 루트
        trie = new int[n * MAX_BITS + 1][2];
        shareCnt = new int[n * MAX_BITS + 1];

        int prefixXor = 0; // 누적 XOR
        long cnt = 0; // 구간 수가 최대 약 200억이므로 long
        st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            prefixXor ^= Integer.parseInt(st.nextToken());

            // 누적 XOR == 구간 [0, i]의 모든 값을 XOR 한 결과
            // 직접 비교 가능하므로 바로 처리
            if(prefixXor >= k) cnt++;

            int flipped = prefixXor ^ MASK;

            cnt += getSectionCnt(flipped); // 앞선 누적값들과의 쌍
            setTrie(prefixXor);            // 조회 후 삽입
        }

        System.out.println(cnt);
    }

    // 앞선 누적값 중 현재 값과의 XOR이 K 이상인 개수 반환
    static int getSectionCnt(int flipped) {
        if(triePointer == 0) return 0;

        int sum = 0, cnt = 0, cur = 0; // sum : 지금까지 확정된 XOR 값

        // 상위 비트부터
        for(int i=MAX_BITS-1; i>=0; i--) {
            int bit = (flipped >> i) & 1;
            if(trie[cur][bit] != 0) {
                if(sum + (1 << i) >= k) {
                    // 이 비트를 1로 만들면 아래 비트가 무엇이든 K 이상 확정-> 서브트리 전체 카운트
                    // 이후 이 비트를 0으로 두는 쪽에서 남은 경우를 계속 탐색
                    cnt += shareCnt[trie[cur][bit]];
                    if(trie[cur][bit ^ 1] == 0) break; // 반대쪽 서브 트리가 없는 경우 바로 탈출
                    cur = trie[cur][bit ^ 1];
                } else {
                    // 1로 만들어도 아직 부족 -> 1로 두고 계속 내려감
                    // 이 비트를 0으로 두는 쪽은 아래를 전부 1로 채워도 K 미만이므로 볼 필요 없음
                    sum += 1 << i;
                    cur = trie[cur][bit];
                }
            } else {
                cur = trie[cur][bit ^ 1];
            }
        }

        return cnt;
    }

    // 누적값을 상위 비트부터 삽입하며 지나가는 노드의 카운트 증가
    static void setTrie(int val) {
        int cur = 0;

        // 상위 비트부터
        for(int i=MAX_BITS-1; i>=0; i--) {
            int bit = (val >> i) & 1;
            if(trie[cur][bit] == 0) {
                trie[cur][bit] = ++triePointer;
            }

            cur = trie[cur][bit];
            shareCnt[cur]++;
        }
    }

}