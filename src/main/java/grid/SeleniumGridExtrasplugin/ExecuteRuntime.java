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


import java.io.IOException;
import java.io.InputStream;

public class ExecuteRuntime {

  public static String execRuntime(String cmd, boolean waitToFinish){
    Process process;

    try {
      process = Runtime.getRuntime().exec(cmd);
    } catch (IOException e) {
      e.printStackTrace();
      return "";
    }

    int exitCode;
    if (waitToFinish) {
      try {
        System.out.println("Waiting to finish");
        exitCode = process.waitFor();
        System.out.println("Command Finished -" + exitCode );
      } catch (InterruptedException e) {
        e.printStackTrace();
        return "";
      }
    } else {
      System.out.println("Not waiting for finish");

      return "Not waiting for finish";
    }

    try {
      String output = inputStreamToString(process.getInputStream());
      String error = inputStreamToString(process.getErrorStream());

      if (!error.equals("")){
        System.out.println("Error " + error);
        return "";
      }

      return output;
    } catch (IOException e) {
      e.printStackTrace();
      return "";

    } finally {
      process.destroy();
    }
  }

  public static String inputStreamToString(InputStream is) throws IOException {
    StringBuilder result = new StringBuilder();
    int in;
    while ((in = is.read()) != -1) {
      result.append((char) in);
    }
    is.close();
    return result.toString();
  }

}
