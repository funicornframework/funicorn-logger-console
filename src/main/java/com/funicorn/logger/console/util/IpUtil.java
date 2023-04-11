package com.funicorn.logger.console.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author Aimee
 * @since 2023/3/17 9:52
 */
public class IpUtil {

    /**
     * 获得ip地址
     *
     * @return String
     */
    public static String getIpAddress() {
        try {
            //从网卡中获取IP
            Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = allNetInterfaces.nextElement();
                //用于排除回送接口,非虚拟网卡,未在使用中的网络接口
                if (!netInterface.isLoopback() && !netInterface.isVirtual() && netInterface.isUp()) {
                    //返回和网络接口绑定的所有IP地址
                    Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                    while (addresses.hasMoreElements()) {
                        ip = addresses.nextElement();
                        if (ip instanceof Inet4Address) {
                            return ip.getHostAddress();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
