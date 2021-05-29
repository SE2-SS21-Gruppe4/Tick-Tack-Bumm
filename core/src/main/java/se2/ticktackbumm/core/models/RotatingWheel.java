package se2.ticktackbumm.core.models;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Disposable;

public class RotatingWheel implements Disposable {
    public static final float N_LAW = 100f;
    private static final float STANDARD_SIZE = 512f;
    private static final short BIT_PEG = 2;
    private static final short BIT_NEEDLE = 6;
    private static final short BIT_BODY1 = 16;
    private static final short BIT_BODY2 = 32;

    private final World world;
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
    private Body wheelCore;
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

    private PolygonShape polygon;
    private CircleShape circle;
    private Fixture fixture;

    public RotatingWheel(float viewportWidth, float viewportHeight, float diameter, float x, float y, int numberOfPegs){
        this.diameter = diameter / N_LAW;
        this.x = x / N_LAW;
        this.y = y / N_LAW;
        this.numberOfPegs = numberOfPegs;

        camera =  new OrthographicCamera();
        camera.setToOrtho(false,viewportWidth/N_LAW,viewportHeight/N_LAW);

        // world with no gravity
        world = new World(new Vector2(0, 0), true);
       // world.setContactListener(new SpinWheelWorldContact());

        // debugging
        renderer = new Box2DDebugRenderer();
    }

    private void createWheel(){
        wheelBasePart();
        wheelCorePart();
        wheelPegsPart();
        jointPartsOfWheel();
    }
    // creating body/base of wheel as static body with as a square shape.
    private void wheelBasePart(){
        polygon = new PolygonShape();
        // body of wheel
        bodyDef.type = BodyDef.BodyType.StaticBody;
        // body position
        bodyDef.position.set(x,y);

        wheelBase = world.createBody(bodyDef);

        //base shape
        polygon.setAsBox(32 / N_LAW , 32 / N_LAW);
        fixtureDef.shape = polygon;

        //base fixture
        wheelBase.createFixture(fixtureDef);
    }
    // creating core of wheel is a dynamic body with as a circle shape.
    private void wheelCorePart(){
        circle = new CircleShape();
        // body of wheel
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // stop after spinning
        bodyDef.angularDamping = 0.25f;
        bodyDef.position.set(x,y);

        wheelCore = world.createBody(bodyDef);

        // shape of base
        circle.setRadius(diameter / 2);
        fixtureDef.shape = circle;

        // physics propertis of shape
        fixtureDef.density = 0.25f;
        fixtureDef.friction = 0.25f;

        // base fixture
        wheelCore.createFixture(fixtureDef);
    }

    private void wheelPegsPart(){
        if (numberOfPegs == 0){
            return;
        }

        circle = new CircleShape();
        // define pegs of wheel
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x,y);

        // physics properties of shape
        fixtureDef.density = 0.0f;
        fixtureDef.friction = 0.0f;

        // allow collide with needle
        fixtureDef.filter.categoryBits = BIT_PEG;
        fixtureDef.filter.maskBits = BIT_NEEDLE;

        for (int i = 0; i<numberOfPegs; i++){
            double theta = Math.toRadians((360f/numberOfPegs)*i);
            float x = (float) Math.cos(theta);
            float y = (float) Math.sin(theta);

            //set peg position
            circle.setPosition(circle.getPosition().set(x * diameter / 2, y * diameter / 2).scl(0.90f));

            // shape of pegs
            circle.setRadius(6 * (diameter / STANDARD_SIZE) / 2);
            fixtureDef.shape = circle;

            // create base fixture
            fixture = wheelCore.createFixture(fixtureDef);

            // set user data as a number of peg to indecator to lucky win element
            fixture.setUserData(i+1);
        }
    }
    // joint allows the wheel to spin freely about the center.
    private void jointPartsOfWheel(){
        revoluteJointDef.bodyA = wheelBase;
        revoluteJointDef.bodyB = wheelCore;
        revoluteJointDef.collideConnected = false;
        world.createJoint(revoluteJointDef);
    }

    public void render(boolean debug){
        world.step(1/60f,8,2);
        if (debug) {
            renderer.render(world, camera.combined);
        }
    }

    public void spin(float value){
        wheelCore.setAngularVelocity(MathUtils.clamp(value,0,15));
        isSpinned = true;
    }

    public boolean spinningStopped(){
        return !wheelCore.isAwake();
    }





    @Override
    public void dispose() {
        polygon.dispose();
        circle.dispose();
    }
}
