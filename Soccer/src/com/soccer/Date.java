package com.soccer;

public class Date {
	
	private int date;
	
	private Game game1;
	private Game game2;
	private Game game3;
	private Game game4;
	private Game game5;

	public Date(int date, Game game1, Game game2, Game game3, Game game4, Game game5) {
		this.setDate(date);
		this.setGame1(game1);
		this.setGame2(game2);
		this.setGame3(game3);
		this.setGame4(game4);
		this.setGame5(game5);
	}

	public Game getGame1() {
		return game1;
	}

	public void setGame1(Game game1) {
		this.game1 = game1;
	}

	public Game getGame2() {
		return game2;
	}

	public void setGame2(Game game2) {
		this.game2 = game2;
	}

	public Game getGame3() {
		return game3;
	}

	public void setGame3(Game game3) {
		this.game3 = game3;
	}

	public Game getGame4() {
		return game4;
	}

	public void setGame4(Game game4) {
		this.game4 = game4;
	}

	public Game getGame5() {
		return game5;
	}

	public void setGame5(Game game5) {
		this.game5 = game5;
	}

	public int getDate() {
		return date;
	}

	public void setDate(int date) {
		this.date = date;
	}
}
