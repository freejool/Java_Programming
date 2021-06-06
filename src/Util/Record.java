package Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Record {
    private int id;
    private String date;//date以yyyy-MM-dd 格式
    private String type;
    private String content;
    private Double amount;

    public Record(int id, String date, String type, String content, Double amount) {
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
    public static void main(String[] args) {
        Conn con=new Conn();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
       /* Record r1=new Record(3,sdf.format(new Date()),"income","salary",5000);
        con.insert(r1);*/
        //System.out.println("insert into t_shouzhi values("+re.getid()+",\""+re.getDate()+"\",\""+re.getType()+"\",\""+re.getContent()+"\","+re.getAmount()+")");
        /*Record r2=new Record(2,sdf.format(new Date()),"outcome","shopping",1500);
        System.out.println("update t_shouzhi set date=\""+record.getDate()+"\",Type=\""+record.getType()+
                "\",content=\""+record.getContent()+"\",amount="+record.getAmount()+" where id="+record.getid());*//*
        con.update(r2);*/
        Record r2=new Record(1,sdf.format(new Date()),"outcome","shopping",1500d);
        con.delete(r2);
        con.close();
    }
}
