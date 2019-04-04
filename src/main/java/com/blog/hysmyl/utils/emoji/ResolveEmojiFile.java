package com.blog.hysmyl.utils.emoji;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Liu Mingyao
 * @since 2018-11-13 17:11
 * @Deprecated 解析sina.txt和sina2表情文件, 并组装
 **/
public class ResolveEmojiFile {


    public String sinaTxtResolve() {
        List list = new ArrayList();
        //sina表情包txt文件所在的位置
        File file = new File("E:\\MyProject\\hysmyl\\src\\main\\java\\com\\blog\\hysmyl\\utils\\emoji\\sina.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            while (br.readLine() != null) {//使用readLine方法，一次读一行
                String line = br.readLine();
                if (line == null) break;
                if (line.contains("icon")) {
                    Map map = new HashMap();
                    String[] split = line.split("icon : \"");
                    String[] split1 = split[1].split("\",");
                    map.put("src", split1[0]);

                    String value = br.readLine();
                    String[] split2 = value.split("value : \"");
                    String[] split3 = split2[1].split("\"");
                    map.put("alt", split3[0]);

                    list.add(map);
                } else {
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(list);
    }

    public String sina2Resolve() {
        List list = new ArrayList();
        //其他表情包位置
        File file = new File("E:\\MyProject\\hysmyl\\src\\main\\java\\com\\blog\\hysmyl\\utils\\emoji\\sina2");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String line = br.readLine();
            String[] strings = line.split(",\"picid\":\"\"},");
            for (int i = 0; i < strings.length; i++) {
                Map map = new HashMap();
                String[] url = strings[i].split("\"url\":\"");
                String[] split1 = url[1].split("\",\"hot\":");
                map.put("src", split1[0].trim());


                String[] value = strings[i].split(",\"value\":\"");
                String[] split3 = value[1].split("\",\"picid\":");
                map.put("alt", split3[0].trim());

                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(list);
    }

}
