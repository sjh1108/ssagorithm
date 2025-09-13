import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1107_손홍민 {
	
	// 알고리즘 분류 : 브루트포스(추가로 간단한 비트마스킹 사용 가능)
	// 풀이 순서 : 
	// 1. 목표 채널 번호 저장 및 고장난 번호 목록을 비트열에 등록
	// 2. 목표 채널이 100인가? 그렇다면 리모컨 조작이 필요 없음. 0 반환 후 종료
	// 3. 일단 +/- 버튼으로만 이동하는 경우의 클릭 수를 등록
	// 4. 숫자 버튼 입력으로 채널 이동 후 +/- 버튼으로 이동하는 경우의 클릭 수를 계산하고 최소값 갱신
	
	// 고장난 숫자 버튼을 저장할 비트열
	static int bit;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int target = Integer.parseInt(br.readLine());
		int err = Integer.parseInt(br.readLine());
		
		if(err > 0) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for(int i=0; i<err; i++) {
				int errNum = Integer.parseInt(st.nextToken());
				bit |= 1 << errNum;
			}
		}
		
		// 원하는 채널 번호가 100인 경우 버튼을 누를 필요가 없음. 즉시 종료
		if(target == 100) {
			System.out.println(0);
			System.exit(0);
		}
		
		// 원하는 채널로 +/- 버튼으로만 이동하는 경우의 버튼 누르는 횟수를 미리 등록
		int ans = Math.abs(target - 100);
		
		// 숫자로만 입력할 수 있는 다른 값까지 눌러야 하는 버튼 수 + 값 자체를 입력하기 위해 눌러야 하는 버튼 수를 더함
		// 이후 버튼 누르는 횟수의 최소값을 갱신
		for(int i=0; i<999_999; i++) {
			int cnt = check(i);
			// cnt == 0이면 번호 입력으로 이동할 수 없는 채널이라는 뜻
			if(cnt > 0) {
				ans = Math.min(ans,  Math.abs(target - i) + cnt);
			}
		}
		
		System.out.println(ans);
	}
	
	// 번호 입력으로만 해당 채널로 이동할 수 있는지 확인
	static int check(int x) {
		// 0인 경우 -> 고장난 번호라면 0, 아니면 1 반환
		if(x == 0) return ((bit & 1) == 1 ? 0 : 1);
		
		// 한 자리씩 비트열과 대조하면서 고장난 번호인지 확인
		// 고장난 번호가 하나라도 섞여있다면 즉시 0 반환
		// 끝까지 검증을 마쳤다면 입력 횟수 반환
		int cnt = 0;
		while(x > 0) {
			if((bit & 1<<(x%10)) != 0) return 0;
			cnt++;
			x /= 10;
		}
		
		return cnt;
	}

}
