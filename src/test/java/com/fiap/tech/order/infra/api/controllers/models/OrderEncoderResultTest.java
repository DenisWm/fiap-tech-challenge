package com.fiap.tech.order.infra.api.controllers.models;

import com.fiap.tech.order.infra.api.controllers.IdUtils;
import com.fiap.tech.order.infra.models.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.io.IOException;

@JacksonTest
public class OrderEncoderResultTest {
    @Autowired
    private JacksonTester<OrderEncoderResult> json;

    @Test
    public void testUnmarshallSuccessResult() throws IOException {
        //given
        final var expectedId = IdUtils.uuid();
        final var expectedOutputBucket = "codeeducationtest";
        final var expectedStatus = "COMPLETED";
        final var expectedEncoderOrderFolder = "anyFolder";
        final var expectedResourceId = IdUtils.uuid();
        final var expectedFilePath = "any.mp4";
        final var expectedMetadata = new OrderMetadata(expectedEncoderOrderFolder, expectedResourceId, expectedFilePath);

        final var json = """
                {
                "id": "%s",
                "output_bucket_path": "%s",
                "status": "%s",
                "order": {
                    "encoded_order_folder": "%s",
                    "resource_id": "%s",
                    "file_path": "%s"
                }
                """.formatted(expectedId, expectedOutputBucket, expectedStatus,
                expectedEncoderOrderFolder, expectedResourceId, expectedFilePath);
        //when
        final var actualResult = this.json.parse(json);

        Assertions.assertThat(actualResult)
                .isInstanceOf(OrderEncoderCompleted.class)
                .hasFieldOrPropertyWithValue("id", expectedId)
                .hasFieldOrPropertyWithValue("outputBucket", expectedOutputBucket)
                .hasFieldOrPropertyWithValue("order", expectedMetadata);
    }

    @Test
    public void testMarshallSuccessResult() throws IOException {
        //given
        final var expectedId = IdUtils.uuid();
        final var expectedOutputBucket = "codeeducationtest";
        final var expectedStatus = "COMPLETED";
        final var expectedEncoderOrderFolder = "anyFolder";
        final var expectedResourceId = IdUtils.uuid();
        final var expectedFilePath = "any.mp4";
        final var expectedMetadata = new OrderMetadata(expectedEncoderOrderFolder, expectedResourceId, expectedFilePath);
        final var aResult = new OrderEncoderCompleted(expectedId, expectedOutputBucket, expectedMetadata);

        //when
        final var actualResult = this.json.write(aResult);

        Assertions.assertThat(actualResult)
                .hasJsonPathValue("$.id", expectedId)
                .hasJsonPathValue("$.output_bucket_path", expectedOutputBucket)
                .hasJsonPathValue("$.status", expectedStatus)
                .hasJsonPathValue("$.order_encoded_order_folder", expectedEncoderOrderFolder)
                .hasJsonPathValue("$.order_resource_id", expectedResourceId)
                .hasJsonPathValue("$.order_file_path", expectedFilePath);
    }

    @Test
    public void testUnmarshallErrorResult() throws IOException {
        //given
        final var expectedMessage = "Resource Not Found";
        final var expectedStatus = "ERROR";
        final var expectedResourceId = IdUtils.uuid();
        final var expectedFilePath = "any.mp4";
        final var expectedOrderMessage = new OrderMessage(expectedResourceId, expectedFilePath);

        final var json = """
                {
                "status": "%s",
                "error": "%s",
                "message": {
                    "resource_id": "%s",
                    "file_path": "%s"
                }
                """.formatted(expectedStatus, expectedMessage, expectedResourceId, expectedFilePath);
        //when
        final var actualResult = this.json.parse(json);

        Assertions.assertThat(actualResult)
                .isInstanceOf(OrderEncoderErro.class)
                .hasFieldOrPropertyWithValue("error", expectedMessage)
                .hasFieldOrPropertyWithValue("message", expectedOrderMessage);
    }

    public void testMarshallErrorResult() throws IOException {
        //given
        final var expectedMessage = "Resource Not Found";
        final var expectedStatus = "ERROR";
        final var expectedResourceId = IdUtils.uuid();
        final var expectedFilePath = "any.mp4";
        final var expectedOrderMessage = new OrderMessage(expectedResourceId, expectedFilePath);
        final var aResult = new OrderEncoderErro(expectedOrderMessage, expectedMessage);

        //when
        final var actualResult = this.json.write(aResult);

        Assertions.assertThat(actualResult)
                .hasJsonPathValue("$.status",expectedStatus)
                .hasJsonPathValue("$.error", expectedMessage)
                .hasJsonPathValue("$.message_resource_id", expectedResourceId)
                .hasJsonPathValue("$.order_file_path", expectedFilePath);
    }
}
