package org.campus02.web;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

  private Socket client;
  private WebProxy proxy;

  public ClientHandler(Socket client, WebProxy proxy) {
    this.client = client;
    this.proxy = proxy;
  }

  @Override
  public void run() {
    try(
        BufferedReader br = new BufferedReader(
            new InputStreamReader(client.getInputStream())
        );
        BufferedWriter bw = new BufferedWriter(
            new OutputStreamWriter(client.getOutputStream())
        );
      ) {
      String raw;
      while((raw = br.readLine())!=null) {

        if(raw.equals("bye")) {
          System.out.println("client wants to exit");
          break;
        }

        String[] cmd = raw.split(" ");

        if(cmd.length != 2) {
          bw.write("error: command invalid");
          bw.newLine(); //!!!
          bw.flush(); //!!!
          continue;
        }

        switch (cmd[0]) {
          case "fetch":
            try {
              WebPage wp = proxy.fetch(cmd[1]);
              bw.write(wp.getContent());
            } catch (UrlLoaderException e) {
              bw.write("error: loading the url failed");
            }
            break;
          case "stats":
            if(cmd[1].equals("hits")) {
              bw.write(proxy.statsHits());
            } else if(cmd[1].equals("misses")) {
              bw.write(proxy.statsMisses());
            } else {
              bw.write("error: command invalid");
            }
            break;
          default:
            bw.write("error: command invalid");
        }

        bw.newLine();
        bw.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


}
