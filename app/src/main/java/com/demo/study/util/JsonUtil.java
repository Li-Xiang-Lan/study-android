package com.demo.study.util;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by cheng on 2019/8/15.
 */
public class JsonUtil {
    public static String getJsonString(Context context, String jsonName){
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager =context.getApplicationContext().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(jsonName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            return "";
        }
    }
}
