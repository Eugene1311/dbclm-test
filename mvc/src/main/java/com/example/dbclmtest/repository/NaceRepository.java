package com.example.dbclmtest.repository;

import com.example.dbclmtest.entity.NaceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NaceRepository extends JpaRepository<NaceEntity, Integer> {
}
