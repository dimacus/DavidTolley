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


import com.google.gson.JsonParser;
import com.google.gson.JsonObject;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public abstract class HTTPRequest {

  private URL url;
  private String error;
  private String contentType;
  private String body;


  public boolean errorsOccurred() {
    if (getError() == null || getError().equals("") || getError().isEmpty()) {
      return false;
    } else {
      return true;
    }
  }

  public void setUrl(String urlString) {
    try {
      url = new URL(urlString);
    } catch (MalformedURLException e) {
      setError(e.toString());
    }
  }

  public URL getUrl() {
    return url;
  }

  public void setError(String errorString) {
    error = errorString;
  }

  public String getError() {
    return error;
  }

  public void setContentType(String content) {
    System.out.println(content);
    contentType = content;
  }

  public String getContentType() {
    return contentType;
  }

  public void processConnection(URLConnection conn) {
    setContentType(conn.getContentType());
    System.out.println(getContentType());
  }

  public void processContent(InputStream content) {
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(content));

      String line = null;
      StringBuffer sb = new StringBuffer();
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
      setBody(sb.toString());
    } catch (IOException e) {
      setError(e.toString());
    }
    System.out.println(getBody());
  }

  public void setBody(String bodyString) {
    body = bodyString;
  }

  public String getBody() {
    return body;
  }

  public JsonObject getJsonBody() {
    return (JsonObject) new JsonParser().parse(getBody());
  }
}
