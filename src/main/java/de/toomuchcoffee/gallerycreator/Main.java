package de.toomuchcoffee.gallerycreator;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class Main {
    public static void main(String[] args) throws IOException {
        String currentDir = System.getProperty("user.dir");
        System.out.println("Working Directory = " + currentDir);

        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.vm");

        Predicate<Path> templatePredicate = path -> pathMatcher.matches(path.getFileName());
        Path templatePath = Files.walk(Paths.get(currentDir))
                .filter(templatePredicate)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No template file provided"));

        List<Path> paths = Files.walk(Paths.get(currentDir))
                .filter(templatePredicate.negate())
                .collect(toList());

        VelocityEngine ve = new VelocityEngine();
        ve.init();

        VelocityContext context = new VelocityContext();
        context.put("items", paths);

        StringWriter stringWriter = new StringWriter();

        InputStream input = new FileInputStream(templatePath.toString());
        InputStreamReader reader = new InputStreamReader(input);

        ve.evaluate(context, stringWriter, "output", reader);

        FileWriter fileWriter = new FileWriter("out.html");
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(stringWriter.toString());
        printWriter.close();
    }

}
