package org.campus02.person;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ClientCommunication implements Runnable {

  private Socket client;

  public ClientCommunication(Socket client) {
    this.client = client;
  }

  @Override
  public void run() {
    try {
      ArrayList<Person> list = new PersonLoader("data/persons.csv").load();
      try(BufferedReader br = new BufferedReader(
          new InputStreamReader(client.getInputStream()));
          ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
      ) {
        String line;
        while((line=br.readLine())!=null) {

          if("EXIT".equals(line)) {
            System.out.println("client wants to quit");
            break;
          }

          if("GETALL".equals(line)) {
            for(Person p : list) {
              oos.writeObject(p);
            }
            oos.writeObject(null);
          } else {
            String[] cmd = line.split(" ");
            if(cmd.length!=2) {
              System.out.println("unknown command");
              oos.writeObject(null);
            } else {
              if("GET".equals(cmd[0])) {
                int id = Integer.parseInt(cmd[1]); //NumberFormatException sollte behandelt werden war aber nicht in der Angabe
                boolean found = false;
                for(Person p : list) {
                  if(p.getId() == id) {
                    oos.writeObject(p);
                    found = true;
                  }
                }
                if(!found) {
                  oos.writeObject(null);
                }
              } else {
                System.out.println("unknown command");
                oos.writeObject(null);
              }
            }
          }

          oos.flush();

        }

      } catch (IOException e) {
        e.printStackTrace();
      }
    } catch (PersonLoadException e) {
      e.printStackTrace();
    }
  }

}
