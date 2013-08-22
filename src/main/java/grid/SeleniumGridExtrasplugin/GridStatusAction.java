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


import com.google.gson.JsonObject;

import grid.SeleniumGridExtrasplugin.nodes.GridNode;
import grid.SeleniumGridExtrasplugin.nodes.GridNodes;
import grid.SeleniumGridExtrasplugin.proxies.Proxy;
import hudson.Extension;
import hudson.Plugin;
import hudson.model.Action;
import hudson.model.Actionable;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Hudson;
import hudson.model.RootAction;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.export.ExportedBean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;


@Extension
public class GridStatusAction extends Plugin implements Action, RootAction {


//  @SuppressWarnings("unchecked")
//  public Descriptor<GridStatus> getDescriptor() {
//    return Hudson.getInstance().getDescriptorOrDie(getClass());
//  }

  public String getIconFileName() {
    return "up.png";
  }

  public String getDisplayName() {
    return "Selenium Grid Extras";
  }

  public String getUrlName() {
    return "/gridExtras";
  }


  public Object getDynamic(String token, StaplerRequest req, StaplerResponse rsp) {
    return null;
  }

  @Override
  public void postInitialize() throws Exception {
    load();

    System.out.println("DIMA!!!!!!!!!!!!!!!");

  }

  public String doNodes(StaplerRequest req, StaplerResponse rsp) {
    rsp.setContentType("application/json");

    GridNodes nodes = new GridNodes(Config.getInfoServletUrl());
    return nodes.getJson().toString();
  }

  public String doScreenshot(StaplerRequest req, StaplerResponse rsp) {
    rsp.setContentType("application/json");

    System.out.println(req.getParameterMap());
    if (req.hasParameter("ip")) {

      String height = req.hasParameter("height") ? req.getParameter("height").toString() : "100";
      String width = req.hasParameter("width") ? req.getParameter("width").toString() : "200";
      String ip = req.getParameter("ip");

      GridNode node = new GridNode(ip);
      return node.getScreenshot(height, width).toString();
    } else {
      JsonObject error = new JsonObject();
      error.addProperty("error", "ip param is required");
      return error.toString();
    }
  }

  private void startGridExtrasService(String pathToJar){

  }
}