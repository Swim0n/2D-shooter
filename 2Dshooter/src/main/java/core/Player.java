package core;

import javax.vecmath.Vector3f;

/**
 * Created by David on 2016-04-15.
 */
public class Player {

    private float health;
    private float speed;
    private float damage;
    private float dashSpeed;
    private int wins;
    private int dashMillis;
    private float gunRotationSpeed;
    private Vector3f position;
    private Vector3f direction = new Vector3f(0,0,0);

    public boolean left,right,up,down,gunLeft,gunRight,dashing;
    private float radius;
    private float height;
    private float mass;
    private boolean needsReset;

    public Player(){
        setStandard();
    }

    public void setStandard(){
        this.radius = 1.2f;
        this.height = 1;
        this.mass = 1;
        this.health = 100;
        this.speed = 23;
        this.damage = 10;
        this.dashMillis = 300;
        this.dashSpeed = 50f;
        this.gunRotationSpeed = 140f;
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

    public Vector3f getWalkingDirection(){
        if(!left && !right && !up && !down && !dashing){
            direction.set(0,0,0);
        }
        if(left){
            direction.set(-speed,0,0);
        }
        if(right){
            direction.set(speed,0,0);
        }
        if(up){
            direction.set(0,0,speed);
        }
        if(down){
            direction.set(0,0,-speed);
        }
        if (left && up){
            direction.set(-getDiagonalSpeed(),0,getDiagonalSpeed());
        }
        if (left && down){
            direction.set(-getDiagonalSpeed(),0,-getDiagonalSpeed());
        }
        if (right && up){
            direction.set(getDiagonalSpeed(),0,getDiagonalSpeed());
        }
        if (right && down){
            direction.set(getDiagonalSpeed(),0,-getDiagonalSpeed());
        }
        if(dashing && (up || down  || right || left)){
            direction.normalize();
            direction.scale(dashSpeed);
        }
        return direction;
    }

    public float getGunRotation(){
        if(gunLeft){
            return -gunRotationSpeed;
        } else if(gunRight){
            return gunRotationSpeed;
        } else {
            return 0;
        }
    }

    public void reset(){
        if(health!=0){
            incWins();
        }
        needsReset = true;
        setStandard();
    }

    public void setNeedsResetFalse(){
        needsReset = false;
    }
    public void incWins(){this.wins += 1;}
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
    public float getGunRotationSpeed() {return gunRotationSpeed;}
    public Vector3f getPosition() {return position;}
    public void setPosition(Vector3f position) {this.position = position;}

    public boolean getNeedsReset() {
        return needsReset;
    }
}