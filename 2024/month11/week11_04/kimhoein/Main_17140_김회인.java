package study;


import java.io.*;
import java.util.*;
import java.util.Map.Entry;

class Main_17140_김회인 {
	static class Node implements Comparable<Node>
	{
		int x;
		int y;
		
		Node(int x, int y)
		{
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Node o) {
			if(this.y == o.y)
			{
				return this.x - o.x;
			}
			return this.y - o.y;
		}
	}
	static int r;
	static int c;
	static int k;
	static int hight;
	static int row;
	
	static int map[][];
	
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        hight = 3;
        row = 3;
        
        map = new int[3][3];

        for(int i=0; i<3; i++)
        {
        	st = new StringTokenizer(br.readLine());
        	
        	for(int j=0; j<3; j++)
        	{
        		map[i][j] = Integer.parseInt(st.nextToken());
        	}
        }
        int time=0;
        //map[2][2] = 0;
        //System.out.println("map[2][2] : " + map[2][2]);
        while (time <= 100) {
        
        	if (r - 1 < map.length && c - 1 < map[0].length && map[r - 1][c - 1] == k) {
        		System.out.println(time);
                return;
            }

            //if (time == 100) break;

            if (map.length >= map[0].length) {
                cal_r();
            } else {
                cal_c();
            }
            time++;
        }
        
        System.out.println(-1);
//        int[] array = {2,1,3};
//        makeArr(array);
//        if(map.length > r-1 && map[0].length > c-1 && count == 100 && map[r-1][c-1] != k) count = -1;
//        if(map.length <= r-1 && map[0].length <= c-1) count= -1;
//        bw.write(Integer.toString(count));
        bw.flush();
        bw.close();
        br.close();

    }
    
    static public void cal_r()
    {
    	int max=0;
    	List<int[]> list = new LinkedList<>();
    	
    	for(int i=0; i<hight; i++)
    	{
    		int[] temp = makeArr(map[i]);
    		max = Math.max(max, temp.length);
    		list.add(temp);
    	}
    	
    	max = Math.min(100, max);
    	row = max;
    	map = new int[hight][row];
    	
    	for(int i=0; i<hight; i++)
    	{
    		for(int j=0; j<row; j++)
    		{
    			if(list.get(i).length <= j) break;
    			else map[i][j] = list.get(i)[j];
    			
    		}
    	}
    }
    
    static public void cal_c()
    {
    	int max=0;
    	List<int[]> list = new LinkedList<>();
    	for(int i=0; i<row; i++)
    	{
    		int[] arr = new int[hight];
    		//System.out.println("row : " + row + " " + map.length + " " +map[0].length + " " + map[2].length );
    		//arr[2] = map[2][2];
    		for(int j=0; j<hight; j++) {
    			//int a = map[j][i];
    			//System.out.println( i + " " + j + " " + map[j][i]);
    			arr[j] = map[j][i]; 
    		}
    		int[] temp = makeArr(arr);
    		
//    		for(int j=0; j<temp.length; j++)
//    		{
//    			System.out.println(temp[j] + " ");
//    		}
    		max = Math.max(max, temp.length);
    		
    		list.add(temp);
    	}
    	
    	max = Math.min(100, max);
    	hight = max;
    	map = new int[hight][row];
    	//System.out.println("hight : " + hight + " " + row);
    	for(int i=0; i<row; i++)
    	{
    		for(int j=0; j<hight; j++)
    		{
    			
    			if(list.get(i).length <= j) break;
    			//System.out.println("i : " + i + " " + list.get(i).length + " " + j);
    			map[j][i] = list.get(i)[j]; 
    		}
    	}
    	
//    	for(int i=0; i<hight; i++)
//    	{
//    		for(int j=0; j<row; j++)
//    		{
//    			if(list.get(j).length <= i) break;
//    			map[i][j] = list.get(i)[j];
//    		}
//    	}
    		
    	
    }
    
    static public int[] makeArr(int[] arr)
    {
    	HashMap<Integer, Integer> hash = new HashMap<>();
    	PriorityQueue<Node> pq = new PriorityQueue<>();
    	
    	for(int i=0; i<arr.length; i++)
    	{
    		if(arr[i] == 0) continue;
    		hash.put(arr[i], hash.getOrDefault(arr[i], 0) + 1);
    		//System.out.println("hash in : " + i + " " + arr[i]  + " " + hash.size());
    	}
    	
    	for(Entry<Integer, Integer> h : hash.entrySet())
    	{
    		//System.out.println(h.getKey() + " " + h.getValue());
    		pq.add(new Node(h.getKey(),h.getValue()));
    	}
    	
    	int array[] = new int[hash.size()*2];
    	//System.out.println("hash.size() : " + hash.size() + " " + pq.size());
    	int count=0;
    	
    	for(int i=0; i<hash.size(); i++)
    	{
    		Node n = pq.poll();
    		//System.out.println("int for : " + n.x + " " + n.y + " " + i);
    		array[count++] = n.x;
    		array[count++] = n.y;
    	}
    	
//    	System.out.println("");
//    	for(int i=0; i<array.length; i++)
//    	{
//    		System.out.print(array[i] + " ");
//    	}
//    	System.out.println("");
    	
    	return array;
    }
    
}