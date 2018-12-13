import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Vector;

public class Graph {
	int numOfvertix ;
	Vertex[] vertcies = new Vertex[100] ;
	Edge[][] edges = new Edge[100][100];
	boolean[] VertExp = new boolean[100] ; 
	boolean[][] EdgeExp = new boolean[100][100];
	final static int inf = 999999;
	Vector<PathSegment> ps = new Vector<>();

	// returns the name you have given to this graph library [1 pt]
	// choose whatever name you like!
	public String getLibraryName(){
		return "GUC Graph" ; 
	}

	// returns the current version number [1 pt]
	public String getLibraryVersion( ) {
		return "v1.0";
	}

	// the following method adds a vertex to the graph [2 pts]
	public void insertVertex(String strUniqueID, String strData,int nX,
			int nY) throws GraphException{

		StringBuffer strUniqueID1 = new StringBuffer(strUniqueID);
		StringBuffer strData1 = new StringBuffer(strData);
		Vertex vertex = new Vertex(strUniqueID1, strData1, nX, nY);
		vertcies[numOfvertix++] = vertex ;

		PrintGraph();

	}

	// inserts an edge between 2 specified vertices [2 pts]
	public void insertEdge(String strVertex1UniqueID,
			String strVertex2UniqueID,
			String strEdgeUniqueID,
			String strEdgeData,
			int nEdgeCost) throws GraphException{
		Vector<Vertex> v=vertices();
		boolean f =false;
		boolean f2 =false;
		for (int i = 0; i < v.size(); i++) {
			if(v.get(i)._strUniqueID.toString().equals(strVertex1UniqueID))
				f=true;
		}
		for (int i = 0; i < v.size(); i++) {
			if(v.get(i)._strUniqueID.toString().equals(strVertex2UniqueID))
				f2=true;
		}
		if(!(f|f2))
			return ;
		StringBuffer strEdgeUniqueID1 = new StringBuffer(strEdgeUniqueID);
		StringBuffer strEdgeData1 = new StringBuffer(strEdgeData);
		Edge edge = new Edge(strEdgeUniqueID1, strEdgeData1, nEdgeCost);
		int x = getIDVer(strVertex1UniqueID);
		int y = getIDVer(strVertex2UniqueID);
		edges[x][y] = edge ;
		edges[y][x] = edge ;

		PrintGraph();
	}

	// removes vertex and its incident edges [1 pt]
	public void removeVertex(String strVertexUniqueID) throws
	GraphException{
		Vector<Vertex> v=vertices();
		boolean f =false;
		for (int i = 0; i < v.size(); i++) {
			if(v.get(i)._strUniqueID.toString().equals(strVertexUniqueID))
				f=true;
		}
		if(!f)
			return ;

		Vector<Edge> edge = incidentEdges(strVertexUniqueID) ; 
		for(int i = 0 ; i < edge.size() ; i++ ){			

			removeEdge(edge.get(i)._strUniqueID.toString());


		}
		vertcies[getIDVer(strVertexUniqueID)] = null ; 
		PrintGraph();
	}

	// removes an edge from the graph [1 pt]
	public void removeEdge(String strEdgeUniqueID) throws
	GraphException{
		boolean found = false;

		for(int i = 0 ; i<vertcies.length&&found==false ; i ++){
			for(int j = 0 ; j <vertcies.length&&found==false ; j ++){
				if(edges[i][j]!=null)
					if(edges[i][j]._strUniqueID.toString().equals(strEdgeUniqueID)){
						edges[i][j]=null;
						edges[j][i]=null;
						found = true;
					}

			}

		}
		PrintGraph();


	}

	public int getIDVer(String strUniqueID){
		int Id = -1 ;
		for(int i = 0  ; i <numOfvertix ; i++){
			if(vertcies[i]!=null)
				if(vertcies[i]._strUniqueID.toString().equals(strUniqueID) ){
					Id = i ;
					break;
				}
		}
		return Id ;
	}

	public int[] getIDEdge(String strEdgeUniqueID){
		int[] locations = {-1,-1} ;
		boolean found = false;

		for(int i = 0 ; i<vertcies.length&&found==false ; i ++){
			for(int j = 0 ; j <vertcies.length&&found==false ; j ++){
				if(edges[i][j]!=null)
					if(edges[i][j]._strUniqueID.toString().equals(strEdgeUniqueID)){
						locations[0] = i ; 
						locations[1] = j ;
						found = true;
					}

			}

		}

		return locations ; 
	}



	// returns a vector of edges incident to vertex whose
	// id is strVertexUniqueID [1 pt]

	public Vector<Edge> incidentEdges(String strVertexUniqueID)
			throws GraphException {
		Vector<Vertex> ver=vertices();
		boolean f =false;
		for (int i = 0; i < ver.size(); i++) {
			if(ver.get(i)._strUniqueID.toString().equals(strVertexUniqueID))
				f=true;
		}
		if(!f)
			return null ;
		Vector<Edge> vector = new Vector<Edge>();
		int VertId = getIDVer(strVertexUniqueID);
		System.out.print("Incident Edges for vertex " +strVertexUniqueID +": " );
		for (int i = 0 ; i < numOfvertix ; i ++){
			if(edges[VertId][i]!=null){
				vector.add(edges[VertId][i]) ;
				System.out.print(edges[VertId][i]._strUniqueID+ "  ");
			}
		}
		if(vector.size()==0)  System.out.println("No Incident Edges");
		System.out.println();
		return vector ;
	}

	public Vector<Vertex> vertices()throws GraphException{
		Vector<Vertex> vector = new Vector<Vertex>();
		System.out.print("Vertices in graph are : " );
		for(int i = 0;i<numOfvertix;i++){
			if(vertcies[i]!=null){
				vector.add(vertcies[i]);
				System.out.print(vertcies[i]._strUniqueID + " ");
			}
		}

		System.out.println();
		return vector;
	}

	public Vector<Edge> edges() throws GraphException{
		Vector<Edge> vector = new Vector<>();
		System.out.print("edges in graph are : " );
		for(int i = 0;i<numOfvertix;i++){
			for(int j = 0;j<numOfvertix;j++){

				if(edges[i][j]!=null)
					if(!checkEdge(vector, edges[i][j]._strUniqueID.toString())){
						vector.add(edges[i][j]);
						System.out.print(edges[i][j]._strUniqueID + " ");
					}
			}
		}
		System.out.println();
		return vector;
	}

	public boolean checkEdge(Vector<Edge> vector , String EdgeId){
		boolean found = false ;
		for(int i = 0 ; i < vector.size()&&!found ; i ++){
			if(vector.get(i)._strUniqueID.toString().equals(EdgeId))
				found = true ;
		}
		return found ; 
	}





	public Vertex[] endVertices(String strEdgeUniqueID) throws GraphException {

		boolean found = false;

		Vertex[]endvertcies = new Vertex[2];
		System.out.print("End vertices for edge "+strEdgeUniqueID + " :");
		for(int i=0;  i<vertcies.length&&found==false ; i ++){
			for(int j=0; j<vertcies.length&&found==false ; j ++){
				if(edges[i][j]!=null)
					if(edges[i][j]._strUniqueID.toString().equals(strEdgeUniqueID)){
						found = true;
						endvertcies[0]=vertcies[i];
						endvertcies[1]=vertcies[j];
						System.out.print(vertcies[i]._strUniqueID +"and " + vertcies[j]._strUniqueID);
					}
			}

		}

		System.out.println();
		return endvertcies;

	}

	public Vertex opposite(String strVertexUniqueID,String strEdgeUniqueID) throws GraphException{
		Vector<Vertex> v=vertices();
		boolean f =false;
		for (int i = 0; i < v.size(); i++) {
			if(v.get(i)._strUniqueID.toString().equals(strVertexUniqueID))
				f=true;
		}
		if(!f)
			return null ;

		Vertex vertex = null ;
		int VertId = getIDVer(strVertexUniqueID);
		System.out.print("opposite vertix for edge and ve "+strEdgeUniqueID + " and " +strVertexUniqueID + " : ");
		for(int i = 0 ; i < numOfvertix ; i ++){
			if(edges[VertId][i]!=null)
				if(edges[VertId][i]._strUniqueID.toString().equals(strEdgeUniqueID)){
					vertex = vertcies[i];
					System.out.print(vertcies[i]._strUniqueID );
				}
		}
		System.out.println();
		return vertex;
	}


	public void bfs(String  strStartVertexUniqueID, Visitor visitor) throws GraphException{
		Vector<Vertex> v=vertices();
		boolean f =false;
		for (int i = 0; i < v.size(); i++) {
			if(v.get(i)._strUniqueID.toString().equals(strStartVertexUniqueID))
				f=true;
		}
		if(!f)
			return ;
		clearLabels();
		ArrayList<LinkedList<Vertex>> queues = new ArrayList<LinkedList<Vertex>>();
		LinkedList <Vertex> queue=new LinkedList <Vertex>();
		queue.add(vertcies[getIDVer(strStartVertexUniqueID)]);
		queues.add(queue);
		setLabelV(strStartVertexUniqueID, true);
		visitor.visit(vertcies[getIDVer(strStartVertexUniqueID)]);


		for(int i = 0 ; !(queues.get(i).isEmpty()) ; i++){
			LinkedList <Vertex> L0=new LinkedList <>();
			queues.add(L0);
			for(int j=0;j<queues.get(i).size();j++){
				Vector<Edge> incidentEdges = incidentEdges(queues.get(i).get(j)._strUniqueID.toString());
				for(int k=0;k<incidentEdges.size();k++){
					if (!getLabelE(incidentEdges.get(k)._strUniqueID.toString())){
						int[] location = getIDEdge(incidentEdges.get(k)._strUniqueID.toString());
						Vertex w = opposite(queues.get(i).get(j)._strUniqueID.toString(), incidentEdges.get(k)._strUniqueID.toString());
						setLabelE(incidentEdges.get(k)._strUniqueID.toString(), true);
						if(w!=null)
							if(!getLabelV(w._strUniqueID.toString())){
								setLabelV(w._strUniqueID.toString(), true);
								visitor.visit(edges[location[0]][location[1]]);
								visitor.visit(vertcies[getIDVer(w._strUniqueID.toString())]);
								queues.get(i+1).add(vertcies[getIDVer(w._strUniqueID.toString())]);
							}
					}
				}
			}
		}
	}

	//performs depth first search starting from passed vertex
	//visitor is called on each vertex and edge visited. [12 pts]
	public void dfs(String strStartVertexUniqueID,Visitor visitor) throws GraphException{
		Vector<Vertex> v=vertices();
		boolean f =false;
		for (int i = 0; i < v.size(); i++) {
			if(v.get(i)._strUniqueID.toString().equals(strStartVertexUniqueID))
				f=true;
		}
		if(!f)
			return ;
		setLabelV(strStartVertexUniqueID, true);
		visitor.visit(vertcies[getIDVer(strStartVertexUniqueID)]);
		Vector<Edge> incidentEdges = incidentEdges(strStartVertexUniqueID);

		for(int i = 0 ; i<incidentEdges.size() ; i++){	
			if(!getLabelE(incidentEdges.get(i).toString())){
				Vertex w = opposite(strStartVertexUniqueID, incidentEdges.get(i)._strUniqueID.toString());
				setLabelE(incidentEdges.get(i).toString(), true);
				int[] location = getIDEdge(incidentEdges.get(i)._strUniqueID.toString());
				if(!getLabelV(w._strUniqueID.toString())){
					visitor.visit(edges[location[0]][location[1]]);
					dfs(w._strUniqueID.toString(), visitor);

				}

			}

		}



	}


	public Vector<PathSegment> pathDFS(String strStartVertexUniqueID,String strEndVertexUniqueID)
			throws GraphException{
		Vector<PathSegment> path = new Vector<PathSegment>();
		return pathDFSRec(strStartVertexUniqueID, strEndVertexUniqueID, path);







	}

	public Vector<PathSegment> pathDFSRec(String strStartVertexUniqueID,String strEndVertexUniqueID ,Vector<PathSegment> path )
			throws GraphException{

		setLabelV(strStartVertexUniqueID, true);

		if(strStartVertexUniqueID.equals(strEndVertexUniqueID)){
			PathSegment pathseg = new PathSegment(vertcies[getIDVer(strEndVertexUniqueID)], null) ;
			path.addElement(pathseg);
			return path;
		}
		Vector<Edge> incidentEdges = incidentEdges(strStartVertexUniqueID);

		for(int i = 0 ; i <incidentEdges.size() ; i++){
			if(!getLabelE(incidentEdges.get(i)._strUniqueID.toString())){

				Vertex w = opposite(strStartVertexUniqueID, incidentEdges.get(i)._strUniqueID.toString());
				setLabelE(incidentEdges.get(i)._strUniqueID.toString(), true);

				if(!getLabelV(w._strUniqueID.toString())){
					PathSegment pathseg = new PathSegment(vertcies[getIDVer(strStartVertexUniqueID)],incidentEdges.get(i)) ;
					path.addElement(pathseg);
					pathDFSRec(w._strUniqueID.toString(),strEndVertexUniqueID , path);
					if(!path.get(path.size()-1)._vertex._strUniqueID.toString().equals(strEndVertexUniqueID))
						path.remove(path.size()-1);
				}
			}

		}

		return path;


	}


	public boolean getLabelV( String strVertexUniqueID ){

		int VertId = getIDVer(strVertexUniqueID);
		return VertExp[VertId];


	}

	public void setLabelV( String strVertexUniqueID , boolean label) {
		int VertId = getIDVer(strVertexUniqueID);
		VertExp[VertId] = label ;
	}

	public boolean getLabelE( String strEdgeUniqueID ){
		boolean label = false ;
		boolean found = false;

		for(int i=0;  i<vertcies.length&&found==false ; i ++){
			for(int j=0; j<vertcies.length&&found==false ; j ++){
				if(edges[i][j]!=null)
					if(edges[i][j]._strUniqueID.toString().equals(strEdgeUniqueID)){
						label = EdgeExp[i][j];
						found = true;

					}
			}

		}
		return label ;

	}

	public void setLabelE( String strEdgeUniqueID , boolean label) {

		boolean found = false;

		for(int i=0;  i<vertcies.length&&found==false ; i ++){
			for(int j=0; j<vertcies.length&&found==false ; j ++){
				if(edges[i][j]!=null)
					if(edges[i][j]._strUniqueID.toString().equals(strEdgeUniqueID)){
						EdgeExp[i][j] = label ;
						found = true;

					}
			}

		}	

	}

	public void clearLabels(){
		for(int i = 0  ; i <numOfvertix ; i++){
			VertExp[i] = false ;
		}

		for(int i=0;  i<vertcies.length ; i ++){
			for(int j=0; j<vertcies.length ; j ++){
				EdgeExp[i][j] = false ;

			}
		}

	}

	public Vertex[] closestPair() throws GraphException{
		vertices();

		Vertex[]  verts =  vertices().toArray(new Vertex[ vertices().size()]);
		sort(verts, 0, verts.length-1, false);
		return closestPairH(verts, verts.length-1);

	}


	int dist(Vertex x,Vertex y){
		int dist=0;
		dist=((y._nX-x._nX)*(y._nX-x._nX)+(y._nY-x._nY)*(y._nY-x._nY));
		return dist;
	}

	Vertex[] bruteForce(Vertex v[],int n){
		int min=10000;
		Vertex [] closestPair = new Vertex[2];
		for (int i=0;i<n;i++){
			for(int j=i+1;j<n;j++){
				if(dist(v[i],v[j]) < min)
					min=dist(v[i],v[j]);
				closestPair[0]=v[i];
				closestPair[1]=v[j];
			}	
		}
		return closestPair;

	}


	public Vertex[] minVer(Vertex v1[],Vertex []v2){

		if(dist(v1[0],v1[1])>dist(v2[0],v2[1]))
			return v2;
		else
			return v1;



	}

	Vertex[] stripClosest(Vertex[] strip,int size, Vertex[] d) 
	{ 
		Vertex[] min = d;  // Initialize the minimum distance as d 

		sort(strip, 0, size-1, true);  

		// Pick all points one by one and try the next points till the difference 
		// between y coordinates is smaller than d. 
		// This is a proven fact that this loop runs at most 6 times 
		for (int i = 0; i < size; ++i) {
			for (int j = i+1; j < size && (strip[j]._nY - strip[i]._nY) < dist(min[0],min[1]); ++j) {
				if (dist(strip[i],strip[j]) < dist(min[0],min[1])) {
					min[0] = strip[i];  
					min[1] = strip[j];
				}
			}
		}
		return min; 
	} 

	public Vertex [] startFromMid (Vertex v[]){
		int mid=v.length/2;
		Vertex[]x=new Vertex[mid];
		for (int i = 0; i < x.length; i++) {
			x[i]=v[mid+i];
		}
		return x;

	}
	public Vector<PathSegment> startFromIndex (Vector<PathSegment> p,int j){
		Vector<PathSegment> path = new Vector<PathSegment>();
		for ( int i = 0; i < j; i++) {
			path.add(p.get(i));
		}
		return path;

	}

	public Vertex[] closestPairH(Vertex v[],int n) throws GraphException{


		if(n<=3)

			return bruteForce(v, n);

		int mid = n/2;
		Vertex midvertex = v[mid];

		Vertex[] dl = closestPairH(v, mid); 
		Vertex[] dr = closestPairH(startFromMid(v), n-mid); 
		Vertex[] d=minVer(dl,dr);

		Vertex strip[]=new Vertex[n]; 
		int j = 0; 
		for (int i = 0; i < n; i++) 
			if (Math.abs(v[i]._nX - midvertex._nX) < dist(d[0],d[1])) {
				strip[j] = v[i];
				j++; 
			}
		// Find the closest points in strip.  Return the minimum of d and closest 
		// distance is strip[] 
		return minVer(d, stripClosest(strip, j, d) ); 

	}


	int partitionX(Vertex arr[], int low, int high) 
	{ 
		Vertex pivot = arr[high];  
		int i = (low-1); // index of smaller element 
		for (int j=low; j<high; j++) 
		{ 
			// If current element is smaller than or 
			// equal to pivot 
			if (arr[j]._nX <= pivot._nX) 
			{ 
				i++; 

				// swap arr[i] and arr[j] 
				Vertex temp = arr[i]; 
				arr[i] = arr[j]; 
				arr[j] = temp; 
			} 
		} 

		// swap arr[i+1] and arr[high] (or pivot) 
		Vertex temp = arr[i+1]; 
		arr[i+1] = arr[high]; 
		arr[high] = temp; 

		return i+1; 
	} 

	int partitionY(Vertex arr[], int low, int high) 
	{ 
		Vertex pivot = arr[high];  
		int i = (low-1); // index of smaller element 
		for (int j=low; j<high; j++) 
		{ 
			// If current element is smaller than or 
			// equal to pivot 
			if (arr[j]._nY <= pivot._nY) 
			{ 
				i++; 

				// swap arr[i] and arr[j] 
				Vertex temp = arr[i]; 
				arr[i] = arr[j]; 
				arr[j] = temp; 
			} 
		} 

		// swap arr[i+1] and arr[high] (or pivot) 
		Vertex temp = arr[i+1]; 
		arr[i+1] = arr[high]; 
		arr[high] = temp; 

		return i+1; 
	} 


	/* The main function that implements QuickSort() 
  arr[] --> Array to be sorted, 
  low  --> Starting index, 
  high  --> Ending index */
	void sort(Vertex arr[], int low, int high , boolean XorY) 
	{ 
		if (low < high) 
		{ 
			if(!XorY){
				/* pi is partitioning index, arr[pi] is  
          now at right place */
				int pi = partitionX(arr, low, high); 

				// Recursively sort elements before 
				// partition and after partition 
				sort(arr, low, pi-1,XorY); 
				sort(arr, pi+1, high,XorY); 

			}
			else if(XorY){
				/* pi is partitioning index, arr[pi] is  
            now at right place */
				int pi = partitionY(arr, low, high); 

				// Recursively sort elements before 
				// partition and after partition 
				sort(arr, low, pi-1,XorY); 
				sort(arr, pi+1, high,XorY); 

			} 
		} 

	}

	int partitionE(Edge arr[], int low, int high) 
	{ 
		Edge pivot = arr[high];  
		int i = (low-1); // index of smaller element 
		for (int j=low; j<high; j++) 
		{ 
			// If current element is smaller than or 
			// equal to pivot 
			if (arr[j]._nEdgeCost <= pivot._nEdgeCost) 
			{ 
				i++; 

				// swap arr[i] and arr[j] 
				Edge temp = arr[i]; 
				arr[i] = arr[j]; 
				arr[j] = temp; 
			} 
		} 

		// swap arr[i+1] and arr[high] (or pivot) 
		Edge temp = arr[i+1]; 
		arr[i+1] = arr[high]; 
		arr[high] = temp; 

		return i+1; 
	} 

	void sortE(Edge arr[], int low, int high ) 
	{ 
		if (low < high) 
		{ 

			/* pi is partitioning index, arr[pi] is  
          now at right place */
			int pi = partitionE(arr, low, high); 

			// Recursively sort elements before 
			// partition and after partition 
			sortE(arr, low, pi-1); 
			sortE(arr, pi+1, high); 
		} 

	}



	void PrintGraph(){

		System.out.print("INDEX V : ");
		for (int i = 0; i < numOfvertix; i++) {
			System.out.print(i+1+" ");
		}
		System.out.print("\n          ");
		for (int i = 0; i < numOfvertix; i++) {
			if(vertcies[i]!=null)
				System.out.print(vertcies[i]._strUniqueID.toString()+" ");
			else System.out.print(vertcies[i]+" ");
		}
		System.out.print("\n");
		System.out.print("INDEX E :   ");
		for (int i = 0; i < numOfvertix; i++) {
			System.out.print(i+1+"   ");
		}

		System.out.print("\n          ");

		for (int i = 0; i < numOfvertix; i++) {
			System.out.print("\n          "+ (i+1)+ " ");
			for (int j = 0; j < numOfvertix; j++) {
				if(edges[i][j]!=null)
					System.out.print(edges[i][j]._strUniqueID.toString()+" ");
				else System.out.print(edges[i][j]+" ");
			}
		}
		System.out.print("\n");
	}


	public Vertex find(Vertex[] parent , Vertex i){
		if(parent[getIDVer(i._strUniqueID.toString())] == null)
			return i ;
		return find(parent , parent[getIDVer(i._strUniqueID.toString())]);
	}

	public void Union(Vertex[] parent, Vertex x , Vertex y){
		Vertex xset = find(parent, x);
		Vertex yset = find(parent, y);
		parent[getIDVer(xset._strUniqueID.toString())]= yset ;
	}
	boolean isCycle() throws GraphException 
	{ 
		// Allocate memory for creating V subsets 
		Vertex parent[] = new Vertex[vertices().size()]; 

		// Initialize all subsets as single element sets 
		for (int i=0; i<vertices().size(); ++i) 
			parent[i]=null; 

		// Iterate through all edges of graph, find subset of both 
		// vertices of every edge, if both subsets are same, then 
		// there is cycle in graph. 
		Vector<Edge> edges = edges();
		for (int i = 0; i < edges.size(); ++i) 
		{ 
			Vertex x = find(parent,endVertices(edges.get(i)._strUniqueID.toString())[0]); 
			Vertex y = find(parent,endVertices(edges.get(i)._strUniqueID.toString())[1]); 

			if (x._strUniqueID.toString().equals(y._strUniqueID.toString())) 
				return true; 

			Union(parent, x, y); 
		} 
		return false; 
	} 

	public Vector<PathSegment> minSpanningTree() throws GraphException{

		Vector<PathSegment> result = new Vector<PathSegment>();  // Tnis will store the resultant MST 


		// Step 1:  Sort all the edges in non-decreasing order of their 
		// weight.  If we are not allowed to change the given graph, we 
		// can create a copy of array of edges 
		Edge[]  edgs =  edges().toArray(new Edge[ edges().size()]);
		sortE(edgs,0,edgs.length-1); 

		// Create V subsets with single elements 
		Graph g = new Graph();
		int i = 0;  // Index used to pick next edge 

		// Number of edges to be taken is equal to V-1 
		int e =0;
		while (e < vertices().size()-1) 
		{ 
			// Step 2: Pick the smallest edge. And increment  
			// the index for next iteration 


			Edge next_edge = edgs[i++]; 
			Vertex endV[] = endVertices(next_edge._strUniqueID.toString());
			if(g.getIDVer(endV[0]._strUniqueID.toString())==-1)
				g.insertVertex(endV[0]._strUniqueID.toString(), endV[0]._strData.toString(), endV[0]._nX, endV[0]._nY);

			if(g.getIDVer(endV[1]._strUniqueID.toString())==-1)
				g.insertVertex(endV[1]._strUniqueID.toString(), endV[1]._strData.toString(), endV[1]._nX, endV[1]._nY);

			g.insertEdge(endV[0]._strUniqueID.toString(),
					endV[1]._strUniqueID.toString(),
					next_edge._strUniqueID.toString(),
					next_edge._strData.toString(),
					next_edge._nEdgeCost);


			// If including this edge does't cause cycle, 
			// include it in result and increment the index  
			// of result for next edge 
			System.out.println(g.isCycle());
			if (!g.isCycle()) 
			{ 	
				PathSegment pathseg = null ;
				if(! existV(result, endV[0]))
					if(e==vertices().size()-2){
						PathSegment pathseg1 = new PathSegment(endV[0], next_edge);
						result.add(pathseg1);
						pathseg = new PathSegment(endV[1], null);
					}
					else 
						pathseg = new PathSegment(endV[0], next_edge);
				else 
					if(e==vertices().size()-2){
						PathSegment pathseg1 = new PathSegment(endV[1], next_edge);
						result.add(pathseg1);
						pathseg = new PathSegment(endV[1], null);
					}
					else	
						pathseg = new PathSegment(endV[1], next_edge);
				result.add(pathseg); 
				//  System.out.println(g.edges().size());



			}
			else{
				g.removeEdge(next_edge._strUniqueID.toString());

			}
			e= g.edges().size() ;	
			System.out.println("e = "+e);
		}



		return result;
	} 

	boolean existV(Vector<PathSegment> paths,Vertex v){
		boolean exist = false ;
		for(int i =0 ; i<paths.size();i++){
			if(v._strUniqueID.toString().equals(paths.get(i)._vertex._strUniqueID.toString())) exist =true ;

		}
		return exist ;
	}
	
	
	// finds all shortest paths using Floyd–Warshall dynamic
	// programming algorithm and returns all such paths. Use
	// Edge._nEdgeCost attribute in finding the shortest path
	public Vector<Vector<PathSegment>> findAllShortestPathsFW() throws GraphException {
		Vector<Vector<PathSegment>> vvps = floydWarshall(edges);
	//	System.out.println(vvps);
		return vvps;
	}
	
	public Vector<Vector<PathSegment>> floydWarshall(Edge[][] array) {
		
		Vector<Vector<PathSegment>> vvps = new Vector<>();
		Edge[][] duplicate = new Edge[array.length][array.length];
		int len = numOfvertix;
		
		for(int i=0;i<len;i++) {
			for(int j=0;j<len;j++) {
				
				duplicate[i][j] = array[i][j];
			}
		}
		//printArray(edges);
		printArray(duplicate);
		for (int k = 0; k < len; k++) 
        { 
            // Pick all vertices as source one by one 
            for (int i = 0; i < len; i++) 
            { 
                // Pick all vertices as destination for the 
                // above picked source 
                for (int j = 0; j < len; j++) 
                { 
                    // If vertex k is on the shortest path from 
                    // i to j, then update the value of dist[i][j]
                	if(duplicate[i][j] != null && duplicate[i][k]!=null && duplicate[k][j]!=null) {
                		if(duplicate[i][j].getCost() != 0 || duplicate[i][j].getCost() != inf) {
                			Vector<PathSegment> ps = new Vector<>();
                    		if (duplicate[i][k].getCost() + duplicate[k][j].getCost() <= duplicate[i][j].getCost())
                    		{
                    		
                            	duplicate[i][j].setCost(duplicate[i][k].getCost() + duplicate[k][j].getCost());
                            	ps.add(new PathSegment(vertcies[i], edges[i][k]));
                            	ps.add(new PathSegment(vertcies[k], edges[k][j]));
                            	
                            }
                    		
                    		else ps.add(new PathSegment(vertcies[i], edges[i][j]));
                    		vvps.add(ps);
                    		
                    	}
                	}
                } 
            } 
        }
		printArray(duplicate);
		return vvps;
	}
	
	public void printArray(Edge[][] e) {
		System.out.println("The following matrix shows the shortest "+ 
				"distances between every pair of vertices"); 
		for (int i=0; i<numOfvertix; ++i) 
		{ 
			for (int j=0; j<numOfvertix; ++j) 
			{ 
				if (e[i][j] == null) 
					System.out.print("INF "); 
				else
					System.out.print(e[i][j].getCost()+"   "); 
			} 
			System.out.println(); 
		} 
	}
	
	public Vector<Vector<PathSegment>> findShortestPathBF(String strStartVertexUniqueID) throws GraphException{
		 int V = this.vertices().size();
	    HashMap<String,Vector<PathSegment>> dist = new HashMap<>();
	    Vector<Vector<PathSegment>> paths = new Vector<>();
	    Vertex src = null;
	    for(Vertex v :this.vertices())
	   	  if(v._strUniqueID.toString().equals(strStartVertexUniqueID)) {
	   		  src=v;
	   	  }
	       for (Vertex v :this.vertices()) 
	           dist.put(v._strUniqueID.toString(),new Vector<>() ); 
	       
	       Vector<PathSegment> path= (new Vector<>());
	       path.add(new PathSegment(src, new Edge(new StringBuffer("zero"), new StringBuffer("zero"), 0)));
	      dist.put(src._strUniqueID.toString(), path );
	      
	     
	      
	       for (int i=1; i<V; ++i) 
	       { 
	    	   
	    	    
	           for (Edge e :this.edges()) 
	           { 	
	        	   
		        	int []ed =getIDEdge(e._strUniqueID.toString());
		        	String u=vertcies[ed[0]]._strUniqueID.toString();
		   			String v=vertcies[ed[1]]._strUniqueID.toString();
	                int weight = e._nEdgeCost;
	                if (dist.get(u).size()!=0 ){
	                	
	                	int costu = getCost(dist.get(u));
	                	int costv = getCost(dist.get(v));
	                	if( costu+weight<costv) {
	                		Vector<PathSegment> path2 = null ;
	                		if((vertcies[ed[0]]._strUniqueID.toString().equals(strStartVertexUniqueID))){
	                		  path2= new Vector<>();
	                		}
	                		else path2 = dist.get(u);
	              	       	path2.add(new PathSegment(vertcies[ed[0]], e));
	              	       	dist.put(v,  path2);
	                   
	                	}
	                
	           } 
	                
	                 v=vertcies[ed[0]]._strUniqueID.toString();
		   			 u=vertcies[ed[1]]._strUniqueID.toString();
	                
	                if (dist.get(u).size()!=0 ){
	                	
	                	int costu = getCost(dist.get(u));
	                	int costv = getCost(dist.get(v));
	                	if( costu+weight<costv) {
	                		Vector<PathSegment> path2 = null ;
	                		if((vertcies[ed[0]]._strUniqueID.toString().equals(strStartVertexUniqueID))){
	                		  path2= new Vector<>();
	                		}
	                		else path2 = dist.get(u);
	              	       	path2.add(new PathSegment(vertcies[ed[0]], e));
	              	       	dist.put(v,  path2);
	                   
	                	}
	                
	           } 
	                
	         
	           }
	          
	       } 
	       
	       for (Vertex v :this.vertices()) 
	    	   paths.add(dist.get(v._strUniqueID.toString()));
	 
	    
	     
	      return paths;
	}
	
	
	public int getCost(Vector<PathSegment> paths){
		if( paths.size() ==0) return Integer.MAX_VALUE ;
		int cost = 0 ;
		for(int i = 0 ; i< paths.size() ; i ++){
			cost += paths.get(i)._edge.getCost() ; 
		}
		return cost ;
	}
	
	
	public static void main(String[] args) throws GraphException {
		Graph g = new Graph();
		GradingVisitor Gv = new GradingVisitor();
		//	g.insertVertex("a", "a" , 2 ,3  );
		//	g.insertVertex("b", "b" , 1 ,2  );
		//	g.insertVertex("c", "c" , 3 ,2  );
		//	g.insertVertex("d", "d" , 0 ,1  );
		//	g.insertVertex("e", "e" , 1 ,1  );
		//	g.insertVertex("f", "f" , 3 ,1  );
		//	g.insertVertex("g", "g" , 4 ,1  );
		//	g.insertVertex("i", "i" , 1 ,3  );
		//	g.insertEdge("a", "b", "1e", "1", 2);
		//	g.insertEdge("a", "c", "2e", "1", 5);
		//	g.insertEdge("b", "e", "3e", "1", 14);
		//	g.insertEdge("b", "d", "4e", "1", 4);	
		//	g.insertEdge("c", "g", "5e", "1", 34);
		//	g.insertEdge("c", "f", "6e", "1", 58);
		//	g.insertEdge("e", "i", "7e", "1", 58);
		//	g.insertEdge("b", "c", "8e", "1", 700);

//		g.insertVertex("2", "2" , 0 ,0  );
//		g.insertVertex("3", "3" , 1 ,0  );
//		g.insertVertex("1", "1" , 1 ,1  );
//		g.insertVertex("0", "0" , 0 ,1  );
//		g.insertEdge("2", "3", "1e", "1", 4);
//		g.insertEdge("3", "1", "2e", "1", 15);
//		g.insertEdge("1", "0", "3e", "1", 10);
//		g.insertEdge("0", "2", "4e", "1", 6);
//		g.insertEdge("0", "3", "5e", "1", 5);
		
		//g.insertVertex("1", "0", 0, 0);
		
//		g.insertVertex("1", "1", 1, 1);
//		g.insertVertex("2", "2", 0, 1);
//		g.insertVertex("3", "3", 1, 0);
//		g.insertEdge("1", "3", "e1", "e1", 3);
//		g.insertEdge("1", "2", "e2", "e2", 2);
//		g.insertEdge("2", "3", "e3", "e3", 50);
//		g.insertVertex("3", "3", 1, 0);
//		g.insertVertex("4", "4", 1, -1);
//		g.insertEdge("0", "0", "e0", "e0", 0);
//		g.insertEdge("0", "1", "e1", "e1", 2);
//		g.insertEdge("0", "3", "e2", "e2", 5);
//		g.insertEdge("1", "1", "e3", "e3", 0);
//		g.insertEdge("1", "0", "e4", "e4", 2);
//		g.insertEdge("1", "3", "e5", "e5", 5);
//		g.insertEdge("1", "4", "e6", "e6", 4);
//		g.insertEdge("1", "2", "e7", "e7", 14);
//		g.insertEdge("2", "2", "e8", "e8", 0);
//		g.insertEdge("2", "4", "e9", "e9", 34);
//		g.insertEdge("2", "1", "e10", "e10", 14);
//		g.insertEdge("3", "3", "e11", "e11", 0);
//		g.insertEdge("3", "0", "e12", "e12", 5);
//		g.insertEdge("3", "1", "e13", "e13", 5);
//		g.insertEdge("3", "4", "e14", "e14", 58);
//		g.insertEdge("4", "4", "e15", "e15", 0);
//		g.insertEdge("4", "2", "e16", "e16", 34);
//		g.insertEdge("4", "3", "e17", "e17", 58);
		
		
		g.insertVertex("a", "a" , 2 ,3  );
		g.insertVertex("b", "b" , 1 ,2  );
		g.insertVertex("c", "c" , 3 ,2  );
		g.insertVertex("d", "d" , 0 ,1  );
		g.insertVertex("e", "e" , 1 ,1  );
		g.insertVertex("f", "f" , 3 ,1  );
		g.insertVertex("g", "g" , 4 ,1  );
		g.insertVertex("i", "i" , 1 ,3  );
		g.insertEdge("a", "b", "1e", "1", 2);	
		g.insertEdge("a", "c", "2e", "1", 5);
		g.insertEdge("b", "e", "3e", "1", 14);
		g.insertEdge("b", "d", "4e", "1", 4);	
		g.insertEdge("c", "g", "5e", "1", 34);
		g.insertEdge("c", "f", "6e", "1", 58);
		g.insertEdge("e", "i", "7e", "1", 59);
		g.insertEdge("g", "f", "8e", "1", 3);
		
		
	//	g.findAllShortestPathsFW();
		
		//g.removeEdge(g.edges().get(0)._strUniqueID.toString());
		//g.removeVertex("1");
		//g.incidentEdges("1");
		//g.incidentEdges("2");
		//g.incidentEdges("3");
		//	g.vertices();
		//	g.edges();
		//	g.endVertices("2e");
		//	//g.opposite("2", "1e");
		//	System.out.println(g.isCycle());
		//	//g.bfs("a ",Gv);
		//	//g.dfs("a", Gv);
		//	//System.out.println(Gv._strResult);
		//	
		//	Vector<PathSegment> path1 = g.pathDFS("a", "i");
		//	
		//	for(int i = 0 ; i < path1.size() ; i++){
		//		
		//		System.out.print("Vertex "+ path1.get(i)._vertex._strUniqueID  );
		//		if(path1.get(i)._edge==null ) System.out.print(" ");
		//		else System.out.print( " Edge "+path1.get(i)._edge._strUniqueID+" ");
		//		
		//		
		//	}
		//	
		//	System.out.println();
		//	Vertex[] closestpair = g.closestPair();
		//	for(int i = 0 ; i < closestpair.length ; i++){
		//		
		//		System.out.print("Vertex "+ closestpair[i]._strUniqueID+ "  " );
		//		
		//		
		//	}
		//	
		//	
		//System.out.println(g.path);

//		Vector<PathSegment> path2 = g.minSpanningTree();
//
//		for(int i = 0 ; i < path2.size() ; i++){
//
//			System.out.print("Vertex "+ path2.get(i)._vertex._strUniqueID  );
//			if(path2.get(i)._edge==null ) System.out.print(" ");
//			else System.out.print( " Edge "+path2.get(i)._edge._strUniqueID+" ");
//
//
//		}
		
//		Vector<Vector<PathSegment>> findAllShortestPathsFW = g.findAllShortestPathsFW() ; 
//		
//		for(int j = 0 ; j < findAllShortestPathsFW.size() ; j++ ){
//			Vector<PathSegment> path2 = findAllShortestPathsFW.get(j);
//		for(int i = 0 ; i < path2.size() ; i++){
//
//			System.out.print("Vertex "+ path2.get(i)._vertex._strUniqueID  );
//			if(path2.get(i)._edge==null ) System.out.print(" ");
//			else System.out.print( " Edge "+path2.get(i)._edge._strUniqueID+" ");
//
//
//		}
//		System.out.println();
//		
//
//	}
		Vector<Vector<PathSegment>> findShortestPathBF = g.findShortestPathBF("g") ; 
		for(int j = 0 ; j < findShortestPathBF.size() ; j++ ){
			Vector<PathSegment> path2 = findShortestPathBF.get(j);
		for(int i = 0 ; i < path2.size() ; i++){

			System.out.print("Vertex "+ path2.get(i)._vertex._strUniqueID  );
			if(path2.get(i)._edge==null ) System.out.print(" ");
			else System.out.print( " Edge "+path2.get(i)._edge._strUniqueID+" ");


		}
		System.out.println();
		

	}

	}
}