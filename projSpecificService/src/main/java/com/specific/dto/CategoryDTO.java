package com.specific.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

import com.specific.model.Product;

@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class CategoryDTO implements Serializable {

    private Long id;

    @NonNull
    @NotNull(message = "Name is mandatory")
    private String name;

    @JsonIgnore
    private Set<Product> products;

}