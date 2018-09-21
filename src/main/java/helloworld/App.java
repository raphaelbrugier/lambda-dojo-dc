package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.google.gson.Gson;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        Gson gson = new Gson();
        Game game = gson.fromJson(input.getBody(), Game.class);
        System.out.println(game);
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        String bestTeam = (game.team1Score > game.team2Score) ? game.team1Name: game.team2Name;
        response.setBody("{\"bestTeam=\": \""+ bestTeam +"\"}");
        response.setStatusCode(200);
        return response;
    }
}
