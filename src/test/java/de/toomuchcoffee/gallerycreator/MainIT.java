package de.toomuchcoffee.gallerycreator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static de.toomuchcoffee.gallerycreator.Main.DEFAULT_OUTPUT_FILE;
import static org.assertj.core.api.Assertions.assertThat;

class MainIT {
    @BeforeEach
    void setUp() throws Exception{
        Files.deleteIfExists(Path.of(DEFAULT_OUTPUT_FILE));
    }

    @Test
    void runJarShouldCreateOutput() throws Exception {
        int exit = execute(new String[]{});
        assertThat(exit).isEqualTo(0);

        String path = System.getProperty("user.dir") + File.separator + "src/main/java/de/toomuchcoffee/gallerycreator/Main.java";
        assertThat(getFileContent()).contains("href=\"" + path + "\"");
    }

    private int execute(String[] args) throws Exception {
        File jar = new File("target/gallery-creator-jar-with-dependencies.jar");
        String[] execArgs = new String[args.length + 3];
        System.arraycopy(args, 0, execArgs, 3, args.length);
        execArgs[0] = "java";
        execArgs[1] = "-jar";
        execArgs[2] = jar.getCanonicalPath();
        Process p = Runtime.getRuntime().exec(execArgs);
        p.waitFor();
        return p.exitValue();
    }

    private String getFileContent() throws Exception {
        Path path = Path.of("out.html");
        return Files.readString(path,  StandardCharsets.UTF_8);
    }

}