package grid.SeleniumGridExtrasplugin.nodes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.List;

import grid.SeleniumGridExtrasplugin.GetRequest;
import grid.SeleniumGridExtrasplugin.HTTPRequest;
import grid.SeleniumGridExtrasplugin.StringWrapper;
import grid.SeleniumGridExtrasplugin.proxies.Proxy;

/**
 * Created with IntelliJ IDEA. User: dima Date: 8/7/13 Time: 12:19 PM To change this template use
 * File | Settings | File Templates.
 */
public class GridNodes {

  private String url;
  private HTTPRequest originalRequest;
  private List<Proxy> proxyList;
  private List<GridNode> gridNodeList;

  public GridNodes(String infoUrl) {
    System.out.println("In nodes");

    url = infoUrl;
    System.out.println("Making get request on " + url);
    originalRequest = makeGetRequest(url);

    proxyList = parseProxies(originalRequest);

    System.out.println("Proxy count " + proxyList.size());

    gridNodeList = getAllGridNodes(proxyList);
    System.out.println("Node count: " + gridNodeList.size());

    assignProxiesToNodes(gridNodeList, proxyList);

    assignTestSlotsToNodes(gridNodeList, getTestSlotsFromRequest(originalRequest));
  }


  public JsonArray getJson() {
    JsonArray nodes = new JsonArray();

    System.out.println("Looping through nodes: " + getGridNodeList().size());
    for (GridNode n : getGridNodeList()){
      System.out.println(n.getIp());
      nodes.add(n.getJson());
    }

    return nodes;
  }

  public List<GridNode> getGridNodeList() {
    return gridNodeList;
  }


  private void assignProxiesToNodes(List<GridNode> nodes, List<Proxy> proxies){

    for (Proxy p : proxies){
      GridNode node = findGridNode(nodes, p.getIp());
      node.addProxy(p);
    }

  }


  private void assignTestSlotsToNodes(List<GridNode> nodes, JsonArray slots) {

    System.out.println("Slots " + slots.size() + " nodes " + nodes.size());

    for (int i = 0; i < slots.size(); i++) {
      JsonObject slot = (JsonObject) slots.get(i);

      String host = StringWrapper.sanitize(slot.get("host").toString());
      System.out.println("Looking for host with ip " + host);

      GridNode node = findGridNode(getGridNodeList(), host);
      node.addTestSlot(slot);

    }

  }

  private JsonArray getTestSlotsFromRequest(HTTPRequest req) {
    JsonObject request = getRequestBody(req);
    return (JsonArray) request.get("Proxies");
  }


  private List<GridNode> getAllGridNodes(List<Proxy> proxies) {
    List<GridNode> nodes = new LinkedList<GridNode>();

    for (Proxy p : proxies) {
      GridNode node = findGridNode(nodes, p.getIp());
      nodes.add(node);
    }

    return nodes;
  }

  private GridNode findGridNode(List<GridNode> nodes, String desiredNodeIp) {
    GridNode desiredNode;
    int position = getDesiredNodePosition(nodes, desiredNodeIp);

    if (position == -1) {
      System.out.println("Node not found, making new one");
      desiredNode = new GridNode(desiredNodeIp);
    } else {
      System.out.println("Node found in " + position + " position");
      desiredNode = nodes.get(position);
    }

    return desiredNode;
  }

  private int getDesiredNodePosition(List<GridNode> nodes, String desiredNodeIp) {
    int position = -1;

    for (int i = 0; i < nodes.size(); i++) {
      if (nodes.get(i).getIp().equals(desiredNodeIp)) {
        position = i;
        break;
      }
    }

    return position;
  }

  private HTTPRequest makeGetRequest(String url) {
    return new GetRequest(url);
  }


  private List<Proxy> parseProxies(HTTPRequest req) {
    List<Proxy> proxies = new LinkedList<Proxy>();

    JsonObject request = getRequestBody(req);
    JsonArray totalProxies = (JsonArray) request.get("TotalProxies");

    if (totalProxies != null) {
      for (int i = 0; i < totalProxies.size(); i++) {
        JsonObject proxyJson = (JsonObject) totalProxies.get(i);
        proxies.add(new Proxy(proxyJson));
      }

    }

    return proxies;
  }

  private JsonObject getRequestBody(HTTPRequest req) {
    return req.getJsonBody();
  }


}
