package bk.silver; // 실버 2

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

/**
 * 문제이해
 * - 차 N대가 주차장에 진입
 * - 큐에서 차례대로 대기하다가 자리가 비면 해당 자리에 주차  
 * - 자리 많으면 가장 작은 번호 자리에 주차 
 *
 * - 주차 비용 =  자리 별 비용 x 차 무게
 * 
 * - M : 오늘 이용할 차랑 수 
 * 
 * 입력
 * - N M
 * - N개줄 : 요금
 * - M개줄 : 차량 무게 정수
 * - 2*M개줄 : 차량의 주차장 출입 순서 
 * - 	(양의정수 i는 차량 i가 주차장에 들어옴,
 * - 	(음의정서 -i는 차량 i가 주차장에 나감)
 * - 1~M까지 모든 차량은 정확하게 한번 주차장에 들어오고 한번 나감
 * - 대기하는 차량이 주차 못하는 경우 없음 
 */
public class _Main_5464_주차장 {

	static int N, M;
	static int[] map;
	static int[] cost;
	static int[] weight;
	static int[] order; // 답보고 추가 
	static Queue<Integer> q;
	static PriorityQueue<Integer> freeSpace; // PQ 필요 
	static int total;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        
        map = new int[N+1];
        cost = new int[N+1]; // 1부터 N 인덱스까지 사용. 0은 미사용
        weight = new int[M+1]; // 1	부터 M인덱스까지 사용. 0은 미사용 
        
        order = new int[2*M + 1]; // 차량들의 출차 순서
        
        q = new ArrayDeque<Integer>();
        freeSpace = new PriorityQueue<>(); // 답 
        
        for(int i = 1; i <= N; i++) {
        	cost[i] = Integer.parseInt(br.readLine());
        	freeSpace.add(i);
        }
        
        for(int i = 1; i <= M; i++) {
        	weight[i] = Integer.parseInt(br.readLine());
        }
        
        for(int i = 1; i <= 2 * M; i++){
            order[i] = Integer.parseInt(br.readLine());
        }
        
        total = 0;
        for(int i = 0; i < 2*M; i++) {
        	int n = Integer.parseInt(br.readLine());
        	
        	//?? 아니... 가장 작은 주차장 번호로 넣으려면.. 뭘 큐로 만들어야하는지
        	// 감이 안 잡힘...
        	
        	// 검색해보니 PQ를 쓴다고하네요. 정말 황당하고요
        	// 다시 풀어보겠습니다.
        	// 죄송
        	
        	// 차가 들어오는 경우 / 나가는 경우 나눠서 작성
        	// 대기되어 있는 차 먼저 주차시키고 비용부과로직 짜기 
        	
        }
        

        System.out.println(total);
    }
	
}
