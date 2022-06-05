package cn.edu.sict.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import java.util.ArrayList;
import cn.edu.sict.bean.Music;

public class MusicUtil {

    //静态类：获取系统媒体库中的音频信息
    public static ArrayList<Music> scanMusic(Context context){

        //音乐数据集合
        ArrayList<Music> musicList = new ArrayList<>();

        //1.在manifest清单文件中添加权限
        //<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

        //2.获取ContentResolver对象
        ContentResolver resolver = context.getContentResolver();

        //3.调用resolver的查询方法，获取结果游标集
        //参数1：读取Uri指向的数据表
        //参数2：获取数据表中的哪几列
        Cursor cursor = resolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Media.TITLE,MediaStore.Audio.Media.ARTIST,MediaStore.Audio.Media.DATA},
                null,null,null);

        //遍历所有行(如果游标向下移动一行，返回true)
        while(cursor.moveToNext()){
            //获取歌曲名
            int titleIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            String title = cursor.getString(titleIndex);

            //获取歌手名
            int singerIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            String singer = cursor.getString(singerIndex);

            //获取歌曲信息
            int pathIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            String songPath = cursor.getString(pathIndex);

            //创建一首音乐，保存歌曲名、歌手、歌曲地址信息
            Music m = new Music(title,singer,songPath);

            //将歌曲添加到musicList列表中
            musicList.add(m);
        }

        return musicList;
    }
}
