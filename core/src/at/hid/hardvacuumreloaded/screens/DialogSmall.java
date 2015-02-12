package at.hid.hardvacuumreloaded.screens;

import at.hid.hardvacuumreloaded.HardVacuumReloaded;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

public class DialogSmall extends Window {
	private Table contentTable, buttonTable;
	private ImageButton ibtnCancel;
	private Skin skin;

	private boolean cancelHide, btnCancelDisabled;
	private Actor previousKeyboardFocus, previousScrollFocus;
	private FocusListener focusListener;

	protected InputListener ignoreTouchDown = new InputListener() {
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
			event.cancel();
			return false;
		}
	};

	public DialogSmall(Skin skin) {
		super("", skin, "dialogSmall");
		setSkin(skin);
		this.skin = skin;
		initialize();
	}

	public DialogSmall(String title, Skin skin) {
		super("", skin, "dialogSmall");
		setSkin(skin);
		this.skin = skin;
		initialize();
	}

	public DialogSmall(String title, Skin skin, String windowStyleName) {
		super("", skin.get(windowStyleName, WindowStyle.class));
		setSkin(skin);
		this.skin = skin;
		initialize();
	}

	public DialogSmall(String title, WindowStyle windowStyle) {
		super("", windowStyle);
		initialize();
	}

	private void initialize() {
		setModal(true);

		add(contentTable = new Table(skin)).width(740).height(300);
		row();
		add(buttonTable = new Table(skin)).width(740).height(140);

		ImageButton ibtnOkay = new ImageButton(skin, "okay");
		ibtnOkay.setBounds(30, 55, 185, 70);
		ibtnCancel = new ImageButton(skin, "cancel");
		ibtnCancel.setBounds(520, 55, 185, 70);

		buttonTable.add().height(15).width(30);
		buttonTable.add().width(185);
		buttonTable.add().width(280);
		buttonTable.add().width(185);
		buttonTable.add().width(60).row();
		buttonTable.add().width(30);
		buttonTable.add(ibtnOkay).width(185).height(70);
		buttonTable.add().width(280);
		buttonTable.add(ibtnCancel).width(185).height(65);
		buttonTable.add().width(60).row();
		buttonTable.add().height(55).width(30);
		buttonTable.add().width(185);
		buttonTable.add().width(280);
		buttonTable.add().width(185);
		buttonTable.add().width(60);

		if (HardVacuumReloaded.DEBUG) {
			debug();
			contentTable.debug();
			buttonTable.debug();
		}

		ibtnOkay.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				result();
				if (!cancelHide)
					hide();
				cancelHide = false;
			}
		});

		ibtnCancel.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (!isBtnCancelDisabled()) {
					if (!cancelHide)
						hide();
					cancelHide = false;
				}
			}
		});
	}

	public DialogSmall text(String text) {
		if (skin == null)
			throw new IllegalStateException("This method may only be used if a Skin has been set.");
		return text(new Label(text, skin));
	}

	public DialogSmall text(Label label) {
		contentTable.add(label).row();
		return this;
	}

	public DialogSmall show(Stage stage) {
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
		setPosition(Math.round((stage.getWidth() - getWidth()) / 2), Math.round((stage.getHeight() - getHeight()) / 2));

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

	public void result() {
	}

	public void cancel() {
		cancelHide = true;
	}

	public boolean isBtnCancelDisabled() {
		return btnCancelDisabled;
	}
	public void setBtnCancelDisabled(boolean btnCancelDisabled) {
		this.btnCancelDisabled = btnCancelDisabled;
		if (btnCancelDisabled) {
			ibtnCancel.setDisabled(true);
		} else {
			ibtnCancel.setDisabled(false);
		}
	}
}
