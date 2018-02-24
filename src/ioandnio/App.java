package ioandnio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {

        Path path = Paths.get("test.txt");
        List<String> result;
        try {
            result = Files.readAllLines(path);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.lines(path).forEach(System.out::println);
            List<String> res = Files.lines(path).filter(s -> s.startsWith("a")).collect(Collectors.toList());
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File aFile = new File("test.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(aFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
