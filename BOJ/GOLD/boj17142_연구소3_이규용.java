package march;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class boj17142_������ {
	// ���̷���. Ȱ��/��Ȱ��
	// ó���� ��Ȱ��. Ȱ�� �����¿�� 1�ʸ��� ����
	// M���� ���̷��� Ȱ���� �� ��� ���� ���̷��� ������ �ּ� �ð�
	// ���� �ִ� �����δ� �� ����

	// ���̷��� �߿� M���� ����(����)
	// �� ���տ��� dfs ����

	private static int[][] map, virus, active;
	private static int N, M, v, time = 123456789;
	
	static int[][] copy(){
		int[][] cp = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				cp[i][j] = map[i][j];
			}
		}  //deep copy of map
		return cp;
	}
	
	static boolean inRange(int r, int c) {
		return r >= 0 && r < N && c >= 0 && c < N;
	}
	
	static int spreadVirus() {
		Queue<int[]> q = new LinkedList<>();
		int[][] d = {{-1,0}, {0,-1}, {0,1}, {1,0}};
		int[][] spread = copy();
		boolean[][] visit = new boolean[N][N];
		int t = 0;
		
		for (int i = 0; i < M; i++) {
			q.offer(active[i]);
			visit[active[i][0]][active[i][1]] = true;
		}  //stack�� �ʱⰪ �ֱ�
		
		while(!q.isEmpty()) {
			int size = q.size();
			for (int s = 0; s < size; s++) {
				int[] cur = q.poll();
				for (int i = 0; i < d.length; i++) {
					int nr = cur[0] + d[i][0];
					int nc = cur[1] + d[i][1];
					
					if(inRange(nr, nc) && !visit[nr][nc]) {  //if not visited
						visit[nr][nc] = true;
						if(spread[nr][nc] == 0) {  //if there is no virus
							q.offer(new int[] {nr,nc});
							spread[nr][nc] = 2;  //spread virus
						}
					}
				}  //4-way depth-first-search
				
			}
			
			t++;
		}
		
		return t;
	}
	static void comb(int cnt, int cur) {
		if (cnt == M) {
			// ���� ���� �Ϸ�
			int t = spreadVirus();
			time = time > t ? t: time;
			
		} else {
			for (int i = cur; i < v; i++) {
				active[cnt] = virus[i];
				comb(cnt+1, i+1);
			}
		}
	}

	public static void main(String[] args) throws Exception, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String in[] = br.readLine().split(" ");
		N = Integer.parseInt(in[0]);
		M = Integer.parseInt(in[1]);
		
		map = new int[N][N];
		virus = new int[10][2];
		v = 0;
		active = new int[M][2];
		
		for (int i = 0; i < N; i++) {
			in = br.readLine().split(" ");
			for (int j = 0; j < N; j++) {
				map[i][j] = Integer.parseInt(in[j]);
				if(map[i][j] == 2) {
					virus[v][0] = i;
					virus[v][1] = j;
					v++;
				}
			}
		}
		
		comb(0, 0);
		System.out.println(time);
	}

}
