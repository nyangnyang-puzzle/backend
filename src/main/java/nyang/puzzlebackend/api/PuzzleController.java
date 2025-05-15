package nyang.puzzlebackend.api;

import java.net.URI;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nyang.puzzlebackend.api.common.ApiResponse;
import nyang.puzzlebackend.api.common.PageContent;
import nyang.puzzlebackend.api.request.puzzle.PuzzleCreateRequest;
import nyang.puzzlebackend.api.response.puzzle.PuzzleItem;
import nyang.puzzlebackend.auth.AppUser;
import nyang.puzzlebackend.auth.AuthPrincipal;
import nyang.puzzlebackend.auth.OptionalAuthPrincipal;
import nyang.puzzlebackend.domain.puzzle.CreatePuzzleService;
import nyang.puzzlebackend.domain.puzzle.dto.append.CreatePuzzle;
import nyang.puzzlebackend.service.ReadPuzzleService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/puzzles")
@RequiredArgsConstructor
public class PuzzleController {

  private final CreatePuzzleService createPuzzleService;
  private final ReadPuzzleService readPuzzleService;

  @PostMapping
  public ResponseEntity<?> createPuzzle(
      @RequestBody PuzzleCreateRequest puzzleCreateRequest,
      @AuthPrincipal AppUser appUser
  ) {
    String puzzleId = createPuzzleService.createPuzzle(appUser, CreatePuzzle.from(puzzleCreateRequest));
    return ResponseEntity
        .created(URI.create(String.format("/api/puzzles/%s", puzzleId)))
        .build();
  }

  @GetMapping
  public ApiResponse<PageContent<PuzzleItem>> getPublicPuzzles(
    @OptionalAuthPrincipal Optional<AppUser> appUser,
    @RequestParam(required = false, value = "false") boolean completed,
    @PageableDefault(size = 20, page = 1, sort = "createdAt", direction = Direction.ASC) Pageable pageable
  ) {
    var puzzleItems = readPuzzleService.findPuzzlesWithCondition(appUser, completed, pageable);
    return ApiResponse.ok(puzzleItems);
  }
}
