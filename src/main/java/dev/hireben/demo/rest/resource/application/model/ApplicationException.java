package dev.hireben.demo.rest.resource.application.model;

public abstract class ApplicationException extends RuntimeException {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  protected ApplicationException(String message) {
    super(message);
  }

}
