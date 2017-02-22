package com.shakirov.dragonsofmugloar.main;

import com.google.gson.Gson;
import com.shakirov.dragonsofmugloar.controller.BattleResolveController;
import com.shakirov.dragonsofmugloar.controller.DragonClassSerializer;
import com.shakirov.dragonsofmugloar.controller.GameController;
import com.shakirov.dragonsofmugloar.controller.WeatherController;
import com.shakirov.dragonsofmugloar.entity.Dragon;
import com.shakirov.dragonsofmugloar.entity.Game;
import com.shakirov.dragonsofmugloar.entity.GameResult;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vadim.shakirov
 */
public class Main {

    /**
     * @param args the command line arguments
     * 
     */
    public static void main(String[] args) {
        File f = new File("result.log");
        int count = 0;
        if (args.length > 0) {
            count = new Integer(args[0]);
        } 
        try {
            PrintWriter pw = new PrintWriter(f);
            for (int i = 0; i < count; i++) {
                GameController newGame = new GameController();
                Game game = newGame.getGame();
                String weather = new WeatherController().getWeather(game.getId());
                Dragon dragon = new Dragon(game.getKnight(), weather);
                BattleResolveController bc = new BattleResolveController();
                GameResult result = bc.calculate(game, dragon);
                pw.println(new Gson().toJson(game.getKnight()));
                DragonClassSerializer ds = new DragonClassSerializer(dragon);
                pw.println(ds.toJson());
                pw.printf("%s, %s\n", result.getStatus(), result.getMessage());

            }
            pw.close();
        } catch (IOException | IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
