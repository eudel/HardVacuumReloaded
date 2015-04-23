package at.hid.hardvacuumreloaded.entities;

import at.hid.hardvacuumreloaded.Assets;
import at.hid.hardvacuumreloaded.HardVacuumReloaded;

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
	
	private int id;
	
	public enum Direction {
		minerN, minerNE, minerE, minerSE, minerS, minerSW, minerW, minerNW
	}
	
	public Direction direction;
	
	public Miner(Sprite sprite, TiledMapTileLayer collisionLayer, Sprite iconSelected) {
		super(sprite);
		setCollisionLayer(collisionLayer);
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
		
		// set skin according to move direction
		if (velocity.y > 0) {
			setRegion(Assets.minerN);
			setDirection(Direction.minerN);
		}
		if (velocity.x > 0) {
			setRegion(Assets.minerE);
			setDirection(Direction.minerE);
		}
		if (velocity.y < 0) {
			setRegion(Assets.minerS);
			setDirection(Direction.minerS);
		}
		if (velocity.x < 0) {
			setRegion(Assets.minerW);
			setDirection(Direction.minerW);
		}
		
		if ((velocity.x > 0) && (velocity.y > 0)) {
			setRegion(Assets.minerNE);
			setDirection(Direction.minerNE);
		}
		if ((velocity.x > 0) && (velocity.y < 0)) {
			setRegion(Assets.minerSE);
			setDirection(Direction.minerSE);
		}
		if ((velocity.x < 0) && (velocity.y < 0)) {
			setRegion(Assets.minerSW);
			setDirection(Direction.minerSW);
		}
		if ((velocity.x < 0) && (velocity.y > 0)) {
			setRegion(Assets.minerNW);
			setDirection(Direction.minerNW);
		}

		// save old position
		float oldX = getX(), oldY = getY();
		boolean collisionX = false, collisionY = false;

		// move on x
		setX(getX() + (velocity.x * delta));
		if (velocity.x < 0) {
			try {
				// top left
				collisionX = collisionLayer.getCell((int) ((getX() / 5) / tileWidth), (int) (((getY() / 5) + getHeight()) / tileHeight)).getTile().getProperties().containsKey("collision");

				// middle left
				if (!collisionX)
					collisionX = collisionLayer.getCell((int) ((getX() / 5) / tileWidth), (int) (((getY() / 5) + getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey("collision");

				// bottom left
				if (!collisionX)
					collisionX = collisionLayer.getCell((int) ((getX() / 5) / tileWidth), (int) (((getY()) / 5) / tileHeight)).getTile().getProperties().containsKey("collision");

			} catch (Exception e) {

			}
			if (collisionX) {
				setX(oldX);
				velocity.x = 0;
			}
			HardVacuumReloaded.gameProfile.saveProfile();
		} else if (velocity.x > 0) {
			try {
				// top right
				collisionX = collisionLayer.getCell((int) ((getX() / 5) / tileWidth), (int) (((getY() / 5) + getHeight()) / tileHeight)).getTile().getProperties().containsKey("collision");

				// middle right
				if (!collisionX)
					collisionX = collisionLayer.getCell((int) ((getX() / 5) / tileWidth), (int) (((getY() / 5) + getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey("collision");

				// bottom right
				if (!collisionX)
					collisionX = collisionLayer.getCell((int) ((getX() / 5) / tileWidth), (int) (((getY()) / 5) / tileHeight)).getTile().getProperties().containsKey("collision");

			} catch (Exception e) {

			}
			if (collisionX) {
				setX(oldX);
				velocity.x = 0;
			}
			HardVacuumReloaded.gameProfile.saveProfile();
		}

		// move on y
		setY(getY() + velocity.y * delta);
		if (velocity.y < 0) {
			try {
				// bottom left
				collisionY = collisionLayer.getCell((int) ((getX() / 5) / tileWidth), (int) ((getY() / 5) / tileHeight)).getTile().getProperties().containsKey("collision");

				// bottom middle
				if (!collisionY)
					collisionY = collisionLayer.getCell((int) (((getX() / 5) + tileWidth / 2) / tileWidth), (int) ((getY() / 5) / tileHeight)).getTile().getProperties().containsKey("collision");

				// bottom right
				if (!collisionY)
					collisionY = collisionLayer.getCell((int) (((getX() / 5) + tileWidth) / tileWidth), (int) ((getY() / 5) / tileHeight)).getTile().getProperties().containsKey("collision");

			} catch (Exception e) {

			}
			if (collisionY) {
				setY(oldY);
				velocity.y = 0;
			}
			HardVacuumReloaded.gameProfile.saveProfile();
		} else if (velocity.y > 0) {
			try {
				// top left
				collisionY = collisionLayer.getCell((int) ((getX() / 5) / tileWidth), (int) ((getY() / 5) / tileHeight)).getTile().getProperties().containsKey("collision");

				// top middle
				if (!collisionY)
					collisionY = collisionLayer.getCell((int) (((getX() / 5) + tileWidth / 2) / tileWidth), (int) ((getY() / 5) / tileHeight)).getTile().getProperties().containsKey("collision");

				// top right
				if (!collisionY)
					collisionY = collisionLayer.getCell((int) (((getX() / 5) + tileWidth) / tileWidth), (int) ((getY() / 5) / tileHeight)).getTile().getProperties().containsKey("collision");

			} catch (Exception e) {

			}
			if (collisionY) {
				setY(oldY);
				velocity.y = 0;
			}
			HardVacuumReloaded.gameProfile.saveProfile();
		}

		if ((velocity.x == 0) && (velocity.y == 0))
			target = false;

		iconSelected.setX(getX() + velocity.x * delta);
		iconSelected.setY(getY() + 50 + velocity.y * delta);
//		HardVacuumReloaded.gameProfile.saveProfile();
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
	
	public void setTarget(boolean hasTarget) {
		this.target = hasTarget;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	public Sprite getIconSelected() {
		return iconSelected;
	}

	public void setIconSelected(Sprite iconSelected) {
		this.iconSelected = iconSelected;
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
			HardVacuumReloaded.gameProfile.saveProfile();
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
			HardVacuumReloaded.gameProfile.saveProfile();
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
			HardVacuumReloaded.gameProfile.saveProfile();
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
			HardVacuumReloaded.gameProfile.saveProfile();
		}
	}
}
