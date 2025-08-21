package com.imd.ufrn.prompt.Service;

import java.util.List;

import com.imd.ufrn.prompt.DataTypes.Animal;

public interface ChatService {
    String getAnswer(String question);
    List<Animal> getAnimals(String question);
}