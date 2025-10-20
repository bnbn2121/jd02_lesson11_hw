package com.edu.less08.dao.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.NewsContextStorageDao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class NewsContextStorageDaoFile implements NewsContextStorageDao {
    private String storagePath = "content_storage";

    public NewsContextStorageDaoFile() {
        createDirectories();
    }

    public NewsContextStorageDaoFile(String storagePath) {
        this.storagePath = storagePath;
        createDirectories();
    }

    private void createDirectories() {
        try {
            Files.createDirectories(Path.of(storagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String addContext(int newsId, String context) throws DaoException {
        try {
            String fileName = generateFileName(newsId);
            Path filePath = getAbsFilePath(fileName);
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, context, StandardOpenOption.CREATE_NEW);
            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new DaoException("error writing file", e);
        }
    }

    private String generateFileName(int id) {
        return String.format("news_content_%05d.txt", id);
    }

    private Path getAbsFilePath(String fileName) {
        return Path.of(storagePath, fileName).toAbsolutePath();
    }

    @Override
    public String getContextById(int newsId) throws DaoException {
        try {
        String fileName = generateFileName(newsId);
        Path filePath = getAbsFilePath(fileName);
        if (!Files.exists(filePath)) {
            throw new DaoException("context file not found");
        }
            String context = Files.readString(filePath);
            return context;
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public String updateContextById(int newsId, String context) throws DaoException {
        try {
            String fileName = generateFileName(newsId);
            Path filePath = getAbsFilePath(fileName);
            if (!Files.exists(filePath)) {
                throw new DaoException("context file not found");
            }
            Files.writeString(filePath, context, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new DaoException("error updating file", e);
        }
    }

    @Override
    public boolean deleteContextById(int id) throws DaoException {
        String fileName = generateFileName(id);
        Path filePath = getAbsFilePath(fileName);
        if (!Files.exists(filePath)) {
            throw new DaoException("context file not found");
        }
        try {
            Files.delete(filePath);
            return true;
        } catch (IOException e) {
            throw new DaoException("error deleting file", e);
        }
    }
}
