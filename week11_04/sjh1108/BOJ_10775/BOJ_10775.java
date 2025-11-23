package week11_04.sjh1108.BOJ_10775;

import java.io.*;
import java.util.*;

// 백준 10775 - 공항 (Union-Find)
class Main{
    static StringTokenizer st;
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringBuilder sb = new StringBuilder();

    static int N, M, K;
    
    // 각 게이트의 부모 노드(다음으로 도킹 시도해볼 게이트)를 저장할 배열
    static int[] gate;

    // Find 함수: 경로 압축(Path Compression)을 사용하여 루트 노드를 찾음
    // x번 게이트를 원할 때, 실제로 도킹하게 될 게이트 번호(루트)를 반환
    static int find(int x){
        // 자기 자신이 루트라면(아직 다른 게이트에 연결되지 않음 = 빈 게이트) 반환
        if(gate[x] == x) return x;
        // 루트가 아니라면 부모를 찾아 거슬러 올라가며 경로 압축 수행
        return gate[x] = find(gate[x]);
    }

    // Union 함수: 두 집합을 합침
    // a번 게이트를 사용했으면, a번 게이트의 루트를 b(a-1)번 게이트의 루트와 연결
    static void union(int a, int b){
        a = find(a);
        b = find(b);

        // a의 부모를 b로 설정하여, 다음에 a를 찾으면 b쪽으로 가도록 함
        if(a != b) gate[a] = b;
    }

    public static void main(String[] args) throws IOException{
        N = Integer.parseInt(br.readLine()); // 게이트의 수 (1 ~ N)
        M = Integer.parseInt(br.readLine()); // 비행기의 수

        // 초기화: 각 게이트의 부모는 자기 자신
        // gate[i] = i 라는 것은 i번 게이트가 아직 비어있어 사용 가능하다는 의미
        gate = new int[N+1];
        for(int i=1; i<=N; i++){
            gate[i] = i;
        }
        
        int count = 0; // 도킹된 비행기 수
        for(int i=0; i<M; i++){
            int n = Integer.parseInt(br.readLine()); // 현재 비행기가 도킹 가능한 최대 게이트 번호
            
            // n번 게이트 이하 중에서, 도킹 가능한 가장 번호가 큰 게이트(루트)를 찾음 (Greedy)
            // 왜 가장 큰 번호인가? -> 작은 번호 게이트를 아껴야 나중에 들어올(번호 제한이 작은) 비행기를 받을 확률이 높음
            int g = find(n);
            
            // 찾은 게이트가 0번이라면, 1~n번 게이트가 모두 사용 중이라는 뜻 -> 도킹 불가
            // 문제 조건: 도킹할 수 없다면 공항 폐쇄 -> 루프 종료
            if(g == 0) break;

            count++; // 도킹 성공
            
            // g번 게이트를 사용했으므로, 이제 g번 게이트를 찾는 요청이 오면
            // g-1번 게이트(혹은 g-1의 루트)를 가리키도록 합침(Union)
            union(g, g-1);
        }

        System.out.println(count);
    }
}