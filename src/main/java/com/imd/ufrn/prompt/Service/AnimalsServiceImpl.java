package com.imd.ufrn.prompt.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imd.ufrn.prompt.DAO.AnimalsDAO;

@Service
public class AnimalsServiceImpl implements AnimalsService {

    private final AnimalsDAO animalsDao;

    public AnimalsServiceImpl(AnimalsDAO animalsDao) {
        this.animalsDao = animalsDao;
    }

    @Override
    public void save(List<String> animals) {
        animalsDao.add(animals);
    }

    @Override
    public List<String> findClosestMatches(String query) {
        return animalsDao.findClosestMatches(query, 5);
    }

    @Override
    public String findClosestMatch(String query) {
        return animalsDao.findClosestMatch(query);
    }
    
}
