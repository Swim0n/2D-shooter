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


    public Player(){}

    public void setHealth(float health){
        this.health = health;
        if(this.health<0){
            this.health = 0;
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

    public void setStandard(){
        this.radius = 1;
        this.height = 1;
        this.mass = 1;
        this.health = 100;
        this.speed = 40;
        this.damage = 5;
    }







}
