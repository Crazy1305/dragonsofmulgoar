package com.shakirov.dragonsofmugloar.controller;

import com.google.gson.Gson;
import com.shakirov.dragonsofmugloar.entity.Dragon;
import com.shakirov.dragonsofmugloar.entity.Game;
import com.shakirov.dragonsofmugloar.entity.GameResult;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.util.UriTemplate;

/**
 * Resolve the epic battle!
 * @author vadim.shakirov
 */
public class BattleResolveController {
    
    static final String REQUEST_URL = "http://www.dragonsofmugloar.com/api/game/{gameid}/solution";
    
    public GameResult calculate(Game game, Dragon dragon) throws MalformedURLException, IOException {
        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("gameid", Integer.toString(game.getId()));
        UriTemplate template = new UriTemplate(REQUEST_URL);
        URI uri = template.expand(uriParams);        
        URL url = uri.toURL();
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "Application/json");
        connection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        DragonClassSerializer dragonSerializer = new DragonClassSerializer(dragon);
        System.out.println(dragonSerializer.toJson());
        wr.writeBytes(dragonSerializer.toJson());
        wr.flush();
        wr.close();            
        
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        reader.close();
        return new Gson().fromJson(response, GameResult.class);
    }
    
}
