package grid.SeleniumGridExtrasplugin.nodes;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.LinkedList;
import java.util.List;

import grid.SeleniumGridExtrasplugin.GetRequest;
import grid.SeleniumGridExtrasplugin.proxies.Proxy;

/**
 * Created with IntelliJ IDEA. User: dima Date: 8/7/13 Time: 12:19 PM To change this template use
 * File | Settings | File Templates.
 */
public class GridNode {

  private String hostIp;
  private List<Proxy> proxies;
  private List<JsonObject> slots;

  public GridNode(String ip) {
    hostIp = ip;

    System.out.println("New Node Created - " + hostIp);

    proxies = new LinkedList<Proxy>();
    slots = new LinkedList<JsonObject>();
  }


  public String getIp() {
    return hostIp;
  }

  public void addTestSlot(JsonObject slot) {
    getSlots().add(slot);
  }

  public void addProxy(Proxy proxy) {
    getProxies().add(proxy);
  }

  public List<JsonObject> getSlots() {
    return slots;
  }

  public List<Proxy> getProxies() {
    return proxies;
  }

  public JsonArray getProxiesAsJson() {
    JsonArray proxies = new JsonArray();

    for (Proxy p : getProxies()) {
      proxies.add(p.getJson());
    }

    return proxies;

  }

  public JsonArray getTestSlotsAsJson() {
    JsonArray slots = new JsonArray();

    for (JsonObject s : getSlots()) {
      slots.add(s);
    }
    return slots;

  }

  public JsonObject getScreenshot(String height, String width) {
    JsonObject resp = new JsonObject();

    resp.addProperty("ip", getIp());
    resp.addProperty("error", "");

    try {
      GetRequest
          req =
          new GetRequest(
              "http://" + getIp() + ":3000/screenshot?width=" + width + "&height=" + height
              + "&keep=false");

      JsonObject getResp = req.getJsonBody();
      resp.addProperty("image", getResp.get("image").toString());
    } catch (Exception e) {
      e.printStackTrace();
      resp.addProperty("error", e.toString());
    }

    return resp;
  }


  public JsonObject getJson() {
    JsonObject json = new JsonObject();

    json.addProperty("host", getIp());
    json.add("proxies", getProxiesAsJson());
    json.add("slots", getTestSlotsAsJson());

    return json;
  }


}
