package dev.hireben.demo.rest.resource.presentation.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.hireben.demo.rest.resource.application.usecase.CreateResourceUseCase;
import dev.hireben.demo.rest.resource.application.usecase.DeleteResourceUseCase;
import dev.hireben.demo.rest.resource.application.usecase.RetrieveResourceUseCase;
import dev.hireben.demo.rest.resource.application.usecase.UpdateResourceUseCase;
import dev.hireben.demo.rest.resource.domain.repository.ResourceRepository;

@Configuration
public class UseCaseConfig {

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Bean
  CreateResourceUseCase createResourceUseCase(ResourceRepository resourceRepository) {
    return new CreateResourceUseCase(resourceRepository);
  }

  // ---------------------------------------------------------------------------//

  @Bean
  RetrieveResourceUseCase retrieveResourceUseCase(ResourceRepository resourceRepository) {
    return new RetrieveResourceUseCase(resourceRepository);
  }

  // ---------------------------------------------------------------------------//

  @Bean
  UpdateResourceUseCase updateResourceUseCase(ResourceRepository resourceRepository) {
    return new UpdateResourceUseCase(resourceRepository);
  }

  // ---------------------------------------------------------------------------//

  @Bean
  DeleteResourceUseCase deleteResourceUseCase(ResourceRepository resourceRepository) {
    return new DeleteResourceUseCase(resourceRepository);
  }

}
