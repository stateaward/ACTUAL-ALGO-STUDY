package baekjoon;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/* 풀이 생각
 * 1. 바이러스(2)인 수를 전부 virusList에 넣기
 * 2. nCm 의 형식으로 모든 경우에 대한 시간 계산
 * 3. m번 반복하며 BFS로 바이러스 확산 처리
 */

class Virus {
	int x;
	int y;
	int level;
	
	public Virus(int x, int y, int level) {
		this.x = x;
		this.y = y;
		this.level = level;
	}
}

public class BOJ17142_연구소3 {
	
	static int N, M;
	static ArrayList<Virus> virusList = new ArrayList<>();
	static int[] picked;
	static int[][] map;
	static boolean[][] isVisit;
	
	static int[] dx = {-1,1,0,0};
	static int[] dy = {0,0,-1,1};
	
	static int min = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws Exception {
		
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String[] input = br.readLine().split(" ");
		N = Integer.parseInt(input[0]); // 연구소 크기
		M = Integer.parseInt(input[1]); // 바이러스 개수
		
		map = new int[N][N];
		
		// ==============
		//  * 데이터 입력
		// ==============
		for (int i = 0; i < N; i++) {
			input = br.readLine().split(" ");
			for (int j = 0; j < N; j++) {
				int num = Integer.parseInt(input[j]);
				
				if(num == 2) {
					virusList.add(new Virus(i,j,1));
					map[i][j] = -1;		// 바이러스 = -1
				}else if(num == 1) {
					map[i][j] = -8;		// 벽 = -8
				}
			}
		}
		
		picked = new int[M];
		
		combination(0, 0);
		
		if(min == Integer.MAX_VALUE) {
			System.out.println(-1);
			return;
		}
		
		System.out.println(min);
		
	}

	// cnt(현재까지 뽑은 수), cur(현재 가르키는 번호)
	private static void combination(int cnt, int cur) {
		if(cnt == M) {
//			System.out.println(Arrays.toString(picked));
			BFS();
			return;
		}
		
		for (int i = cur; i < virusList.size(); i++) {
			picked[cnt] = i;
			combination(cnt+1, i+1);
		}
	}

	private static void BFS() {
		Queue<Virus> q = new LinkedList<>();
		isVisit = new boolean[N][N];
		
		for (int i = 0; i < M; i++) {
			q.add(virusList.get(picked[i]));
			isVisit[virusList.get(picked[i]).x][virusList.get(picked[i]).y] = true;
		}
		
		int[][] copyMap = new int[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				copyMap[i][j] = map[i][j];
			}
		}
		
		int lev = 0;
		
		while(!q.isEmpty()) {
			Virus v = q.poll();
			
			for (int k = 0; k < 4; k++) {
				int nx = v.x + dx[k];
				int ny = v.y + dy[k];
				lev = v.level;
				
				// 범위 밖 아웃
				if(!(0 <= nx && nx < N && 0 <= ny && ny < N)) continue;
				
				if(map[nx][ny] == 0 && !isVisit[nx][ny]) {
					copyMap[nx][ny] = v.level;
					isVisit[nx][ny] = true;
					q.add(new Virus(nx,ny,v.level+1));
				}
			}
		};
		
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				if(copyMap[i][j] == 0) {
					return;
				}
			}
		}
		
		min = Math.min(min, lev-1);
	}

}
