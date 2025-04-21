package nyang.puzzlebackend.domain.user;

import java.time.LocalDateTime;
import lombok.Getter;
import nyang.puzzlebackend.auth.oauth.OAuthProvider;
import nyang.puzzlebackend.global.error.ErrorCode;
import nyang.puzzlebackend.global.error.PuzzleException;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Field.Write;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Getter
@Document(collection = "users")
public class User {

  @Id
  private ObjectId id;

  @Indexed(unique = true)
  private String oauthId;

  @Field(targetType = FieldType.STRING)
  private OAuthProvider oAuthProvider;

  @Indexed(unique = true)
  private String nickname;

  @CreatedDate
  private LocalDateTime createdAt;

  @LastModifiedDate
  private LocalDateTime updatedAt;

  private LocalDateTime deletedAt;

  @Field(write = Write.NON_NULL)
  private boolean isDeleted;

  public User(String oauthId, OAuthProvider oAuthProvider) {
    this.oauthId = oauthId;
    this.oAuthProvider = oAuthProvider;
    this.isDeleted = false;
  }

  public void initNickname(String nickname) {
    if (this.nickname != null)
      throw new PuzzleException(ErrorCode.U005);
    this.nickname = nickname;
  }
}

