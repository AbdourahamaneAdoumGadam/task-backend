package com.fad.tasktracker.entities.others;

import com.fad.tasktracker.entities.Cart;
import com.fad.tasktracker.entities.CartItem;
import com.fad.tasktracker.entities.Product;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CartSerializer extends StdSerializer<Cart> {

    public CartSerializer() {
        this(null);
    }

    public CartSerializer(Class<Cart> t) {
        super(t);
    }

    @Override
    public void serialize(Cart cart, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("id", cart.getId().toString());
        jsonGenerator.writeArrayFieldStart("items");
        for (CartItem item : cart.getItems()) {
            jsonGenerator.writeStartObject();
            jsonGenerator.writeStringField("id", item.getId().toString());
            jsonGenerator.writeNumberField("quantity", item.getQuantity());
            // Serialize Product object
            jsonGenerator.writeObjectField("product", item.getProduct());
            jsonGenerator.writeEndObject();
        }
        jsonGenerator.writeEndArray();
        jsonGenerator.writeEndObject();
    }
}