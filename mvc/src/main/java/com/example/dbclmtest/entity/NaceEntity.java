package com.example.dbclmtest.entity;

import lombok.Data;
//import org.hibernate.annotations.Table;
import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Table;

@Data
@Table(schema = "nace")
public final class NaceEntity {
    @Id
    @Column(name = "id")
    private final int order;
    @Column(name = "level_value")
    private final int level;
    private final String code;
    private final String parent;
    private final String description;
    private final String thisItemIncludes;
    private final String thisItemAlsoIncludes;
    private final String rulings;
    private final String thisItemExcludes;
    private final String referenceToISIC;
}
