package com.walladog.walladog.models.apiservices;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Created by hadock on 20/12/15.
 *
 */
public class GenericDeserializer<T> implements JsonDeserializer<T> {

    public GenericDeserializer(T detailClass) {

    }

    @Override
    public T deserialize(JsonElement je, Type type, JsonDeserializationContext context) throws JsonParseException {
        // Get the "content" element from the parsed JSON
        JsonElement genericObject = je.getAsJsonObject().get("detail");

        // Deserialize it. You use a new instance of Gson to avoid infinite recursion
        // to this deserializer
        return new Gson().fromJson(genericObject, type);
    }
}
