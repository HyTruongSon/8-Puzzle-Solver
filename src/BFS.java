// Software: 8 - Puzzle Solver
// Author: Hy Truong Son
// Major: BSc. Computer Science
// Class: 2013 - 2016
// Institution: Eotvos Lorand University
// Email: sonpascal93@gmail.com
// Website: http://people.inf.elte.hu/hytruongson/
// Copyright 2015 (c) Hy Truong Son. All rights reserved.

// +--------------------------------------+
// | Breadth First Search (BFS) Algorithm |
// +--------------------------------------+

public class BFS {
	
	static int N = 3;
	static int Step[][][];
	static int queue[];
	static int trace[];
	static int d[];
	static int Factorial[] = new int [N * N + 1];
	static int StartIndex, FinishIndex, rear, front;
	static int DX[] = {-1, 1, 0, 0};
	static int DY[] = {0, 0, -1, 1};
	
	public static void getStep(int i, int table[][]){
		for (int x = 0; x < N; x++)
			for (int y = 0; y < N; y++)
				table[x][y] = Step[i][x][y];
	}
	
	public static int PermutationIndex(int table[][]){
		boolean used[] = new boolean [N * N];
		for (int i = 0; i < N * N; i++) used[i] = false;
		
		int index = 0;
		int count = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++){
				count++;
				int k = 0;
				for (int v = 0; v < table[i][j]; v++)
					if (!used[v]) k++;
				index += k * Factorial[N * N - count];
				used[table[i][j]] = true;
			}
			
		return index + 1;
	}
	
	public static void getPermutation(int index, int table[][]){
		boolean used[] = new boolean [N * N];
		for (int i = 0; i < N * N; i++) used[i] = false;
		
		int count = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++){
				count++;
				int k = 0;
				for (int v = 0; v < N * N; v++)
					if (!used[v]){
						k++;
						if (k * Factorial[N * N - count] >= index){
							table[i][j] = v;
							used[v] = true;
							index -= (k - 1) * Factorial[N * N - count];
							break;
						}
					}
			}
	}
	
	public static void PushQueue(int index){
		front++;
		queue[front] = index;
	}
	
	public static int PopQueue(){
		rear++;
		return queue[rear];
	}
	
	public static void Search(){
		int table[][] = new int [N][N];
		for (int i = 1; i <= Factorial[N * N]; i++) d[i] = -1;
		
		rear = 0;
		front = 0;
		d[StartIndex] = 1;
		PushQueue(StartIndex);
		
		if (StartIndex == FinishIndex) return;
		
		while (rear != front){
			int index = PopQueue();
			getPermutation(index, table);
			
			int x = 0;
			int y = 0;
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					if (table[i][j] == 0){
						x = i;
						y = j;
						break;
					}
			
			for (int t = 0; t < 4; t++){
				int i = x + DX[t];
				int j = y + DY[t];
				if ((i >= 0) && (i < N) && (j >= 0) && (j < N)){
					int temp = table[x][y];
					table[x][y] = table[i][j];
					table[i][j] = temp;
					
					int next = PermutationIndex(table);
					if (d[next] == -1){
						d[next] = d[index] + 1;
						trace[next] = index;
						PushQueue(next);
						if (next == FinishIndex) return;
					}
					
					temp = table[x][y];
					table[x][y] = table[i][j];
					table[i][j] = temp;
				}
			}
		}
	}
	
	public static int Search(int Start[][], int Finish[][]){
		Factorial[0] = 1;
		for (int i = 1; i <= N * N; i++) Factorial[i] = Factorial[i - 1] * i;
		
		queue = new int [Factorial[N * N] + 1];
		trace = new int [Factorial[N * N] + 1];
		d = new int [Factorial[N * N] + 1];
		
		StartIndex = PermutationIndex(Start);
		FinishIndex = PermutationIndex(Finish);
		
		Search();
		
		if (d[FinishIndex] == -1) return -1;
		
		int nSteps = d[FinishIndex];
		Step = new int[nSteps][N][N];
		int index = FinishIndex;
		
		while (nSteps > 0){
			getPermutation(index, Step[nSteps - 1]);
			nSteps--;
			index = trace[index];
		}
		
		return d[FinishIndex];
	}
	
}
