package study;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.StringTokenizer;

public class Solution_5648_김회인 {
	
	static class Atom {
		int x;
		int y;
		int dir;
		//int energy=0;
		
		Atom(int x, int y,int dir)//, int energy) 
		{
			this.x = x;
			this.y = y;
			this.dir = dir;
			//this.energy = energy;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(x,y);
		}
		
		@Override
		public boolean equals(Object obj)
		{
			if(this == obj) return true;
			if(obj == null || getClass() != obj.getClass()) return false;
			Atom atom = (Atom) obj;
			return x == atom.x && y == atom.y;
			
		}
	}
	
	static int delta[][] = {{0,1},{0,-1},{-1,0},{1,0}};
	static HashMap<Atom,Integer> map;
	static int total_energy = 0;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;// = new StringTokenizer(br.readLine());
		
		int T =Integer.parseInt(br.readLine());
		
		for(int t=1; t<=T; t++)
		{
			
			int N = Integer.parseInt(br.readLine());
			map = new HashMap<>();
			total_energy = 0;
			
			for(int i=0; i<N;i++)
			{
				st = new StringTokenizer(br.readLine());
				int x = Integer.parseInt(st.nextToken())*2;
				int y = Integer.parseInt(st.nextToken())*2;
				int dir = Integer.parseInt(st.nextToken());
				int energy = Integer.parseInt(st.nextToken());
				
				Atom atom = new Atom(x,y,dir);
				map.put(atom, energy);
			}
			
			//System.out.println("map.size() : "  + map.size());
			while(map.size() > 1)
			{
				//System.out.println("a");
				map = atom_move();
			}
			
			bw.write("#" + Integer.toString(t) + " " + Integer.toString(total_energy) +"\n");
		}
		
		bw.flush();
		bw.close();
		br.close();
	}
	
	static HashMap<Atom,Integer> atom_move()
	{
		HashMap<Atom,Integer> new_map = new HashMap<>();
		HashMap<Atom,Integer> q = new HashMap<>();
		
		for(Atom atom : map.keySet())
		{
			int energy = map.get(atom);
			int dx = atom.x + delta[atom.dir][0];
			int dy = atom.y + delta[atom.dir][1];
			
			if(dx<-2000 || dx > 2000 || dy < -2000 || dy > 2000) continue;
			
			Atom new_atom = new Atom(dx,dy,atom.dir);
			
			if(new_map.containsKey(new_atom))		// 만약 충돌 한다면?
			{ 
				//System.out.println("total_energy : " + total_energy + " energy : " + energy);
				total_energy += energy;
				q.put(new_atom, new_map.get(new_atom));
				continue;
			}
			
			// 한번 앞에 충돌 하는것도 만들어 줄것 이것은 따로 부딫힘 큐에 저장 필요 없음
			
			new_map.put(new_atom, energy);
		}
		
		for(Atom atom : q.keySet())		// 아까 저장되어있던 충돌 되어있는 위치의 원자 최종 제거
		{
			new_map.remove(atom);
			int energy = q.get(atom);
			total_energy += energy;
			//System.out.println("최종 total_energy : " + total_energy + " energy : " + energy);
		}
		
		return new_map;
	}
	
	//static move_half

}
