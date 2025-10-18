package week10_03.sjh1108.BOJ_31001;

import java.io.*;
import java.util.*;

class Main {
    private static long M;

    private static StringBuilder sb;

    private static HashMap<String, Integer> havingMap;
    private static HashMap<Integer, List<String>> groupMap;
    private static HashMap<String, Long> priceMap;

    private static void buy(String A, int B){
        long price = priceMap.get(A) * B;

        if(M < price) return;

        M -= price;
        havingMap.put(A, havingMap.get(A) + B);
    }
    private static void sell(String A, int B){
        int having = havingMap.get(A);

        if (having == 0) return;

        int sell;

        if (B >= having) {
            sell = having;
        } else {
            sell = B;
        }

        havingMap.put(A, having - sell);
        M += priceMap.get(A) * sell;
    }
    private static void price(String A, long B){
        priceMap.put(A, priceMap.get(A) + B);
    }
    private static void groupPrice(int D, int C){
        for(String h: groupMap.get(D)){
            price(h, C);
        }
    }
    private static void groupPricePercent(int D, int E){
        for(String h: groupMap.get(D)){
            long price = priceMap.get(h);

            long C = (long)(price * (100.0 + E) / 100.0);
            C -= C%10;

            priceMap.put(h, C);
        }
    }
    private static void printMoney(){
        sb.append(M).append('\n');
    }
    private static void ifSellAll(){
        long m = M;

        for(String h: havingMap.keySet()){
            int cnt = havingMap.get(h);
            long price = priceMap.get(h);

            m += cnt * price;
        }

        sb.append(m).append('\n');
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        int Q = Integer.parseInt(st.nextToken());

        havingMap = new HashMap<>();
        groupMap = new HashMap<>();
        priceMap = new HashMap<>();
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            int g = Integer.parseInt(st.nextToken());
            String h = st.nextToken();
            long p = Long.parseLong(st.nextToken());

            List<String> group = groupMap.getOrDefault(g, new ArrayList<String>());
            group.add(h);
            groupMap.putIfAbsent(g, group);
            priceMap.put(h, p);
            havingMap.put(h, 0);
        }

        sb = new StringBuilder();
        for(int q = 0; q < Q; q++){
            st = new StringTokenizer(br.readLine());

            int cmd = Integer.parseInt(st.nextToken());

            String A;
            int B, C, D, E;
            switch(cmd){
                case 1:
                    A = st.nextToken();
                    B = Integer.parseInt(st.nextToken());

                    buy(A, B);
                    break;
                
                case 2:
                    A = st.nextToken();
                    B = Integer.parseInt(st.nextToken());

                    sell(A, B);
                    break;
                
                case 3:
                    A = st.nextToken();
                    C = Integer.parseInt(st.nextToken());

                    price(A, C);
                    break;

                case 4:
                    D = Integer.parseInt(st.nextToken());
                    C = Integer.parseInt(st.nextToken());

                    groupPrice(D, C);
                    break;

                case 5:
                    D = Integer.parseInt(st.nextToken());
                    E = Integer.parseInt(st.nextToken());

                    groupPricePercent(D, E);
                    break;

                case 6:
                    printMoney();
                    break;
                case 7:
                    ifSellAll();
                    break;
            }
        }

        System.out.println(sb);
    }
}