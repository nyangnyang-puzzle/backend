package nyang.puzzlebackend.domain.puzzle;

import nyang.puzzlebackend.domain.puzzle.model.Puzzle;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PuzzleRepository extends MongoRepository<Puzzle, ObjectId> {

}
