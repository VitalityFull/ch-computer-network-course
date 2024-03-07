package com.example;

public class UserFile {
    private String name;

    private String filePath;

    public UserFile() {
    }

    public UserFile(String name, String filePath) {
        this.name = name;
        this.filePath = filePath;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return filePath
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 设置
     * @param filePath
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String toString() {
        return "UserFile{name = " + name + ", filePath = " + filePath + "}";
    }
}
