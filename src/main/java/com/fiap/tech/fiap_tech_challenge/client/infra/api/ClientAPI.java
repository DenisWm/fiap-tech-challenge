package com.fiap.tech.fiap_tech_challenge.client.infra.api;

import com.fiap.tech.fiap_tech_challenge.client.infra.models.ClientResponse;
import com.fiap.tech.fiap_tech_challenge.client.infra.models.CreateClientRequest;
import com.fiap.tech.fiap_tech_challenge.product.infra.models.CreateProductRequest;
import com.fiap.tech.fiap_tech_challenge.product.infra.models.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping (value = "clients")
@Tag (name = "Clients")

public interface ClientAPI {
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Create a new client")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully"),
            @ApiResponse(responseCode = "422", description = "A validation error was thrown"),
            @ApiResponse(responseCode = "500", description = "An internal error was thrown"),
    })
    ResponseEntity<?> createClient(@RequestBody CreateClientRequest input);

    @GetMapping(
            value = "{cpf}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(summary = "Get a client by it's cpf")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Client retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Client was not found"),
            @ApiResponse(responseCode = "500", description = "An internal error was thrown"),
    })
    ClientResponse getByCpf(@PathVariable(name = "cpf") String cpf);

}
