package ru.gaew.springcourse.catalogueservice.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewProductPayload(

        @NotNull
        @Size(min = 3, max = 50, message = "{catalogue.products.create.errors.title_is_invalid}")
        String title,

        @Size(max = 1000)
        String details) {
}
