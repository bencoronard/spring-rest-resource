package dev.hireben.demo.rest.resource.application.exception;

import dev.hireben.demo.rest.resource.application.model.ApplicationException;

public class InvalidUserInfoException extends ApplicationException {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public InvalidUserInfoException(String message) {
    super(message);
  }

}
