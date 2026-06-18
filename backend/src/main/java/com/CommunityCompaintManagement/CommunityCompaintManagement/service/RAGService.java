package com.CommunityCompaintManagement.CommunityCompaintManagement.service;

import jakarta.annotation.PostConstruct;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.ai.embedding.EmbeddingModel;

import jakarta.annotation.PostConstruct;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RAGService {
    private final EmbeddingModel embeddingModel;
    private SimpleVectorStore vectorStore;

    @Value("${file.path}")
    private Resource pdfFile;

    public RAGService(EmbeddingModel embeddingModel) {
        this.embeddingModel = embeddingModel;
    }

    @PostConstruct
    public void init() {
        try {
            vectorStore = SimpleVectorStore.builder(embeddingModel).build();
            String text = extractPdfText(pdfFile);
            Document document = new Document(text);
            vectorStore.accept(List.of(document));
            System.out.println("✅ PDF loaded into vector store successfully!");
        } catch (Exception e) {
            throw new RuntimeException("Error initializing RAG service", e);
        }
    }

    private String extractPdfText(Resource pdfFile) throws IOException {
        try (PDDocument document = PDDocument.load(pdfFile.getFile())) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }

    public String retrieveRelevantText(String query) {
        return vectorStore.similaritySearch(query)
                .stream()
                .map(Document::getText)
                .collect(Collectors.joining("\n"));
    }
}
