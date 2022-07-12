import com.github.cuponadesk.DTO.AnalyzeCommentRequest;
import com.github.cuponadesk.DTO.AnalyzeCommentRequest.Comment;
import com.github.cuponadesk.DTO.AnalyzeCommentResponse;
import com.github.cuponadesk.DTO.Attributes;
import com.github.cuponadesk.PerspectiveClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class RequestTest {

  private static PerspectiveClient perspectiveClient;

  @BeforeAll
  public static void init() {
    perspectiveClient = new PerspectiveClient();
  }

  @Test
  public void getUserByUserName() {
    AnalyzeCommentRequest commentRequest = AnalyzeCommentRequest.builder()
                                                                .comment(Comment.builder()
                                                                                .text("This is a test").build())
                                                                .build();
    commentRequest.addAttribute(Attributes.THREAT_ATTRIBUTE);
    commentRequest.addAttribute(Attributes.TOXICITY_ATTRIBUTE);
    AnalyzeCommentResponse response = perspectiveClient.execute(commentRequest);
    System.out.println(response.toString());
  }
}
