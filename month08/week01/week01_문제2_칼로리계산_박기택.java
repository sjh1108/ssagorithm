package month08.week01;
// Source code is decompiled from a .class file using FernFlower decompiler.
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
 
public class Main {
   public static void main(String[] args) throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      int k = Integer.parseInt(br.readLine());
      int[] dir = new int[6];
      int[] mov = new int[6];
      int maxWidth = -1;
      int maxheigh = -1;

      int i;
      for(i = 0; i < 6; ++i) {
         StringTokenizer st = new StringTokenizer(br.readLine());
         dir[i] = Integer.parseInt(st.nextToken());
         mov[i] = Integer.parseInt(st.nextToken());
         if (dir[i] != 1 && dir[i] != 2) {
            if ((dir[i] == 3 || dir[i] == 4) && (maxheigh == -1 || mov[maxheigh] < mov[i])) {
               maxheigh = i;
            }
         } else if (maxWidth == -1 || mov[maxWidth] < mov[i]) {
            maxWidth = i;
         }
      }

      i = mov[maxheigh] * mov[maxWidth];
      int smallHeight = Math.abs(mov[(maxWidth + 1) % 6] - mov[(maxWidth + 5) % 6]); // 얘네 이해가 안됨. 
      int smallWidth = Math.abs(mov[(maxheigh + 1) % 6] - mov[(maxheigh + 5) % 6]);
      int resultArea = i - smallWidth * smallHeight;
      int totalMelon = resultArea * k;
      System.out.println(totalMelon);
   }
}
