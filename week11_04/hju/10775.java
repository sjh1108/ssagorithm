import java.io.*;
import java.util.*;

class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int answer = 0;

        // 1 ≤ G, P ≤ 10^5
        int G = Integer.parseInt(br.readLine());
        int P = Integer.parseInt(br.readLine());

        TreeSet<Integer> airport = new TreeSet<>();
        for(int i = 1; i <= G; i++)
            airport.add(i);

        for(int i = 0; i < P; i++) {
            int g = Integer.parseInt(br.readLine());

            if(airport.floor(g) == null) 
                break;

            airport.remove(airport.floor(g));
            answer++;
        }

        System.out.println(answer);
    }
}