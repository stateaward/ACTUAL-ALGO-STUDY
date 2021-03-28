package march;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;

public class boj16236_�Ʊ��� {
	// ����� M������ ��� 1����
	// �Ʊ� ����� ũ��� 2�� ����
	// �Ʊ� ���� 1�ʿ� �����¿�� �̵�
	// �Ʊ� ��� > ����� : ��Ƹ���
	// �Ʊ� ��� = ����� : ������
	// �Ʊ� ��� < ����� : �� ������

	static int[] dr = { -1, 0, 0, 1 }, dc = { 0, -1, 1, 0 };
	static int sr, sc; // �Ʊ� ����� ��ġ
	static int fish, shark, ate; // ������� ��, ����� ũ��, ���� ������� ��
	static int[][] map;

	static int haunt() {
//		�� �̻� ���� �� �ִ� ����Ⱑ ������ ���ٸ� �Ʊ� ���� ���� ���� ������ ��û�Ѵ�.
//		���� �� �ִ� ����Ⱑ 1�������, �� ����⸦ ������ ����.
//		���� �� �ִ� ����Ⱑ 1�������� ���ٸ�, �Ÿ��� ���� ����� ����⸦ ������ ����.
//		�Ÿ��� �Ʊ� �� �ִ� ĭ���� ����Ⱑ �ִ� ĭ���� �̵��� ��, �������ϴ� ĭ�� ������ �ּڰ��̴�.
//		�Ÿ��� ����� ����Ⱑ ���ٸ�, ���� ���� �ִ� �����, �׷��� ����Ⱑ �����������, ���� ���ʿ� �ִ� ����⸦ �Դ´�.

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

					if (nr >= 0 && nr < N && nc >= 0 && nc < N && !visit[nr][nc]) { // ���� �ȿ� �ְ� �湮�� �� ����
						visit[nr][nc] = true; // �湮 ó��
						if (map[nr][nc] == shark || map[nr][nc] == 0) { // ���� ũ��. ������
							q.add(new int[] { nr, nc });
						} else if (map[nr][nc] < shark) { // �� �� ŭ
							arr.add(new int[] { nr, nc });
						}
					}
				}

			}

			// ���� �Ÿ��� ������ �� ���� �� -> ���ʿ� �ִ� ����� ã��
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

			ate++; // ��� Ŀ��
			if (ate == shark) {
				ate = 0;
				shark++;
			}
			sr = nr; // ��� �̵�
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
				if (map[i][j] == 9) { // �Ʊ� ����� ��ġ �߰�
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
