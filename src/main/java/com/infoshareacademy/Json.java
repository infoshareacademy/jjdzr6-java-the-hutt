package com.infoshareacademy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;



public class Json {

    public static void writeJson(Object object, String name) throws IOException {
        Path path = Path.of("src", "resources", name);
        File file = new File(path.toString());
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(file, object);
    }

}
