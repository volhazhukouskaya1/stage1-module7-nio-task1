package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Objects;


public class FileReader {

    public static Profile getDataFromFile(File file) {
        StringBuilder stringFromFile = new StringBuilder();

        try (RandomAccessFile myFile = new RandomAccessFile(file,"r")) {
            FileChannel channel = myFile.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (channel.read(buffer) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    stringFromFile.append((char) buffer.get());
                }

                buffer.clear();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        HashMap <String, String> dataMap = new HashMap<>();
        String[] lines = stringFromFile.toString().split("\n");

        Profile first = new Profile();

        for (String pair : lines) {
            String[] keyValue = pair.split(": ");
            dataMap.put(keyValue[0].trim(), keyValue[1].trim());
        }
        for (HashMap.Entry <String, String> entry : dataMap.entrySet()) {
            if (Objects.equals(entry.getKey(), "Name")) first.setName(entry.getValue());
            if (Objects.equals(entry.getKey(), "Age")) first.setAge(Integer.parseInt(entry.getValue()));
            if (Objects.equals(entry.getKey(), "Email")) first.setEmail(entry.getValue());
            if (Objects.equals(entry.getKey(),"Phone")) first.setPhone(Long.parseLong(entry.getValue()));
        }

        return first;
    }
    public static void main(String[] args) {

        File file = new File("src/main/resources/Profile.txt");
        getDataFromFile(file); }
}


