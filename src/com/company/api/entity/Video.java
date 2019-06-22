package com.company.api.entity;

public class Video {
    private String file_id;
    private int width;
    private int height;
    private int duration;
    private PhotoSize thumb;
    private String mime_type;
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

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public PhotoSize getThumb() {
        return thumb;
    }

    public void setThumb(PhotoSize thumb) {
        this.thumb = thumb;
    }

    public String getMimeType() {
        return mime_type;
    }

    public void setMimeType(String mimeType) {
        this.mime_type = mimeType;
    }

    public int getFileSize() {
        return file_size;
    }

    public void setFileSize(int fileSize) {
        this.file_size = fileSize;
    }
}
