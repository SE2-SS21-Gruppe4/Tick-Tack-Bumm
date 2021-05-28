package se2.ticktackbumm.core.models;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

public class RotatingWheel {
    public static final float N_LAW = 100f;
    private static final float STANDARD_SIZE = 512f;
    private static final short BIT_PEG = 2;
    private static final short BIT_NEEDLE = 6;
    private static final short BIT_BODY1 = 16;
    private static final short BIT_BODY2 = 32;

   // private final World world;
    private final OrthographicCamera camera;
    private final Box2DDebugRenderer renderer;

    // body definieren
    private final BodyDef bodyDef = new BodyDef();
    // fixture definieren
    private final FixtureDef fixtureDef = new FixtureDef();
    // keep needle and wheel in the place
    private final RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
    // join needle with external bodies to constrain and keep it in place.
    private final DistanceJointDef distanceJoinDef = new DistanceJointDef();

    // circle shape which join with all pegs.
    private Body wheelBody;
    // any shape to join with core of wheel.
    private Body wheelBase;

    private Body needle;
    // three bodies to constrain and keep the needle in the place.
    private Body needleBody0;
    private Body needleBody1;
    private Body needleBody2;

    //diameter of wheel
    private final float diameter;
    //position x
    private final float x;
    //position y
    private final float y;
    // number of pegs
    private final int numberOfPegs;
    // distance of needle from the wheel
    private float farNeedle;
    // to ensure a spinning is one time only
    private boolean isSpinned = false;

    public RotatingWheel(float viewportWidth, float viewportHeight, float diameter, float x, float y, int numberOfPegs){
        this.diameter = diameter / N_LAW;
        this.x = x / N_LAW;
        this.y = y / N_LAW;
        this.numberOfPegs = numberOfPegs;

        camera =  new OrthographicCamera();
        camera.setToOrtho(false,viewportWidth/N_LAW,viewportHeight/N_LAW);

        // debugging
        renderer = new Box2DDebugRenderer();
    }


}
