package at.hid.hardvacuumreloaded.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;

public class Mine extends Sprite {
	private int tileWidth = 20, tileHeight = 20;
	private boolean selected, visible;
	private Sprite iconSelected;

	public Mine(Sprite sprite, Sprite iconSelected) {
		super(sprite);
		this.iconSelected = iconSelected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
