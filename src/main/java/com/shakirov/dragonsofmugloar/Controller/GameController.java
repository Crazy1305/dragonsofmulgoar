package com.shakirov.dragonsofmugloar.Controller;

import com.shakirov.dragonsofmugloar.Entity.Game;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Controller starts new game and got game info
 * @author vadim.shakirov
 */
public class GameController {
    
    static final String REQUEST_URL = "http://www.dragonsofmugloar.com/api/game";
    
    public Game getGame() throws MalformedURLException, IOException {
        URL url = new URL(REQUEST_URL);
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String response = reader.readLine();
        Game game = (Game)GsonObjectParser.toObject(response, Game.class);
        reader.close();
        return game;
    }
}
