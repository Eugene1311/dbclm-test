package com.example.dbclmtest.controller;

import com.example.dbclmtest.dto.Nace;
import com.example.dbclmtest.service.NaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/naces")
@RequiredArgsConstructor
public class NaceController {
    private final NaceService naceService;

    @GetMapping("/{order}")
    public Nace getNaceDetailsByOrder(@PathVariable int order) {
        return naceService.getNaceDetailsByOrder(order);
    }

    @GetMapping
    public List<Nace> getAllNaceDetails() {
        return naceService.getAllNaceDetails();
    }
}
