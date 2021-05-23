package com.company;

//import shufflerCode.Shuffler;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main
{

    public static void main(String[] args) throws Exception
    {
        new Compiler().Compile();


        List<String> cmds = Arrays.asList("cmd.exe","/c","javac","-d","Classes", "*.java");
        ProcessBuilder builder = new ProcessBuilder(cmds).directory(new File(Paths.get(".").toAbsolutePath().normalize().toString() + "\\shufflerCode\\src"));
        builder.redirectErrorStream(true);
        Process p = builder.start();
        printLines(p.getInputStream());
        //printLines(p.getErrorStream());
        p.waitFor();
        TimeUnit.SECONDS.sleep(1);
        System.out.println(p.exitValue());
        if (p.exitValue() == 0)
        {
            cmds = Arrays.asList("cmd.exe","/c","start","java","shufflerCode.Shuffler");
            builder = new ProcessBuilder(cmds).directory(new File(Paths.get(".").toAbsolutePath().normalize().toString() + "\\shufflerCode\\src\\Classes"));
            builder.redirectErrorStream(true);
            p = builder.start();
            printLines(p.getInputStream());
            printLines(p.getErrorStream());
        }
        else
        {
            throw new Exception("Error in java compiler");
        }

    }

    private static void printLines(InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }
    }
}

