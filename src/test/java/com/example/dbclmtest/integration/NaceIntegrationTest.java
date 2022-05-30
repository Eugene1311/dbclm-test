package com.example.dbclmtest.integration;

import com.example.dbclmtest.controller.NaceController;
import com.example.dbclmtest.dto.Nace;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
public class NaceIntegrationTest {
    @Autowired
    private DatabaseClient databaseClient;
    @Autowired
    private WebTestClient webClient;

    @BeforeEach
    private void setUp() {
        // create table on the first run
        databaseClient.sql("""
                            CREATE TABLE IF NOT EXISTS nace (
                                id INTEGER PRIMARY KEY,
                                level_value INTEGER,
                                code VARCHAR(16),
                                parent VARCHAR(16),
                                description TEXT,
                                this_item_includes TEXT,
                                this_item_also_includes TEXT,
                                rulings TEXT,
                                this_item_excludes TEXT,
                                reference_to_ISIC VARCHAR(16)
                            );
                        """)
                .then().block();
        // delete all data from previous run
        databaseClient.sql("truncate table nace")
                .then().block();
        // insert mock data
        databaseClient.sql("""
                        INSERT INTO nace VALUES (
                            1,
                            1,
                            'A',
                            'qqq',
                            'qqq',
                            'qqq',
                            'qqq',
                            'qqq',
                            'qqq',
                            'A'
                        );
                        """)
                .then().block();
    }

    @Test
    @DisplayName("should upload file and store data in db")
    public void testFileUpload() {
        var expected = List.of(
                new Nace(
                        398481,
                        1,
                        "A",
                        "",
                        "AGRICULTURE, FORESTRY AND FISHING",
                        "This section includes the exploitation of vegetal and animal natural resources, comprising the activities of growing of crops,"
                                + " raising and breeding of animals, harvesting of timber and other plants, animals or animal products from a farm or their natural habitats.",
                        "",
                        "",
                        "",
                        "A"
                )
        );

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", new ClassPathResource("files/test.csv"));

        webClient.post().uri("/naces/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .bodyValue(builder.build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectAll(responseSpec ->
                        responseSpec.expectBodyList(Nace.class).isEqualTo(expected)
                );
    }

    @Test
    @DisplayName("should fetch Nace by Order")
    public void testGetNaceByOrder() {
        var order = 1;
        var expected = new Nace(
                order,
                1,
                "A",
                "qqq",
                "qqq",
                "qqq",
                "qqq",
                "qqq",
                "qqq",
                "A"
        );

        webClient.get().uri("/naces/" + order)
                .exchange()
                .expectStatus()
                .isOk()
                .expectAll(responseSpec ->
                        responseSpec.expectBody(Nace.class).isEqualTo(expected)
                );
    }
}
