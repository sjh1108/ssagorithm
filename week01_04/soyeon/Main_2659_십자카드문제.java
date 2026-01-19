package week; // 실버 3

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * 문제 이해 
 * - 십자모양의 한 장의 카드에서, 네 모서리에 1 이상 9 이하의 숫자가 하나씩 씌여 있다. 
 * - 이 네 개의 숫자 중에는 같은 숫자도 있을 수 있다.
 * 
 * - 시계수는 카드의 숫자들을 시계 방향으로 읽어서 만들어지는 네 자리 수들 중에서 가장 작은 수
 * 
 * - 입력으로 주어진 카드의 시계수를 계산하여, 
 * - 그 시계수가 모든 시계수들 중에서 몇 번째로 작은 시계수인지를 알아내는 프로그램을 작성
 * 
 * 생각
 * - 시계수를 구하고. 
 * - 시계수들 중에 몇번째라는건 결국 가장 작은 시계수인 1111에서 얼마나 떨어져있냐는건가?
 * - 아님. 1120과 1121은 불가능. 단순 숫자 계산으로 해결 x
 * 
 * - 그럼 어떻게 구하는거지?
 * - 일단 9xxx는 존재할 수 없음
 * - 전부 써보자
 * - 1111, 1112, 1113, 1114, 1115, 1116, 1117, 1118, 1119
 * - 1122, 1123, 1124, 1125, 1126, 1127, 1128, 1129
 * - 1132, 1133, 1134, 1135, 1136, 1137, 1138, 1139
 * - 1142, 1143, 1144, 1145, 1146, 1147, 1148, 1149
 * - 1152, ,,,
 * - 1162, ,,,
 * - 1172,
 * - 1182,
 * - 1192
 *
 * - 1212, 1213, 1214, 1215, 1216, 1217, 1218, 1219
 * - 1221
 *
 * - 가장 작은 숫자를 앞에 오게하고
 * - 그 다음숫자들이 제일 작은지 채그해서
 * - 시계수가 몇인지부터 알아내보자
 */
public class _Main_2659_십자카드문제 {

	static int[] arr;
	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		Deque<String> q = new ArrayDeque<>();

		for(int i = 0; i < 4; i++){
			q.offer(st.nextToken());
		}// 입력끝

		int min;
		// 기존 시계수
		String now = "";

		for(int i = 0; i < 4; i++){

			String s = q.poll();
			q.offer(s);

			now += s;
		}

		min = Integer.parseInt(now); // 지금을 가장 최소라고 가정


		for(int t = 0; t < 3; t++){
			// 다음 시계수를 위해 한칸을 빼고 뒤에 넣음
			String next = "";
			String n = q.poll();
			q.offer(n);

			for(int i = 0; i < 4; i++){
				String s = q.poll();
				q.offer(s);

				next += s;
			}

			if(Integer.parseInt(next) < min){
				min =  Integer.parseInt(next);
			}
		}

		System.out.println(min);
		// 최소 시계수 구함 : min

		// 여기 규칙을 못찾겠음. 검색결과 모든 숫자를 다 탐색해보라는 답을 받음
		// 내일 시도해보겠습니다.
		int ans = min - 1111;
		ans = 1 + ans/10%10 * 8  + ans/100%10 + ans%10;

		System.out.println(ans);
	}
}
