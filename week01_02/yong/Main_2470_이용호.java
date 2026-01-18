package algostudy.baek.week01_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;
/*
 * 서로 다른 두 용액 조합
 * 투포인터로 풀이(start, end)
 * 1. 용액 오름차순 정렬
 * 2. 두 합이 0보다 크면 작아져야 함(0에 가까워져야 하므로) -> end를 왼쪽으로
 * 2-1. 두 합이 0보다 작으면 커져야함 -> start를 오른쪽으로
 * 매번 최소 갱신
 */
public class Main_2470_이용호 {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		// hasMoreTokens 써봤음
		ArrayList<Integer> list = new ArrayList<>();
		while(st.hasMoreTokens()) {
			list.add(Integer.parseInt(st.nextToken()));
		}
		Collections.sort(list);
//		System.out.println(list);
		search(list);
	}

	private static void search(ArrayList<Integer> list) {
		//시작 끝 포인터 인덱스
		int start = 0;
		int end = list.size() - 1;
		int min = Integer.MAX_VALUE;
		int[] ans = new int[2];
		
		while(start < end) {
			int sum = Math.abs(list.get(start) + list.get(end));
			// 두 수의 합이 현재 min보다 작으면 갱신
			if(sum < min) {
				min = sum;
				ans[0] = list.get(start);
				ans[1] = list.get(end);
			}
			// 두 수의 합이 0보다 크면 합이 더 작아져야함 -> end 왼쪽으로
			if(list.get(start) + list.get(end) > 0) {
				end--;
			}
			// 두 수의 합이 0보다 작으면 합이더 커져야함 -> start 오른쪽으로
			else if(list.get(start) + list.get(end) < 0) {
				start++;
			}
			// 0이면 최적이니까 바로 종료
			else {
				ans[0] = list.get(start);
				ans[1] = list.get(end);
				break;
			}
		}
		System.out.println(ans[0] + " " + ans[1]);
	}
	

}
