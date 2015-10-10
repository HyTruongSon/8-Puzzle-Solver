// Software: 8 - Puzzle Solver
// Author: Hy Truong Son
// Major: BSc. Computer Science
// Class: 2013 - 2016
// Institution: Eotvos Lorand University
// Email: sonpascal93@gmail.com
// Website: http://people.inf.elte.hu/hytruongson/
// Copyright 2015 (c) Hy Truong Son. All rights reserved.

// +------------------------------+
// | A* Algorithm with Heuristics |
// +------------------------------+

public class Astar {
	
	static int INF = (int)(1e+9);
	
	static int N = 3;
	static int nheap, StartIndex, FinishIndex;
	static int d[], h[], trace[], heap[], pos[];
	static int Step[][][];
	static int Factorial[] = new int [N * N + 1];
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
	
	public static int Manhattan(int table[][], int PosX[], int PosY[]){
		int distance = 0;
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++)
				distance += Math.abs(i - PosX[table[i][j]]) + Math.abs(j - PosY[table[i][j]]);
		return distance;
	}
	
	public static boolean isBetter(int u, int v){
		if (d[heap[u]] + h[heap[u]] < d[heap[v]] + h[heap[v]]) return true;
		return false;
	}
	
	public static void swap(int u, int v){
		int temp = heap[u];
		heap[u] = heap[v];
		heap[v] = temp;
		
		pos[heap[u]] = u;
		pos[heap[v]] = v;
	}
	
	public static void upheap(int node){
		while (node > 0){
			int parent = node / 2;
			if (isBetter(heap[node], heap[parent])){
				swap(node, parent);
				node = parent;
			}else break;
		}
	}
	
	public static void downheap(int node){
		while (2 * node <= nheap){
			int child = 2 * node;
			if ((child < nheap) && (isBetter(child + 1, child))) child++;
			if (!isBetter(child, node)) break;
			swap(node, child);
			node = child;
		}
	}
	
	public static void pushheap(int index){
		nheap++;
		heap[nheap] = index;
		pos[index] = nheap;
		upheap(nheap);
	}
	
	public static int popheap(int node){
		int res = heap[node];
		heap[node] = heap[nheap];
		nheap--;
		upheap(node);
		downheap(node);
		return res;
	}
	
	public static void Dijkstra(int Start[][], int Finish[][]){
		int PosX[] = new int [N * N];
		int PosY[] = new int [N * N];
		int table[][] = new int [N][N];
		
		for (int i = 0; i < N; i++)
			for (int j = 0; j < N; j++){
				PosX[Finish[i][j]] = i;
				PosY[Finish[i][j]] = j;
			}
		
		for (int i = 1; i <= Factorial[N * N]; i++) d[i] = INF;
		nheap = 0;
		d[StartIndex] = 1;
		h[StartIndex] = Manhattan(Start, PosX, PosY);
		pushheap(StartIndex);
		
		while (nheap > 0){
			int index = popheap(1);
			if (index == FinishIndex) return;
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
					if (d[next] > d[index] + 1){
						d[next] = d[index] + 1;
						trace[next] = index;
						if (pos[next] == 0){
							h[next] = Manhattan(table, PosX, PosY);
							pushheap(next);
						}else
							upheap(pos[next]);
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
		
		StartIndex = PermutationIndex(Start);
		FinishIndex = PermutationIndex(Finish);
		
		if (StartIndex == FinishIndex){
			Step = new int [1][N][N];
			for (int i = 0; i < N; i++)
				for (int j = 0; j < N; j++)
					Step[0][i][j] = Start[i][j];
			return 1;
		}
		
		trace = new int [Factorial[N * N] + 1];
		d = new int [Factorial[N * N] + 1];
		h = new int [Factorial[N * N] + 1];
		heap = new int [Factorial[N * N] + 1];
		pos = new int [Factorial[N * N] + 1];
		
		Dijkstra(Start, Finish);
		
		if (d[FinishIndex] == INF) return -1;
		
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
	
	public static void main(String args[]){
	}
	
}
