package ru.gb.retrofit.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import ru.gb.retrofit.dto.CategoryDto;
import ru.gb.retrofit.dto.ProductDto;

import java.util.ArrayList;
import java.util.List;

    @Data
    public class GetCategoryResponse {
        @JsonProperty("id")
        private Integer id;
        @JsonProperty("title")
        private String title;
        @JsonProperty("products")
        private List<CategoryDto> products = new ArrayList<>();
    }

