package com.company;

import com.company.Data.Production;
import com.company.Parse.ParseUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import static com.company.Parse.ParseUtil.*;
import static com.company.Conversion.ConversionUTIL.convertToCNF;

public class Main {
    public static void main(String[] args) {
        String filePath = new File("src/com/company/IO/input.txt").getAbsolutePath();
        String content = readLineByLine(filePath);
        
        //Parsing the grammar from the input.txt file
        readGrammar(content);

        //Converting to CNF
        convertToCNF(getNonTerm(), getTerm(), getProductions());

    }

    private static String readLineByLine(String filePath)
    {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return contentBuilder.toString();
    }

}
