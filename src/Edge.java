import java.util.*;
public class Edge {
	private int capacity;
	private int toVertex;
	private int flow;
	private int reverse;
	public Edge(int capacity,int toVertex,int flow) {
		this.capacity = capacity;
		this.toVertex = toVertex;
		this.flow = flow;
	}
	public static void createEdges(HashMap<Integer,ArrayList<Edge>> graph,int weight,int start,int end,int bagIndex) {
		if(start!=-1) {
			for(int j = start; j<=end;j++) {
				Edge edge2 = new Edge(weight,j,0);
				graph.get(bagIndex).add(edge2);
				Edge reverse = new Edge(0,bagIndex,0); 
				graph.get(j).add(reverse);
				edge2.setRev(graph.get(j).size()-1);
				reverse.setRev(graph.get(bagIndex).size()-1);
			}
		}
	}
	public int availableCapacity() {
		return capacity-flow;
	}
	public void addFlow(int flow,HashMap<Integer,ArrayList<Edge>> graph) {
		this.flow += flow;
		
		graph.get(this.toVertex).get(this.reverse).setFlow(graph.get(this.toVertex).get(this.reverse).getFlow()-flow);
	}
	
	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}
	/**
	 * @param capacity the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	/**
	 * @return the toVertex
	 */
	public int getToVertex() {
		return toVertex;
	}
	/**
	 * @param toVertex the toVertex to set
	 */
	public void setToVertex(int toVertex) {
		this.toVertex = toVertex;
	}
	/**
	 * @return the flow
	 */
	public int getFlow() {
		return flow;
	}
	/**
	 * @param flow the flow to set
	 */
	public void setFlow(int flow) {
		this.flow = flow;
	}
	/**
	 * @return the reverse
	 */
	public int getRev() {
		return reverse;
	}
	/**
	 * @param rev the rev to set
	 */
	public void setRev(int reverse) {
		this.reverse = reverse;
	}
	
}
