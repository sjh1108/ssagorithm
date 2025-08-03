package study_0523;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class week01_���簢����_��ȸ�� {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));		// 입력 출력 선언
		int t = Integer.parseInt(br.readLine());
		
		for(int T=1; T<=t;T++)				// 테스트 케이스 만큼 반복
        {
             
            int n = Integer.parseInt(br.readLine());	// 배열 크기 입력
             
             
            int delta[][] = {{1,0},{-1,0},{0,1},{0,-1}};		// 델타 선언 상하좌우
            int array[][] = new int[n][n];						// 배열 크기 만큼 n 선언
             
            for(int i=0; i<n;i++)							// 배열 입력 받는 부분
            {
                String s= br.readLine();
                st = new StringTokenizer(s);
                for(int j=0; j<n; j++)
                {
                    array[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            //System.out.println("입력끝");
            int min=Integer.MAX_VALUE;		
            int max_count=0;
             
            for(int i=0; i<n; i++)			// 탐색하는 로직
            {
                for(int j=0; j<n; j++)
                {
                    int count =0;
                     
                    int x = i;
                    int y = j;
                    int count_move=0;
                    while(true)				// 이어지는 방이 있는지 계속 탐색 
                    {
                        //System.out.println("while");
                        for(int w=0; w<delta.length; w++)
                        {
                            int dx = x + delta[w][0];
                            int dy = y + delta[w][1];
                            if(((dx >= 0)&&(dx < n))&&((dy >=0 )&&(dy <n)))
                            {
                                if((array[x][y]+1)== array[dx][dy])
                                {
                                    count++;
                                    count_move++;
                                    x = dx;
                                    y = dy;
                                     
                                    if(max_count == count_move && min >= array[i][j])		// 만약 방 개수가 같다면 숫자가 더 작은쪽 데이터 보관
                                    {
                                        //if(min >= array[i][j])
                                        min = array[i][j];
                                        max_count = count_move;
                                    }
                                    else if(max_count < count_move)// 이어진 방의 개수가 많다면 바꿔줌
                                    {
                                        min = array[i][j];
                                        max_count = count_move;
                                    }
                                     
                                    break;
                                }
                            }
                        }
                         
                        if(count == 0) break;
                         
                        count=0;
                         
                    }
 
                }
            }
             
            max_count++;
            bw.write("#"+T+" "+min +" "+ max_count+"\n");
        }
        bw.flush();
        bw.close();
	}
}

