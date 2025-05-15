package nyang.puzzlebackend.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nyang.puzzlebackend.api.common.PageContent;
import nyang.puzzlebackend.api.response.puzzle.PuzzleItem;
import nyang.puzzlebackend.auth.AppUser;
import nyang.puzzlebackend.domain.puzzle.PuzzleRepository;
import nyang.puzzlebackend.domain.puzzle.model.Puzzle;
import nyang.puzzlebackend.domain.puzzle.model.QPuzzle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadPuzzleService {

  private final PuzzleRepository puzzleRepository;

  public PageContent<PuzzleItem> findPuzzlesWithCondition(Optional<AppUser> appUser, boolean completed, Pageable pageable) {
    final var puzzlesWithPages = findPuzzles(completed, pageable);
    final var puzzleItems = toPuzzleItems(appUser, puzzlesWithPages);
    final long totalCount = countPuzzles(completed);
    return new PageContent<>(
      puzzleItems,
      pageable.getPageNumber() + 1,
      puzzlesWithPages.getTotalPages(),
      totalCount,
      pageable.getPageSize()
    );
  }

  private Page<Puzzle> findPuzzles(boolean completed, Pageable pageable) {
    QPuzzle puzzle = QPuzzle.puzzle;
    final var query = puzzle.isDeleted.isFalse()
      .and(puzzle.perfection.eq(completed ? 1 : 0))
      .and(puzzle.secretRoom.eq(false));
    return puzzleRepository.findAll(query, pageable);
  }

  // TODO: 2025. 5. 15. Caching
  private long countPuzzles(boolean completed) {
    QPuzzle puzzle = QPuzzle.puzzle;
    final var query = puzzle.isDeleted.isFalse()
      .and(puzzle.perfection.eq(completed ? 1 : 0))
      .and(puzzle.secretRoom.eq(false));
    return puzzleRepository.count(query);
  }

  private static List<PuzzleItem> toPuzzleItems(Optional<AppUser> appUser, Page<Puzzle> puzzlePages) {
    return puzzlePages.getContent().stream()
      .map(p ->
        appUser.map(user -> PuzzleItem.withUser(p, user))
          .orElse(PuzzleItem.of(p))
      )
      .toList();
  }
}
