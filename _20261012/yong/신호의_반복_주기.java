import java.util.*;
import java.io.*;
public class Main {
  public static void main(String[] args) throws IOException{
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String st = br.readLine();
    int L = st.length();
    boolean found = false;
    for(int i = 1; i <= L/2; i++){
      
      if(L % i == 0){
        int k = L / i; // 반복 횟수
        String repeat = st.substring(0,i);
        StringBuilder sb = new StringBuilder();
        
        for(int j = 0; j < k; j++){
          sb.append(repeat);
        }

        String result = sb.toString();
        if(result.equals(st)){
          System.out.print(i + " ");
          System.out.println(k);
          found = true;
          break;
        }
        
      }
      
    }
    if(!found){
      System.out.println(L +" 1");
    }
    
  }
}
