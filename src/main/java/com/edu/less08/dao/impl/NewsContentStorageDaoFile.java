package com.edu.less08.dao.impl;

import com.edu.less08.dao.DaoException;
import com.edu.less08.dao.NewsContentStorageDao;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Properties;

public class NewsContentStorageDaoFile implements NewsContentStorageDao {
    private String storagePath;

    public NewsContentStorageDaoFile() {
        storagePath = loadPathFromProperties();
        createDirectories();
    }

    public NewsContentStorageDaoFile(String storagePath) {
        this.storagePath = storagePath;
        createDirectories();
    }

    private String loadPathFromProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input != null) {
                Properties properties = new Properties();
                properties.load(input);
                return properties.getProperty("news.storage.path");
            }
        } catch (IOException e) {
            return "C:/NewsApp/content_storage";
        }
        return "C:/NewsApp/content_storage";
    }

    private void createDirectories() {
        try {
            Files.createDirectories(Path.of(storagePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String addContent(int newsId, String content) throws DaoException {
        try {
            String fileName = generateFileName(newsId);
            Path filePath = getAbsFilePath(fileName);
            Files.createDirectories(filePath.getParent());
            Files.writeString(filePath, content, StandardOpenOption.CREATE_NEW);
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
    public String getContentById(int newsId) throws DaoException {
        try {
        String fileName = generateFileName(newsId);
        Path filePath = getAbsFilePath(fileName);
        if (!Files.exists(filePath)) {
            throw new DaoException("content file not found");
        }
            String content = Files.readString(filePath);
            return content;
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public String getContentByPath(String path) throws DaoException {
        try {
            Path filePath = Path.of(path);
            if (!Files.exists(filePath)) {
                throw new DaoException("content file not found");
            }
            String content = Files.readString(filePath);
            return content;
        } catch (IOException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public String updateContentById(int newsId, String content) throws DaoException {
        try {
            String fileName = generateFileName(newsId);
            Path filePath = getAbsFilePath(fileName);
            if (!Files.exists(filePath)) {
                throw new DaoException("content file not found");
            }
            Files.writeString(filePath, content, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return filePath.toAbsolutePath().toString();
        } catch (IOException e) {
            throw new DaoException("error updating file", e);
        }
    }

    @Override
    public boolean deleteContentById(int id) throws DaoException {
        String fileName = generateFileName(id);
        Path filePath = getAbsFilePath(fileName);
        if (!Files.exists(filePath)) {
            throw new DaoException("content file not found");
        }
        try {
            Files.delete(filePath);
            return true;
        } catch (IOException e) {
            throw new DaoException("error deleting file", e);
        }
    }
}
