package com.example.payments.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public abstract class IntegrationTestBase {

  public static final String MYSQL_DOCKER_IMAGE = "mysql:8.0";

  private static final MySQLContainer<?> MYSQL_CONTAINER = initMySqlContainer();

  @DynamicPropertySource
  public static void registerPgProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);

    registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
  }

  private static MySQLContainer<?> initMySqlContainer() {
    MySQLContainer<?> result = new MySQLContainer<>(DockerImageName.parse(MYSQL_DOCKER_IMAGE));

    Runtime.getRuntime().addShutdownHook(new Thread(
        () -> {
          log.info("Stopping {} container", result.getContainerName());
          result.stop();
        }));

    result.start();
    return result;
  }
}
