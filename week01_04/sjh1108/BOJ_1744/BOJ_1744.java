package week01_04.sjh1108.BOJ_1744;

import java.util.*;
import java.io.*;

class Main {
    static StringBuilder sb = new StringBuilder();
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    // static BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
    static StringTokenizer st;
    static int N, M;

    public static void main(String[] args) throws IOException {
        N = Integer.parseInt(br.readLine());

        List<Integer> pList = new ArrayList<>();
        List<Integer> nList = new ArrayList<>();

        for(int i = 0; i < N; i++){
            int num = Integer.parseInt(br.readLine());
            if(num > 0) pList.add(num);
            else nList.add(num);
        }

        Collections.sort(pList, Collections.reverseOrder());
        Collections.sort(nList);

        int sum = 0;
        int i = 0;
        while(i < pList.size()){
            if(i + 1 < pList.size() && pList.get(i) != 1 && pList.get(i + 1) != 1)

                sum += pList.get(i++) * pList.get(i++);
            else
                sum += pList.get(i++);
        }

        i = 0;
        while(i < nList.size()){
            if(i + 1 < nList.size())
                sum += nList.get(i++) * nList.get(i++);
            else
                sum += nList.get(i++);
        }

        System.out.println(sum);
    }
}