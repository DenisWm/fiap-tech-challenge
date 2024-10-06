package com.fiap.tech;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.fiap.tech.client.application.create.CreateClientCommand;
import com.fiap.tech.client.application.create.CreateClientUseCase;
import org.springframework.boot.actuate.autoconfigure.metrics.export.prometheus.PrometheusMetricsExportAutoConfiguration;

@SpringBootApplication
public class FiapTechChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FiapTechChallengeApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo() {
		return (args) -> {
			// Adicionando um cliente de teste
			CreateClientCommand newClient = new CreateClientCommand("Usuario Teste",
					"usuarioteste@hotmail.com", "185.482.357-48");
		};
	}
}
