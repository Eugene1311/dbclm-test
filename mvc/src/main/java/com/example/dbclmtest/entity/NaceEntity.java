package com.example.dbclmtest.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity(name = "nace")
@Table(name = "nace")
public final class NaceEntity {
    @Id
    @Column(name = "id")
    private int order;
    @Column(name = "level_value")
    private int level;
    private String code;
    private String parent;
    private String description;
    private String thisItemIncludes;
    private String thisItemAlsoIncludes;
    private String rulings;
    private String thisItemExcludes;
    @Column(name = "reference_to_isic")
    private String referenceToISIC;
}
