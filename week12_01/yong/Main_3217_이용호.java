package baek_week12_01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

/*
 * 1 ~ 10만
 * var=malloc(size); -> 처음 등장하는 size개의 연속된 공간 할당(리턴값: 할당 공간의 처음 주소, 할당 못하면 0)
 * free(var); -> 할당 해제(var에 0, 이미 0이면 아무일도 안일어남)
 * print(var); -> var값 출력
 * 명령 최대 10만번
 * -----
 * 할당 할때 해당 var는 할당했던 위치랑 size 갖고있어야함(해제시 필요)
 * 공간 할당 시작을 해시 키 ,남은 공간을 해시 값
 * hash = startIndex,size
 * for(size : map){
 * 	if(size >= wantSize) -> 할당
 * 	else -> continue
 * }
 * 
 * 
 * 
 */
public class Main_3217_이용호 {
	public static class Node{
		private int startIdx;
		private int size;
		public Node(int startIdx,int size) {
			this.startIdx = startIdx;
			this.size = size;
		}
	}
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		// 할당 가능한 공간들(할당 시작 인덱스: 할당 가능한 사이즈)
		HashMap<Integer,Integer> map = new HashMap<>();
		// 할당 한 공간들
		HashMap<String,Node> memmory = new HashMap<>();
		map.put(1, 100000);
		
		StringBuilder sb = new StringBuilder();
		
		for(int i = 0; i < N; i++) {
			String line = br.readLine();
			// 항상 4글자 free -> (, malloc -> =, print -> t
			String var;
			switch(line.substring(4, 5)) {
			case "(":
				var = line.substring(5,9);

				// 현재 공간 + 뒤에 할당 가능공간 합쳐줘야함
				Node now = memmory.getOrDefault(var,new Node(100001,0));
				int backIdx = now.size + now.startIdx;
				//뒤에 합칠 공간 있으면
				if(backIdx <= 100000 && map.containsKey(backIdx)) {
					// 공간 합치기
//					System.out.println("공간 합치기: (startIdx: " + now.startIdx + " / newSize: " + (map.get(backIdx) + now.size));
					map.put(now.startIdx, (map.get(backIdx) + now.size));
					map.remove(backIdx);
				}
				// 뒤에 합칠 공간 없으면
				else {
					// 
//					System.out.println(now.startIdx);
					map.put(now.startIdx, now.size);
					
				}
				// 할당 해제
				memmory.remove(var);
				break;
			case "=":
				var = line.substring(0,4);
				int size = Integer.parseInt(line.substring(12, line.length()-2));
				for(int idx : map.keySet()) {
					int mapSize = map.get(idx);
//					System.out.println("할당하려는 사이즈: " + size + "/ 할당 가능 한 사이즈:" + size);
					// 해당 size만큼 할당 해주고, 할당 할수 있는 공간 map 갱신
					if(mapSize >= size) {
						// 남는 공간
						int remain = mapSize - size;
						// 공간 할당
						memmory.put(var, new Node(idx ,size));
//						System.out.println(var + "할당 완료");
						// 할당 가능한 공간 갱신
						map.remove(idx);
						// idx + size 부터, 할당 가능공간(남은 공간 = remain)
						if(remain > 0) {
							map.put(idx + size, remain);
						}
							
					}
//					else {
//						sb.append("0" + "\n");
//					}
				}
				break;
			case "t":
				var = line.substring(6,line.length()-2);
				
				Node result = memmory.getOrDefault(var,new Node(-1,0));
				if(result.startIdx != -1) {
					sb.append(result.startIdx + "\n");
				}
				else {
					sb.append("0" + "\n");
				}
				break;
			}
			
		}
		System.out.println(sb);

	}

}
