package com.fiap.tech.fiap_tech_challenge.common.infra.configuration.usecases;

import com.fiap.tech.fiap_tech_challenge.client.application.create.CreateClientUseCase;
import com.fiap.tech.fiap_tech_challenge.client.application.create.DefaultCreateClientUseCase;
import com.fiap.tech.fiap_tech_challenge.client.application.retrieve.get.DefaultGetClientByCpfUseCase;
import com.fiap.tech.fiap_tech_challenge.client.application.retrieve.get.GetClientByCpfUseCase;
import com.fiap.tech.fiap_tech_challenge.client.domain.ClientGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientUseCaseConfiguration {

    private final ClientGateway clientGateway;

    public ClientUseCaseConfiguration(ClientGateway clientGateway) {
        this.clientGateway = clientGateway;
    }
    @Bean
    public CreateClientUseCase createClientUseCase(){
        return new DefaultCreateClientUseCase(clientGateway);
    }
    @Bean
    public GetClientByCpfUseCase getClientByCpfUseCase(){
        return new DefaultGetClientByCpfUseCase(clientGateway);
    }


}
