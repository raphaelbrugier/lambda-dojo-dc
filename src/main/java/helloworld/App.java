package helloworld;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

import java.util.UUID;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    private final DynamoDB dynamoDB;

    public App() {
        AmazonDynamoDB dynamoDBClient = AmazonDynamoDBClientBuilder.defaultClient();
        dynamoDB = new DynamoDB(dynamoDBClient);
    }

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        Gson gson = new Gson();
        Game game = gson.fromJson(input.getBody(), Game.class);
        System.out.println("insert in dynamoDB:" + game);

        dynamoDB.getTable("dojo-dc-raph").putItem(
            new Item()
                .withPrimaryKey("id", UUID.randomUUID().toString())
                .withString("team1Name", game.team1Name)
                .withString("team2Name", game.team2Name)
                .withInt("team1Score", game.team1Score)
                .withInt("team2Score", game.team2Score)
        );

        System.out.println("game inserted");

        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        String bestTeam = (game.team1Score > game.team2Score) ? game.team1Name: game.team2Name;
        response.setBody("{\"bestTeam=\": \""+ bestTeam +"\"}");
        response.setStatusCode(200);
        return response;
    }
}
