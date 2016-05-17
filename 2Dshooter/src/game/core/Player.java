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
    private float dashSpeed;
    private int wins;
    private int dashMillis;
    private float gunRotationSpeed;

    public Player(){
        setStandard();
    }

    public void setHealth(float health){
        if(health < 0){
            this.health = 0;
        }if(health > 100){
            this.health = 100;
        } else {
            this.health = health;
        }

    }

    public void setSpeed(float speed){this.speed = speed;}
    public void setDamage(float damage){this.damage = damage;}
    public float getHealth(){return this.health;}
    public float getSpeed(){return this.speed;}
    public float getDiagonalSpeed(){return speed*0.707f;}
    public float getDamage(){return this.damage;}
    public float getRadius(){ return this.radius; }
    public float getHeight(){return this.height; }
    public float getMass(){return this.mass; }
    public int getDashMillis(){return dashMillis;}
    public float getDashSpeed(){return dashSpeed;}
    public int getWins(){return wins;}

    public void setStandard(){
        this.radius = 1;
        this.height = 1;
        this.mass = 1;
        this.health = 100;
        this.speed = 23;
        this.damage = 10;
        this.dashMillis = 300;
        this.dashSpeed = 50f;
        this.gunRotationSpeed = 140f;
    }


    public void incWins(){this.wins += 1;}


    public float getGunRotationSpeed() {
        return gunRotationSpeed;
    }
}