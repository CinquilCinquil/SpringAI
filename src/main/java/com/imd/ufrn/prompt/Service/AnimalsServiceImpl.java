package com.imd.ufrn.prompt.Service;

import java.util.List;

import org.springframework.ai.document.Document;
import com.imd.ufrn.prompt.Utils.DocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import com.imd.ufrn.prompt.DAO.AnimalsDAO;

@Service
public class AnimalsServiceImpl implements AnimalsService {

    private final AnimalsDAO animalsDao;
    private final DocumentReader documentReader;

    public AnimalsServiceImpl(AnimalsDAO animalsDao, DocumentReader documentReader) {
        this.animalsDao = animalsDao;
        this.documentReader = documentReader;
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

    @Override
    public void loadDocument() {
        List<Document> documents = documentReader.loadText();
        TokenTextSplitter splitter = new TokenTextSplitter();
        List<Document> chunks = splitter.apply(documents);
        animalsDao.addDocs(chunks);
    }
    
}
