package com.example.dbclmtest.service;

import com.example.dbclmtest.dto.Nace;
import com.example.dbclmtest.entity.NaceEntity;
import com.example.dbclmtest.repository.NaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaceService {
    private final NaceRepository naceRepository;

    public Nace getNaceDetailsByOrder(int order) {
        return naceRepository.findById(order)
                .map(this::mapToDto)
                .orElseThrow();
    }

    public List<Nace> getAllNaceDetails() {
        return naceRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    private Nace mapToDto(NaceEntity naceEntity) {
        return new Nace(
                naceEntity.getOrder(),
                naceEntity.getLevel(),
                naceEntity.getCode(),
                naceEntity.getParent(),
                naceEntity.getDescription(),
                naceEntity.getThisItemIncludes(),
                naceEntity.getThisItemAlsoIncludes(),
                naceEntity.getRulings(),
                naceEntity.getThisItemExcludes(),
                naceEntity.getReferenceToISIC()
        );
    }
}
