import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.*;

public class project4main {
	public static void relabel(HashMap<Integer,ArrayList<Edge>> graph,int[] height,int vertex,HashSet<Integer> tr,int numOfVehicles) {
		int min = Integer.MAX_VALUE;
		for(int i=0; i<graph.get(vertex).size();i++) {
			if(graph.get(vertex).get(i).availableCapacity()>0) {
				min = Math.min(height[graph.get(vertex).get(i).getToVertex()], min);
			}}
		height[vertex] = min+1;
		if(2<=vertex&& vertex<numOfVehicles && height[vertex]>2) {
			tr.remove(vertex);
		}
		
	}
	public static boolean push(HashMap<Integer,ArrayList<Edge>> graph,int[] height,int vertex,int[]excessflow,Stack<Integer> queue) {

		for(Edge e: graph.get(vertex)) {
			if(height[e.getToVertex()]<height[vertex]&& e.availableCapacity()>0) {
				int flow = Math.min(excessflow[vertex], e.availableCapacity());
				e.addFlow(flow, graph);
				excessflow[vertex]-=flow;
				excessflow[e.getToVertex()]+=flow;
				if(e.getToVertex()>1) {
					queue.add(e.getToVertex());}
				if(excessflow[vertex]==0) {
					break;
				}


			}
		}
		if(excessflow[vertex]!=0) {
			queue.add(vertex);
		}
		return (excessflow[vertex]==0);
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		HashMap<Integer,ArrayList<Edge>> graph = new HashMap<Integer,ArrayList<Edge>>();
		graph.put(1, new ArrayList<Edge>());// 1 = sink
		graph.put(0, new ArrayList<Edge>());// 0 = source
		int numGreenTrains = in.nextInt();
		int index = 2;
		int startIndexGT;int endIndexGT;

		if(numGreenTrains!=0) {
			startIndexGT = 2;
			endIndexGT = numGreenTrains-1+2;
		}
		else {
			startIndexGT = -1;
			endIndexGT = -1;
		}

		for(int i=0; i<numGreenTrains;i++) {
			graph.put(i+index,new ArrayList<Edge>());
			Edge edge = new Edge(in.nextInt(),1,0);//1 = sink
			Edge revEdge = new Edge(0,i+index,0);
			graph.get(i+index).add(edge);
			graph.get(1).add(revEdge);
			edge.setRev(graph.get(1).size()-1);
			revEdge.setRev(graph.get(i+index).size()-1);
		}
		index += numGreenTrains;
		int numRedTrains = in.nextInt();
		int startIndexRT;int endIndexRT;
		if(numRedTrains!=0) {
			startIndexRT = index;
			endIndexRT = startIndexRT+numRedTrains-1;
		}
		else {
			startIndexRT = -1;
			endIndexRT = -1;
		}

		for(int i=0; i<numRedTrains;i++) {
			graph.put(i+index,new ArrayList<Edge>());
			Edge edge = new Edge(in.nextInt(),1,0);//1 = sink
			graph.get(i+index).add(edge);
			Edge revEdge = new Edge(0,i+index,0);
			graph.get(1).add(revEdge);
			edge.setRev(graph.get(1).size()-1);
			revEdge.setRev(graph.get(i+index).size()-1);
		}
		index += numRedTrains;
		int numGreenReindeers = in.nextInt();

		int startIndexGR; int endIndexGR;
		if(numGreenReindeers!=0) {
			startIndexGR = index;
			endIndexGR = startIndexGR+ numGreenReindeers-1;
		}
		else {
			startIndexGR = -1;
			endIndexGR = -1;
		}
		for(int i=0; i<numGreenReindeers;i++) {
			graph.put(i+index,new ArrayList<Edge>());
			Edge edge = new Edge(in.nextInt(),1,0);//1 = sink
			graph.get(i+index).add(edge);
			Edge revEdge = new Edge(0,i+index,0);
			graph.get(1).add(revEdge);
			edge.setRev(graph.get(1).size()-1);
			revEdge.setRev(graph.get(i+index).size()-1);
		}
		index += numGreenReindeers;
		int numRedReindeers = in.nextInt();

		int startIndexRR; int endIndexRR;
		if(numRedReindeers!=0) {
			startIndexRR = index;
			endIndexRR = startIndexRR + numRedReindeers-1;
		}
		else {
			startIndexRR = -1;
			endIndexRR = -1;
		}
		for(int i=0; i<numRedReindeers;i++) {
			graph.put(i+index,new ArrayList<Edge>());
			Edge edge = new Edge(in.nextInt(),1,0);//1 = sink
			graph.get(i+index).add(edge);
			Edge revEdge = new Edge(0,i+index,0);
			graph.get(1).add(revEdge);
			edge.setRev(graph.get(1).size()-1);
			revEdge.setRev(graph.get(i+index).size()-1);
		}
		index+= numRedReindeers;
		int totalGifts = 0;
		int numBags = in.nextInt();
		for(int i=0 ;i<numBags;i++) {
			String bag = in.next();
			int gifts = in.nextInt();
			totalGifts +=gifts;
			graph.put(i+index,new ArrayList<Edge>());
			Edge edge1 = new Edge(gifts,i+index,0);
			graph.get(0).add(edge1);
			Edge revEdge = new Edge(0,0,0);
			graph.get(i+index).add(revEdge);
			edge1.setRev(graph.get(i+index).size()-1);
			revEdge.setRev(graph.get(0).size()-1);
			if(bag.contains("a")) {
				if(bag.contains("b")) {
					if(bag.contains("d")) {
						//abd - weight 1 GT
						Edge.createEdges(graph, 1,startIndexGT, endIndexGT, i+index);
					}
					else if(bag.contains("e")) {
						///abe -weight 1 GR
						Edge.createEdges(graph, 1,startIndexGR, endIndexGR, i+index);
					}
					else {
						///ab - weight 1 G
						Edge.createEdges(graph, 1,startIndexGT, endIndexGT, i+index);
						Edge.createEdges(graph, 1,startIndexGR, endIndexGR, i+index);
					}
				}
				else if (bag.contains("c")) {
					if(bag.contains("d")) {
						///acd - weight 1 RT
						Edge.createEdges(graph, 1,startIndexRT, endIndexRT, i+index);
					}
					else if(bag.contains("e")) {
						///ace - weight 1 RR
						Edge.createEdges(graph, 1,startIndexRR, endIndexRR, i+index);
					}
					else {
						///ac - weight 1 R
						Edge.createEdges(graph, 1,startIndexRT, endIndexRT, i+index);
						Edge.createEdges(graph, 1,startIndexRR, endIndexRR, i+index);
					}
				}
				else if(bag.contains("d")) {
					///ad w1 T	
					Edge.createEdges(graph, 1,startIndexRT, endIndexRT, i+index);
					Edge.createEdges(graph, 1,startIndexGT, endIndexGT, i+index);
				}
				else if(bag.contains("e")) {
					///ae w1 Reindeer
					Edge.createEdges(graph, 1,startIndexRR, endIndexRR, i+index);
					Edge.createEdges(graph, 1,startIndexGR, endIndexGR, i+index);
				}
				else {
					///a - weight 1
					Edge.createEdges(graph, 1,startIndexRT, endIndexRT, i+index);
					Edge.createEdges(graph, 1,startIndexRR, endIndexRR, i+index);
					Edge.createEdges(graph, 1,startIndexGT, endIndexGT, i+index);
					Edge.createEdges(graph, 1,startIndexGR, endIndexGR, i+index);
				}
			}
			else {
				if(bag.contains("b")) {
					if(bag.contains("d")) {
						//bd -  GT
						Edge.createEdges(graph, gifts,startIndexGT, endIndexGT, i+index);
					}
					else if(bag.contains("e")) {
						///be - GR
						Edge.createEdges(graph, gifts,startIndexGR, endIndexGR, i+index);
					}
					else {
						///b - 1 G
						Edge.createEdges(graph, gifts,startIndexGT, endIndexGT, i+index);
						Edge.createEdges(graph, gifts,startIndexGR, endIndexGR, i+index);
					}
				}
				else if (bag.contains("c")) {
					if(bag.contains("d")) {
						///cd - RT
						Edge.createEdges(graph, gifts,startIndexRT, endIndexRT, i+index);
					}
					else if(bag.contains("e")) {
						///ce -  RR
						Edge.createEdges(graph, gifts,startIndexRR, endIndexRR, i+index);
					}
					else {
						///c -  R
						Edge.createEdges(graph, gifts,startIndexRT, endIndexRT, i+index);
						Edge.createEdges(graph, gifts,startIndexRR, endIndexRR, i+index);
					}
				}
				else if (bag.contains("d")){
					///d -T
					Edge.createEdges(graph, gifts,startIndexRT, endIndexRT, i+index);
					Edge.createEdges(graph, gifts,startIndexGT, endIndexGT, i+index);
				}
				else if(bag.contains("e")) {
					//e -reindeer
					Edge.createEdges(graph, gifts,startIndexRR, endIndexRR, i+index);
					Edge.createEdges(graph, gifts,startIndexGR, endIndexGR, i+index);
				}
			}

		}
		int numOfVehicles = graph.size()-2-numBags;
		int[] height = new int[graph.size()];
		int[] excessflow = new int[graph.size()];
		height[0] = graph.size();
		Stack<Integer> queue = new Stack<Integer>();
		for(Edge e : graph.get(0)) {
			excessflow[e.getToVertex()] = e.availableCapacity();
			e.addFlow(e.availableCapacity(), graph);
			queue.add(e.getToVertex());
			
		}
		HashSet<Integer> tr = new HashSet<Integer>();
		for(int i = 2;i<numOfVehicles;i++) {
			tr.add(i);
		}
		while(!queue.isEmpty()) {
			int vertex = queue.pop();
			if(tr.isEmpty()) {
				break;
			}
			if(!push(graph,height,vertex,excessflow,queue)) {
				relabel(graph,height,vertex,tr,numOfVehicles);
				
			}
		}

		int distributedGifts =0;
		for(Edge e: graph.get(1)) {
			if(e.getFlow()<0) {
				distributedGifts-= e.getFlow();
			}
		}
		out.print(totalGifts-distributedGifts);


	}


}
