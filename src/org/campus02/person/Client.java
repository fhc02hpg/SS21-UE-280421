package org.campus02.person;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

  public static void main(String[] args) {

    try(Socket s = new Socket("localhost",1234);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        ObjectInputStream ois = new ObjectInputStream(s.getInputStream())
    ) {

      //GET 23
      bw.write("GET x");
      bw.newLine();
      bw.flush();

      //read object
      Person p23 = (Person) ois.readObject();
      System.out.println(p23);

      //GET 23
      bw.write("GETALL");
      bw.newLine();
      bw.flush();

      Person p;
      while((p = (Person) ois.readObject()) != null) {
        System.out.println(p);
      }

      //EXIT
      bw.write("EXIT");
      bw.newLine();
      bw.flush();

    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }


  }

}
