package nyang.puzzlebackend.domain.puzzle;

import nyang.puzzlebackend.auth.AppUser;
import nyang.puzzlebackend.domain.puzzle.dto.append.CreatePuzzle;
import nyang.puzzlebackend.domain.puzzle.model.Puzzle;
import org.springframework.stereotype.Service;

@Service
public class PuzzleAppendService {

  private final PuzzleRepository puzzleRepository;

  public PuzzleAppendService(PuzzleRepository puzzleRepository) {
    this.puzzleRepository = puzzleRepository;
  }

  public String createPuzzle(AppUser appUser, CreatePuzzle createPuzzle) {
    Puzzle puzzle = createPuzzle.toPuzzle(appUser);
    Puzzle savedPuzzle = puzzleRepository.save(puzzle);
    return savedPuzzle.getId().toString();
  }
}
