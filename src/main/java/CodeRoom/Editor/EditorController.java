package CodeRoom.Editor;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class EditorController {
    @PostMapping("/compile")
    public String compile(@RequestBody String content) {
        try {
            // 1. Write code to file
            Path filePath = Paths.get("JavaProgram.java");
            Files.writeString(filePath, content);

            // 2. Run shell script
            ProcessBuilder builder = new ProcessBuilder("./run.sh");
            builder.redirectErrorStream(true);

            System.out.println("1 . Running shell script...");

            Process process = builder.start();

            // 3. Capture output
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            StringBuilder output = new StringBuilder();
            String line;

            System.out.println("2 . Capturing output...");

            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            process.waitFor();

            System.out.println("3 . Returning output...");

            return output.toString();

        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
