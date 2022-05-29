package com.example.dbclmtest.repository;

import com.example.dbclmtest.entity.NaceEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class NaceRepository {
    private final R2dbcEntityTemplate template;

    public Mono<NaceEntity> insert(NaceEntity nace) {
        return template.insert(nace);
    }

    public Mono<NaceEntity> findNaceDetailsById(int id) {
        return template.selectOne(
            Query.query(Criteria.where("id").is(id)), NaceEntity.class
        );
    }

    public Flux<NaceEntity> findAllNaceDetails() {
        return template.select(NaceEntity.class).all();
    }
}
