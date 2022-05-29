package com.example.dbclmtest.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("nace")
public final class NaceEntity {
    @Id
    @Column("id")
    private final int order;
    @Column("level_value")
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
