package de.bgesw.app.game.tetris;

import java.awt.Color;

import de.bgesw.appclient.AppClient;

/**
 * The {@code PieceType} enum describes the properties of the various pieces that can be used in the game.
 * @author Brendan Jones
 *
 */
public enum TileType {

	/**
	 * Piece TypeI.
	 */
	
	
	//Alle in 4x4 umschreiben
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
	
	/**
	 * Piece TypeJ.
	 */
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
	
	/**
	 * Piece TypeL.
	 */
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
	
	/**
	 * Piece TypeO.
	 */
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
	
	/**
	 * Piece TypeS.
	 */
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
	
	/**
	 * Piece TypeT.
	 */
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
	
	/**
	 * Piece TypeZ.
	 */
	TypeZ(new boolean[][][] {
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
	
	public static TileType random()
	{
		return TileType.values()[AppClient.randInt(0, TileType.values().length)];
	}
	
	/**
	 * The base color of tiles of this type.
	 */
	private Color baseColor;
	
	/**
	 * The light shading color of tiles of this type.
	 */
	private Color lightColor;
	
	/**
	 * The dark shading color of tiles of this type.
	 */
	private Color darkColor;
	
	/**
	 * The column that this type spawns in.
	 */
	private int spawnCol;
	
	/**
	 * The row that this type spawns in.
	 */
	private int spawnRow;
	
	/**
	 * The dimensions of the array for this piece.
	 */
	private int dimension;
	
	/**
	 * The number of rows in this piece. (Only valid when rotation is 0 or 2,
	 * but it's fine since we're only using it for displaying the next piece
	 * preview, which uses rotation 0).
	 */
	private int rows;
	
	/**
	 * The number of columns in this piece. (Only valid when rotation is 0 or 2,
	 * but it's fine since we're only using it for displaying the next piece
	 * preview, which uses rotation 0).
	 */
	private int cols;
	
	/**
	 * The tiles for this piece. Each piece has an array of tiles for each rotation.
	 */
	public boolean[][][] tiles;
	
	/**
	 * Creates a new TileType.
	 * @param color The base color of the tile.
	 * @param dimension The dimensions of the tiles array.
	 * @param cols The number of columns.
	 * @param rows The number of rows.
	 * @param tiles The tiles.
	 */
	private TileType( boolean[][][] tiles) {
		//this.baseColor = color;
		//this.lightColor = color.brighter();
		//this.darkColor = color.darker();
		this.tiles = tiles;
	}
	
	/**
	 * Gets the base color of this type.
	 * @return The base color.
	 */
	public Color getBaseColor() {
		return baseColor;
	}
	
	/**
	 * Gets the light shading color of this type.
	 * @return The light color.
	 */
	public Color getLightColor() {
		return lightColor;
	}
	
	/**
	 * Gets the dark shading color of this type.
	 * @return The dark color.
	 */
	public Color getDarkColor() {
		return darkColor;
	}
	
	/**
	 * Gets the dimension of this type.
	 * @return The dimension.
	 */
	public int getDimension() {
		return dimension;
	}
	
	/**
	 * Gets the spawn column of this type.
	 * @return The spawn column.
	 */
	public int getSpawnColumn() {
		return spawnCol;
	}
	
	/**
	 * Gets the spawn row of this type.
	 * @return The spawn row.
	 */
	public int getSpawnRow() {
		return spawnRow;
	}
	
	/**
	 * Gets the number of rows in this piece. (Only valid when rotation is 0 or 2,
	 * but it's fine since this is only used for the preview which uses rotation 0).
	 * @return The number of rows.
	 */
	public int getRows() {
		return rows;
	}
	
	/**
	 * Gets the number of columns in this piece. (Only valid when rotation is 0 or 2,
	 * but it's fine since this is only used for the preview which uses rotation 0).
	 * @return The number of columns.
	 */
	public int getCols() {
		return cols;
	}
	
	
}
