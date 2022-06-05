package cn.edu.sict.bean;

//音乐信息实体类
public class Music {
    //成员变量：歌曲名title、歌手artist、路径data
    String name, singer, path;

    //无参的构造函数
    public Music() {
    }

    //有参的构造函数
    public Music(String name, String singer, String path) {
        this.name = name;
        this.singer = singer;
        this.path = path;
    }

    //三个成员变量的getter和setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSinger() {
        return singer;
    }
    public void setSinger(String singer) {
        this.singer = singer;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
}
