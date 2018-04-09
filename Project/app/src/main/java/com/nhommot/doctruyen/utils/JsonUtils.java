package com.nhommot.doctruyen.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;

import com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class JsonUtils {
    private static Gson gson = new Gson();

    public static List<String> decodeList(String jsonStr) {
        Type typeOfT = new TypeToken<List<String>>(){}.getType();
        List<String> decodedList = gson.fromJson(jsonStr, typeOfT);
        return decodedList;
    }

    public static <T> List<T> decodeListT(String jsonStr) {
        Type typeOfT = new TypeToken<List<T>>(){}.getType();
        List<T> decodedList = gson.fromJson(jsonStr, typeOfT);
        return decodedList;
    }

    public static String encode(Object obj) {
        String jsonStr = "";
        jsonStr = gson.toJson(obj);

        return jsonStr;
    }

    public static List<String> decodeList(String jsonStr, Gson gson) {
        Type typeOfT = new TypeToken<List<String>>(){}.getType();
        List<String> decodedList = gson.fromJson(jsonStr, typeOfT);
        return decodedList;
    }

    public static String encode(Object obj, Gson gson) {
        String jsonStr = "";
        jsonStr = gson.toJson(obj);

        return jsonStr;
    }

    public static <T> T decode(String jsonStr, Class<T> c, Gson gson) {
        return gson.fromJson(jsonStr, c);
    }

    public static Gson getGson() {
        return gson;
    }

    public static <T> T decode(String jsonStr, Class<T> c) {
        T obj = null;
        try {
            obj = gson.fromJson(jsonStr, c);
        } catch (JsonSyntaxException e) {
        }
        return obj;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        String pattern = "{'catid':0,'name':\"Danh M\u1ee5c\",\"pcid\":-1,\"ccids\":[12,970,18,17,735,1076,1,9,1000,10,11,784,776,780,767,772,22,23,20,788]}";
        Map<String,Object> map = JsonUtils.decode(pattern, Map.class);
        for(Map.Entry<String, Object> entry: map.entrySet()) {
            System.out.println(String.format("Key:%s - Value:%s - Type:%s", entry.getKey(), entry.getValue(),entry.getValue().getClass().getName()));
        }
//		String test = "[12,970,18,17,735,1076,1,9,1000,10,11,784,776,780,767,772,22,23,20,788]";
//		ArrayList<Double> nums = JsonUtils.decode(test, ArrayList.class);
//		for(double i: nums) {
//			System.out.println(i);
//		}
        String test = "[true,false]";
        ArrayList<Boolean> strs = JsonUtils.decode(test, ArrayList.class);
        for(Boolean s: strs) {
            System.out.println(s);
        }
        List<Object> objs = new ArrayList<Object>();
        objs.add("StringObj");
        objs.add(1);
        objs.add(8.5);
        String objStr = JsonUtils.encode(objs);
        System.out.println(objStr);
        objs = JsonUtils.decode(objStr, List.class);
        for(Object o: objs) {
            System.out.println(o);
        }

        test = "[1,2,3]";
        List<String> re = JsonUtils.decodeList(test);
        if(re != null) {
            List<Object> is = new ArrayList<Object>();
            for(String i: re) {
                System.out.println(i);
                is.add(Integer.parseInt(i));
            }
            System.out.println(JsonUtils.encode(is));
        }

        Double score = 1.2789;
        System.out.println(String.format("%.3f", score));

        String str = "450	[1454:12.0,23587:12.0,35772:12.0,38056:12.0]";
        String[] ss = str.split("\\s");
        for(String s: ss) {
            System.out.println(s);
        }
        str = ss[1];

        System.out.println("-----");
        System.out.println(str.substring(1, str.length()-1));
        str = str.substring(1, str.length()-1);
        System.out.println("-----");
        ss = str.split(",");
        for(String s: ss) {
            System.out.println(s);
        }

        String num = "2.298345E7";
        System.out.println(num.matches("[-+]?\\d*\\.?\\d+E\\d+"));
        System.out.println(Double.parseDouble(num));
    }
}