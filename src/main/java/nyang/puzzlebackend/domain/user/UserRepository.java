package nyang.puzzlebackend.domain.user;

import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, ObjectId> {

  @Override
  @Query("{ '_id' : ?0, 'isDeleted' : false }")
  Optional<User> findById(ObjectId id);

  Optional<User> findByOauthId(String id);
}
