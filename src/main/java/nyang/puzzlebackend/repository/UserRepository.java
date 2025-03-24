package nyang.puzzlebackend.repository;

import java.util.Optional;
import nyang.puzzlebackend.domain.user.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
  Optional<User> findByOauthId(String id);
}
