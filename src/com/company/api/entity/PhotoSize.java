package com.company.api.entity;

public class PhotoSize {
    private String file_id;
    private int width;
    private int height;
    private int file_size;

    public String getFileId() {
        return file_id;
    }

    public void setFileId(String fileId) {
        this.file_id = fileId;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFileSize() {
        return file_size;
    }

    public void setFileSize(int fileSize) {
        this.file_size = fileSize;
    }
}
