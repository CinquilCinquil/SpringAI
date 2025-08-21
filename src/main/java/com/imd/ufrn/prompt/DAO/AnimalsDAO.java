package com.imd.ufrn.prompt.DAO;

import java.util.List;

import org.springframework.ai.document.Document;

public interface AnimalsDAO {
    void addDocs(List<Document> chunks);
    void add(List<String> animals);
    List<String> findClosestMatches(String query, int numberOfMatches);
    String findClosestMatch(String query);
}
