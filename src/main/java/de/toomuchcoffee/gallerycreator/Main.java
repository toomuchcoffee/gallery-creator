package de.toomuchcoffee.gallerycreator;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.function.Predicate;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toList;

public class Main {
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

        Velocity.mergeTemplate(templateName, UTF_8.name(), context, new FileWriter("out.html"));
    }

}
