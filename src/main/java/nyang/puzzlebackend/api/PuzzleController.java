package nyang.puzzlebackend.api;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import nyang.puzzlebackend.api.request.puzzle.PuzzleCreateRequest;
import nyang.puzzlebackend.auth.AppUser;
import nyang.puzzlebackend.auth.AuthPrincipal;
import nyang.puzzlebackend.domain.puzzle.PuzzleAppendService;
import nyang.puzzlebackend.domain.puzzle.dto.append.CreatePuzzle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PuzzleController {

  private final PuzzleAppendService puzzleCreateService;

  @PostMapping(value = "/api/puzzles")
  public ResponseEntity<?> createPuzzle(
      @RequestBody PuzzleCreateRequest puzzleCreateRequest,
      @AuthPrincipal AppUser appUser
  ) {
    String puzzleId = puzzleCreateService.createPuzzle(appUser, CreatePuzzle.from(puzzleCreateRequest));
    return ResponseEntity
        .created(URI.create(String.format("/api/puzzles/%s", puzzleId)))
        .build();
  }
}
