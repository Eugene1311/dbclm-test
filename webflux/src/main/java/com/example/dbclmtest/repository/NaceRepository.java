package com.example.dbclmtest.repository;

import com.example.dbclmtest.entity.NaceEntity;
import io.r2dbc.spi.Batch;
import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NaceRepository {
    private final R2dbcEntityTemplate template;
    private final ConnectionFactory connectionFactory;

    public Mono<NaceEntity> insert(NaceEntity nace) {
        return template.insert(nace);
    }

    public Mono<Integer> insertMany(List<NaceEntity> naces) {
        return Mono.from(connectionFactory.create())
                .flatMapMany(connection -> {
                    Batch batch = connection.createBatch();
                    naces.forEach(nace ->
                        batch.add("INSERT INTO nace(" +
                                "id," +
                                "level_value," +
                                "code," +
                                "parent," +
                                "description," +
                                "this_item_includes," +
                                "this_item_also_includes," +
                                "rulings," +
                                "this_item_excludes," +
                                "reference_to_ISIC" +
                            ") values (" +
                                nace.getOrder() + ", " +
                                nace.getLevel() + ", " +
                                "'" + nace.getCode() + "', " +
                                "'" + nace.getParent() + "', " +
                                "'" + nace.getDescription() + "', " +
                                "'" + nace.getThisItemIncludes() + "', " +
                                "'" + nace.getThisItemAlsoIncludes() + "', " +
                                "'" + nace.getRulings() + "', " +
                                "'" + nace.getThisItemExcludes() + "', " +
                                "'" + nace.getReferenceToISIC() + "'" +
                            ")"
                        )
                    );
                    return Flux.from(batch.execute())
                            .doFinally(st -> connection.close());
                })
                .flatMap(result -> Mono.from(result.getRowsUpdated()))
                .collectList()
                .map(List::size)
                .log();
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
