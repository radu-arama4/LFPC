package com.utm;

import com.utm.process.ProcessUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;
import static com.utm.parse.ParseUtil.*;

public class Main {

    public static void main(String[] args) {
        String filePath = new File("src/com/utm/io/grammar.txt").getAbsolutePath();
        String grammar = readLineByLine(filePath);
        readGrammar(grammar);
        filePath = new File("src/com/utm/io/input.txt").getAbsolutePath();
        String input = readLineByLine(filePath);

        ProcessUtil.processInput(input, getProductions(), getNonTerm(), getTerm());


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
