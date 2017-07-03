package com.qainfotech.tap.training.snl.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.annotations.Test;

import junit.framework.Assert;

import static org.assertj.core.api.Assertions.*;
import static org.testng.Assert.assertNotEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class BoardTestIO
{
	Board board;
	UUID uuid;
@BeforeMethod
public void loadDb() throws FileNotFoundException, UnsupportedEncodingException, IOException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption
{
board =new Board();
board.registerPlayer("sumit");
board.registerPlayer("shubham");
}
@Test
public void register_player()
			throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException, InvalidTurnException, JSONException, NoUserWithSuchUUIDException {

		assertThat(((JSONObject) board.getData().getJSONArray("players").get(0)).get("position")).isEqualTo(0);
		assertThat( ((JSONObject) board.registerPlayer("Abhi").get(2)).get("name")).isEqualTo("Abhi");
		}
@Test(expectedExceptions=MaxPlayersReachedExeption.class)
public void max_player_reached() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException
{
	board.registerPlayer("ankit");
	board.registerPlayer("paras");
	board.registerPlayer("shivam");
}
@Test(expectedExceptions=PlayerExistsException.class)
public void player_already_exist() throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException, MaxPlayersReachedExeption, IOException
{
	board.registerPlayer("sumit");
	}
@Test(expectedExceptions = GameInProgressException.class)
	public void qregister_player_should_throw_game_in_progress_exception_when_player_comes_in_between_the_game()
			throws FileNotFoundException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
			MaxPlayersReachedExeption, IOException, InvalidTurnException, JSONException, NoUserWithSuchUUIDException {

		board.rollDice((UUID) ((JSONObject) board.getData().getJSONArray("players").get(0)).get("uuid"));
		board.registerPlayer("Ankur");
	}
@Test	public void Check_Constructor_Board() throws IOException{
	   JSONObject  data1 = board.data;
		Board newtest= new Board(board.uuid);
	   JSONObject data2 = newtest.data;
		assertNotEquals(data1,data2);
		
	}
}
