package com.jupitertools.springdynamicpropertyresolver.integrationtests.testcontainers;

import com.jupitertools.springdynamicpropertyresolver.DynamicTestProperty;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = InitializeTestContainerExtensionAfterSpringExtensionTest.class)
@Testcontainers
class InitializeTestContainerExtensionAfterSpringExtensionTest {

	@Container
	private static PostgreSQLContainer postgresqlContainer = new PostgreSQLContainer();

	@DynamicTestProperty
	private static TestPropertyValues props() {
		return TestPropertyValues.of("jdbc=" + postgresqlContainer.getJdbcUrl());
	}

	@Value("${jdbc}")
	private String jdbc;

	@Test
	void container() {
		String pattern = String.format("^(jdbc:postgresql://%s:)(%d)(/test)$",
		                               postgresqlContainer.getContainerIpAddress(),
		                               postgresqlContainer.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT));

		assertThat(jdbc).containsPattern(pattern);
	}
}


