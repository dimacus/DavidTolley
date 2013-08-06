/**
 * Copyright (c) 2013, Groupon, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * Neither the name of GROUPON nor the names of its contributors may be
 * used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

 * Created with IntelliJ IDEA.
 * User: Dima Kovalenko (@dimacus) && Darko Marinov
 * Date: 5/10/13
 * Time: 4:06 PM
 */

package grid.SeleniumGridExtrasplugin;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Node {

  private JsonObject info;
  private JsonObject configuration;
  private JsonObject systemInfo;

  public Node(JsonObject nodeInfo) {
    info = nodeInfo;
    configuration = (JsonObject) info.get("configuration");
    setupSystemInfo();
  }

  public String getIp() {
    return sanitize(configuration.get("host").toString());
  }

  public boolean extrasServerRunning() {
    return HttpWrapper.hostReachable(getHostBaseUrl() + "/api", 2000);
  }

  public String getHostBaseUrl() {
    return "http://" + getIp() + ":3000";
  }

  public String getScreenshot() {
    String screenshotUrl = getHostBaseUrl() + "/screenshot?width=150&height=100&keep=false";
    JsonObject screen = new GetRequest(screenshotUrl).getJsonBody();

    JsonArray image = (JsonArray) screen.get("image");
    return sanitize(image.get(0).toString());
  }

  public List<String> getFreeRam() {
    List<String> returnArray = new LinkedList<String>();
    JsonObject ram = (JsonObject) getSystemInfo().get("ram");

    returnArray.add(readableFileSize(sanitize(ram.get("free").toString())));
    returnArray.add(readableFileSize(sanitize(ram.get("total").toString())));
    return returnArray;
  }

  public List<Map<String, String>> getHDInfo() {
//
    List<Map<String, String>> allDrives = new LinkedList<Map<String, String>>();

    JsonArray drives = (JsonArray) getSystemInfo().get("drives");

    for (int i = 0; i < drives.size(); i++) {
      Map<String, String> currentDrive = new HashMap<String, String>();
      JsonObject drive = (JsonObject) drives.get(i);

      currentDrive.put("drive", sanitize(drive.get("drive").toString()));
      currentDrive.put("free", readableFileSize(sanitize(drive.get("free").toString())));
      currentDrive.put("size", readableFileSize(sanitize(drive.get("size").toString())));

      allDrives.add(currentDrive);
    }

    return allDrives;
  }

  public List<String> getHostNetworkInfo(){
    List<String> host = new LinkedList<String>();


    JsonArray hostname = (JsonArray) getSystemInfo().get("hostname");
    JsonArray ip = (JsonArray) getSystemInfo().get("ip");


    host.add(sanitize(hostname.get(0).toString()));
    host.add(sanitize(ip.get(0).toString()));

    return host;
  }


  public JsonObject getSystemInfo() {
    return systemInfo;
  }


  private String sanitize(String input) {
    return input.replaceAll("\"", "");
  }

  private void setupSystemInfo() {
    systemInfo = new GetRequest(getHostBaseUrl() + "/system").getJsonBody();
  }

  public String readableFileSize(String sizeString) {
    long size = Long.parseLong(sizeString);
    if (size <= 0) {
      return "0";
    }
    final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
    int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
    return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " "
           + units[digitGroups];
  }


}
