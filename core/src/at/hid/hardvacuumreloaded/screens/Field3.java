package at.hid.hardvacuumreloaded.screens;

import at.hid.hardvacuumreloaded.HardVacuumReloaded;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

public class Field3 extends Window {
	private Table contentTable;
	private Skin skin;
	private Label lblField1, lblField2;
	private boolean field1Red = false, field2Red = false;

	private Actor previousKeyboardFocus, previousScrollFocus;
	private FocusListener focusListener;

	protected InputListener ignoreTouchDown = new InputListener() {
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			event.cancel();
			return false;
		}
	};

	public Field3(Skin skin) {
		super("", skin, "field3");
		setSkin(skin);
		this.skin = skin;
		initialize();
	}

	private void initialize() {
		setModal(true);

		add(contentTable = new Table(skin)).width(300).height(400);

		lblField1 = new Label("", skin);
		lblField2 = new Label("", skin);

		contentTable.add().width(15).height(100);
		contentTable.add(lblField1).width(85).height(100);
		contentTable.add().width(200).height(100).row();
		contentTable.add().colspan(3).width(300).height(200).row();
		contentTable.add().width(15).height(70);
		contentTable.add(lblField2).colspan(2).width(285).height(70).row();
		contentTable.add().colspan(3).width(300).height(30);

		if (HardVacuumReloaded.DEBUG) {
			debug();
			contentTable.debug();
			lblField1.debug();
			lblField2.debug();
		}
	}

	public Field3 show(Stage stage, float x, float y) {
		clearActions();
		removeCaptureListener(ignoreTouchDown);

		previousKeyboardFocus = null;
		Actor actor = stage.getKeyboardFocus();
		if (actor != null && !actor.isDescendantOf(this))
			previousKeyboardFocus = actor;

		previousScrollFocus = null;
		actor = stage.getScrollFocus();
		if (actor != null && !actor.isDescendantOf(this))
			previousScrollFocus = actor;

		pack();
		stage.addActor(this);
		stage.setKeyboardFocus(this);
		stage.setScrollFocus(this);
		setPosition(x, y);

		return this;
	}

	public void hide() {
		Stage stage = getStage();
		if (stage != null) {
			removeListener(focusListener);
			if (previousKeyboardFocus != null && previousKeyboardFocus.getStage() == null)
				previousKeyboardFocus = null;
			Actor actor = stage.getKeyboardFocus();
			if (actor == null || actor.isDescendantOf(this))
				stage.setKeyboardFocus(previousKeyboardFocus);
			if (previousScrollFocus != null && previousScrollFocus.getStage() == null)
				previousScrollFocus = null;
			actor = stage.getScrollFocus();
			if (actor == null || actor.isDescendantOf(this))
				stage.setScrollFocus(previousScrollFocus);
		}
		remove();
	}

	public void setField1Red(boolean red) {
		if (red == true) {
			if (isField2Red()) {
				this.setBackground("field3.red12");
			} else {
				this.setBackground("field3.red1");
			}
		} else {
			if (isField2Red()) {
				this.setBackground("field3.red2");
			} else {
				this.setBackground("field3.red");
			}
		}
		field1Red = red;
	}

	public boolean isField1Red() {
		return field1Red;
	}

	public void setField2Red(boolean red) {
		if (red == true) {
			if (isField1Red()) {
				this.setBackground("field3.red12");
			} else {
				this.setBackground("field3.red2");
			}
		} else {
			if (isField1Red()) {
				this.setBackground("field3.red1");
			} else {
				this.setBackground("field3.red");
			}
		}
		field2Red = red;
	}

	public boolean isField2Red() {
		return field2Red;
	}

	public String getField1() {
		return lblField1.getText().toString();
	}

	public void setField1(int value) {
		if (value < 0)
			value = 0;
		else if (value > 99) {
			value = 99;
		}
		lblField1.setText(Integer.toString(value));
	}

	public String getField2() {
		return lblField2.getText().toString();
	}

	public void setField2(int value) {
		if (value < 0)
			value = 0;
		else if (value > 99999999) {
			value = 99999999;
		}
		lblField2.setText(Integer.toString(value));
	}
}
