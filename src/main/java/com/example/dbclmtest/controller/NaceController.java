package com.example.dbclmtest.controller;

import com.example.dbclmtest.dto.Nace;
import com.example.dbclmtest.service.NaceService;
import io.r2dbc.spi.R2dbcDataIntegrityViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    @PostMapping(value = "/upload")
    public Mono<Integer> saveNaceDetails(@RequestPart("file") FilePart file) {
        return naceService.saveNaceDetails(file)
                .onErrorMap(err -> {
                    if (err instanceof R2dbcDataIntegrityViolationException) {
                        return new ResponseStatusException(HttpStatus.BAD_REQUEST, err.getMessage());
                    }
                    return err;
                });
    }

    @GetMapping(value = "/{order}")
    public Mono<Nace> getNaceDetailsByOrder(@PathVariable int order) {
        return naceService.getNaceDetailsByOrder(order)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Nace not found")));
    }

    @GetMapping
    public Flux<Nace> getNaceDetailsByOrder() {
        return naceService.getAllNaceDetails()
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Naces not found")));
    }
}
