package org.campus02.web;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMT {

  public static void main(String[] args) {

    PageCache pc = new PageCache();
    pc.warmUp("data/demo_urls.txt");

    WebProxy webProxy = new WebProxy(pc);

    System.out.println("server socket on port 5678");
    try(ServerSocket ss = new ServerSocket(5678)) {
      while(true) {
        System.out.println("waiting for client...");
        try {
          //diesen socket nicht in try-with-resources geben
          //weil sonst der socket automatisch geschlossen wird
          //und im thread mit dem client dann nicht mehr verfügbar ist
          Socket client = ss.accept();
          System.out.println("client connected -> handling logic");
          new Thread(new ClientHandler(client,webProxy)).start();
        } catch (Exception exc) {
          exc.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
