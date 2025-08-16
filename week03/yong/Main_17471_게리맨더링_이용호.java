package algostudy.baek.b17471;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main_17471_게리맨더링_이용호 {
/*
 * 구역은 1번~N번 
 * 구역을2개의 선거구로 나눠야한다
 * 한 선거구에 포함되어 있는 구역은 모두 연결되어 있엉야 한다
 * 두 선거구의 인구 차이를 최소화
 * 백준시의 정보가 주어졌을떄, 인구차이의 최솟값을 구해보자
 */
	static int[] popu;
	static boolean visited[];//bfs용
	static boolean used[];//부분집합용
	static int minGap;
	static List<LinkedList<Integer>> list;
	static List<Integer> Alist, Blist;
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		popu = new int[N+1];//1base
		StringTokenizer st = new StringTokenizer(br.readLine());
		for(int i = 1; i < N+1; i++) {
			popu[i] = Integer.parseInt(st.nextToken());
		}
		
		list = new LinkedList<>();//1base 안쓰는 리스트{{},...}
		list.add(new LinkedList<>());
		for(int i = 1; i < N+1; i++) {
			st = new StringTokenizer(br.readLine());
			int adj = Integer.parseInt(st.nextToken()); //인접 구역 수
			list.add(new LinkedList<>());
			for(int j = 0; j < adj; j++) {
				list.get(i).add(Integer.parseInt(st.nextToken()));
			}
		}
		used = new boolean[N+1];
		minGap = Integer.MAX_VALUE;
		
		PS(1,N);
		
		
		System.out.println(minGap == Integer.MAX_VALUE ? -1 : minGap); //갱신 안됐으면 -1 아니면 갱신된 mingap출력
		
		
//		for(int i = 0; i < N+1; i++) {
//			System.out.println(Arrays.toString(list.get(i).toArray()));
//		}

	}
	private static boolean isConnected(List<Integer> group) {
	    visited = new boolean[popu.length];
	    Queue<Integer> q = new LinkedList<>();
	    q.add(group.get(0));
	    visited[group.get(0)] = true;

	    int count = 1;
	    while(!q.isEmpty()) {
	        int now = q.poll();
	        for(int next : list.get(now)) {
	            if(!visited[next] && group.contains(next)) {
	                visited[next] = true;
	                q.add(next);
	                count++;
	            }
	        }
	    }

	    return count == group.size();
	}
	private static void PS(int idx,int N) {
	    if(idx == N+1) {
	        int Asum=0;
	        int Bsum=0;
	        Alist = new LinkedList<>();
	        Blist = new LinkedList<>();
	        
	        for(int i = 1; i < N+1; i++) {
	            if(used[i]) {	//A구역
	                Asum += popu[i];
	                Alist.add(i);
	            }
	            else {			//B구역
	                Bsum += popu[i];
	                Blist.add(i);
	            }
	        }

	        if(!Alist.isEmpty() && !Blist.isEmpty()) {
	            if(isConnected(Alist) && isConnected(Blist)) {   //연결성 검사
	                minGap = Math.min(minGap, Math.abs(Asum-Bsum));
	            }
	        }
	        return;
	    }
	    used[idx] = true;
	    PS(idx+1,N);
	    used[idx] = false;
	    PS(idx+1,N);
	}

}
