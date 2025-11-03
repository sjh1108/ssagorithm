package week;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 문제 이해
 * - 10개의 버슷이 일렬로 놓여 있음
 * - 버섯을 먹으면 점수를 받음
 * - 슈퍼마리오는 버섯을 처음 나온 순서대로 집으려고 함
 * - 모든 버섯을 집을 필요는 없고 중간에 중단 가능
 * - 중단하면 이후 버섯 전부 못먹음
 * - 점수의 합을 최대한 100에 가깝게 만들려고함
 * 
 * 입력
 * - 10개의 버섯의 점수가 주어짐
 * 출력
 * - 마리오가 받는 점수
 * - 주의 : 98과 102라면 102 선택
 * 
 * 전략
 * - 100을 넘길 떄까지 돌려서 넘기기 전후를 비교하면 됨
 * - 못넘기면 못넘긴대로 출력
 * 
 * 경우의 수 
 * - 10개를 덜 돌았는데 100이 만들어짐 -> 100이 만들어진 순간과 그 이전 값을 비교 -> 더 큰값
 * - 10개를 도는 중 100이 만들어짐 -> 100 출력
 * - 10개를 다 돌았는데 100이 안 만들어짐 -> 마지막 버섯까지의 합
 * - 맨 처음 버섯이 200을 초과하는 경우 -> 0 출력 
 * 
 * 오답노트
 * - 10개를 다 돌았는데도 100이 되지 않는 경우 출력을 빼먹음
 * - 첫 숫자가 200을 초과하는 경우 0이 답이 될 수 있음
 * - sum += mushroom[i] 코드의 위치가 조건문 뒤에 있어서 모든 조건 계산이 한번씩 밀리는 문제 발생 
 */
public class Main_2851_슈퍼마리오 {

	
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int[] mushroom = new int[10];	
		for(int i = 0; i < 10; i++) {
			mushroom[i] = Integer.parseInt(br.readLine());			
		} // 입력 끝
		
		// 버섯 순회
		int sum = 0;
		int preSum = 0; 
		
		for(int i = 0; i < 10; i ++) {
			preSum = sum;
			sum += mushroom[i];
			
			// 합이 100보다 크거나 같으면 이전 합과 비교해서 더 100에 가까운 값 출력하고 return
			if(sum >= 100) { 
				// 100넘긴 것과 100의 갭과 100넘기기 직전의 것과 100의 갭을 비교
				int diffCur = sum - 100;
				int diffPre = 100 - preSum;
				if(diffCur < diffPre) {
					System.out.println(sum); 
				}else if (diffCur == diffPre){
					System.out.println(sum);
				}else {
					System.out.println(preSum);
				}
				return;
			}
			
		}
		
		// 오답노트 : 100이상을 발견하지 못한 경우 마지막을 출력함으로서 마무리해야함
		System.out.println(sum);
	}
}
