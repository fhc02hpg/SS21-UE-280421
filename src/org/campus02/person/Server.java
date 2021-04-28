package org.campus02.person;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.campus02.web.ClientHandler;

public class Server {

  public static void main(String[] args) {

    try(ServerSocket ss = new ServerSocket(1234)) {
      while(true) {
        System.out.println("waiting for client...");
        try {
          //diesen socket nicht in try-with-resources geben
          //weil sonst der socket automatisch geschlossen wird
          //und im thread mit dem client dann nicht mehr verfügbar ist
          Socket client = ss.accept();
          System.out.println("client connected -> handling logic");
          new Thread(new ClientCommunication(client)).start();
        } catch (Exception exc) {
          exc.printStackTrace();
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

}
