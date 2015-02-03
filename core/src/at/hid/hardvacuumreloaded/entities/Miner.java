package at.hid.hardvacuumreloaded.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Miner extends Sprite {

	private Vector2 velocity = new Vector2();
	int targetX = 0, targetY = 0;
	private float speed = 60 * 2, gravity = 20;

	private int tileWidth = 20, tileHeight = 20;
	private boolean selected, target;

	private TiledMapTileLayer collisionLayer;
	private Sprite iconSelected;

	public Miner(Sprite sprite, TiledMapTileLayer collisionLayer, Sprite iconSelected) {
		super(sprite);
		this.collisionLayer = collisionLayer;
		this.iconSelected = iconSelected;
	}

	public void draw(SpriteBatch spriteBatch) {

		super.draw(spriteBatch);
		update(Gdx.graphics.getDeltaTime());
	}

	public void update(float delta) {
		iconSelected.setAlpha(0);
		if (isSelected()) {
			iconSelected.setAlpha(1);
		} else {
			iconSelected.setAlpha(0);
		}
		iconSelected.setX(getX());
		iconSelected.setY(getY() + 50);
		System.out.println(targetX);
		System.out.println((int) getX());

		// move to target if target is set
		if (targetX != (int) getX()) {
			if (targetX > (int) getX()) {
				velocity.x += gravity;
			} else if (targetX < (int) getX()) {
				velocity.x -= gravity;
			}
		} else {
			velocity.x = 0;
		}
		if (targetY != (int) getY()) {
			if (targetY > (int) getY()) {
				velocity.y += gravity;
			} else if (targetY < (int) getY()) {
				velocity.y -= gravity;
			}
		} else {
			velocity.y = 0;
		}

		// clamp velocity
		if (velocity.x > speed)
			velocity.x = speed;
		else if (velocity.x < -speed)
			velocity.x = -speed;

		if (velocity.y > speed)
			velocity.y = speed;
		else if (velocity.y < -speed)
			velocity.y = -speed;

		// save old position
		float oldX = getX(), oldy = getY();
		boolean collisionX = false, collisionY = false;

		// move on x
		setX(getX() + (velocity.x * delta));
		if (velocity.x < 0) {
			// top left
			try {
				collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight()) / tileHeight)).getTile().getProperties().containsKey("collision");

				// middle left
				collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey("collision");

				// bottom left
				collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY()) / tileHeight)).getTile().getProperties().containsKey("collision");
			} catch (Exception e) {

			}

		} else if (velocity.x > 0) {

		}

		// move on y
		setY(getY() + velocity.y * delta);
		if (velocity.y < 0) {

		} else if (velocity.y > 0) {

		}
		
		if ((velocity.x == 0) && (velocity.y == 0))
			target = false;

		iconSelected.setX(getX() + velocity.x * delta);
		iconSelected.setY(getY() + 50 + velocity.y * delta);
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected) {
			iconSelected.setAlpha(1);
		} else {
			iconSelected.setAlpha(0);
		}
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public float getTargetX() {
		return targetX;
	}

	public float getTargetY() {
		return targetY;
	}

	public void setTarget(int targetX, int targetY) {
		this.targetX = targetX;
		this.targetY = targetY;
		target = true;
	}
	
	public boolean hasTarget() {
		return target;
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
