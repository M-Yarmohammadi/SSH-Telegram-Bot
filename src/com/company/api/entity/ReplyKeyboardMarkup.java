package com.company.api.entity;

import java.util.ArrayList;
import java.util.List;

public class ReplyKeyboardMarkup {
    private List<List<String>> keyboard = new ArrayList<List<String>>();
    private boolean resize_keyboard;
    private boolean one_time_keyboard;
    private boolean selective;

    public List<List<String>> getKeyboard() {
        return keyboard;
    }

    public void setKeyboard(List<List<String>> keyboard) {
        this.keyboard = keyboard;
    }

    public boolean isResizeKeyboard() {
        return resize_keyboard;
    }

    public void setResizeKeyboard(boolean resizeKeyboard) {
        this.resize_keyboard = resizeKeyboard;
    }

    public boolean isOneTimeKeyboard() {
        return one_time_keyboard;
    }

    public void setOneTimeKeyboard(boolean oneTimeKeyboard) {
        this.one_time_keyboard = oneTimeKeyboard;
    }

    public boolean isSelective() {
        return selective;
    }

    public void setSelective(boolean selective) {
        this.selective = selective;
    }
}
