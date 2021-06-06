package Util;

import java.util.Date;

public class Record {
    private int id;
    private String date;//date以yyyy-MM-dd 格式
    private String type;
    private String content;
    private double amount;

    public Record(int id, String date, String type, String content, double amount) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.content = content;
        this.amount = amount;
    }

    public int  getid() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public double getAmount() {
        return amount;
    }

    // 增删改查
}
