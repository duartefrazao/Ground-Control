package com.groundcontrol.game.controller.elements;

public class FixtureInfo {

   public int width;
    public int height;
    public float density;
    public float friction;
    public float restitution;
    public short category;
    public short mask;
    public float[] vertexes;

   public FixtureInfo(float[] vertexes, int width, int height){
       this.vertexes = vertexes;
       this.width = width;
       this.height = height;
   }

   public void physicsComponents(float density, float friction, float restitution){
       this.density = density;
       this.friction = friction;
       this.restitution = restitution;
   }

   public void collisionComponents(short category, short mask){
       this.category = category;
       this.mask = mask;
   }
}
