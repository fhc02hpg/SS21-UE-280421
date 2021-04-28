package org.campus02.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

  public static void main(String[] args) {

    PageCache pc = new PageCache();
    pc.warmUp("data/demo_urls.txt");

    //System.out.println(pc.getCache());

    WebProxy webProxy = new WebProxy(pc);

    System.out.println("server socket on port 5678");
    try(ServerSocket ss = new ServerSocket(5678)) {
      while(true) {
        System.out.println("waiting for client...");
        try(Socket client = ss.accept()) {
          System.out.println("client connected -> handling logic");
          new ClientHandler(client,webProxy).run();
        } catch (Exception exc) {
         exc.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
