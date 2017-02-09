package de.bgesw.app.game.tetris;

import de.bgesw.appclient.AppClient;

public enum TileType {

	TypeI( new boolean[][][] {
		{
			{false,	false,	false,	false},
			{true,	true,	true,	true},
			{false,	false,	false,	false},
			{false,	false,	false,	false}
		},
		{
			{false,	false,	true,	false},
			{false,	false,	true,	false},
			{false,	false,	true,	false},
			{false,	false,	true,	false}
		},
		{
			{false,	false,	false,	false},
			{false,	false,	false,	false},
			{true,	true,	true,	true},
			{false,	false,	false,	false}
		},
		{
			{false,	true,	false,	false},
			{false,	true,	false,	false},
			{false,	true,	false,	false},
			{false,	true,	false,	false}
		}
	}),
	
	TypeJ(new boolean[][][] {
		{
			{false,	false,	false, false},
			{true,	false,	false, false},
			{true,	true,	true, false},
			{false,	false,	false, false}
		},
		{
			{false,	false,	false, false},
			{false,	true,	true, false},
			{false,	true,	false, false},
			{false,	true,	false, false}
		},
		{
			{false,	false,	false, false},
			{true,	true,	true, false},
			{false,	false,	true, false},
			{false,	false,	false, false}
		},
		{
			{false,	false,	false, false},
			{false,	true,	false, false},
			{false,	true,	false, false},
			{true,	true,	false, false}
		}
	}),
	
	TypeL(new boolean[][][] {
		{
			{false ,false, false, false},
			{false,	false,	true, false},
			{true,	true,	true, false},
			{false,	false,	false, false}
		},
		{
			{false ,false, false, false},
			{false,	true,	false, false},
			{false,	true,	false, false},
			{false,	true,	true, false}
		},
		{
			{false ,false, false, false},
			{false,	false,	false, false},
			{true,	true,	true, false},
			{true,	false,	false, false}
		},
		{
			{false ,false, false, false},
			{true,	true,	false, false},
			{false,	true,	false, false},
			{false,	true,	false, false}
		}
	}),
	
	TypeO(new boolean[][][] {
		{
			{false ,false, false, false},
			{false ,false, false, false},
			{true,	true, false, false},
			{true,	true, false, false}
		},
		{
			{false ,false, false, false},
			{false ,false, false, false},
			{true,	true, false, false},
			{true,	true, false, false}
		},
		{
			{false ,false, false, false},
			{false ,false, false, false},	
			{true,	true, false, false},
			{true,	true, false, false}
		},
		{
			{false ,false, false, false},
			{false ,false, false, false},
			{true,	true, false, false},
			{true,	true, false, false}
		}
	}),
	
	TypeS(new boolean[][][] {
		{
			{false ,false, false, false},
			{false,	true,	true, false},
			{true,	true,	false, false},
			{false,	false,	false, false}
		},
		{
			{false ,false, false, false},
			{false,	true,	false, false},
			{false,	true,	true, false},
			{false,	false,	true, false}
		},
		{
			{false ,false, false, false},
			{false,	false,	false, false},
			{false,	true,	true, false},
			{true,	true,	false, false}
		},
		{
			{false ,false, false, false},
			{true,	false,	false, false},
			{true,	true,	false, false},
			{false,	true,	false, false}
		}
	}),
	
	TypeT(new boolean[][][] {
		{
			{false ,false, false, false},
			{false,	true,	false, false},
			{true,	true,	true, false},
			{false,	false,	false, false}
		},
		{
			{false ,false, false, false},
			{false,	true,	false, false},
			{false,	true,	true, false},
			{false,	true,	false, false}
		},
		{
			{false ,false, false, false},
			{false,	false,	false, false},
			{true,	true,	true, false},
			{false,	true,	false, false}
		},
		{
			{false ,false, false, false},
			{false,	true,	false, false},
			{true,	true,	false, false},
			{false,	true,	false, false}
		}
	}),
	
	TypeZ(new boolean[][][]{
		{
			{false ,false, false, false},
			{true,	true,	false, false},
			{false,	true,	true, false},
			{false,	false,	false, false}
		},
		{
			{false ,false, false, false},
			{false,	false,	true, false},
			{false,	true,	true, false},
			{false,	true,	false, false}
		},
		{
			{false ,false, false, false},
			{false,	false,	false, false},
			{true,	true,	false, false},
			{false,	true,	true, false}
		},
		{
			{false ,false, false, false},
			{false,	true,	false, false},
			{true,	true,	false, false},
			{true,	false,	false, false}
		}
	});
	
	public static TileType random() //Zufällige Tile Form
	{
		return TileType.values()[AppClient.randInt(0, TileType.values().length)];
	}
	
	public boolean[][][] tiles;
	
	private TileType( boolean[][][] tiles) {
		this.tiles = tiles;
	}
	
}
