package com.imd.ufrn.prompt.Utils;

import java.util.List;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.document.Document;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.core.io.Resource;

@Component
public class DocumentReader {
    private final Resource resource;

    DocumentReader() {
        String filePath = "data/ANIMAL_BIOLOGY.pdf"; // Downloaded from archive.org
        this.resource = new FileSystemResource(filePath);
    }

    public List<Document> loadText() {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(this.resource);
        return tikaDocumentReader.read();
    }
}