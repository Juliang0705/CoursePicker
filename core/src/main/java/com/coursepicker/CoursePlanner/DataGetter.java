package com.coursepicker.courseplanner;

/**
 * Created by Juliang on 1/4/16.
 * this class get raw pdf data from url and parse it to string
 */
import java.net.*;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.*;
import java.nio.charset.*;
import java.nio.file.Files;
import java.nio.file.Paths;


class Pair<T1,T2>{
    public final T1 fst;
    public final T2 snd;
    public Pair(T1 elem1, T2 elem2){
        this.fst = elem1;
        this.snd = elem2;
    }
}

public class DataGetter {
    private PDDocument pd;
    private PDFTextStripper stripper;
    private String data;

    /**
     *
     * @param url a string specifies where the data comes from
     * @throws Exception if the documents doesn't end with .pdf or connection cannot be made
     */
    public DataGetter(String url) throws Exception{
        this(new URL(url));
    }

    /**
     * read a whole file into a single string
     * @param path to the file
     * @param encoding character encoding
     * @return a string contains all the data
     * @throws IOException
     */
    private String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
    /**
     * check if the file is already on local machine
     * @param url an URL object specifies where the data comes from
     * @return a pair of Boolean and String. First = true if file exists, Second = filename
     */
    private Pair<Boolean,String> isOnLocal(URL url){
        //check if the directory already exists
        File dir = new File("CoursePickerData");
        if (!dir.exists()){
            dir.mkdir();
        }
        String filename = url.getFile();
        int index = filename.lastIndexOf("/");
        String localFileName = filename.substring(index+1,filename.length()-4) + ".txt";
        File localFile = new File(dir + "/" +localFileName);
        return new Pair<>(localFile.exists() && localFile.isFile(),localFile.getAbsolutePath());
    }
    /**
     * read the data from the url
     * if the file is already on local machine then it won't make internet connection
     * otherwise make connection and save the file for future use
     * @param url an URl object specifies where the data comes from
     * @throws Exception if the documents doesn't end with .pdf or connection cannot be made
     */
    public DataGetter(URL url) throws Exception{
        if (!url.getFile().endsWith(".pdf"))
            throw new Exception("CoursePicker.DataGetter Error: url must end with .pdf");
        Pair<Boolean,String> filePair = this.isOnLocal(url);
        if (filePair.fst){
            this.data = readFile(filePair.snd, StandardCharsets.UTF_8);
        }else {
            URLConnection connection = url.openConnection();
            this.pd = PDDocument.load(connection.getInputStream());
            this.stripper = new PDFTextStripper();
            this.data = stripper.getText(pd);
            pd.close();
            PrintWriter fileOut = new PrintWriter(filePair.snd);
            fileOut.print(this.data);
            fileOut.close();
        }
    }

    /**
     *
     * @return the data as a string
     */
    String getData(){
        return this.data;
    }
}
