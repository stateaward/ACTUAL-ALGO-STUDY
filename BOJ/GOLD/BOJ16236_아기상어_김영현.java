import java.io.*;
import java.util.*;

public class BOJ16236_아기상어_김영현 {

	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	static int N, room[][], ans, sy, sx, level, eat;
	static int dy[] = { -1, 0, 1, 0 };
	static int dx[] = { 0, -1, 0, 1 };
	static boolean visited[][];

	static class Node implements Comparable<Node> {
		int y, x;

		public Node(int y, int x) {
			this.y = y;
			this.x = x;
		}

		@Override
		public int compareTo(Node o) {
			if (this.y == o.y)
				return this.x - o.x;
			return this.y - o.y;
		}

	}

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		N = stoi(br.readLine());
		room = new int[N][N];
		level = 2; // 현재 물고기 크기
		eat = 2; // 커지기 전까지 남은 물고기 개수
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				room[i][j] = stoi(st.nextToken());
				if (room[i][j] == 9) {
					sy = i;
					sx = j;
					room[i][j] = 0; // 요거 때문에 30분 씀.
				}
			}
		}
		while (true) {
			if (!search())
				break;
		}
		bw.append(String.valueOf(ans));
		bw.flush();
		br.close();
		bw.close();

	}

	private static boolean search() {
		visited = new boolean[N][N];
		Queue<Node> q = new LinkedList<>();
		q.add(new Node(sy, sx));
		visited[sy][sx] = true;
		int time = 1;
		while (!q.isEmpty()) {
			int size = q.size();
			List<Node> tmpList = new ArrayList<>();
			for (int i = 0; i < size; i++) {
				Node node = q.poll();
				int y = node.y;
				int x = node.x;
				for (int j = 0; j < 4; j++) {
					int ny = y + dy[j];
					int nx = x + dx[j];
					if (ny < 0 || nx < 0 || ny > N - 1 || nx > N - 1 || visited[ny][nx] || room[ny][nx] > level)
						continue;
					visited[ny][nx] = true;
					if (room[ny][nx] < level && room[ny][nx] != 0) {
						// 여기서 한 번에 처리가 아니라 정답이 될 수 있는 녀석들을 넣어둠.
						tmpList.add(new Node(ny, nx));
					}
					q.add(new Node(ny, nx));
				}
			}
			if (tmpList.size() > 0) {
				Collections.sort(tmpList);
				Node node = tmpList.get(0);
				int ny = node.y;
				int nx = node.x;
				room[ny][nx] = 0;
				if (--eat == 0) {
					level++;
					eat = level;
				}
				ans += time;
				sy = ny;
				sx = nx;
				return true;
			}
			time++;
		}
		// 물고기를 못 먹었으면 끝.
		return false;
	}

	static int stoi(String s) {
		return Integer.parseInt(s);
	}
}