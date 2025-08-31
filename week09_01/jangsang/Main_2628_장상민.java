
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main_2628_장상민 {
	static final BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	static StringTokenizer tokens;
	
	public static void main(String[] args) throws IOException {
		
		tokens = new StringTokenizer(input.readLine());
		int width = Integer.parseInt(tokens.nextToken());
		int height = Integer.parseInt(tokens.nextToken());
		
		int n = Integer.parseInt(input.readLine());
		
		List<Integer> sero = new ArrayList<>();
		List<Integer> garo = new ArrayList<>();
		
		sero.add(0);
		sero.add(width);
		garo.add(0);
		garo.add(height);

		for (int i = 0; i < n; i++) {
			tokens = new StringTokenizer(input.readLine());
			int gase = Integer.parseInt(tokens.nextToken());
			int range = Integer.parseInt(tokens.nextToken());
			if(gase == 0) {
				garo.add(range);
			}else if(gase == 1) {
				sero.add(range);
			}
		}
		
		Collections.sort(sero);
		Collections.sort(garo);
		
		int maxwidth = 0;
		for (int i = 1; i < garo.size(); i++) {
			maxwidth = Math.max(maxwidth, garo.get(i) - garo.get(i -1));
		}

		int maxheight = 0;
		for (int i = 1; i < sero.size(); i++) {
			maxheight = Math.max(maxheight, sero.get(i) - sero.get(i -1));
		}
		System.out.println("Garo: " + garo); // 세로 컷
		System.out.println("Sero: " + sero); // 가로 컷

		System.out.println(maxheight * maxwidth);
	}
}
