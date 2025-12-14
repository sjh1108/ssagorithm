package study;

import java.io.*;
import java.util.*;

class Main_2696_김회인 {
	/*
	 * 이전에 나왔던 문제와 비슷한 문제
	 * max_pq ,min_pq로 우선 순위 큐를 두개 쓰면 해결 가능하다
	 * 큰 수열에서 가장 작은 값 그리고 작은 수열에서 가장 큰 값을 비교하면서 중앙 값을 제어한다.
	 * min_pq의 peek 과 max_pq의 peek을 비교 해서 서로 비교한 후 min_pq > max_pq 이라면 서로 값 교환
	 * 1, 2, 3, 4, 5 개의 숫자가 있다면
	 * 1,2,3를 min_pq에 저장
	 * 4,5를 max_pq에 저장해서
	 * min_pq의 peek()값이 중앙 값을 저장하게 된다
	 * min_pq는 내림차순이 아니라 오름차순이 될 수 있도록 따로 설정 해줄 것
	 * 
	 */
	
	static PriorityQueue<Integer> max_pq;		// 큰 부분 저장 해줄 pq
	static PriorityQueue<Integer> min_pq;		// 작은 부분 저장해줄 pq
	
    public static void main(String[] args) throws IOException {		// 할당 및 입출력 부분
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;// = new StringTokenizer(br.readLine());
        
        int t = Integer.parseInt(br.readLine());
        
        for(int T=0; T<t; T++)
        {
        	max_pq = new PriorityQueue<Integer>();					// 오른쪽 부분 큰것 중에 가장 작은게 peek
        	min_pq = new PriorityQueue<Integer>(Collections.reverseOrder());		// 왼쪽 부분 작은것 중에 가장 큰게 peek
        	
        	int n = Integer.parseInt(br.readLine());
        	
        	st = new StringTokenizer(br.readLine());
        	
        	bw.write(Integer.toString(n/2+1) + "\n");		// 중앙값 몇개나오는지 
        	
        	int count =0;
        	
        	for(int i=1; i<=n; i++)
        	{
        		//System.out.print("i : " + i);
        		
        		int num = Integer.parseInt(st.nextToken());
        		
        		if(i % 10 == 0) st = new StringTokenizer(br.readLine());		// 10개 입력 받으면 새로운 st업데이트
        		
        		//System.out.println(" num : " + num);
        		if(min_pq.size() <= max_pq.size()) {
        			min_pq.add(num);
        		}
        		else
        		{
        			max_pq.add(num);
        		}
        		
        		// 만약 min_pq의 peek가 max_pq의 peek 보다 크다면?
        		// 서로 값 바꿔주기 업데이트 이를 통해 min_pq의 중앙값은 유지
        		
        		if((!min_pq.isEmpty() && !max_pq.isEmpty()) && min_pq.peek() > max_pq.peek()) {
        			int temp_min = min_pq.poll();
        			int temp_max = max_pq.poll();
        			min_pq.add(temp_max);
        			max_pq.add(temp_min);
        		}
        		
        		// 홀수일때만 중앙 값 구하기
        		if(i%2 == 1)
        		{
        			count++;
        			bw.write(Integer.toString(min_pq.peek()) + " ");
        		}
        		
        		// 홀 수 열번째 출력이면 줄바꿈 마지막이 10번째 출력이면 두번 출력하니깐
        		// 감안해서 마지막은 제외
        		if(count == 10 && i != n) {
        			count = 0;
        			bw.write("\n");
        		}
        		
        	}
        	bw.write("\n");
        }

        bw.flush();
        bw.close();       
    }

}