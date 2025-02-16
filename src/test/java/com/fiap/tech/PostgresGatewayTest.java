package com.fiap.tech;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test-integration")
@DataJpaTest
@ComponentScan(
        basePackages = "com.fiap.tech",
        useDefaultFilters = false,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*PostgresGateway")
        }
)
@ExtendWith(PostgresCleanUpExtension.class)
public @interface PostgresGatewayTest {


}
