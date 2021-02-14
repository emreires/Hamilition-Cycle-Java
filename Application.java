import java.util.*;

public class Application {
	static int numV, numE, minVertex, lastVertex;
	static LinkedList<Integer> graph[];
	static ArrayList<Integer> check;
	static ArrayList<Edge> edges;
	static Scanner input;

	public static void main(String[] args) {
		Random rand = new Random();
		input = new Scanner(System.in);
		System.out.print("Enter the number of Vertices: ");
		numV = input.nextInt();
		boolean hamiltonian = false;

		while (numV > 50) {
			System.out.println("Too many Vertices, dont break me. \nTry again: ");
			numV = input.nextInt();
		}

		graph = new LinkedList[numV];
		check = new ArrayList<Integer>();

		for (int v = 0; v < numV; v++) {
			graph[v] = new LinkedList<>();
		} // end for

		edges = new ArrayList<Edge>();
		int rNum, rNum2;
		int maxEdges = ((numV - 1) * numV) / 2;
		int minEdges = numV - 1;
		numE = rand.nextInt(maxEdges - minEdges) + minEdges; 

		for (int i = 0; i < numE; i++) {
			do {
				
				rNum = rand.nextInt(numV);
				rNum2 = rand.nextInt(numV);

				while (rNum == rNum2) {
					rNum = rand.nextInt(numV);
				}
			} while (checkEdge(rNum, rNum2));

			edges.add(new Edge(rNum, rNum2));
			addEdge(rNum, rNum2);

		}
		int minDeg = smallestDegree();

		print();
		System.out.println();

		if (minDeg < 2) {
			System.out.println("Not a hamiltonian graph! The degree of vertex " + minVertex + " is less than 2!");
		} else {
			for (int i = 0; i < numV; i++) {
				boolean visited[] = new boolean[numV];
				check.clear();
				System.out.println("Iteration " + i + "\n");
				traverseGraph(i, visited);
				if (check.size() > numV) {
					System.out.println("Not a Hamiltonian graph! A Vertex was visited twice.");
				}
				for (Integer n : graph[lastVertex]) {
					if (n == i) {
						hamiltonian = true;
					}
				}
				if (hamiltonian) {
					System.out.println("HAMILTONIAN");
					break;
				}
			}
			if (!hamiltonian) {
				System.out.println("Not Hamiltonian");
			}
		}

	}

	static int smallestDegree() {
		int[] tempArray = countEdges();

		int minNum = 500;
		minVertex = -1;
		for (int i = 0; i <= tempArray.length - 1; i++) {
			if (minNum > tempArray[i]) {
				minNum = tempArray[i];
				minVertex = i;
			}
		}

		return minNum;
	}

	public static int[] countEdges() {


		int[] totalEdges = new int[numV];

		for (int v = 0; v < numV; v++) {

			int countEdges = 0;
			for (Integer n : graph[v]) {
				countEdges++;
			}

			totalEdges[v] = countEdges;
		}

		return totalEdges;
	}


	public static boolean checkEdge(int num1, int num2) {

		for (Edge e : edges) {
			if ((e.v1 == num1 && e.v2 == num2) || (e.v1 == num2 && e.v2 == num1)) {
				return true;
			}
		}
		return false;
	}

	public static void addEdge(int v1, int v2) {
		graph[v1].add(v2);
		graph[v2].add(v1);
	}

	public static void print() {
		for (int v = 0; v < numV; v++) {
			System.out.println("Vertex " + v);
			for (Integer n : graph[v]) {
				System.out.print(" -> " + n); 
			}
			System.out.println();
		}
	}


	static void traverseGraph(int v, boolean visit[]) {

		visit[v] = true;
		check.add(v);
		System.out.print(v + " -> ");

		Iterator<Integer> i = graph[v].listIterator();
		lastVertex = v;
		while (i.hasNext()) {
			int n = i.next();
			if (!visit[n])
				traverseGraph(n, visit);
		}
	}

}

class Edge {
	int v1, v2;

	public Edge(int v1, int v2) {
		this.v1 = v1;
		this.v2 = v2;
	}

	public int getV1() {
		return v1;
	}

	public int getV2() {
		return v2;
	}

	public String toString() {
		return v1 + " is connected to " + v2;
	}
