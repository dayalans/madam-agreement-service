package com.telecom.b2b.customermicroservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SearchCriteria {

   // key — Represents the entity field name, i.e. title, genre, etc.
    // value — Represents the parameter value, i.e. Troy, Action, etc.
     //operation — Indicates the search operation, i.e. equality, match, comparison, etc.
    private String key;
    private Object value;
    private SearchOperation operation;
}