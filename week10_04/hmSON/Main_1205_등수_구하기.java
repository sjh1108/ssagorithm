import java.io.*;
import java.util.*;

public class Main_1205_등수_구하기 {
	
	// BOJ_1205 : 등수 구하기(Silver_4)
	// 자료구조 및 알고리즘 : 구현
	// 현재 랭킹에 등록된 유저들의 점수(비오름차순), 내 점수, 랭킹에 등록 가능한 최대 유저 수가 주어진다.
	// 현재 랭킹에서 내 점수를 추가할 수 있는지, 된다면 몇 위인지 구해야 한다.
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// 랭크에 현재 등록된 유저 수
		int n = Integer.parseInt(st.nextToken());
		// 내 점수
		int point = Integer.parseInt(st.nextToken());
		// 랭크에 등록 가능한 최대 유저 수. 즉 n == max인 경우 반드시 랭크 내 다른 사람보다 점수가 높아야 랭크 갱신 가능
		int max = Integer.parseInt(st.nextToken());
		
		// 등록된 유저 없음 -> 즉시 등록, 랭크 1위
		if(n == 0) {
			System.out.println(1);
			return;
		}
		
		st = new StringTokenizer(br.readLine());
		// 랭크 현황
		int[] scores = new int[max];
		// 0점도 랭크에 등록될 수 있으므로 아직 안채워진 부분을 -1로 명시
        Arrays.fill(scores, -1);
        // 1위부터 순서대로 랭크 등록
		for(int i=0; i<n; i++) {
			scores[i] = Integer.parseInt(st.nextToken());
		}
		
		// 현재 나의 랭크, 인덱스 포인터, 동일한 점수를 가진 유저 수
		int rank = 1, idx = 0;
		
		// 내 점수와 현재 랭커들의 점수를 비교
		// idx == max인 경우 현재 점수로는 랭크 갱신 불가
		while(idx < max) {
			// 랭커의 점수가 내 점수보다 클 때 : 내 순위를 1씩 뒤로 밀어낸다
			if(scores[idx] > point) {
				rank ++; idx++;
			} 
			// 랭커의 점수와 내 점수가 동일한 경우 : 나보다 더 낮은 점수의 랭커가 나올 때까지 뒤로 미루기
			else if(scores[idx] == point) {
				idx++;
			} 
			// 랭커의 점수가 내 점수보다 작을 때 : 루프 종료. 해당 위치에 내 점수가 등록됨
			else break;
		}
		
		// 랭크 내에 내 점수를 입력할 수 있다면 랭크 출력, 없다면 -1 출력
		System.out.println(idx == max ? -1 : rank);
	}
	
}
