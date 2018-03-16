package com.ideal.regexp;


import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regexp {
    public static void main(String[] args) {
        String IMEI = "[0-9a-fA-F]{14,15}";
        String IMSI = "4600[0-3]{1}[0-9]{10}";
        String MDN = "[1][3578][0-9]{9}";
        String MAC = "([0-9A-Fa-f]{2}){5}[0-9A-Fa-f]{2}"; //去掉了[-|:])
        String IDFA = "[A-Za-z0-9]{8}-([A-Za-z0-9]{4}-){3}[A-Za-z0-9]{12}";
        String ANDROID_ID = "[A-Za-z0-9]{10,40}";
        String EMAIL = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
        for (int x =10000;x<99999;x++) {
            try {
                InputStreamReader isr = new InputStreamReader(new FileInputStream("/data2/u_lx_tst2/cxy/part-"+x), "UTF-8");
                //会出现中文乱码
                //FileReader reader = new FileReader("C:/Users/chenyang/Desktop/test");
                BufferedReader br = new BufferedReader(isr);
                StringBuilder action = new StringBuilder("");
                StringBuilder error = new StringBuilder("");
                String str = null;
                while ((str = br.readLine()) != null) {
                    String line = "";
                    try {
                        line = str.substring(str.indexOf(',') + 1);
                    } catch (StringIndexOutOfBoundsException e) {
                        error.append("StringIndexOutOfBoundsException" + str + "\n");
                    }
                    String[] map = line.split(";");
                    String[] map2 = map;
                    if (map.length >= 2) {
                        for (int i = 1; i < map.length; i++) {
                            map2[i] = map[i].substring(map[i].indexOf(',') + 1);
                        }
                    }
                    for (int i = 0; i < map2.length; i++) {
                        String key = map2[i].split(",")[0];
                        String value = "";
                        try {
                            value = map2[i].split(",")[1];
                        } catch (ArrayIndexOutOfBoundsException e) {
                            error.append("ArrayIndexOutOfBoundsException" + str + "\n");
                        }
                        if (key.contains("imei")) {
                            Pattern pattern = Pattern.compile(IMEI);
                            Matcher matcher = pattern.matcher(value);
                            boolean rs = matcher.matches();
                            if (!rs) {
                                action.append(str + "\n");
                            }
                        } else if (key.contains("imsi")) {
                            Pattern pattern = Pattern.compile(IMSI);
                            Matcher matcher = pattern.matcher(value);
                            boolean rs = matcher.matches();
                            if (!rs) {
                                action.append(str + "\n");
                            }
                        } else if (key.contains("mdn")) {
                            Pattern pattern = Pattern.compile(MDN);
                            Matcher matcher = pattern.matcher(value);
                            boolean rs = matcher.matches();
                            if (!rs) {
                                action.append(str + "\n");
                            }
                        } else if (key.contains("mac")) {
                            Pattern pattern = Pattern.compile(MAC);
                            Matcher matcher = pattern.matcher(value);
                            boolean rs = matcher.matches();
                            if (!rs) {
                                action.append(str + "\n");
                            }
                        } else if (key.contains("idfa")) {
                            Pattern pattern = Pattern.compile(IDFA);
                            Matcher matcher = pattern.matcher(value);
                            boolean rs = matcher.matches();
                            if (!rs) {
                                action.append(str + "\n");
                            }
                        } else if (key.contains("androidid")) {
                            Pattern pattern = Pattern.compile(ANDROID_ID);
                            Matcher matcher = pattern.matcher(value);
                            boolean rs = matcher.matches();
                            if (!rs) {
                                action.append(str + "\n");
                            }
                        } else if (key.contains("email")) {
                            Pattern pattern = Pattern.compile(EMAIL);
                            Matcher matcher = pattern.matcher(value);
                            boolean rs = matcher.matches();
                            if (!rs) {
                                action.append(str + "\n");
                            }
                        } else {
                            action.append("Fail!!!--->" + str + key + value + "\n");
                        }
                    }
                }

                OutputStreamWriter ws = new OutputStreamWriter(new FileOutputStream("/data2/u_lx_tst2/cxy/testMap"+x), "UTF-8");
                //FileWriter writer = new FileWriter("C:/Users/chenyang/Desktop/testMap");
                OutputStreamWriter err = new OutputStreamWriter(new FileOutputStream("/data2/u_lx_tst2/cxy/testError"+x), "UTF-8");
                BufferedWriter bwErr = new BufferedWriter(err);
                BufferedWriter bw = new BufferedWriter(ws);
                bw.write(action.toString());
                bwErr.write(error.toString());
                bw.flush();
                bwErr.flush();
                br.close();
                isr.close();
                bw.close();
                bwErr.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
