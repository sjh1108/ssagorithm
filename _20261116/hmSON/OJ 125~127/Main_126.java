import java.io.*;
import java.util.*;

public class Main {

    /*
     * 자료구조 및 알고리즘 : 비트 마스킹, 이진 트라이(Binary Trie)
     *
     * 모든 수를 30비트로 쪼개 상위 비트부터 이진 트라이에 저장
     * 조각 하나를 고정했을 때 XOR 최댓값의 짝은 상위 비트부터 반대 비트를 최대한 많이 가진 수
     * -> 상위 비트 하나가 하위 비트 전부보다 크므로, 상위부터 반대 비트를 탐욕적으로 고르면 최적
     * -> 조회 한 번이 30단계로 끝남
     *
     * 조회 후 삽입하면 트라이에는 앞선 조각만 있어 자기 자신과 짝지어지지 않음
     */

    // MAX_BITS : 최대 비트 수, MASK : 하위 30비트가 모두 1인 반전용 마스크
    static final int MAX_BITS = 30, MASK = (1 << MAX_BITS) - 1;
    static int[][] trie;
    static int triePointer = 0, max = 0;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        // 이진 트라이 배열
        // trie[x][y] : x번 노드에서 비트 y로 갈 때의 노드 번호 (0이면 없음)
        // 수 하나당 최대 30개 노드 + 루트
        trie = new int[n * MAX_BITS + 1][2];

        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i=0; i<n; i++) {
            int val = Integer.parseInt(st.nextToken());
            // 비트 반전(마스크 변수와 XOR 연산)
            // flipped와 가장 유사한 조각이 val과의 연산으로 최대값을 만드는 나머지 조각임
            int flipped = val ^ MASK;

            updateMaxXOR(flipped); // 최대값 갱신(XOR 연산 결과 최대값 찾기)
            setTrie(val); // 현재 처리하는 조각을 트라이에 입력
        }

        System.out.println(max);
    }

    // 반전된 비트를 목표로 트라이를 따라가며 XOR 최댓값 갱신
    static void updateMaxXOR(int flipped) {
        if(triePointer == 0) return; // 아직 입력된 조각 없음

        int sum = 0, cur = 0;

        // 상위 비트부터
        for(int i=MAX_BITS-1; i>=0; i--) {
            int bit = (flipped >> i) & 1;
            if(trie[cur][bit] != 0) {
                sum += 1 << i; // 반대 비트가 있으면 이 자리의 XOR은 1
                cur = trie[cur][bit];
            } else {
                cur = trie[cur][bit ^ 1];
            }
        }

        if(sum > max) max = sum;
    }

    // 값의 비트를 상위부터 따라가며 삽입, 경로가 없으면 새 노드 할당
    static void setTrie(int val) {
        int cur = 0;

        for(int i=MAX_BITS-1; i>=0; i--) {
            int bit = (val >> i) & 1;
            if(trie[cur][bit] == 0) {
                // 새 노드 할당
                trie[cur][bit] = ++triePointer;
            }

            cur = trie[cur][bit];
        }
    }

}