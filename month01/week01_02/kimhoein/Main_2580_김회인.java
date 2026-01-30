
import java.io.*;
import java.util.*;

public class Main_2580_김회인 {
	/*
	 * 백트래킹 문제인데
	 * 생각을 좀 해내는게 어려웠다.
	 * 1~9 까지 중 넣을 수 있는 숫자를 넣고
	 * 다음으로 넘어간다. 
	 * 마찬가지 계속 수행
	 * 수행하다가 안되는 경우 나오면 뒤로 백
	 * 다시 수행한다.
	 * 이렇게 해서 모든 경우를 구한다.
	 * 
	 */
	static int map[][] = new int[9][9];
	
	public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    	StringTokenizer st;// = new StringTokenizer(br.readLine());
    	
    	for(int i=0; i<9; i++) {
    		st = new StringTokenizer(br.readLine());
    		for(int j=0; j<9; j++) {
    			
    			map[i][j] = Integer.parseInt(st.nextToken());
    		}
    	}
    	
    	
    	if(solve(0,0)) {
    		for(int i=0; i<9; i++) {
    			for(int j=0; j<9; j++) {
    				System.out.print(map[i][j] + " ");
    			}
    			System.out.println("");
    		}
    	}
    	bw.flush();
    	bw.close();
    	br.close();
    }
	
	public static boolean solve(int row, int col) {
		
		if(row == 9) return true;
		
		int nextR = (col == 8) ? row + 1 : row;
		int nextC = (col == 8) ? 0 : col + 1;
		
		if(map[row][col] != 0) return solve(nextR, nextC);
		
		for(int i=1; i<10; i++) {
			if(isvalid(row,col, i)) {
				map[row][col] = i;
				
				if(solve(nextR, nextC)) {
					return true;
				}
				
				map[row][col] = 0;
			}
			
		}
		
		return false;
	}
	
	public static boolean isvalid(int row, int col, int num) {
		for(int i=0; i<9;i++) {
			if(map[row][i] == num || map[i][col] == num) return false;
		}
		
		int dx = (row/3)*3;
		int dy = (col/3)*3;
		
		for(int i=dx; i < dx+3; i++) {
			for(int j=dy; j < dy + 3; j++) {
				if(map[i][j] == num) return false;
			}
		}
		
		return true;
	}
    
	
}
