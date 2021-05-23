package com.company.ShufflerSymbols;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import com.google.gson.Gson;

public class LoadShufflerSymbols
{
    public ShufflerSymbols Load() throws Exception
    {
        FileReader fr = new FileReader(Paths.get(".").toAbsolutePath().normalize().toString() + "/ShufflerSymbols.json");
        BufferedReader br = new BufferedReader(fr);
        String json = Files.readString(Paths.get("./ShufflerSymbols.json").toAbsolutePath().normalize());

        return ReadJson(json);
    }

    ShufflerSymbols ReadJson(String json)
    {
        return new Gson().fromJson(json, ShufflerSymbols.class);
    }

}
