package study;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

public class Main_14891_김회인 {
	static class Cogwheel
	{
		int cog[] = new int[8];
		int index_score = 0; // 12시 방향 톱니
		int index_r = 2; // 오른쪽 방향 톱니
		int index_l = 6; // 왼쪽 방향 톱니
		
		Cogwheel(int[] cog)
		{
			this.cog = cog;
		}
		
		public void rotation_l() // 반시계 방향 회전
		{
			index_score = (index_score+1)%8;
			index_r = (index_r+1)%8;
			index_l = (index_l+1)%8;
		}
		
		public void rotation_r() // 시계 방향 회전
		{
			index_score = (index_score-1)<0 ? 7:index_score-1;
			index_r = (index_r-1)<0 ? 7:index_r-1;
			index_l = (index_l-1)<0 ? 7:index_l-1;
		}
		
		public int get_score()
		{
			return cog[index_score];
		}
		
		public int get_leftstate()
		{
			return cog[index_l];
		}
		
		public int get_rightstate()
		{
			return cog[index_r];
		}
	}
	static int[] rotate_states = new int[5];		// 각 톱니별 회전 방향 저장 배열
	static boolean[] brotate_states = new boolean[5];	// 각 톱니별 회전 할지 말지
	static Cogwheel cogwheels[] = new Cogwheel[5];
	static int scores[] = {0,1,2,4,8};
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;// = new StringTokenizer(br.readLine());
		
		int cogs[][] = new int[5][8];
		
		for(int i=1; i<=4; i++)
		{
			String s = br.readLine();
			for(int j=0; j<8; j++)
			{
				cogs[i][j] = s.charAt(j)-'0';
			}
		}
		
		for(int i=1; i<=4; i++)
		{
			cogwheels[i] = new Cogwheel(cogs[i]);
		}
		
		int T = Integer.parseInt(br.readLine());
		
		for(int t=0; t<T; t++)
		{
			st = new StringTokenizer(br.readLine());
			rotate_states = new int[5];		// 회전 방향 초기화
			brotate_states = new boolean[5];	// 회전 여부 저장 배열 초기화
			
			int index = Integer.parseInt(st.nextToken());
			int rotate_state = Integer.parseInt(st.nextToken());
			
			check_cog_l(index,rotate_state);
			check_cog_r(index,rotate_state);
			
			rotate();
		}
		
		int score=0;
		
		
		for(int i=1; i<=4; i++)
		{
			score += cogwheels[i].get_score() * scores[i];
		}
		
		bw.write(Integer.toString(score));
		bw.flush();
		bw.close();
		br.close();
		
	}
	
	static void rotate()
	{
		for(int i=1; i<=4; i++)
		{
			if(!brotate_states[i]) continue;
			if(rotate_states[i] == 1) cogwheels[i].rotation_r();
			else cogwheels[i].rotation_l();
		}
	}
	
	static void check_cog_l(int index, int rotate_state) // 회전 상태가 1면 시계방향 -1이면 반시계
	{
		rotate_states[index] = rotate_state;
		brotate_states[index] = true;
		
		if(index >1 && cogwheels[index].get_leftstate() != cogwheels[index-1].get_rightstate())
		{
			check_cog_l(index - 1,rotate_state*(-1));
		}
	}
	
	static void check_cog_r(int index, int rotate_state) // 회전 상태가 1면 시계방향 -1이면 반시계
	{
		rotate_states[index] = rotate_state;
		brotate_states[index] = true;
		
		if(index <4 && cogwheels[index+1].get_leftstate() != cogwheels[index].get_rightstate())
		{
			check_cog_r(index + 1,rotate_state*(-1));
		}
	}

}
