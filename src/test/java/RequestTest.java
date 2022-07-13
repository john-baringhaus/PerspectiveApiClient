import com.github.cuponadesk.DTO.AnalyzeCommentRequest;
import com.github.cuponadesk.DTO.AnalyzeCommentRequest.Comment;
import com.github.cuponadesk.DTO.AnalyzeCommentResponse;
import com.github.cuponadesk.DTO.Attributes;
import com.github.cuponadesk.PerspectiveClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RequestTest {

  private static PerspectiveClient perspectiveClient;

  @BeforeAll
  public static void init() {
    perspectiveClient = new PerspectiveClient();
  }

  @Test
  public void apiResponseIsValid() {
    AnalyzeCommentRequest commentRequest = AnalyzeCommentRequest.builder()
                                                                .comment(Comment.builder()
                                                                                .text("This is a test").build())
                                                                .build();
    commentRequest.addAttribute(Attributes.TOXICITY_ATTRIBUTE);
    AnalyzeCommentResponse response = perspectiveClient.execute(commentRequest);
    assertNotNull(response);
  }

  @Test
  public void commentIsNotToxic() {
    AnalyzeCommentRequest commentRequest = AnalyzeCommentRequest.builder()
                                                                .comment(Comment.builder()
                                                                                .text("This is a test").build())
                                                                .build();
    commentRequest.addAttribute(Attributes.TOXICITY_ATTRIBUTE);
    AnalyzeCommentResponse response = perspectiveClient.execute(commentRequest);
    assertTrue(response.getToxicityScore()<1, "Message is toxic. Expected non-toxic comment.");
  }

  @Test
  public void commentIsToxic() {
    AnalyzeCommentRequest commentRequest = AnalyzeCommentRequest.builder()
                                                                .comment(Comment.builder()
                                                                                .text("You are a piece of shit").build())
                                                                .build();
    commentRequest.addAttribute(Attributes.TOXICITY_ATTRIBUTE);
    AnalyzeCommentResponse response = perspectiveClient.execute(commentRequest);
    System.out.println(response.getToxicityScore());
    assertTrue(response.getToxicityScore()>.5, "Message isn't toxic. Expected toxic comment.");
  }

  @Test
  public void commentIsSeverelyToxic() {
    AnalyzeCommentRequest commentRequest = AnalyzeCommentRequest.builder()
                                                                .comment(Comment.builder()
                                                                                .text("You are a piece of shit").build())
                                                                .build();
    commentRequest.addAttribute(Attributes.SEVERE_TOXICITY_ATTRIBUTE);
    AnalyzeCommentResponse response = perspectiveClient.execute(commentRequest);
    System.out.println(response.getSevereToxicityScore());
    assertTrue(response.getSevereToxicityScore()>0.5, "Message is non-toxic. Expected severely toxic comment.");
  }

  @Test
  public void commentIsNotThreat() {
    AnalyzeCommentRequest commentRequest = AnalyzeCommentRequest.builder()
                                                                .comment(Comment.builder()
                                                                                .text("You are a piece of shit").build())
                                                                .build();
    commentRequest.addAttribute(Attributes.THREAT_ATTRIBUTE);
    AnalyzeCommentResponse response = perspectiveClient.execute(commentRequest);
    System.out.println(response.getThreatScore());
    assertTrue(response.getThreatScore()<0.5, "Message is a threat. Expected non threatening comment.");
  }

  @Test
  public void commentIsThreat() {
    AnalyzeCommentRequest commentRequest = AnalyzeCommentRequest.builder()
                                                                .comment(Comment.builder()
                                                                                .text("I'm going to come to your house").build())
                                                                .build();
    commentRequest.addAttribute(Attributes.THREAT_ATTRIBUTE);
    AnalyzeCommentResponse response = perspectiveClient.execute(commentRequest);
    System.out.println(response.getThreatScore());
    assertTrue(response.getThreatScore()>0.5, "Message is not a threat. Expected threatening comment.");
  }

}
