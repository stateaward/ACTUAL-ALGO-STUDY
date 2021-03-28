package march;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class boj16236_아기상어 {
	// 물고기 M마리와 상어 1마리
	// 아기 상어의 크기는 2로 시작
	// 아기 상어는 1초에 상하좌우로 이동
	// 아기 상어 > 물고기 : 잡아먹음
	// 아기 상어 = 물고기 : 지나감
	// 아기 상어 < 물고기 : 못 지나감

	static int[] dr = { -1, 0, 0, 1 }, dc = { 0, -1, 1, 0 };
	static int sr, sc; // 아기 상어의 위치
	static int fish, shark, ate; // 물고기의 수, 상어의 크기, 먹은 물고기의 수
	static int[][] map;

	static int haunt() {
//		더 이상 먹을 수 있는 물고기가 공간에 없다면 아기 상어는 엄마 상어에게 도움을 요청한다.
//		먹을 수 있는 물고기가 1마리라면, 그 물고기를 먹으러 간다.
//		먹을 수 있는 물고기가 1마리보다 많다면, 거리가 가장 가까운 물고기를 먹으러 간다.
//		거리는 아기 상어가 있는 칸에서 물고기가 있는 칸으로 이동할 때, 지나야하는 칸의 개수의 최솟값이다.
//		거리가 가까운 물고기가 많다면, 가장 위에 있는 물고기, 그러한 물고기가 여러마리라면, 가장 왼쪽에 있는 물고기를 먹는다.

		int N = map.length;

		// bfs
		boolean[][] visit = new boolean[N][N];
		Queue<int[]> q = new LinkedList<>();
		q.add(new int[] { sr, sc });
		visit[sr][sc] = true;

		int time = 0;
		ArrayList<int[]> arr = new ArrayList<>();

		while (!q.isEmpty()) {
			time++;
			int size = q.size();
			for (int s = 0; s < size; s++) {
				int[] cur = q.poll();
				for (int d = 0; d < 4; d++) {
					int nr = cur[0] + dr[d];
					int nc = cur[1] + dc[d];

					if (nr >= 0 && nr < N && nc >= 0 && nc < N && !visit[nr][nc]) { // 범위 안에 있고 방문한 적 없음
						visit[nr][nc] = true; // 방문 처리
						if (map[nr][nc] == shark || map[nr][nc] == 0) { // 같은 크기. 지나감
							q.add(new int[] { nr, nc });
						} else if (map[nr][nc] < shark) { // 상어가 더 큼
							arr.add(new int[] { nr, nc });
						}
					}
				}

			}

			// 같은 거리의 물고기들 중 가장 위 -> 왼쪽에 있는 물고기 찾기
			if (arr.isEmpty())
				continue;
			arr.sort(new Comparator<int[]>() {
				@Override
				public int compare(int[] o1, int[] o2) {
					return o1[0] - o2[0];
				}
			});

			int nr = arr.get(0)[0];
			int nc = arr.get(0)[1];

			System.out.println(shark + ": " +nr + " " + nc);
			map[nr][nc] = 0;
			fish--;

			ate++; // 상어 커짐
			if (ate == shark) {
				ate = 0;
				shark++;
			}
			sr = nr; // 상어 이동
			sc = nc;
			return time;

		}

		return 0;
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		map = new int[N][N];

		for (int i = 0; i < N; i++) {
			String line[] = br.readLine().split(" ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(line[j]);
				if (map[i][j] == 9) { // 아기 상어의 위치 발견
					sr = i;
					sc = j;
					map[i][j] = 0;
					shark = 2;
				} else if (map[i][j] > 0) {
					fish++;
				}
			}
		}

		int ans = 0;
		while (fish > 0) {
			int time = haunt();
			if (time == 0)
				break;
			ans += time;
		}

		System.out.println(ans);
	}
}
