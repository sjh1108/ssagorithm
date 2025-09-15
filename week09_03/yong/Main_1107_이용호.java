import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_1107_이용호 {
/*
 * 리모컨 버튼 고장
 * 
 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());
		
		
		//리모컨 버튼 고장 여부
        boolean[] broken = new boolean[10];
        if(M != 0){
            StringTokenizer st = new StringTokenizer(br.readLine());
            for(int i = 0; i < M; i ++) {
			    int btn = Integer.parseInt(st.nextToken());
			    broken[btn] = true;
		    }
        }
		

//		System.out.println("t");
		//모든 채널중 돌릴수 있는 채널중 마다 버튼 누른횟수 갱신
		//마지막채널 999999인 이유(9빼고 다 고장날수 있으니)
		int answer = Math.abs(100-N); //100에서 돌리는 경우
		
		for(int i = 0; i <= 999999; i++) {
			int ch = i;//변경할 채널
			
			boolean possible = true;
			String sNum = Integer.toString(i);
			
			//해당 채널로 돌릴수 있는지 체크
			for(int j = 0; j < sNum.length(); j++) {
				int num = ch % 10; // 1의 자리 확인
				ch = ch/10;//자리수 감소
				if(broken[num]) { //해당 숫자 고장났으면 false + break
					possible = false;
					break;
				}
				
			}
			if(!possible) continue;
			
			//채널 돌릴때 누른 횟수 + +-로 이동한 횟수
			int press = sNum.length() + Math.abs(N-i);
			//
			answer = Math.min(answer, press);//100에서 +-, 채널변경후+-
		}
		System.out.println(answer);
	}

}
