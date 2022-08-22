
import io.restassured.config.DecoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.*;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WebServiceTest extends BaseMethots {

    public int cardsIDSize;
    @Test
    @Order(1)
    public void createBoard(){
        String postData = "{\n" +
                "  \"name\": \"TestBoard\",\n" +
                "  \"key\": \"" + APIKey + "\",\n" +
                "  \"token\": \"" + APIToken + "\"\n" +
                "}";
        given().
                contentType(ContentType.JSON).
                body(postData).
                when().
                post("https://api.trello.com/1/boards/").
                then().
                statusCode(200);

        getBoardId();
        System.out.println("Olusturulan BoardID = "+boardID);
    }

    @Test
    @Order(2)
    public void createCards(){
        idList();

        String postData = "{\n" +
                "  \"idList\": \"" + idList + "\",\n" +
                "  \"key\": \"" + APIKey + "\",\n" +
                "  \"token\": \"" + APIToken + "\"\n" +
                "}";
        int cardQuantity = 2;

       for(int i=0;i < cardQuantity; i++ ){

           given().
                   contentType(ContentType.JSON).
                   body(postData).
                   when().
                   post("https://api.trello.com/1/cards").
                   then().
                   statusCode(200);
       }
    }

    @Test
    @Order(3)
    public void updateCard(){
        cardsID();
        Random rand = new Random();

        cardsIDSize = cardsID.size();
        int randomCardIndex = rand.nextInt(cardsIDSize);
        String updateCardID = cardsID.get(randomCardIndex);
        String postData = "{\n" +
                "  \"name\": \"updated card\",\n" +
                "  \"key\": \"" + APIKey + "\",\n" +
                "  \"token\": \"" + APIToken + "\"\n" +
                "}";
        given().
                contentType(ContentType.JSON).
                body(postData).
                when().
                put("https://api.trello.com/1/cards/{updateCardID}",updateCardID).
                then().
                statusCode(200);
    }

    @Test
    @Order(4)
    public void deleteCards(){
        String postData = "{\n" +
                "  \"key\": \"" + APIKey + "\",\n" +
                "  \"token\": \"" + APIToken + "\"\n" +
                "}";
        for(int j=0; j<cardsIDSize; j++){
            String currentCardID =cardsID.get(j) ;
            given().
                    contentType(ContentType.JSON).
                    body(postData).
                    when().
                    delete("https://api.trello.com/1/cards/{currentCardID}",currentCardID).
                    then().
                    statusCode(200);
        }
    }

    @Test
    @Order(5)
    public void deleteBoard(){
        String postData = "{\n" +
                "  \"key\": \"" + APIKey + "\",\n" +
                "  \"token\": \"" + APIToken + "\"\n" +
                "}";
        given().
                contentType(ContentType.JSON).
                body(postData).
                when().
                delete("https://api.trello.com/1/boards/{BoardID}",boardID).
                then().
                statusCode(200);
        System.out.println(boardID+" id'li Board silindi.");

    }
}
