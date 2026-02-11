package com.testApi.restapi.model;

import tools.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Component
public class FileWorker {
    private final Random random = new Random();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String filePathForRead = "C:\\Users\\user\\Desktop\\filesfortest\\jsonforget.txt";
    private final String filePathForWrite = "C:\\Users\\user\\Desktop\\filesfortest\\jsonfwithdatafromdb.txt";


    public Map<String, String> getRandomUserFromFile() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePathForRead));
        String randomLine = lines.get(random.nextInt(lines.size()));
        return objectMapper.readValue(randomLine, Map.class);
    }

    public synchronized void writeUserToFile(User user) throws IOException {
        String userJSON = objectMapper.writeValueAsString(user);
        Files.writeString(
                Paths.get(filePathForWrite),
                userJSON + System.lineSeparator(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }
}