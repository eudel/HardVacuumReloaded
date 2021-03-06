package at.hid.hardvacuumreloaded.missions;

import at.hid.hardvacuumreloaded.HardVacuumReloaded;
import at.hid.hardvacuumreloaded.screens.DialogBig;
import at.hid.hardvacuumreloaded.screens.MissionMenu;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Tut0 {

	public Tut0(Stage stage, Skin skin) {
		DialogBig tut0 = new DialogBig(HardVacuumReloaded.getLangBundle().format("tut0.lblHeadingText.text"), skin) {
			@Override
			public void result() {
				HardVacuumReloaded.playerProfile.setOnMission(false);
				HardVacuumReloaded.playerProfile.setTut0(true);
				HardVacuumReloaded.gameProfile.setActiveMission(null);
				((Game) Gdx.app.getApplicationListener()).setScreen(new MissionMenu());
			}
			
			@Override
			public void cancelled() {
				HardVacuumReloaded.playerProfile.setOnMission(false);
			}
		};
		
		ApplicationType appType = Gdx.app.getType(); // TODO test
		
		tut0.text(HardVacuumReloaded.getLangBundle().format("tut0.controls.text"));
		if (appType.equals(ApplicationType.Android)) {
			tut0.text(HardVacuumReloaded.getLangBundle().format("tut0.select.and") + " " + HardVacuumReloaded.getLangBundle().format("tut0.select.text"));
			tut0.text(HardVacuumReloaded.getLangBundle().format("tut0.move.text1") + " " + HardVacuumReloaded.getLangBundle().format("tut0.move.and") + " " + HardVacuumReloaded.getLangBundle().format("tut0.move.text2"));
		} else {
			tut0.text(HardVacuumReloaded.getLangBundle().format("tut0.select.pc") + " " + HardVacuumReloaded.getLangBundle().format("tut0.select.text"));
			tut0.text(HardVacuumReloaded.getLangBundle().format("tut0.move.text1") + " " + HardVacuumReloaded.getLangBundle().format("tut0.move.pc") + " " + HardVacuumReloaded.getLangBundle().format("tut0.move.text2"));
		}
		tut0.text(" ");
		tut0.text(HardVacuumReloaded.getLangBundle().format("tut0.interfaceButtons.text"));
		tut0.iconText("btn.menu.airstrike", 115, 90, HardVacuumReloaded.getLangBundle().format("tut0.btnMenuAirstrike.text"));
		tut0.iconText("btn.menu.buy", 115, 90, HardVacuumReloaded.getLangBundle().format("tut0.btnMenuBuy.text"));
		tut0.iconText("btn.menu.disc", 115, 90, HardVacuumReloaded.getLangBundle().format("tut0.btnMenuDisc.text"));
		tut0.iconText("btn.menu.grab", 115, 90, HardVacuumReloaded.getLangBundle().format("tut0.btnMenuGrab.text"));
		tut0.iconText("btn.menu.help", 115, 90, HardVacuumReloaded.getLangBundle().format("tut0.btnMenuHelp.text"));
		tut0.iconText("btn.menu.info", 115, 90, HardVacuumReloaded.getLangBundle().format("tut0.btnMenuInfo.text"));
		tut0.iconText("btn.menu.map", 115, 90, HardVacuumReloaded.getLangBundle().format("tut0.btnMenuMap.text"));
		tut0.iconText("btn.menu.options", 115, 90, HardVacuumReloaded.getLangBundle().format("tut0.btnMenuOptions.text"));
		tut0.iconText("btn.menu.selectGroup", 115, 90, HardVacuumReloaded.getLangBundle().format("tut0.btnMenuSelectGroup.text"));
		tut0.iconText("btn.menu.stats", 115, 90, HardVacuumReloaded.getLangBundle().format("tut0.btnMenuStats.text"));
		tut0.setBtnCancelDisabled(true);
		tut0.show(stage);
	}
}
