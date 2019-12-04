package ru.itpark.implementation.service;

import lombok.RequiredArgsConstructor;
import ru.itpark.framework.annotation.Component;
import ru.itpark.implementation.domain.Auto;
import ru.itpark.implementation.repository.AutoRepository;

import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class AutoService {
    private final AutoRepository repository;

    public List<Auto> getAll() throws SQLException {
        return repository.getAll();
    }

    public void create(String name, String description, String image) {
        repository.create(name, description, image);
    }

    public List<Auto> search(String text) {
        return repository.search(text);
    }
}
