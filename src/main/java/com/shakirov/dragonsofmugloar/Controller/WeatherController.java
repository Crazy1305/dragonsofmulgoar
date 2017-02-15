package com.shakirov.dragonsofmugloar.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.util.UriTemplate;

/**
 * Controller gets a weather value as String
 * @author vadim.shakirov
 */
public class WeatherController {
    static final String REQUEST_URL = "http://www.dragonsofmugloar.com/weather/api/report/{gameid}";
    
    public String getWeather(int gameId) throws MalformedURLException, IOException {
        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("gameid", Integer.toString(gameId));
        UriTemplate template = new UriTemplate(REQUEST_URL);
        URL url = template.expand(uriParams).toURL();
        URLConnection connection = url.openConnection();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String result = reader.readLine();
        reader.close();
        return result;
    }
    
}
