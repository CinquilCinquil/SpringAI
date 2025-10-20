package com.imd.ufrn.prompt.Utils;

import java.util.List;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.document.Document;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

@Component
public class DocumentReader {

    public List<Document> loadText(String filepath) {
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(
            new FileSystemResource(filepath));
        return tikaDocumentReader.read();
    }
}