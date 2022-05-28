package com.example.dbclmtest.repository;

import com.example.dbclmtest.dto.Nace;
import com.example.dbclmtest.entity.NaceEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaceRepository extends ReactiveCrudRepository<NaceEntity, Integer> {

}
