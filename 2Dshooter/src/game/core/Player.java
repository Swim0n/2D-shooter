package game.core;


/**
 * Created by David on 2016-04-15.
 */
public class Player {

    private float radius;
    private float height;
    private float mass;
    private float health;
    private float speed;
    private float damage;


    public Player(){
        setStandard();
    }

    public void setHealth(float health){
        if(health < 0){
            this.health = 0;
        }
        else {
            this.health = health;
        }

    }

    public void setSpeed(float speed){
        this.speed = speed;
    }

    public void setDamage(float damage){
        this.damage = damage;
    }

    public float getHealth(){
        return this.health;
    }

    public float getSpeed(){
        return this.speed;
    }

    public float getDamage(){
        return this.damage;
    }

    public float getRadius(){ return this.radius; }
    public float getHeight() { return this.height; }
    public float getMass() { return  this.mass; }

    public void setStandard(){
        this.radius = 1;
        this.height = 1;
        this.mass = 1;
        this.health = 100;
        this.speed = 35;
        this.damage = 5;
    }







}
