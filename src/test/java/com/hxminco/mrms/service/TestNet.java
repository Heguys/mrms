package com.hxminco.mrms.service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by Employee on 2017/9/26.
 */
public class TestNet {
   public static void isAddressAvailable(String ip) throws UnknownHostException {
        try {
            InetAddress address = InetAddress.getByName(ip);//ping this IP
                if(address instanceof java.net.Inet4Address){
                System.out.println(ip + " is ipv4 address");
            }else
            if (address instanceof java.net.Inet6Address) {
                System.out.println(ip + " is ipv6 address");
            }else{
                System.out.println(ip + " is unrecongized");
            }
            if (address.isReachable(5000)) {
                    System.out.println("SUCCESS - ping " + ip + " with no interface specified ");
                }else{ System.out.println(" FAILURE - ping " + ip + " with no interface specified ");
            } System.out.println("\n------ - Trying different interfaces--------\n ");
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
                    while (netInterfaces.hasMoreElements()) {
                        NetworkInterface ni = netInterfaces.nextElement();
                        System.out.println("Checking interface,DisplayName:" + ni.getDisplayName() + ", Name:" + ni.getName());
                        if (address.isReachable(ni, 0, 5000)) {
                            System.out.println("SUCCESS - ping " + ip);
                        } else {
                            System.out.println("FAILURE - ping " + ip);
                        }
                        Enumeration<InetAddress> ips = ni.getInetAddresses();
                        while (ips.hasMoreElements()) {
                            System.out.println("IP: " +
                                    ips.nextElement().getHostAddress());
                        }
                        System.out.println("-------------------------------------------");
                    }
                }catch(Exception e){
                    System.out.println("error occurs.");
                    e.printStackTrace();
                }
            }

            public static void main(String[] args) {
                try {
                    isAddressAvailable("http://192.168.1.61:8080/mrms-server/services/MRMSService?wsdl");
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }

        }
