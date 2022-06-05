package cn.edu.sict.bean;

public class MineGridBean {
    int imgSrc;
    String title;

    //无参的构造函数
    public MineGridBean() {
    }
    //有参的构造函数：创建本对象的同时对两个成员变量进行赋值
    public MineGridBean(int imgSrc, String title) {
        this.imgSrc = imgSrc;
        this.title = title;
    }

    //getter、setter

    public int getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(int imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
