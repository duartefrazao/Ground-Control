package com.groundcontrol.game.view.UiFactory;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;

public class ButtonFactory {


    public ButtonFactory(){
    }

    public Button makeButton(Texture textureUp,Texture textureDown,float x,float y, int width,int height){
       Button button = new Button(
           new TextureRegionDrawable(new TextureRegion(textureUp)),
           new TextureRegionDrawable(new TextureRegion(textureDown)));

       button.setBounds(x,y,width,height);
       button.setPosition(x,y, Align.center);

       return button;
    }
}
