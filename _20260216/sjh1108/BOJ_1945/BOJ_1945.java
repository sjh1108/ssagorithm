package _20260216.sjh1108.BOJ_1945;

import java.io.*;
import java.util.*;

class Main {
    static class Point {
        int x, y;
        int type; 

        public Point(int x, int y, int type) {
            this.x = x;
            this.y = y;
            this.type = type;
        }
    }

    private static int N;

    private static ArrayList<Point> points = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // BOJ 1945 - 직사각형
        // 원점에서 그은 선분이 가장 많은 직사각형을 지나는 경우를 찾는 문제
        // 스위핑 알고리즘: 각 직사각형의 양 끝 각도를 이벤트로 처리
        String line = br.readLine();
        if (line == null) return;
        N = Integer.parseInt(line.trim());

        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());

            // 직사각형의 왼쪽 위(x1, y2): 기울기가 가장 큼 -> 선분이 진입하는 시점 (+1)
            points.add(new Point(x1, y2, 1));
            // 직사각형의 오른쪽 아래(x2, y1): 기울기가 가장 작음 -> 선분이 나가는 시점 (-1)
            points.add(new Point(x2, y1, -1));
        }

        // 기울기 기준 내림차순 정렬 (y축 -> x축 방향 스위핑)
        Collections.sort(points, new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                double gradA = (double) a.y / a.x;
                double gradB = (double) b.y / b.x;

                if (gradA == gradB) {
                    // 기울기가 같다면 시작점(+1)을 끝점(-1)보다 먼저 처리하여 겹침을 최대로 인정
                    // b.type(1) - a.type(-1) > 0 => b가 앞섬? (내림차순 정렬 기준이라 헷갈릴 수 있음)
                    // 여기서는 b.type(1) > a.type(-1) 이면 return 1 (양수) -> b가 a보다 '크다' -> 순서상 뒤?
                    // Comparator 로직: compare(a, b) > 0 이면 a가 b보다 뒤에 옴.
                    // 즉, b.type(1), a.type(-1) 일 때 1을 리턴하면 a(End)가 뒤로 감. -> Start가 먼저 옴. 맞음.
                    return Integer.compare(b.type, a.type);
                }
                
                return Double.compare(gradB, gradA);
            }
        });

        System.out.println(sweeping());
    }

    private static int sweeping() {
        int ret = 0, cnt = 0;
        for (Point p : points) {
            // 현재 시점의 최대 겹침 갱신
            // (cnt 증가 전에 갱신하는 이유는, 감소하는 이벤트가 발생하기 직전의 상태가 최대일 수 있기 때문.
            //  하지만 증가하는 이벤트의 경우 다음 루프에서 반영됨)
            ret = Math.max(ret, cnt);
            cnt += p.type;
        }
        // 마지막 구간 체크
        ret = Math.max(ret, cnt);
        return ret;
    }
}