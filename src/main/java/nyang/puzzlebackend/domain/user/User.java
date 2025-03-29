package nyang.puzzlebackend.domain.user;

import java.time.LocalDateTime;
import nyang.puzzlebackend.auth.oauth.OAuthProvider;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Document(collection = "users")
public record User(
    @Id ObjectId id,
    @Indexed(unique = true) String oauthId,
    @Field(targetType = FieldType.STRING) OAuthProvider oAuthProvider,
    @Indexed(unique = true) String nickname,
    @CreatedDate LocalDateTime createdAt,
    @LastModifiedDate LocalDateTime updatedAt,
    LocalDateTime deletedAt,
    boolean isDeleted
) {

  public User(String oauthId, OAuthProvider oAuthProvider) {
    this(null, oauthId, oAuthProvider, null, null, null, null, false);
  }
}
