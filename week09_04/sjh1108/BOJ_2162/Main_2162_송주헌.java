package week09_04.sjh1108.BOJ_2162;
import java.io.*;
import java.util.*;

class Main {
    static class Point{
        double x1, y1;
        double x2, y2;

        public Point(double x1, double y1, double x2, double y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }
    }
    private static final int INF = 5000;

    private static int N;

    private static int[] parent;

	public static int find(int x) {
		if (x == parent[x]) return x;
		return parent[x] = find(parent[x]);
	}

	public static void union(int x, int y) {
		x = find(x);
		y = find(y);

		if (x != y) {
			if (x < y)  parent[y] = x;
			else        parent[x] = y;
		}
	}

    // 교점의 X좌표
    private static double intersectionPointX(Point p1, Point p2){
        // p1과 p2로 이루어지는 직선이 평행 또는 일치할 경우
		// 교점 식의 분모에 해당하는 부분이 0이라는 의미
		if ((p1.x1 - p1.x2) * (p2.y1 - p2.y2) - (p1.y1 - p1.y2) * (p2.x1 - p2.x2) == 0) {
			return INF + 1;
		}

		double denominator = ((p1.x1 * p1.y2 - p1.y1 * p1.x2) * (p2.x1 - p2.x2))
				- ((p1.x1 - p1.x2) * (p2.x1 * p2.y2 - p2.y1 * p2.x2));
		double numerator = ((p1.x1 - p1.x2) * (p2.y1 - p2.y2)) - ((p1.y1 - p1.y2) * (p2.x1 - p2.x2));

		return denominator / numerator;
    }

    // 교점의 Y좌표
	public static double intersectionPointY(Point l1, Point l2) {
        // p1과 p2로 이루어지는 직선이 평행 또는 일치할 경우
		// 교점 식의 분모에 해당하는 부분이 0이라는 의미
		if ((l1.x1 - l1.x2) * (l2.y1 - l2.y2) - (l1.y1 - l1.y2) * (l2.x1 - l2.x2) == 0) {
			return INF + 1;
		}

		double denominator = ((l1.x1 * l1.y2 - l1.y1 * l1.x2) * (l2.y1 - l2.y2)
				- (l1.y1 - l1.y2) * (l2.x1 * l2.y2 - l2.y1 * l2.x2));
		double numerator = ((l1.x1 - l1.x2) * (l2.y1 - l2.y2) - (l1.y1 - l1.y2) * (l2.x1 - l2.x2));

		return denominator / numerator;
	}

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());

        parent = new int[N];
        Point[] points = new Point[N];

        StringTokenizer st;
        for(int i = 0; i < N; i++){
            parent[i] = i;

            st = new StringTokenizer(br.readLine());
            double x1 = Double.parseDouble(st.nextToken());
            double y1 = Double.parseDouble(st.nextToken());
            double x2 = Double.parseDouble(st.nextToken());
            double y2 = Double.parseDouble(st.nextToken());

            points[i] = new Point(x1, y1, x2, y2);
        }

        for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){
				Point p1 = points[i];
                Point p2 = points[j];

                double res = intersectionPointX(p1, p2);

				// p1으로 이루어진 직선과 p2로 이루어진 직선이 평행 또는 일치할 경우
				if (res == INF + 1) {
					// x = a 형태인 경우
					if (p1.x2 - p1.x1 == 0 && p2.x2 - p2.x1 == 0) {
						// 두 직선이 평행
						if (p1.x1 != p2.x1) {
							continue;
						}

						// p1 선분과 p2 선분이 만나지 않음.
						if ((p1.y1 > p2.y1 && p1.y1 > p2.y2 && p1.y2 > p2.y1 && p1.y2 > p2.y2)
								|| (p2.y1 > p1.y1 && p2.y1 > p1.y2 && p2.y2 > p1.y1 && p2.y2 > p1.y2)) {
							continue;
						}

						union(i, j);
						continue;
					}

					// x = a 형태가 아닌 경우
					double m1 = (p1.y2 - p1.y1) / (p1.x2 - p1.x1);
					double m2 = (p2.y2 - p2.y1) / (p2.x2 - p2.x1);

					double n1 = -m1 * p1.x1 + p1.y1;
					double n2 = -m2 * p2.x1 + p2.y1;

					// 두 직선이 평행
					if (n1 != n2) {
						continue;
					}

					// 두 직선이 일치하지만, 각각의 선분이 만나지는 않는 경우
					if ((p1.x1 > p2.x1 && p1.x1 > p2.x2 && p1.x2 > p2.x1 && p1.x2 > p2.x2)
							|| (p2.x1 > p1.x2 && p2.x1 > p1.x2 && p2.x2 > p1.x1 && p2.x2 > p1.x2)) {
						continue;
					}

					// y = a인 형태를 고려해야 함.
					if ((p1.y1 > p2.y1 && p1.y1 > p2.y2 && p1.y2 > p2.y1 && p1.y2 > p2.y2)
							|| (p2.y1 > p1.y1 && p2.y1 > p1.y2 && p2.y2 > p1.y1 && p2.y2 > p1.y2)) {
						continue;
					}

					union(i, j);
				}
				else {
					// 교점이 p1 선분과 p2 선분 내의 존재해야 함.
					if (res >= Math.min(p1.x1, p1.x2) && res <= Math.max(p1.x1, p1.x2) && res >= Math.min(p2.x1, p2.x2)
							&& res <= Math.max(p2.x1, p2.x2)) {
						res = intersectionPointY(p1, p2);

						if (res <= INF && res >= -INF) {
							if (res >= Math.min(p1.y1, p1.y2) && res <= Math.max(p1.y1, p1.y2)
									&& res >= Math.min(p2.y1, p2.y2) && res <= Math.max(p2.y1, p2.y2)) {
								union(i, j);
							}
						}
					}
				}
            }
        }

        // parent의 0 ~ N까지 본인의 루트 노드에 해당하는
        // 값이 몇 개인지 계산함.
        int[] cnt = new int[N];
        for (int i = 0; i < N; i++) {
            cnt[find(i)]++;
        }

        int groupCnt = 0;
        int lineCnt = 0;

        for (int i = 0; i < N; i++) {
            // cnt가 0이 아니면 다른 노트에 대하여 합집합 연산을
            // 최소 1번이상 했다는 의미임.
            if (cnt[i] > 0) {
                groupCnt++;
            }

            lineCnt = Math.max(lineCnt, cnt[i]);
        }
        
        System.out.println(groupCnt + "\n" + lineCnt);
    }
}