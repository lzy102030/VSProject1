package server.service;

import java.io.*;
import java.net.*;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetUserIP {
    //获取内网IP
    public String getInnetIp() throws SocketException {
        String localIP = null;          //本地IP，如果没有配置外网IP则返回它
        String netIP = null;            //外网IP

        Enumeration<NetworkInterface> netInterfaces;
        netInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip;

        boolean findNetIPFlag = false;                   //是否找到外网IP
        while (netInterfaces.hasMoreElements() && !findNetIPFlag) {
            NetworkInterface ni = netInterfaces.nextElement();
            Enumeration<InetAddress> address = ni.getInetAddresses();

            while (address.hasMoreElements()) {
                ip = address.nextElement();
                if (!ip.isSiteLocalAddress()
                        && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {              //外网IP
                    netIP = ip.getHostAddress();
                    findNetIPFlag = true;
                    break;
                } else if (ip.isSiteLocalAddress()
                        && !ip.isLoopbackAddress()
                        && ip.getHostAddress().indexOf(":") == -1) {             //内网IP
                    localIP = ip.getHostAddress();
                }
            }
        }

        if (netIP != null && !"".equals(netIP)) {
            return netIP;
        } else {
            return localIP;
        }
    }

    //获取外网ip ipv4格式
    public String getV4IP() {
        String ip = "";
        String chinaz = "http://ip.chinaz.com";

        StringBuilder inputLine = new StringBuilder();
        String read;
        URL url;
        HttpURLConnection urlConnection;
        BufferedReader in = null;

        //获取网页信息
        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            while ((read = in.readLine()) != null) {
                inputLine.append(read + "\r\n");
            }
        } catch (IOException e) {
            return "获取互联网IP失败";
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //正则表达式处理接收的网页端数据
        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine.toString());

        //获取ip地址
        if (m.find()) {
            String ipstr = m.group(1);
            ip = ipstr;
        }

        return ip;
    }
}
