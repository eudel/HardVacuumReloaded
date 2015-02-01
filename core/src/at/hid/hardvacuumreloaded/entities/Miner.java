package at.hid.hardvacuumreloaded.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Miner extends Sprite {

	private int tileWidth = 20, tileHeight = 20;

	private TiledMapTileLayer collisionLayer;

	public Miner(Sprite sprite, TiledMapTileLayer collisionLayer) {
		super(sprite);
		this.collisionLayer = collisionLayer;
	}

	public void draw(SpriteBatch spriteBatch) {
		super.draw(spriteBatch);
	}
	
	public void moveXpos(int steps) {
		float newX = getX() + (steps * tileWidth);
		boolean collisionX = false;

		try {
			collisionX = collisionLayer.getCell((int) ((newX + tileWidth) / tileWidth), (int) (getY() / tileHeight)).getTile().getProperties().containsKey("collision");
		} catch (Exception e) {

		}
		if (!collisionX) {
			setX(newX);
		}
	}
	public void moveXneg(int steps) {
		float newX = getX() - (steps * tileWidth);
		boolean collisionX = false;
		
		try {
			collisionX = collisionLayer.getCell((int) (newX / tileWidth), (int) (getY() / tileHeight)).getTile().getProperties().containsKey("collision");
		} catch (Exception e) {
			
		}
		if (!collisionX) {
			setX(newX);
		}
	}

	public void moveYpos(int steps) {
		float newY = getY() + (steps * tileHeight);
		boolean collisionY = false;

		try {
			collisionY = collisionLayer.getCell((int) (getX() / tileWidth), (int) (newY / tileHeight)).getTile().getProperties().containsKey("collision");
		} catch (Exception e) {

		}
		if (!collisionY) {
			setY(newY);
		}
	}
	public void moveYneg(int steps) {
		float newY = getY() - (steps * tileHeight);
		boolean collisionY = false;
		
		try {
			collisionY = collisionLayer.getCell((int) (getX() / tileWidth), (int) (newY / tileHeight)).getTile().getProperties().containsKey("collision");
		} catch (Exception e) {
			
		}
		if (!collisionY) {
			setY(newY);
		}
	}
}
