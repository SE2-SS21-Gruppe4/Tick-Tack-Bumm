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
    private static final short BIT_PEG = 4;
    private static final short BIT_NEEDLE = 8;
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
    // main body
    private Body needleMainBody;
    // left body of needle
    private Body needleLeftBody;
    // right body of needle
    private Body needleRightBody;

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

    private void createNeedle(){

        needleCore(30F * (diameter / STANDARD_SIZE), 70F * (diameter / STANDARD_SIZE));
        needleMainBody();
        needleJointMainBodyWithCN();
        needleLeftBody();
        needleRightBody();
        needleJointAllPartsOfNeedle();
    }

    private void needleCore(float needleWidth, float needleHeight){
        polygon = new PolygonShape();
        // shape of needle
        float[] vertices = { - needleWidth / 2, 0f,0f, needleHeight /4 , needleWidth / 2, 0f,0f, -3 * needleHeight / 4};
        polygon.set(vertices);
        fixtureDef.shape = polygon;

        //needle define
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        farNeedle = diameter / 1.95f;
        bodyDef.position.set(x,y + farNeedle);


        bodyDef.bullet = true;
        bodyDef.angularDamping = 0.25f;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.0f;

        //create needle body
        needle = world.createBody(bodyDef);

        //allow needle to collide with peg, left body and right body
        fixtureDef.filter.categoryBits = BIT_NEEDLE;
        fixtureDef.filter.maskBits = BIT_PEG | BIT_BODY1 | BIT_BODY2;


    }
    // center static body to join needle
    private void needleMainBody(){
        circle = new CircleShape();

        //shape of base
        circle.setRadius(4 * (diameter / STANDARD_SIZE));
        fixtureDef.shape = circle;

        //base of wheel
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set(x,y + farNeedle + 5F * (diameter / STANDARD_SIZE));

        // create main body
        needleMainBody = world.createBody(bodyDef);
        // physics propertis of shape
        fixtureDef.density = 0.0f;

        needleMainBody.createFixture(fixtureDef);
    }

    private void needleLeftBody(){
        circle = new CircleShape();
        // set shape of left body
        circle.setRadius(4*(diameter/STANDARD_SIZE));
        fixtureDef.shape = circle;

        // define
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.bullet = true;

        bodyDef.position.set(x - 40f * (diameter/STANDARD_SIZE), y + farNeedle);

        //create left body
        needleLeftBody = world.createBody(bodyDef);

        //physics propertis of shape
        fixtureDef.density = 1f;
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 1f;

        // allow collision with needle
        fixtureDef.filter.categoryBits = BIT_BODY1;
        fixtureDef.filter.maskBits = BIT_NEEDLE;

        needleLeftBody.createFixture(fixtureDef);
    }

    private void needleRightBody(){
        circle = new CircleShape();
        // set The Shape of B2
        circle.setRadius(4 * (diameter / STANDARD_SIZE));
        fixtureDef.shape = circle;

        // Define The B2 Of Wheel
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.bullet = true;

        bodyDef.position.set(x + 40F * (diameter / STANDARD_SIZE), y + farNeedle);

        // Create The B2 Body
        needleRightBody = world.createBody(bodyDef);

        // set The physics properties of The Shape
        fixtureDef.density = 1f;
        fixtureDef.restitution = 1f;
        fixtureDef.friction = 1f;

        // set categoryBits To allow collide with (needle)
        fixtureDef.filter.categoryBits = BIT_BODY2;
        fixtureDef.filter.maskBits = BIT_NEEDLE;

        // Create The B2 Fixture
        needleRightBody.createFixture(fixtureDef);
    }

    private void needleJointAllPartsOfNeedle(){
        distanceJoinDef.bodyA = needleLeftBody;
        distanceJoinDef.bodyB = needle;
        distanceJoinDef.localAnchorB.set(0, 15 / N_LAW);
        distanceJoinDef.length = (float) Math.sqrt(Math.pow(40F * (diameter / STANDARD_SIZE), 2) + Math.pow(15 / N_LAW + 5F * (diameter / STANDARD_SIZE), 2));
        distanceJoinDef.collideConnected = true;
        world.createJoint(distanceJoinDef);

        distanceJoinDef.bodyA = needleRightBody;
        distanceJoinDef.bodyB = needle;
        distanceJoinDef.collideConnected = true;
        world.createJoint(distanceJoinDef);
    }

    private void needleJointMainBodyWithCN(){
        revoluteJointDef.bodyA = needleMainBody;
        revoluteJointDef.bodyB = needle;
        world.createJoint(distanceJoinDef);
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
