package algo2025_10_04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class Main_2304_창고다각형 {
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	static int N, x, y;

	public static void main(String[] args) throws NumberFormatException, IOException {
		N = Integer.parseInt(input.readLine());

		List<int[]> list = new LinkedList<>();
		for (int i = 0; i < N; i++) {
			tokens = new StringTokenizer(input.readLine());
			x = Integer.parseInt(tokens.nextToken());
			y = Integer.parseInt(tokens.nextToken());
			list.add(new int[] {x, y});
		}

		// x좌표 기준 오름차순 정렬
		list.sort((a, b) -> a[0] - b[0]);
		//정렬 체크
//		System.out.println(Arrays.toString(list.get(0)));
		
		// 가장 높은 기둥 찾기
		int maxH = 0;
		int maxIdx = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i)[1] > maxH) {
				maxH = list.get(i)[1];
				maxIdx = i;
			}
		}

		int area = 0;
		// 왼쪽부터 최대 기둥
		int leftH = list.get(0)[1];
		int leftX = list.get(0)[0];
		for (int i = 1; i <= maxIdx; i++) {
			if (list.get(i)[1] >= leftH) {
				area += (list.get(i)[0] - leftX) * leftH;
				leftH = list.get(i)[1];
				leftX = list.get(i)[0];
			}
		}

		// 오른쪽부터 최대 기둥
		int rightH = list.get(list.size() - 1)[1];
		int rightX = list.get(list.size() - 1)[0];
		for (int i = list.size() - 2; i >= maxIdx; i--) {
			if (list.get(i)[1] >= rightH) {
				area += (rightX - list.get(i)[0]) * rightH;
				rightH = list.get(i)[1];
				rightX = list.get(i)[0];
			}
		}

		// 가장 높은 기둥 부분 추가
		area += maxH;

		System.out.println(area);
	}
}

		


