package application.adapter.datastore;

import application.domain.Part;
import application.domain.PartsRepository;
import di.annotation.Component;

import java.util.List;

@Component
public class StaticPartsRepository implements PartsRepository {
    @Override
    public List<Part> findAll() {
        return List.of(
                Part.withName("steering wheel"),
                Part.withName("connecting rod"),
                Part.withName("suspension spring"),
                Part.withName("spark plug"));
    }
}
