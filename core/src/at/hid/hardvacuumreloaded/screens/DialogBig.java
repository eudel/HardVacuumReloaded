package at.hid.hardvacuumreloaded.screens;

import at.hid.hardvacuumreloaded.HardVacuumReloaded;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;

public class DialogBig extends Window {
	private Table headerTable, contentTable, buttonTable;
	private ScrollPane spContent;
	private Label lblHeader;
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

	public DialogBig(String title, Skin skin) {
		super("", skin, "dialogBig");
		setSkin(skin);
		this.skin = skin;
		lblHeader = new Label(title, skin);
		initialize();
	}

	public DialogBig(String title, Skin skin, String windowStyleName) {
		super("", skin.get(windowStyleName, WindowStyle.class));
		setSkin(skin);
		this.skin = skin;
		lblHeader = new Label(title, skin);
		initialize();
	}

	public DialogBig(String title, WindowStyle windowStyle) {
		super(title, windowStyle);
		initialize();
	}

	private void initialize() {
		setModal(true);

		add(headerTable = new Table(skin)).width(1560).height(125);
		row();
		add(spContent = new ScrollPane(contentTable = new Table(skin), skin)).width(1560).height(760);
		contentTable.setFillParent(true);
		row();
		add(buttonTable = new Table(skin)).width(1560).height(90);

		ImageButton ibtnOkay = new ImageButton(skin, "okay");
		ibtnOkay.setBounds(30, 55, 185, 70);
		ibtnCancel = new ImageButton(skin, "cancel");
		ibtnCancel.setBounds(520, 55, 185, 70);

		headerTable.add().height(50).row();
		headerTable.add(lblHeader).height(70).width(900).row();
		lblHeader.setAlignment(Align.center);

		buttonTable.add().height(15).width(10);
		buttonTable.add().width(185);
		buttonTable.add().width(1160);
		buttonTable.add().width(185);
		buttonTable.add().width(15).row();
		buttonTable.add().width(10);
		buttonTable.add(ibtnOkay).width(185).height(70);
		buttonTable.add().width(1160);
		buttonTable.add(ibtnCancel).width(185).height(65);
		buttonTable.add().width(15).row();
		buttonTable.add().height(20).width(10);
		buttonTable.add().width(185);
		buttonTable.add().width(1160);
		buttonTable.add().width(185);
		buttonTable.add().width(15);

		if (HardVacuumReloaded.DEBUG) {
			debug();
			headerTable.debug();
			spContent.debug();
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
					cancelled();
					if (!cancelHide)
						hide();
					cancelHide = false;
				}
			}
		});
	}

	public DialogBig text(String text) {
		if (skin == null)
			throw new IllegalStateException("This method may only be used if a Skin has been set.");
		Label label = new Label(text, skin);
		label.setWrap(true);
		return text(label);
	}

	public DialogBig text(Label label) {
		contentTable.add();
		contentTable.add(label).row();
		return this;
	}

	public DialogBig iconText(String iconStyle, float width, float height, String text) {
		if (skin == null)
			throw new IllegalStateException("This method may only be used if a Skin has been set.");
		Label label = new Label(text, skin);
		label.setWrap(true);
		return iconText(new Label("", skin, iconStyle), width, height, label);
	}

	public DialogBig iconText(Label labelIcon, float width, float height, Label labelText) {
		contentTable.add(labelIcon).width(width).height(height);
		contentTable.add(labelText).width(1300 - width).row();
		return this;
	}

	public DialogBig header(String text) {
		if (skin == null)
			throw new IllegalStateException("This method may only be used if a Skin has been set.");
		return header(new Label(text, skin));
	}

	public DialogBig header(Label label) {
		headerTable.add(label);
		return this;
	}

	public DialogBig show(Stage stage) {
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
		contentTable.add().height(300);

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

	public void cancelled() {
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
