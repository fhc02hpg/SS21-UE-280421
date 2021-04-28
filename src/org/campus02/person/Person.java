package org.campus02.person;

import java.io.Serializable;

public class Person implements Serializable {

  private int id;
  private String firstname;
  private String lastname;

  public Person(int id, String firstname, String lastname) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
  }

  public int getId() {
    return id;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  @Override
  public String toString() {
    return "Person{" +
        "id=" + id +
        ", firstname='" + firstname + '\'' +
        ", lastname='" + lastname + '\'' +
        '}';
  }

}
