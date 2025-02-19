package dev.hireben.demo.rest.resource.application.exception;

import dev.hireben.demo.rest.resource.application.model.ApplicationException;

public class ResourceNotFoundException extends ApplicationException {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public ResourceNotFoundException(String message) {
    super(message);
  }

}
