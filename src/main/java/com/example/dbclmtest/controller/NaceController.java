package com.example.dbclmtest.controller;

import com.example.dbclmtest.dto.Nace;
import com.example.dbclmtest.service.NaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/naces")
@RequiredArgsConstructor
public class NaceController {
    private final NaceService naceService;

    @PostMapping(value = "/upload")
    public Flux<Nace> saveNaceDetails(@RequestPart("file") FilePart file) {
        return naceService.saveNaceDetails(file);
    }

    @GetMapping(value = "/{order}")
    public Mono<Nace> getNaceDetailsByOrder(@PathVariable int order) {
        // todo NOT FOUND
        return naceService.getNaceDetailsByOrder(order);
    }

    @GetMapping
    public Flux<Nace> getNaceDetailsByOrder() {
        return naceService.getAllNaceDetails();
    }
}
