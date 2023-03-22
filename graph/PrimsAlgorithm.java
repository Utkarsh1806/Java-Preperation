import java.util.*;

class Pair {
	int node, distance;
	public Pair(int node, int distance) {
		this.node = node;
		this.distance = distance;
	}
}

class PrimsAlgorithm {

	private static List<List<Pair>> buildGraph(int n, int[][] edges) {
		List<List<Pair>> graph = new ArrayList<>();
		for(int i = 0; i <= n; i++) {
			graph.add(new ArrayList<>());
		}
		for(int[] edge : edges) {
			graph.get(edge[0]).add(new Pair(edge[1], edge[2]));
			graph.get(edge[1]).add(new Pair(edge[0], edge[2]));
		}
		return graph;
	}

	private static int findMST(int n, int[][] edges) {
		List<List<Pair>> graph = PrimsAlgorithm.buildGraph(n, edges);
		PriorityQueue<Pair> minHeap = new PriorityQueue<>((p1, p2) -> (p1.distance - p2.distance));
		
		boolean[] mstSet = new boolean[n + 1];
		int[] distance = new int[n + 1];
		Arrays.fill(distance, Integer.MAX_VALUE);
		int[] parent = new int[n + 1];

		distance[1] = 0;
		parent[1] = -1;
		minHeap.offer(new Pair(1, distance[1]));

		while(!minHeap.isEmpty()) {
			Pair pair = minHeap.poll();
			int currNode = pair.node;

			if(mstSet[currNode]) continue;

			mstSet[currNode] = true;

			for(Pair nbrPair : graph.get(currNode)) {
				int nbrNode = nbrPair.node;
				int nbrDistance = nbrPair.distance;
				
				if(!mstSet[nbrNode] && nbrDistance < distance[nbrNode]) {
					distance[nbrNode] = nbrDistance;
					parent[nbrNode] = currNode;
					minHeap.offer(nbrPair);
				}
			}
		}
		int minCost = 0;
		for(int i = 1; i <= n; i++) {
			minCost += distance[i];
		}
		return minCost ;
	}

	public static void main(String[] args) {
		int[][] edges = new int[][]{{1, 2, 1}, {1, 3, 6}, {2, 4, 3}, {2, 6, 5}, {3, 5, 2}, {3, 6, 8}, {4, 8, 7}, {5, 6, 3}, {5, 7, 2}, {7, 8, 9}, {7, 9, 4}, {8, 9, 3}};
		int nodes = 9;
		int cost = PrimsAlgorithm.findMST(nodes, edges);
		System.out.println(cost);
	}
}