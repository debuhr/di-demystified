package application.domain;

import java.util.List;

public interface PartsRepository {
    List<Part> findAll();
}
