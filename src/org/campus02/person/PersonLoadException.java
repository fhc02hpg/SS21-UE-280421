package org.campus02.person;

public class PersonLoadException extends Exception {

  public PersonLoadException(String message) {
    super(message);
  }

  public PersonLoadException(String message, Throwable cause) {
    super(message, cause);
  }

}
