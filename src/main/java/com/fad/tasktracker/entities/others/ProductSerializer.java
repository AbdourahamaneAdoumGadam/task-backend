package com.fad.tasktracker.entities.others;

import com.fad.tasktracker.entities.Product;
import com.fad.tasktracker.entities.ProductCategory;
import com.fad.tasktracker.entities.ProductImage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ProductSerializer extends JsonSerializer<Product> {

    @Override
    public void serialize(Product product, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", product.getId().toString());
        jsonGenerator.writeStringField("name", product.getName());
        jsonGenerator.writeStringField("description", product.getDescription());
        jsonGenerator.writeNumberField("price", product.getPrice());
        jsonGenerator.writeNumberField("quantity", product.getQuantity());
        jsonGenerator.writeArrayFieldStart("category");
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", product.getCategory().getId().toString());
        jsonGenerator.writeStringField("name", product.getCategory().getName());
        jsonGenerator.writeStringField("image", product.getCategory().getImage());
        jsonGenerator.writeEndObject();
        jsonGenerator.writeEndArray();
        jsonGenerator.writeBooleanField("available", product.isAvailable());

        // Other fields...
        jsonGenerator.writeArrayFieldStart("images");
        for (ProductImage image : product.getImages()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("id", image.getId().toString());
            jsonGenerator.writeStringField("url", image.getUrl());
            // Other fields...
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}
