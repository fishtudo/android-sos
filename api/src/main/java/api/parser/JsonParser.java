package api.parser;

import java.io.IOException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParser<T> {
    public T parse(String jSon, Class<T> classe) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        T objectMapped = mapper.readValue(jSon, classe);
        return objectMapped;
    }

    /**
     * To create a TypeReference use this example: new TypeReference<List<Version>>()
     *
     * @param jSon
     * @param typedReference
     * @return
     * @throws IOException
     */
    public T parse(String jSon, TypeReference<T> typedReference) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        T objectMapped = mapper.readValue(jSon, typedReference);
        return objectMapped;
    }
}
