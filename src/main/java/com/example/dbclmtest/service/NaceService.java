package com.example.dbclmtest.service;

import com.example.dbclmtest.dto.Nace;
import com.example.dbclmtest.entity.NaceEntity;
import com.example.dbclmtest.repository.NaceRepository;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class NaceService {
    private final NaceRepository naceRepository;

    public Flux<Nace> saveNaceDetails(FilePart file) {
        final CsvMapper mapper = new CsvMapper();

        return file.content()
                .map(db -> db.asByteBuffer().array())
                .map(String::new)
                .collect(Collectors.joining())
                .flatMap(content -> {
                    try(MappingIterator<Nace> iterator = mapper
                            .readerFor(Nace.class)
                            .with(CsvSchema.emptySchema().withHeader())
                            .readValues(content)) {

                        return Mono.just(iterator.readAll());
                    } catch (IOException e) {
                        return Mono.error(e);
                    }
                })
                .log()
                .flatMapMany(Flux::fromIterable)
                .filter(nace -> Objects.nonNull(nace.order()))
                .map(this::mapToEntity)
                .flatMap(naceRepository::insert)
                .map(this::mapToDto);
    }

    public Mono<Nace> getNaceDetailsByOrder(int order) {
        return naceRepository.findNaceDetailsById(order)
                .map(this::mapToDto);
    }

    public Flux<Nace> getAllNaceDetails() {
        return naceRepository.findAllNaceDetails()
                .map(this::mapToDto);
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

    private NaceEntity mapToEntity(Nace nace) {
        return new NaceEntity(
                nace.order(),
                nace.level(),
                nace.code(),
                nace.parent(),
                nace.description(),
                nace.thisItemIncludes(),
                nace.thisItemAlsoIncludes(),
                nace.rulings(),
                nace.thisItemExcludes(),
                nace.referenceToISIC()
        );
    }
}
