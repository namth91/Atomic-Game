package com.atomic.game;
import java.util.ArrayList;

import com.atomic.abstractgame.AbstractScreen;
import com.atomic.abstractgame.ActorGame;
import com.atomic.abstractgame.BroadGame;
import com.atomic.abstractgame.ButtonGame;
import com.atomic.abstractgame.MenuCreator;
import com.atomic.asset.Asset;
import com.atomic.asset.JsonLevel;
import com.atomic.option.OptionGame;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class ScreenGame extends AbstractScreen{
	
	private ButtonGame btnRedo;
	private ButtonGame btnUndo;
	private ButtonGame btnReset;
	private ButtonGame btnLeft;
	private ButtonGame btnRight;
	private ButtonGame btnDown;
	private ButtonGame btnUp;
	
	private int pointCurrentX;
	private int pointCurrentY;
	
	private Image avatar;
	private BroadGame broad;
	
	private ArrayList<ActorGame> lstActor = new ArrayList<ActorGame>();
	
	public ScreenGame(Game _game, String _nameScreen, JsonLevel _jsonInfo) {
		super(_game, _nameScreen);
		broad = new BroadGame(_jsonInfo);
		setUpScreenElement();
	}

	@Override
	public void setUpScreenElement() {
		setBackButton(true);
		setBackground(new TextureRegion(Asset.loadTexture("bg.png")));	
		setUpButtonGame();
		setUpLstActor();
		setUpMapGame();
		setUpbtnMoveGame();
	}
	
	private void setUpbtnMoveGame() {
		btnLeft = MenuCreator.createCustomGameButton(Asset.btnLeft, Asset.btnLeft);
		btnRight = MenuCreator.createCustomGameButton(Asset.btnRight, Asset.btnRight);
		btnDown = MenuCreator.createCustomGameButton(Asset.btnDown, Asset.btnDown);
		btnUp = MenuCreator.createCustomGameButton(Asset.btnUp, Asset.btnUp);
		pointCurrentX = lstActor.get(0).getPosX();
		pointCurrentY = lstActor.get(0).getPoxY();
		setPosBtnMove();
		
		getStage().addActor(btnLeft);
		getStage().addActor(btnRight);
		getStage().addActor(btnDown);
		getStage().addActor(btnUp);
		showBtnMove();
		
	}
	
	private void setPosBtnMove() {
		btnLeft.setPosition(broad.getStartDrawX() + (pointCurrentY-1)*53, broad.getStartDrawY() + (broad.getWidthBroad() - pointCurrentX)*53);
		btnRight.setPosition(broad.getStartDrawX() + (pointCurrentY+1)*53, broad.getStartDrawY() + (broad.getWidthBroad() - pointCurrentX)*53);
		btnDown.setPosition(broad.getStartDrawX() + pointCurrentY*53, broad.getStartDrawY() + (broad.getWidthBroad() - pointCurrentX - 1)*53);
		btnUp.setPosition(broad.getStartDrawX() + pointCurrentY*53, broad.getStartDrawY() + (broad.getWidthBroad() - pointCurrentX + 1)*53);
	}
	
	private void showBtnMove() {
		if (!broad.getMapBroad()[pointCurrentX-1][pointCurrentY].equals(".")) {
			btnDown.setVisible(false);
			btnDown.setDisabled(true);
		} else {
			btnDown.setVisible(true);
			btnDown.setDisabled(false);
		}
		if (!broad.getMapBroad()[pointCurrentX+1][pointCurrentY].equals(".")) {
			btnUp.setVisible(false);
			btnUp.setDisabled(true);
		} else {
			btnUp.setVisible(true);
			btnUp.setDisabled(false);
		}
		if (!broad.getMapBroad()[pointCurrentX][pointCurrentY-1].equals(".")) {
			btnLeft.setVisible(false);
			btnLeft.setDisabled(true);
		} else {
			btnLeft.setVisible(true);
			btnLeft.setDisabled(false);
		}
		if (!broad.getMapBroad()[pointCurrentX][pointCurrentY+1].equals(".")) {
			btnRight.setVisible(false);
			btnRight.setDisabled(true);
		} else {
			btnRight.setVisible(true);
			btnRight.setDisabled(false);
		}
	}
	
	private void setUpLstActor() {
		for (int i = 0; i < broad.getMapInfo().getListAtoms().size(); i++) {
			ActorGame actorG = new ActorGame();
			actorG.setId(broad.getMapInfo().getListAtoms().get(i).getID());
			actorG.setType(broad.getMapInfo().getListAtoms().get(i).getType());
			actorG.setLstLink(broad.getMapInfo().getListAtoms().get(i).getTypeCon());
			actorG.setUpAuto();
			lstActor.add(actorG);
		}
	}
	
	private void setUpMapGame() {
		for (int i = 0; i < broad.getHeightBroad(); i++) {
			for (int j = 0; j < broad.getWidthBroad(); j++) {
				if (broad.getLocal(i, j).equals(OptionGame.WALL)) {
					drawWall(broad.getWidthBroad() - i, j);
				} else {
					for (int j2 = 0; j2 < lstActor.size(); j2++) {
						if (broad.getLocal(i, j).equals(lstActor.get(j2).getId())) {
							lstActor.get(j2).setPosActor(i, j);
							lstActor.get(j2).setStartBroad(broad.getStartDrawX(), broad.getStartDrawY());
							lstActor.get(j2).setPosScreen(broad.getWidthBroad() - i);
							getStage().addActor(lstActor.get(j2));
						}
					}
				}
			}
		}
	}
	
	private void drawWall(int x, int y) {
		Image imgWall = new Image(Asset.imgWall);
		imgWall.setPosition((broad.getStartDrawX() + (y*53)), broad.getStartDrawY() + x*53);
		getStage().addActor(imgWall);
	}
	
	private void setUpButtonGame() {
		btnRedo = MenuCreator.createCustomGameButton(Asset.btnRecno, Asset.btnRecno);
		btnRedo.setSize(70, 70);
		btnRedo.setPosition(1030, 200);
		getStage().addActor(btnRedo);
		
		btnUndo = MenuCreator.createCustomGameButton(Asset.btnUndo, Asset.btnUndo);
		btnUndo.setSize(70, 70);
		btnUndo.setPosition(900, 200);
		getStage().addActor(btnUndo);
		
		btnReset = MenuCreator.createCustomGameButton(Asset.btnReset, Asset.btnReset);
		btnReset.setSize(80, 80);
		btnReset.setPosition(1150, 190);
		getStage().addActor(btnReset);
		
//		btnUp = MenuCreator.createCustomGameButton(ASSET.imgBtnUp, ASSET.imgBtnUp);
//		btnUp.setSize(53, 53);
//		btnUp.setPosition(lazy.getLazyPointX()*53, (lazy.getLazyPointY()+1)*53);
//		getStage().addActor(btnUp);
		
		avatar = new Image(Asset.imgDefaultAvantarMan);
		avatar.setSize(90, 90);
		avatar.setPosition(850, 700);
		getStage().addActor(avatar);
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
	}
	
	@Override
	public void keyBackPressed() {
		this.dispose();
		getGame().dispose();
	}
}
