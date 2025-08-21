package com.imd.ufrn.prompt.DAO;

import java.util.List;

public interface AnimalsDAO {
    void add(List<String> animals);
    List<String> findClosestMatches(String query, int numberOfMatches);
    String findClosestMatch(String query);
}
