package org.example.tpo_10.repository;

import org.example.tpo_10.model.URL;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface URLRepository extends CrudRepository<URL, String> {
}
