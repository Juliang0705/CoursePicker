package com.coursepickerweb;

public class Main {
    public static void main(String[] args) {
        final int portAddress = 8880;
        WebServer server = new WebServer(portAddress);
        server.start();
        System.out.println("Website is running on localhost:"+ portAddress);
    }
}