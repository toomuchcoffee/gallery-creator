package de.toomuchcoffee.gallerycreator;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

public class Main {
    public static final String DEFAULT_OUTPUT_FILE = "out.html";

    public static void main(String[] args) throws IOException {
        String currentDir = System.getProperty("user.dir");

        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:*.vm");

        Predicate<Path> templatePredicate = path -> pathMatcher.matches(path.getFileName());
        String templateName = Files.walk(Paths.get(currentDir))
                .filter(templatePredicate)
                .map(path -> path.getFileName().toString())
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No template file provided"));

        List<Path> paths = Files.walk(Paths.get(currentDir))
                .filter(templatePredicate.negate())
                .collect(toList());

        VelocityContext context = new VelocityContext();
        context.put("items", paths);

        VelocityEngine ve = new VelocityEngine();
        ve.init();

        StringWriter stringWriter = new StringWriter();
        InputStreamReader reader = new InputStreamReader(new FileInputStream(templateName));
        ve.evaluate(context, stringWriter, "output", reader);

        PrintWriter printWriter = new PrintWriter(new FileWriter(DEFAULT_OUTPUT_FILE));
        printWriter.print(stringWriter.toString());
        printWriter.close();
    }

}
