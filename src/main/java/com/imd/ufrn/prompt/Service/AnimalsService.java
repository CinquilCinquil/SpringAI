package com.imd.ufrn.prompt.Service;

import java.util.List;

public interface AnimalsService {
    void save(List<String> animals);
    List<String> findClosestMatches(String query);
    String findClosestMatch(String query);
}
