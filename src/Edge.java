
public class Edge{
	protected StringBuffer _strUniqueID, //a unique id identifying edge
	_strData; //data associated with this edge.
	//Data could be name of edge or
	// any meaningful property for
	// an edge.
	protected int _nEdgeCost; // cost of traversing this edge


	public Edge(StringBuffer _strUniqueID, StringBuffer _strData, int _nEdgeCost) {

		this._strUniqueID = _strUniqueID;
		this._strData = _strData;
		this._nEdgeCost = _nEdgeCost;
	}

	public int compareTo(Edge e){
		return this._nEdgeCost-e._nEdgeCost;
	}
	public StringBuffer getUniqueID( ){

		return _strUniqueID;
	}

	public StringBuffer getData( ){

		return _strData;
	}
	public int getCost( ){

		return _nEdgeCost;
	}
	
	public void setCost(int cost) {
		this._nEdgeCost=cost;
	}
} 
