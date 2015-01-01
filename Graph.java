import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.List;

public class Graph {
	
	private Vertex[] vertices;
	private int numVertices;
	private int maxNum;
	private int numEdges;
	//...
	
	public class Vertex{
		private String id;
		private int wordCount;
		public ArrayList<Edge> edges;
		private int vEdges;
		public double minDistance = Double.POSITIVE_INFINITY;
		private boolean encountered;
		private boolean done;
		private Vertex parent;
		private double cost;
		private int inDegree;
		private int outDegree;
		
		//Finish Constructor
		public Vertex(String id, int wordCount){
			this.id = id;
			this.wordCount = wordCount;
			edges = new ArrayList<Edge>();
		}
		public String getId(){
			return id;
		}
		public int getCount(){
			return wordCount;
		}
		public int getInDegree(){
			return inDegree;
		}
		public int getOutDegree(){
			return outDegree;
		}
		public int getNumEdges(){
			return vEdges;
		}
		public Edge getEdge(int index){
			return edges.get(index);
		}
	}
	
	public class Edge{
		public Vertex endVertex;
		private int endNode;
		private int startNode;
		private double cost;
		private Edge next;
		//Finish Constructor
		public Edge(){
			
		}
		public int getEndNode(){
			return endNode;
		}
		public Vertex getEndVertex(){
			return endVertex;
		}
	}
	
	public Graph(int maximum){
		if (maximum < 0) throw new IllegalArgumentException("Cannot have a negative number of vertices");
		vertices = new Vertex[maximum];
		numVertices = 0;
		maxNum = maximum;
	}
	
	//Not finished
	public int addNode(String id, int wordCount){
		//code to grow vertices if maxNum
		//is too small to have another node
		vertices[numVertices] = new Vertex(id, wordCount);
		
		//...
		numVertices++;
		return numVertices-1;
	}
	
	//Not finished
	public void addEdge(int i, int j, double cost){
		//add an edge from i to j
		Edge newEdge = new Edge();
		newEdge.startNode = i;
		newEdge.endNode = j;
		newEdge.cost = cost;
		newEdge.endVertex = vertices[j];
		vertices[i].edges.add(newEdge);
		vertices[i].outDegree++;
		vertices[j].inDegree++;
		vertices[i].vEdges++;
		numEdges++;
		
	}
	
	public Vertex getVertex(int i){
		return vertices[i];
	}
	
	public int getNodes(){
		return numVertices;
	}

	public int getEdges(){
		return numEdges;
	}
	
	public int getInDegree(String w){
		int inDegree = 0;
		for(int i = 0; i < vertices.length; i++){
			if(w.compareTo(vertices[i].getId())==0){
				inDegree = vertices[i].getInDegree();
			}
		}
		return inDegree;
	}
	
	public int getOutDegree(String w){
		int outDegree = 0;
		for(int i = 0; i < vertices.length; i++){
			if(w.compareTo(vertices[i].getId())==0){
				outDegree = vertices[i].getOutDegree();
			}
		}
		return outDegree;
	}
	
	public int getWordCount(String w){
		int wordCount = 0;
		for(int i = 0; i < vertices.length; i++){
			if(w.compareTo(vertices[i].getId())==0){
				wordCount = vertices[i].getCount();
			}
		}
		return wordCount;
	}
	public String getId(Vertex v){
		return v.getId();
	}
	

	public Vertex[] getVertices(){
		return vertices;
	}
	
	public boolean search(String w){
		boolean found = false;
		for (int i = 0; i < numVertices; i++){
			if(vertices[i].getId().compareTo(w) == 0){
				found = true;
			}
		}
		return found;
	}
	public int getIndex(String w){
		int index = -1;
		for (int i = 0; i < numVertices; i++){
			if(vertices[i].getId().compareTo(w) == 0){
				index = i;
			}
		}
		return index;
	}
	public int getEdgeCount(int i, int j){
		if(!search(getId(vertices[i])) || !search(getId(vertices[j]))){
			return 0;
		}
		int count = 0;
		for(int k = 0; k < vertices[i].edges.size(); k++){
			if(vertices[i].edges.get(k).getEndNode() == j){
				count = (int)vertices[i].edges.get(k).cost;
			}
		}
		return count;
	}
	
	public Vertex getVertex(String w){
		for(int i = 0; i < vertices.length; i ++){
			if(vertices[i].id.compareTo(w) == 0) return vertices[i];
		}
		return null;
	}
	
	public void compDijkstra(String s){
		
		s = s.toLowerCase().replaceAll("\\p{P}", "").replaceAll("\\s", "");
		PriorityQueue<Vertex> vertexQ = new PriorityQueue<Vertex>();
		getVertex(s).minDistance = 0.0;
		vertexQ.add(getVertex(s));
		while(!vertexQ.isEmpty()){
			Vertex x = vertexQ.poll();
			for(Edge tempEdge:getVertex(s).edges){
				Vertex y = tempEdge.endVertex;
				double weight = tempEdge.cost;
				double costToTemp = x.minDistance + weight;
				if(costToTemp < y.minDistance){
					vertexQ.remove(y);
					y.minDistance = costToTemp;
					y.parent = x;
					vertexQ.add(y);
				}
			}
		}
	}
	public String getShortest(Vertex endVertex){
		List<Vertex> path = new ArrayList<Vertex>();
		for(Vertex temp = endVertex; temp !=null; temp = temp.parent){
			path.add(temp);
		}
		Collections.reverse(path);
		String[] temp = new String[path.size()];
		for(int i = 0; i < path.size(); i++){
			temp[i] = path.get(i).getId();
		}
		String out = "";
		for (String s:temp) out = out.concat(s);
		return out;
	}
	
	public static void main(String[] args){
	}
}
