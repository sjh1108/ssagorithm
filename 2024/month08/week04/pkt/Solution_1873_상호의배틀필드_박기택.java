package month08.week04.pkt;

import java.io.*;
import java.util.*;




public class Solution_1873_상호의배틀필드_박기택 {
	


	// 델타: 상하좌우 -> 방향 인덱스를 정의함.
	static final int[] dx = {-1, 1, 0, 0};
	static final int[] dy = {0, 0, -1, 1};
	
	// 전차 이동 정리
	//	^	위쪽을 바라보는 전차(아래는 평지이다.)
	//	v	아래쪽을 바라보는 전차(아래는 평지이다.)
	//	<	왼쪽을 바라보는 전차(아래는 평지이다.)
	//	>	오른쪽을 바라보는 전차(아래는 평지이다.)
	static final char[] DIR_SYMBOL = {'^','v', '<', '>'}; // 방향인덱스 순서와 동일하게 DIR_SYMBOL을 구성함. 

	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// StringBuilder 선언: 차후 여기에 모아서 출력할 예정
		StringBuilder out = new StringBuilder(); 
		
		int T = Integer.parseInt(br.readLine());
		
		
		for(int tc = 1; tc <= T; tc++) {
			
			// 1) 맵크기를 입력한다. -> readSize메서드를 통해 값을 받아올 예정 
			// 변수명 sz, H랑 W를 받아옴. 
			int[] sz = readSize(br); //br을 전달해야 readSize 안에서 br.readLine() 같은 입력 처리가 가능. 반성) int sz[]은 잘못된 표기, 생성할 때 매서드를 외부에서 바로 생성
			int H = sz[0], W = sz[1]; 
			
			
			// 2) 맵을 입력한다.
			// ㅁㅁㅁㅁㅁㅁㅁ
			// ㅁㅁㅁㅁㅁㅁㅁ
			// ㅁㅁㅁㅁㅁㅁㅁ -> H = 3, W =7
			char[][] map = readMap(br, H, W);  
			
			
			// 3) 전차 위치/방향 찾기
			// Tank라는 클래스 생성없이 매서드에서 Tank로 반환할 수 있음
            // findTank라는 매서드를 활용하기.
            // map은 게임판 전체를 저장한 2차원 문자 배열
            Tank tank = findTank(map);


			// 4) 명령 수  + 명령 문자열 입력
            // 23 <- cmdCount
            // SURSSSSUSLSRSSSURRDSRDS <- cmds
            int cmdCount = Integer.parseInt(br.readLine()); // 얼마나 입력받아야 하나 적음
            String cmds = br.readLine();



			
			// 5) 명령 처리 루프
			// cmds에서 받은 내용을 기준으로
            // 문자 S를 확인하면 -> 1. 문자열을 c에 저장하고, S라면 샷, 아니라면 다른 행동
            // 샷을 날리기. 아니면 다른 행동 시도
            for (int i = 0; i < cmdCount; i++) {
                char c = cmds.charAt(i); // 문자열에서 단어 하나씩 가져오기.
                if (c == 'S') {
                    // 사격: 현재 방향으로 포탄 전진 -> 매서드 생성 shoot(tank, map) 기준
                    shoot(tank, map);
                } else{ // 다른 행동 시도
                	// 회전 + 이동
                	// 문자를 입력받으면, 방향을 설정하고
                	// int ndir 변수에 방향 먹이고, 
                	int ndir = toDirFromCmd(c);
                	// 회전이나 turnTo
                	turnTo(tank, map, ndir); // 전차 심볼만 회전, 사용할거 재료로 넣고,
                	
                	
                	// 이동 tryMove
                	tryMove(tank, map); // 이동 
                	
                	

                }
                



            }
            //    		  6) 출력 (#tc 바로 뒤에 맵 이어 붙이기)
//          out은 보통 **출력 버퍼용 StringBuilder**입니다.
//          append()로 문자열을 이어 붙여 한 번에 찍으려는 거예요(속도↑, I/O 호출↓).
          // #tc 출력
          // map가져오기 -> 매서드 활용
          out.append('#').append(tc).append(' '); // StringBuilder 객체
          appendMap(out, map);
          
			
		}
			
		// 출력	
		System.out.print(out);
		
	
	}/// tc 
			
	// --------------------------------------------------------------
			// [ 입출력, 유틸 관련]
			// --------------------------------------------------------------
			
			/** "H W" 한 줄을 읽어 배열[H, W] 로 반환 ->  H, W는 위에서 입력받을거임  */ 
            //매서드를 static으로 두면 객체 생성 없이 호출 가능하고, 인스턴스 상태와 무관한 유틸성 메서드에 적합
			static int[] readSize(BufferedReader br) throws IOException {
				StringTokenizer st = new StringTokenizer(br.readLine());
				return new int[] {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
			}
			
				
			/** H줄의 맵을 읽어 2차원 char 배열로 반환 */
			static char[][] readMap(BufferedReader br, int H, int W) throws IOException { // 매서드 안에서 br.readLine()을 써야 해서 외부에서 만든 br을 넘겨받음
				// 이차원 배열 a에 담고,
				// 반복문을 통해 배열에 입력값을 넣고,
				// 반환해버리기.
				char[][] a = new char[H][W];
				for (int i = 0; i < H; i++) {
					a[i] = br.readLine().toCharArray(); //문자열(String)을 문자 배열(char[])로 바꿔주는 메서드 
				}
				return a;
			}

            /*

            ***....
            *-..#**
            #<.****
            
            */
				
			
			
			/** 맵에서 전차의 시작 위치/방향을 찾아 Tank 생성 */
            // 객체 안 만들고 바로 호출하기 위해 static Tank findTank으로 씀. 
            // Tank는 반환(return)되는 값의 타입
            // 이전에 받은 map데이터를 탐색함. 
            static Tank findTank(char[][] map) {
                // map의 행 전체와 열 전체를 탐색하고,
                // 값을 하나씩 꺼내서
                // 위치와 방향을 찾아 Tank를 생성
                for (int i = 0; i < map.length; i++) {
                    for (int j = 0; j < map[i].length; j++) {
                        char c = map[i][j];
                        // 위, 아래, 왼쪽, 오른쪽 바라보는지 if문으로 점검하기 
                        //'^','v', '<', '>'
                        if(c == '^' || c == 'v' || c == '<' || c == '>') {
                            // Tank( X위치, y위치, 방향-> 매서드를 통해 숫자로)를 반환하기 
                            // dir 매서드의 이름은 toDirFromSymbol(c)
                            return new Tank(i, j, toDirFromSymbol(c));
                        }
                    }

                }
                // 정상적이지 않은 가짜 탱크”를 만들어서, 최소한 리턴은 되도록 보장하는 코드
                return new Tank(-1, -1, 0);

            }
			
			
			

            // ------------------------------------------------------------
            // [핵심 동작 메서드 3개]
            // ------------------------------------------------------------

            /** 전차의 바라보는 방향만 바꾸고, 현재 칸의 심볼도 해당 방향으로 갱신 */
			static void turnTo(Tank t, char[][] map, int ndir) { // 재료 뽑아와서 넣기 설정.
				// 방향지정
				// 좌표계에 입력, 방향이 지정된 상태로..
				t.dir = ndir;
				map[t.x][t.y] = DIR_SYMBOL[ndir];
			
			}
			



            /** 현재 바라보는 방향으로 한 칸 이동 '시도'
             *  - 범위 안 + '.'(평지)이면: 실제 이동(맵 갱신 + 좌표 갱신)
             *  - 아니면: 제자리 유지(방향 심볼은 이미 turnTo로 갱신됨)
             */
			static void tryMove(Tank t, char[][] map) {
				// 움직여요. -> 4방이용
				int nx = t.x + dx[t.dir];
				int ny = t.y + dy[t.dir];
				
				// 범위 내에 있고, 평지라면 가도 됩니다.
				if (inRange(nx, ny, map) && map[nx][ny] =='.') {
					
					// 방향
					map[nx][ny] = DIR_SYMBOL[t.dir];
					
					// 평지로 전환
					map[t.x][t.y] = '.';
					
					// 이동하고 나서 값을 대입.
					t.x = nx;
					t.y = ny;
					
				}
				
				
				
			}




            /** 현재 방향으로 포탄 발사
             *  - '*'(벽돌) 만나면 '.'으로 파괴 후 정지
             *  - '#'(강철) 만나면 그대로 정지(변화 없음)
             *  - '.' / '-' 는 관통하며 진행
             */

             static void shoot(Tank t, char[][] map) {
            	 // 포탄의 현재 방향 설정 (탱크에서 날아가므로 현재 탱크의 위치 sx, sy 지정)
            	 int sx = t.x, sy = t.y;
            	 // 포탄이 벽이나 강철 또는 외부로 나가기 전까지 계속 진행되어야 함.
            	 // 총알 방향 및 움직임 설정.
            	 while(true) {
            		 sx += dx[t.dir];
            		 sy += dy[t.dir];
            		 
            		 // 맵 밖에서 있으면 움직이지 않으니, 취소 처리해주고,, 게임 맵 밖으로 나갈 때까지 직진한다.
            		 // inRange(sx, sy, map)가 true가 아니라면 맴밖이라서 break하기.
            		 if(!inRange(sx, sy, map)) {
            			 break;
            		 }
                	 
                	 // 셀에 따라 행동 지정
                	 /* 현재 방향으로 포탄 발사
                     *  - '*'(벽돌) 만나면 '.'으로 파괴 후 정지
                     *  - '#'(강철) 만나면 그대로 정지(변화 없음)
                     *  - '.' / '-' 는 관통하며 진행
                     */
            		 
            		 // char cell부터 생성하고, 지정받기 map[sx][sy]
            		 char cell =  map[sx][sy];
            		 
            		 if (cell == '*') {
            			 // 평지로 바꾸셈
            			 map[sx][sy] = '.'; //벽돌파괴
            			 break;
            			 
            		 } else if (cell == '#') { // 강철은 막힘
            			 break;
            		 } else {
            			 // 조건에 안 걸리면 아무 동작도 하지 않고 while 반복을 이어간다
            		 }
     	 
            	 }
            	 
            	            	 

             }



	
			/** (x, y)가 맵 안인지 검사 */
            static boolean inRange(int x, int y, char[][] map) {
            	// 0과 map.length(), 0과 map[0].length()
            	return 0 <= x && x < map.length && 0 <= y && y < map[0].length; 
            }
			
			/** 결과 맵을 출력 버퍼에 붙이기(줄바꿈 포함) */
            // appendMap(out, map); 
            static void appendMap(StringBuilder sb, char[][] map) {
            	// 한줄 씩 가져와서 읽고
            	for(int i = 0; i < map.length; i++) {
            		// 추가하기. 줄바꿈.
            		sb.append(map[i]).append("\n");
            	}
            	
            	
            	
            }
		
		
		


        // ------------------------------------------------------------
        // [간단 변환기]
        // ------------------------------------------------------------

        // toDirFromSymbol(c)의 매서드를 생성하기. 
        // 상하좌우에 따라 숫자를 반환해주기.
        // 상 0 하 1 좌 2 우 3
        static int toDirFromSymbol(char c) {
            // main이 static이라서, 같은 클래스 안의 다른 메서드를 부를 때도 static이어야 바로 호출할 수 있음
            // 만약 static이 아니면,객체를 생성해야만 호출 가능합니다.
            if (c == '^') return 0;
            if (c == 'v') return 1;
            if (c == '<') return 2;
            return 3; // '>'
        }



        /** 'U','D','L','R' -> 0,1,2,3 toDirFromCmd()*/
        static int toDirFromCmd(char cmd) {
        	if (cmd == 'U')  return 0;
        	if (cmd == 'D')  return 1;
        	if (cmd == 'L')  return 2;
        	return 3; // R
        	
        }



        // [전차 상태 - 아주 얕은 데이터 홀더: 매서드는 없고, 단순 필드값만 저장하는 역할]
        // int x, int y, int dir를 한 번에 관리하기 위함 

        static class Tank { // 클래스 정의라서 () 필요없음. 
            
            int x, y, dir;

            Tank(int x, int y, int dir) {
                this.x = x;
                this.y = y;
                this.dir = dir;
            }

         }


}




/* 이 문제를 공부하며 생긴 실수 코드 
 * 
 * 1.
 * "H W" 한 줄을 읽어 배열[H, W] 로 반환 
 * static int[] readSize(br.readLine())
 * 
 * 2. 
 * StringTokenizer st = new StringTokenizer();
 * 
 * 3.
 *  배열로 한번에 갈거라서 이거 안씀
 * 	int H = Integer.parseInt(st.nextToken());
 *  int W = Integer.parseInt(st.nextToken());
 * 
 * 4.
 * return  int[] {Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())};
 * 
 * 5. sz[0] =  
 * 
 * 6. 이 readSize 메서드를 main 메서드 안에 정의해놨어요. 
 * 
 * 
 * 7. int char[][] readMap = readMap(br, H, W);  
 * 
 * 8. return a;가 for문 안에 들어가 있어서,
첫 줄(i=0)만 읽고 바로 메서드를 종료해버립니다.
 * 
 * 9.
 * a[i] = br.readLine().toCharArray(); 안쳐짐
 * 
 * 10. 
 *  map.length[i]
 * 
 * 11. static int toDirFromSymbol(c) {
 * 
 * 12. 서술된 내용을 기준으로 글을 코드로 나타낼 수 있게 더 쪼개기
 * 문자 S를 확인하면 -> 1. 문자열을 c에 저장하고, S라면 샷, 아니라면 다른 행동
 * 
 * 13.
 * static boolean inRange(sx, sy, map)) {
 * 
 * 14. 범위
 * return 0 <= x
 * 
 * 
 * 15.0과 map.length, 0과 map[0].length
 * 
 * 16. 매서드가 너무 많으면 오히려 헷갈릴 수도.. ㄷㄷ
 * 
 * 17.
 *  
        static int toDirFromCmd(char cmd) {
        	if (char c == 'U')  return 0;
        	if (char c == 'D')  return 1;
        	if (char c == 'L')  return 2;
        	if (char c == 'R') 
        	return 3;
        	
        }
 * 
 * 
 * 
 * 
 * 18. if (inRange && )
 * 
 * 19.
 * // 범위 내에 있고, 평지라면 가도 됩니다.
				if (inRange(nx, ny, map) && map[nx][ny] =='.') {
					// 방향
					map[nx][ny] = DIR_SYMBOL[ndir];
					// 평지로 전환
					// 이동하고 나서 값을 대입.
				}
 * 
 * 20. new
 * 
 * 21. sb.append(map[i]).append(\n);
 * 

 * 
 */

