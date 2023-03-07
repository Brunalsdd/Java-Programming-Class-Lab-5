package com.exercise1.brunadonatoni_comp228lab5;

import java.util.Date;

public class PlayerAndGame {

    private int id;
    private Game game;
    private Player player;
    private String playingDate;
    private String score;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public String getPlayingDate() {
        return playingDate;
    }

    public void setPlayingDate(String playingDate) {
        this.playingDate = playingDate;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "PlayerAndGame{" +
                "id=" + id +
                ", game=" + game +
                ", player=" + player +
                ", playingDate='" + playingDate + '\'' +
                ", score='" + score + '\'' +
                '}';
    }
}
