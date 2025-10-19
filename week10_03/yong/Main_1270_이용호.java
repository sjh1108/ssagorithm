import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
/*
 * 부대에 한 번호가 절반이상 차지하면 그 번호의 지배하에 놓임
 * 각 땅들을 지배한 군대의 번호를 출력
 * 아직 전쟁중이라면 SYJKGW를 출력
 * 해시 <부대 번호, 부대 번호 횟수> 선언하고 번호 누적하면 될듯
 * 처음에 잠깐 잘못생각한 부분 -> 과반수 부대 나왔는데 이거보다 더 많은 부대가 있으면 어떡하지? -> ㅋㅋ 이미 과반수 넘었으니까 그럴일 없음(바보 인증 완료)
 * 새로 알게된 점 : 해시맵 순회 방법 Map.Entry이용
 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int n = Integer.parseInt(br.readLine());
		for(int i = 0; i < n; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			Long TiCnt = Long.parseLong(st.nextToken());
			
			HashMap<Long, Integer> Ti = new HashMap<>(); // <부대 번호, 부대 번호 횟수>
			
			for(long j = 0 ; j < TiCnt; j++) {
				Long k = Long.parseLong(st.nextToken());
				Ti.put(k, Ti.getOrDefault(k, 0) + 1); // 횟수 누적
			}
			
			long majority = -1;
			for(Map.Entry<Long, Integer> T : Ti.entrySet()) {
				if(T.getValue() > TiCnt / 2) {
					majority = T.getKey();
					break; //과반수 부대 찾으면 종료
				}
			}
			System.out.println(majority == -1 ? "SYJKGW" : majority);
		}

	}

}
