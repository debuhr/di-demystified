package application.adapter.datastore;

import application.domain.Part;
import application.domain.PartsRepository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CsvPartsRepository implements PartsRepository {
    @Override
    public List<Part> findAll() {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("parts.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        List<String> lines = reader.lines().toList();

        // Behold: the worst CSV parser of all time:
        return lines.stream()
                .skip(1) // skip header line
                .map(line -> line.split(";")[1])
                .map(Part::withName)
                .toList();
    }
}
