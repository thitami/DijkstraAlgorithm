import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Path implements IPath, IEdge
{

 private final List<IEdge> edges;
 private Set<INode> markedNodes;
 private Set<INode> nonMarkedNodes;
 private Map<INode, INode> predecessors;
 private Map<INode, Integer> distance;
   
 // Class constructor
 public Path(IGraph graph)
 {
	  // contains all the nodes of a Path
	  List<INode> nodes = new ArrayList<INode>();
	  
	  // contains all the edges of the path
	  List<IEdge> edges = new ArrayList<IEdge>();
	  
	  //loop through the nodes of the graph and add these and the the edges into proper Lists
	  for (INode node : graph.getNodes()) 
	  {
		   nodes.add(node);
		   edges.addAll(node.getEdges());
      }

	  this.edges = edges;
 }
 
 // Initialize the  Path 
 public void init(INode source)
 {
	  markedNodes = new HashSet<INode>();
	  nonMarkedNodes = new HashSet<INode>();
	  distance = new HashMap<INode, Integer>();
	  predecessors = new HashMap<INode, INode>();
	  distance.put(source, 0);
	  nonMarkedNodes.add(source);
	  while (nonMarkedNodes.size() > 0)
	  {
		   INode node = getMinimum(nonMarkedNodes);
		   markedNodes.add(node);
		   nonMarkedNodes.remove(node);
		   findMinimalDistances(node);
	  }
 }

 // Find the minimal distances between the current and Neighbor nodes
 private void findMinimalDistances(INode node)
 {
	  List<INode> adjacentNodes = getNeighbors(node);
	  for (INode target : adjacentNodes)
	  {
		   if (getShortestDistance(target) > getShortestDistance(node)
		        + getDistance(node, target))
		   {
			    distance.put(target, getShortestDistance(node) + getDistance(node, target));
			    predecessors.put(target, node);
			    nonMarkedNodes.add(target);
		   }
	  }
 }

 // get a distance of two given nodes
 private int getDistance(INode node, INode target) 
 {
	  for (IEdge edge : edges)
	  {
		   if (edge.getOriginNode().getName().equals(node.getName())
		        && edge.getTargetNode().getName().equals(target)) {
		    return edge.getWeight();
	   }
  }
   return 0;
 }

 // get a list of the neighbors
 private List<INode> getNeighbors(INode node)
 {
	  List<INode> neighbors = new ArrayList<INode>();
	  for (IEdge edge : edges)
	  {
		   if (edge.getOriginNode().getName().equals(node.getName())
		     && !isMarked(edge.getTargetNode())) {
		    neighbors.add(edge.getTargetNode());
	   }
  }
  return neighbors;
 }

 private INode getMinimum(Set<INode> nodes)
 {
	  INode minimum = null;
	  for (INode node : nodes) 
	  {
		   if (minimum == null)
		   {
			   minimum = node;
		   } 
		   else
		   {
			    if (getShortestDistance(node) < getShortestDistance(minimum))
			    {
			    	minimum = node;
			    } //end if 
		   } // end else
	  }
	  return minimum;
}

 // check if a given node is marked
 private boolean isMarked(INode node) 
 {
	 return markedNodes.contains(node);
 }

 // get the shortest distance given a node
 private int getShortestDistance(INode destination)
 {
	  Integer d = distance.get(destination);
	  if (d == null)
	  {
		  return Integer.MAX_VALUE;
	  } else
	  {
		  return d;
	  }
 }

// gets a list of previously marked nodes
 public List<INode> getPath() 
 {
	  LinkedList<INode> path = new LinkedList<INode>();
	  INode step = getTargetNode();
	  // check if a path exists
	  if (predecessors.get(step) == null) 
	  {
		  return null;
	  }
	  path.add(step);
	  while (predecessors.get(step) != null)
	  {
		   step = predecessors.get(step);
		   path.add(step);
	  }
	  // Put it into the correct order
	  Collections.reverse(path);
	  return path;
 }

 // gets total weight of a distance
 public int getTotalWeight()
 {
	  int total = 0;
	  for (Entry<INode, Integer> entry : distance.entrySet()) {
	   total += entry.getValue(); 
 }

	  return total;
 }

  // get the origin node
 public INode getOriginNode() 
 {
	 return markedNodes.iterator().next();
 }

 // get the target node
 public INode getTargetNode()
 {
	  final Iterator itr = markedNodes.iterator();
	  Object lastElement = itr.next();
	  while (itr.hasNext()) 
	  {
		   lastElement = itr.next();
		   return (INode) lastElement;
	  }
	
	  return null;
 }


 public int getWeight() 
 {
	 return 0;
 }
 
 
 public class ProblemB 
 { 
	  private IGraph graph; 
	  
	  public ProblemB(IGraph graph) 
	  {
		  this.graph = graph;
	  }
	 // get the shortest path between two nodes  
	  public IPath getShortestPath(INode initNode, INode endNode)
	  {
		  IPath path = new Path(graph);       
	      return path;
	  }
 }

 
}