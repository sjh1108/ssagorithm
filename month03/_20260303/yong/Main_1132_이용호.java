package algo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main_1132_이용호 {
/*
 * N개의 수 = 모두 자연수, A ~ J 모두 한 자리
 * 0으로 시작하는 수 없음
 * 자리수를 가중치로 두고 각 알파벳의 가중치를 계산
 * 가중치를 기준으로 오름차순 정렬
 * 0 할당할 알파벳 찾기
 * 나머지 알파벳도 숫자 할당
 * 할당된 숫자 기반으로 합 계산
 */
	static class Node{
		int idx; // 알파벳 idx
		long weight; // 가중치
		
		Node(int idx, long weight){
			this.idx = idx;
			this.weight = weight;
		}
		
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		long result = 0;
		long[] weight = new long[10]; // 알파벳 A ~ J 가중치
		int[] alphaNum = new int[10]; // A ~ J에 할당된 숫자
		boolean[] isFirst = new boolean[10]; // 처음에 나오는 알파벳인지
		
		for(int i = 0; i < N; i++) {
			String st = br.readLine();
			isFirst[st.charAt(0)-'A'] = true; // 첫 알파벳은 0 불가
			long p = 1; // 가중치 자리수
			
			for(int j = st.length()-1; j >= 0 ; j--) {
				int alphaIdx = st.charAt(j) - 'A';
				weight[alphaIdx] += p;
				p *= 10;
			}
			
		}
		// 사용된 알파벳 리스트에 추가 + 숫자 할당 필요한 알파벳 개수 체크
		int usedAlpha = 0;
		List<Node> list = new ArrayList<>();
		for(int i = 0; i < 10; i++) {
			if(weight[i] != 0) {
				list.add(new Node(i,weight[i]));
				usedAlpha++;
			}
		}
		// 정렬(오름 차순)
		list.sort((a,b) -> Long.compare(a.weight, b.weight));
		
		// 0을 할당 해야하는 경우라면
		// 처음에 등장하지 않으면서 가중치가 가장 작은 알파벳에 0 할당
		int zeroIdx = -1; // 0 알파벳 인덱스
		for(Node i : list) {
			if(usedAlpha != 10) break;
			if(!isFirst[i.idx]) { // 처음에 나오는 알파벳이 아니면
				zeroIdx = i.idx;
				break;
			}
		}
		
		// 나머지 알파벳 숫자 할당
		int Num = 10 - usedAlpha;
		if(Num == 0) Num = 1;
		for(Node i : list) {
			if(i.idx == zeroIdx) {
				alphaNum[i.idx] = 0;
			}
			else {
				alphaNum[i.idx] = Num;
				Num++;
			}
		}
		// 가중치 * 할당된 숫자 합 계산
		for(Node i : list) {
			result += i.weight * alphaNum[i.idx];
		}
		System.out.println(result);
	}

}
