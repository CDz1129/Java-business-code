package com.cdz.javacode.catmach;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CatMatch {
    public static void main(String[] args) {

//        HttpRequest request = HttpUtil.createGet("https://ipfs.io/ipfs/bafybeibkc4lfq5cbbaqgdhlpax5ygfxqnfou3ykj5vmlwy2egao2kg6ogm/"+1);
//        System.out.println(request.execute().body());
//        ArrayList<Integer> list = new ArrayList<>();
//        for (int i = 0; i < 1000; i++) {
//            HttpRequest request = HttpUtil.createGet("https://gateway.pinata.cloud/ipfs/bafybeibkc4lfq5cbbaqgdhlpax5ygfxqnfou3ykj5vmlwy2egao2kg6ogm/" + i);
//            String json = null;
//            try {
//                json = request.execute().body();
//            } catch (HttpException e) {
//                e.printStackTrace();
//            }
//            Integer num = getToeknIdBinneryNum(json);
//            list.add(num);
//            System.out.println(i);
//        }
//        System.out.println(list);

        String arrStr = "[28, 40, 61, 32, 50, 24, 57, 40, 0, 63, 14, 43, 12, 44, 60, 6, 31, 53, 21, 58, 14, 49, 14, 61, 61, 32, 32, 55, 10, 61, 55, 24, 54, 33, 26, 40, 61, 47, 40, 6, 17, 25, 22, 5, 43, 46, 27, 31, 40, 52, 27, 52, 12, 57, 33, 18, 61, 31, 41, 30, 42, 8, 11, 41, 61, 21, 52, 36, 52, 16, 60, 15, 55, 9, 40, 30, 30, 29, 58, 29, 12, 36, 4, 29, 34, 52, 62, 48, 32, 41, 57, 31, 45, 27, 20, 58, 16, 23, 45, 43, 23, 4, 45, 3, 61, 9, 40, 61, 57, 16, 62, 21, 9, 49, 58, 58, 23, 61, 32, 14, 44, 28, 28, 33, 53, 14, 26, 44, 56, 20, 52, 61, 27, 42, 24, 41, 21, 61, 15, 59, 36, 45, 40, 13, 59, 18, 0, 61, 17, 57, 17, 31, 36, 61, 20, 18, 14, 58, 5, 26, 56, 59, 61, 16, 48, 48, 16, 28, 25, 34, 40, 61, 1, 30, 30, 52, 36, 59, 62, 29, 32, 61, 34, 57, 59, 49, 37, 46, 60, 59, 17, 12, 1, 21, 51, 54, 46, 21, 37, 9, 50, 44, 58, 23, 26, 9, 32, 57, 37, 57, 19, 36, 14, 14, 60, 17, 28, 60, 19, 8, 62, 31, 45, 49, 29, 1, 13, 7, 50, 16, 52, 43, 56, 57, 60, 57, 49, 13, 57, 49, 2, 50, 17, 21, 15, 17, 23, 15, 40, 27, 16, 29, 19, 48, 17, 53, 9, 19, 21, 1, 60, 48, 5, 31, 4, 4, 3, 28, 39, 12, 63, 8, 51, 36, 57, 24, 30, 60, 62, 24, 39, 20, 20, 23, 25, 36, 63, 9, 30, 53, 25, 40, 57, 13, 20, 24, 15, 25, 49, 32, 53, 50, 22, 59, 29, 8, 59, 45, 23, 17, 56, 28, 32, 3, 59, 28, 9, 24, 22, 41, 36, 56, 46, 34, 33, 21, 4, 61, 21, 28, 40, 56, 54, 53, 62, 11, 58, 21, 53, 14, 43, 12, 44, 57, 20, 10, 14, 10, 60, 52, 20, 34, 62, 30, 5, 41, 47, 13, 23, 21, 17, 33, 5, 16, 24, 61, 24, 36, 57, 60, 48, 37, 28, 60, 58, 41, 49, 56, 57, 60, 8, 56, 27, 55, 9, 31, 41, 62, 37, 32, 52, 12, 26, 39, 12, 56, 24, 51, 20, 37, 28, 34, 41, 0, 52, 20, 48, 57, 44, 24, 54, 2, 26, 12, 41, 63, 60, 23, 29, 30, 7, 21, 61, 61, 25, 44, 1, 27, 41, 62, 18, 9, 21, 28, 20, 55, 30, 28, 13, 48, 36, 55, 29, 18, 1, 54, 31, 38, 61, 52, 26, 8, 28, 21, 33, 36, 36, 16, 46, 52, 58, 1, 35, 4, 54, 58, 49, 21, 31, 16, 37, 26, 63, 58, 25, 36, 28, 57, 52, 23, 31, 44, 60, 58, 51, 38, 40, 1, 63, 21, 4, 18, 13, 3, 13, 37, 3, 16, 12, 60, 58, 38, 42, 53, 62, 48, 14, 56, 34, 44, 20, 9, 11, 29, 56, 63, 55, 32, 55, 48, 44, 58, 31, 25, 40, 57, 59, 12, 42, 57, 45, 37, 26, 60, 48, 44, 9, 61, 53, 53, 8, 27, 29, 44, 48, 59, 41, 20, 60, 8, 19, 44, 27, 20, 61, 44, 24, 62, 44, 41, 35, 37, 25, 22, 42, 48, 1, 59, 54, 3, 19, 63, 25, 58, 60, 19, 4, 19, 10, 46, 31, 57, 39, 29, 31, 42, 1, 37, 7, 60, 27, 7, 57, 21, 55, 27, 60, 28, 24, 16, 9, 40, 62, 24, 23, 9, 29, 24, 58, 16, 16, 29, 1, 40, 25, 0, 63, 15, 54, 47, 57, 63, 0, 28, 25, 60, 29, 44, 36, 13, 21, 51, 10, 20, 60, 11, 57, 2, 8, 44, 37, 25, 57, 45, 58, 16, 17, 48, 41, 48, 31, 58, 57, 29, 21, 22, 40, 42, 58, 61, 24, 57, 54, 18, 29, 59, 20, 14, 44, 28, 39, 11, 61, 24, 43, 61, 47, 17, 24, 5, 20, 14, 41, 42, 19, 54, 57, 63, 63, 55, 56, 18, 61, 10, 20, 17, 32, 53, 59, 21, 28, 48, 45, 9, 37, 57, 49, 17, 22, 33, 29, 17, 45, 52, 36, 17, 35, 6, 17, 5, 52, 57, 57, 45, 27, 61, 42, 61, 60, 29, 23, 57, 25, 11, 48, 16, 28, 61, 11, 11, 11, 41, 52, 57, 44, 37, 44, 57, 2, 47, 16, 29, 10, 30, 16, 16, 5, 1, 61, 60, 8, 60, 26, 25, 60, 60, 53, 29, 49, 44, 42, 35, 27, 57, 21, 43, 24, 57, 10, 25, 61, 63, 20, 18, 30, 13, 56, 20, 18, 62, 23, 25, 14, 18, 57, 41, 23, 62, 54, 12, 7, 62, 55, 16, 14, 51, 60, 10, 58, 51, 35, 44, 44, 15, 54, 39, 24, 12, 46, 21, 13, 14, 25, 50, 60, 14, 55, 58, 29, 0, 44, 44, 44, 45, 59, 57, 62, 36, 20, 12, 40, 25, 9, 29, 40, 41, 56, 60, 63, 60, 56, 0, 21, 56, 56, 6, 41, 61, 41, 0, 16, 7, 1, 21, 32, 56, 56, 32, 36, 1, 24, 47, 49, 9, 29, 38, 49, 25, 54, 30, 16, 56, 18, 25, 51, 61, 10, 60, 7, 37, 15, 13, 62, 7, 47, 60, 41, 53, 42, 54, 28, 56, 44, 56, 24, 13, 1, 24, 52, 58, 33, 13, 12, 13, 61, 58, 37, 22, 36, 45, 52, 36, 38, 0, 13, 60, 23, 62, 14, 59, 26, 59, 45, 57, 14, 61, 9, 50, 25, 42, 36, 22, 57, 17, 54, 62, 62, 47, 58, 60, 55, 24, 30, 47, 47, 49, 56, 24, 40, 12, 53, 3, 4, 21, 13, 50, 12, 55, 56, 56, 20, 32, 49, 1, 3, 24, 27, 12, 57, 25, 4, 36, 23, 7, 20, 11, 10, 23, 59, 57, 59, 17, 55, 48, 52, 47, 60, 0, 24, 26]";

        List<Integer> arrayLists = JSONUtil.toList(arrStr, Integer.class);

        ArrayList<Pair<Integer, Integer>> pairArrayList = new ArrayList<>();
        for (int i = 0; i < arrayLists.size(); i++) {
            Pair<Integer, Integer> indexNumPair = new Pair<Integer, Integer>(i,arrayLists.get(i));
            pairArrayList.add(indexNumPair);
        }

        AtomicInteger single = new AtomicInteger();
        Map<Integer, List<Pair<Integer, Integer>>> listMap = pairArrayList.stream().collect(Collectors.groupingBy(pair -> (Integer)pair.getValue()));
        listMap.forEach((k,v)->{
            System.out.println("num:"+k+"有相同："+ v.size());
            int size = v.size();
            int i = size % 2;
            if (i==1){
                single.getAndIncrement();
            }
            List<Pair<Integer, Integer>> collect = v.stream().sorted(Comparator.comparing(Pair::getKey)).collect(Collectors.toList());

            for (int i1 = 0; i1 < collect.size()/2; i1++) {
                //前后配对——二分法比较好
            }

            listMap.put(k,collect);
        });
        System.out.println("单个的"+single.get());

//
//        Integer noMachNum = 0;
//        for (int i = 0; i < arrayLists.size(); i++) {
//            Integer num = arrayLists.get(i);
//            ArrayList<Pair> pairs = new ArrayList<>();
//            for (int i1 = 0; i1 < arrayLists.size(); i1++) {
//                Integer machNum = arrayLists.get(i1);
//                if ((getHash(machNum) & getHash(num)) == 1) {
//                    Pair<Integer, Integer> indexNumPair = new Pair<>(i1, machNum);
//                    pairs.add(indexNumPair);
//                }
//            }
//            if (CollUtil.isEmpty(pairs)) {
//                noMachNum++;
//            }
//            System.out.println("token#" + i + ",数字：" + num + ",匹配到(Pair)：" + pairs);
//        }
//        System.out.println("noMachNum:" + noMachNum);
    }

    private static int getHash(int num) {
        int h;
        return (h = num) ^ (h >>> 3);
    }

    private static Integer getToeknIdBinneryNum(String json) {
        JSONObject jsonObject = JSONUtil.toBean(json, JSONObject.class);
        JSONArray attributes = jsonObject.getJSONArray("attributes");
        StrBuilder hex = new StrBuilder();
        for (int i = 0; i < attributes.size(); i++) {
            JSONObject o = (JSONObject) attributes.get(i);
            String value = o.getStr("value");
            String[] split = value.split("_");
            String num = split[split.length - 1];
            Integer integer = Integer.parseInt(num);
            int i1 = integer % 2;
            hex.append(i1);
        }
        Integer value = Integer.valueOf(hex.toString(), 2);
        System.out.println(value);
        return value;
    }
}
