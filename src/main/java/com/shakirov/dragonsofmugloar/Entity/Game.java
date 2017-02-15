package com.shakirov.dragonsofmugloar.Entity;

/**
 *
 * @author vadim.shakirov
 */
public class Game {
    private final int gameId;
    private final Knight knight;
    
    public Game() {
        gameId = 0;
        knight = null;
    }
    
    public int getId() { return gameId; }
    public Knight getKnight() { return knight; }
    
}
