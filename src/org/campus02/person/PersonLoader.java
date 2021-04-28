package org.campus02.person;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class PersonLoader {

  private String pathToFile;

  public PersonLoader(String pathToFile) {
    this.pathToFile = pathToFile;
  }

  public ArrayList<Person> load() throws PersonLoadException {
    ArrayList<Person> list = new ArrayList<>();
    try(BufferedReader br = new BufferedReader(new FileReader(pathToFile))) {
      String line;
      while((line = br.readLine()) != null) {
        String[] csv = line.split(";");
        if(csv.length!=3) {
          throw new PersonLoadException("invalid csv line: "+line);
        }
        list.add(new Person(Integer.parseInt(csv[0]),csv[1],csv[2]));
      }
    } catch (IOException e) {
      throw new PersonLoadException("error: loading from file",e);
    }
    return list;
  }

}
