import io.restassured.config.DecoderConfig;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

public class BaseMethots {
    String APIKey = "APIKey";
    String APIToken ="APIToken";
    public static String boardID;
    public static String idList;
    public static List<String> cardsID;

    //Oluşturulan "Board"un id değerini döndürür.
    public String getBoardId(){
        Response response = given().config(config().decoderConfig(new DecoderConfig().noContentDecoders())).
                when().
                get("https://api.trello.com/1/members/me/boards?key={APIKey}&token={APIToken}",APIKey,APIToken).
                then().extract().response();
        List<String> id =response.jsonPath().getList("id");
        boardID = id.get(0);
        return boardID;


    }
    //Oluşturulan Board'un idList değerini döndürür.
    public String idList(){
        Response response = given().config(config().decoderConfig(new DecoderConfig().noContentDecoders())).
                when().
                get("https://api.trello.com/1/boards/{boardID}/lists?key={APIKey}&token={APIToken}",boardID,APIKey,APIToken).
                then().extract().response();
        List<String> ListsID =response.jsonPath().getList("id");
        idList = ListsID.get(0);
        return idList;
    }
    //Oluşturulan "Card"ların listesini döndürür.
    public List<String> cardsID(){

        Response response = given().config(config().decoderConfig(new DecoderConfig().noContentDecoders())).
                when().
                get("https://api.trello.com/1/lists/{idList}/cards?key={APIKey}&token={APIToken}",idList,APIKey,APIToken).
                then().extract().response();
        cardsID =response.jsonPath().getList("id");

        return cardsID;
    }
}
