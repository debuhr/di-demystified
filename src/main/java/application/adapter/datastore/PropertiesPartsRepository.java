package application.adapter.datastore;

import application.domain.Part;
import application.domain.PartsRepository;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class PropertiesPartsRepository implements PartsRepository {
    @Override
    public List<Part> findAll() {
        try {
            String[] partNames = loadProperties().getProperty("parts").split(",");
            return Arrays.stream(partNames)
                    .map(Part::withName)
                    .toList();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private @NotNull Properties loadProperties() throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("parts.properties");
        Properties properties = new Properties();
        properties.load(stream);
        return properties;
    }
}
