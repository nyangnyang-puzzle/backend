package nyang.puzzlebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class PuzzleBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(PuzzleBackendApplication.class, args);
  }

}
