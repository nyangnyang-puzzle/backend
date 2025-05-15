package nyang.puzzlebackend.domain.puzzle;

import nyang.puzzlebackend.domain.puzzle.model.Puzzle;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PuzzleRepository extends MongoRepository<Puzzle, ObjectId>, QuerydslPredicateExecutor<Puzzle> {

}
