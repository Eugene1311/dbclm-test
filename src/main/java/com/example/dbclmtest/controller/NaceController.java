package com.example.dbclmtest.controller;

import com.example.dbclmtest.entity.Nace;
import com.example.dbclmtest.service.NaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/naces")
@RequiredArgsConstructor
public class NaceController {
    private final NaceService naceService;

    @PostMapping(value = "/upload")
    public Flux<Nace> saveNaceDetails(@RequestPart("file") FilePart file) {
        System.out.println(file);
        return naceService.saveNaceDetails(file);
    }
}
