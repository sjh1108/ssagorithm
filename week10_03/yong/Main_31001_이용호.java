package algostudy.baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main_31001_이용호 {
	/*
	 * 1 A B: 회사 A 주식 B주 구매(B주 못 사면 아예 안삼)
	 * 2 A B: A 주식 B주 팔기
	 * 3 A C: A 회사 C만큼 주가 상승
	 * 4 D C: D 그룹 속하는 회사 C 만큼 주가 상승
	 * 5 D E: D 그룹 속하는 회사 E% 만큼 주가 떨어짐
	 * 6 : 현재 하이비 현금 출력
	 * 7 : 보유 주식 + 현금 출력
	 * 
	 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int companyCnt = Integer.parseInt(st.nextToken());
		int seedMoney = Integer.parseInt(st.nextToken());
		int commandCnt = Integer.parseInt(st.nextToken());
		long cash = seedMoney;
		
		HashMap<String,long[]> map = new HashMap<>();
		HashMap<String,Long> hi = new HashMap<>();
		// 회사 정보
		for(int i = 0; i < companyCnt; i++) {
			st = new StringTokenizer(br.readLine());
			long G = Long.parseLong(st.nextToken());
			String H = st.nextToken();
			long P  = Long.parseLong(st.nextToken());
			
			map.put(H, new long[] {G,P}); // key = 회사 이름, value = [회사 그룹, 주]
		}		
		// 메뉴 입력 처리
		for(int i = 0; i < commandCnt; i ++) {
			st = new StringTokenizer(br.readLine());
			int command = Integer.parseInt(st.nextToken());
			
			String comA;
			long B;
			long P; //조회하는 회사의 주가
			long C;
			long D;
			long E;
			long HB; //하이비가 갖고있는 회사의 주
//			System.out.println(command);
			switch(command) {
			case 1: // A회사의 주식 B만큼 구입
				comA = st.nextToken();
				B = Integer.parseInt(st.nextToken());

				//구매에 필요한 돈 (10 ^ 9 * 100 최대) -> long
				P = map.get(comA)[1];//A회사 주가
				long needMoney = P * B;
				//현금으로 구매 할수 있으면
				if(needMoney <= cash) {
					cash -= needMoney;
					hi.put(comA, hi.getOrDefault(comA, 0L) + B);
//					System.out.println(comA +"회사 " + hi.get(comA) +"주 구매" );
				}
				break;
				
			case 2://입력 B가 내가 갖고있는 주보다 큰게 들어온다면?
				comA = st.nextToken();
				B = Long.parseLong(st.nextToken());
				P = map.get(comA)[1]; //A회사의 주가
				HB = hi.getOrDefault(comA, 0L);// 하이비가 갖고있는 A회사의 주
				
				//갖고있는 주보다 팔고자 하는 주가 더 크면, B를 내가 보유 하고있는 주로 갱신
				if(HB < B) B = HB;

				if(P > 0 && B > 0) {
					//보유 주 갱신
					hi.put(comA,HB-B);
//					System.out.println("현재 현금: " + cash + "| 팔고자 하는 주 :" + comA + "| 주 당 금액 : " + P + "| 총 금액 : " + B * P);
					cash += B * P;
//					System.out.println("하이비 보유: " + HB + "|팔고자하는 주: " + B + "|갱신 :" + (HB - B));
				}
				break;
			case 3:
				comA = st.nextToken();
				C = Long.parseLong(st.nextToken());
//				System.out.print("주식 변동 전: " + map.get(comA)[1]);
				
				map.put(comA, new long[] {map.get(comA)[0],map.get(comA)[1] + C});
//				System.out.println(" | 변동후 : "+map.get(comA)[1]);
				break;
			case 4:
				D = Long.parseLong(st.nextToken());
				C = Long.parseLong(st.nextToken());
				
				for(String com : map.keySet()) {
					if(map.get(com)[0] != D) continue; // D그룹 아니면 패스
//					System.out.print("주식 변동 전: " + map.get(com)[1]);
					map.put(com, new long[] {D, map.get(com)[1] + C});
//					System.out.println(" | 변동후 : "+map.get(com)[1]);
				}
				break;
			case 5: //크아악 크아악 크아악
				D = Long.parseLong(st.nextToken());
				E = Long.parseLong(st.nextToken());
				
				for(String com : map.keySet()) {
					if(map.get(com)[0] != D) continue; // D그룹 아니면 패스
					
//					System.out.println("주식 변동 전: " + map.get(com)[1]);
					long oldPrice = map.get(com)[1];
					
					C = (long)(oldPrice * (100.0 + E) / 100.0);
					C -= C % 10;
					map.put(com, new long[] {D, C});
//					System.out.println(" | 변동후 : "+map.get(com)[1]);
				}
				break;
			case 6:
				System.out.println(cash);
				break;
			case 7:
				long totalMoney = cash;
				for(String com : hi.keySet()) {
					//보유 주 회사 가격 검색
					P = map.get(com)[1];
					HB = hi.getOrDefault(com, 0L);
					totalMoney += (long)(HB * P);
					
				}
				System.out.println(totalMoney);
				break;
			}
		}
	}

}
