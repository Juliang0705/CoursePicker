package com.coursepickerweb;

import com.coursepickerweb.server.WebServer;

public class Main {
    public static void main(String[] args) {
        try {
            final int portAddress = 8880;
            WebServer server = new WebServer(portAddress);
            server.start();
            System.out.println("Website is running on localhost:"+ portAddress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}