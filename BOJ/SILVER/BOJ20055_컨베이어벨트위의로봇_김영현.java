import java.io.*;
import java.util.*;

public class BOJ20055_컨베이어벨트위의로봇_김영현 {

	static BufferedReader br;
	static BufferedWriter bw;
	static StringTokenizer st;
	static int N, K, room[], ans;
	static boolean robots[];

	public static void main(String[] args) throws Exception {
		br = new BufferedReader(new InputStreamReader(System.in));
		bw = new BufferedWriter(new OutputStreamWriter(System.out));
		st = new StringTokenizer(br.readLine());
		N = stoi(st.nextToken());
		K = stoi(st.nextToken());
		room = new int[2 * N];
		robots = new boolean[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < 2 * N; i++) {
			room[i] = stoi(st.nextToken());
		}

		ans = 1;
		while (true) {
			// 1. 처음에는 벨트 회전.
			rotation();

			// 2. 로봇 이동.
			robot_rot();

			// 3. 올리는 위치에 로봇 올림.
			if (room[0] > 0) {
				room[0]--;
				robots[0] = true;
			}

			// 4. 내구도 확인.
			if (zeroCheck() >= K) {
				break;
			}
			ans++;
		}

		bw.append(String.valueOf(ans));
		bw.flush();
		br.close();
		bw.close();

	}

	private static void robot_rot() {
		robots[N - 1] = false; // 마지막 칸에서는 항상 내림.
		for (int i = N - 2; i >= 0; i--) {
			// 내구도가 1 이상이고, 로봇이 없는 경우에만.
			if (robots[i] && room[i + 1] > 0 && !robots[i + 1]) {
				room[i + 1]--;
				robots[i + 1] = true;
				robots[i] = false;
			}
		}

	}

	private static int zeroCheck() {
		int cnt = 0;
		for (int i = 0; i < 2 * N; i++) {
			if (room[i] == 0)
				cnt++;
		}
		return cnt;
	}

	private static void rotation() {
		// 벨트 회전.
		int tmp = room[2 * N - 1];
		for (int i = 2 * N - 1; i > 0; i--) {
			room[i] = room[i - 1];
		}
		room[0] = tmp;

		// 로봇도 같이 회전.
		for (int i = N - 1; i > 0; i--) {
			robots[i] = robots[i - 1];
		}
		robots[0] = false;
	}

	static int stoi(String s) {
		return Integer.parseInt(s);
	}
}