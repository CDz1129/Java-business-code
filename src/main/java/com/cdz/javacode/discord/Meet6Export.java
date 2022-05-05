package com.cdz.javacode.discord;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.WorkbookUtil;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author david.chen
 * @date 2021/12/29 20:20
 */
public class Meet6Export {

    public static void main(String[] args) {
        String url = "https://mee6.xyz/api/plugins/levels/leaderboard/887031170079023115?page={}";
        int num = 0;


//        String res = HttpUtil.get(StrUtil.format(url, num));
//
//        JSONObject jsonObject = JSONUtil.toBean(res, JSONObject.class);
//        JSONArray players = jsonObject.getJSONArray("players");

//        System.out.println(allUser);

        JSONArray allPlayers = new JSONArray();

        while (true){
            String res = HttpUtil.get(StrUtil.format(url, num));
            JSONObject jsonObject = JSONUtil.toBean(res, JSONObject.class);
            JSONArray players = jsonObject.getJSONArray("players");
            allPlayers.addAll(players);
            if (players.isEmpty()){
                break;
            }
            num++;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        List<LinkedHashMap<String, Object>> allUser = allPlayers.stream().map(e -> {
            JSONObject object = (JSONObject) e;
            LinkedHashMap<String, Object> map = new LinkedHashMap();
            map.put("username", object.getStr("username") + "#" + object.get("discriminator"));
            Integer level = object.getInt("level");
            if (level == 0) return null;
            map.put("level", object.getInt("level"));
            return map;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        ExcelWriter writer = ExcelUtil.getWriter("d:/unkown-meet6.xlsx");
        writer.write(allUser,true);
        writer.close();

    }
}
