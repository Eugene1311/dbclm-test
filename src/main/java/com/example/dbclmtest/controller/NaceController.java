package com.example.dbclmtest.controller;

import com.example.dbclmtest.dto.Nace;
import com.example.dbclmtest.service.NaceService;
import io.r2dbc.spi.R2dbcDataIntegrityViolationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/naces")
@RequiredArgsConstructor
public class NaceController {
    private final NaceService naceService;

    @Operation(summary = "Upload file with NACE details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "NACE details are stored, returns number of updated rows",
                    content = { @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400", content = @Content) }
    )
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<Integer> saveNaceDetails(@RequestPart("file") FilePart file) {
        return naceService.saveNaceDetails(file)
                .onErrorMap(err -> {
                    if (err instanceof R2dbcDataIntegrityViolationException) {
                        return new ResponseStatusException(HttpStatus.BAD_REQUEST, err.getMessage());
                    }
                    return err;
                });
    }

    @Operation(summary = "Get a NACE by its id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the NACE",
                content = { @Content(mediaType = "application/json",
                        schema = @Schema(implementation = Nace.class)) }),
        @ApiResponse(responseCode = "404", description = "NACE not found",
                content = @Content) }
    )
    @GetMapping(value = "/{order}")
    public Mono<Nace> getNaceDetailsByOrder(@PathVariable int order) {
        return naceService.getNaceDetailsByOrder(order)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "NACE not found")));
    }

    @Operation(summary = "Get all NACEs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all NACEs",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Nace.class)) }),
            @ApiResponse(responseCode = "404", description = "NACEs not found",
                    content = @Content) })
    @GetMapping
    public Flux<Nace> getAllNaceDetails() {
        return naceService.getAllNaceDetails()
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "NACEs not found")));
    }
}
