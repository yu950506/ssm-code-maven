package cn.yhs.learn.domian;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: ssm-code-maven
 * @Name: cn.yhs.learn.domian.Demo
 * @Author: Splendor -- 加油,你是最棒的 ~_~
 * @Email: 15617577080@163.com
 * @Time: 2020/5/28 21:46
 * @Description: todo
 **/
public class Demo {
    /*演示注入属性 集合*/
    private String[] strings;
    private List<Integer> lists;
    private Map<String,Integer> maps;

    public void setStrings(String[] strings) {
        this.strings = strings;
    }

    public void setLists(List<Integer> lists) {
        this.lists = lists;
    }

    public void setMaps(Map<String, Integer> maps) {
        this.maps = maps;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "strings=" + Arrays.toString(strings) +
                ", lists=" + lists +
                ", maps=" + maps +
                '}';
    }
}
