package com.gdxuser.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class GuOrthoCam extends OrthographicCamera {

	private static final float DEG = (float) (Math.PI / 180f);
	private static final int CAMSPEED = 40;
	private static Vector3 campos;
	private static float aspect;
	private Matrix4 comb;
	private Vector3 dir;
	private Vector2 fieldSize;
	private Vector3 targetVec = new Vector3(0, 0, 0); // need a default value
	private Mesh targetObj;

	private static float vpWidth;
	private static float vpHeight;
	private static float vpAspectRatio;
	private static final float CAM_NEAR_INITIAL = 0.1f;
	private static final float CAM_FAR_INITIAL = 200f;

	// viewSize should be related to the size of your scene
	// scale compared to grid_size - bigger means more area around the playing
	// field
	private static final float VIEW_ZOOM = 1.2f;
	private static final float CAM_HEIGHT = 10; // how high up the cam is

	private static Vector3 camPos;

	private static Vector3 CAM_DIR_INITIAL;
	private static Vector3 CAM_LOOKAT_INITIAL;
	private static Vector3 CAM_UP_INITIAL = new Vector3(0, 1, 0);
	private static float relative_rotation_angle = 0;

	public GuOrthoCam(float vpw, float vph, Vector2 field) {
		// super method calls an update() so all better be ready in our update
		super(field.x * VIEW_ZOOM, field.x * VIEW_ZOOM * (vph / vpw));
		vpAspectRatio = (float) vpWidth / (float) vpHeight;
		vpHeight = vph;
		vpWidth = vpw;
		fieldSize = field;
		init();
	}

	// TODO - calc center of passed in mesh as targetVec
	public void setTargetObj(Mesh obj) {
		targetObj = obj;
	}

	public void setTargetVec(float x, float y, float z) {
		Log.out("set lookAt");
		targetVec = new Vector3(x, y, z);
	}

	public void init() {
		relative_rotation_angle = 0;
		up.set(CAM_UP_INITIAL);
		camPos = new Vector3(-fieldSize.x, CAM_HEIGHT, 2 * fieldSize.y);
		position.set(camPos);
		setTargetVec(fieldSize.x / 2, 0, fieldSize.y / 2); // set default pos
		updateLookAt();
		update();
		logInfo();
	}

	private void updateLookAt() {
		if (targetVec == null) {
			Log.out("caught updateLookAt before init()");
			return;
		}
		lookAt(targetVec.x, targetVec.y, targetVec.z);
	}

	private void logInfo() {
		Log.out("Reset Camera:");
		Log.out("Cam-Up:        " + up);
		Log.out("Cam-Position:  " + position);
		Log.out("Cam-Direction: " + direction);
		Log.out("Cam-LookAt: " + CAM_LOOKAT_INITIAL);
		Log.out("VP_HEIGHT: " + vpHeight);
	}

	public void push() {
		campos = position;
		dir = this.direction;
	}

	public void pop() {
		position.set(campos);
		this.direction.set(dir);
	}

	@Override
	public void update() {
		updateLookAt();
		super.update();
	}

	public void handleKeys() {
		float amt = CAMSPEED * Gdx.graphics.getDeltaTime();

		if (Gdx.input.isKeyPressed(Keys.W)) {
			translate(direction.x, 0, direction.z);
		}
		if (Gdx.input.isKeyPressed(Keys.S)) {
			translate(-direction.x, 0, -direction.z);
		}

		if (Gdx.input.isKeyPressed(Keys.A)) {
			Vector3 right = new Vector3(direction);
			right.crs(up);
			translate(-right.x, 0, -right.z);
		}
		if (Gdx.input.isKeyPressed(Keys.D)) {
			Vector3 right = new Vector3(direction);
			right.crs(up);
			translate(right.x, 0, right.z);
		}
		if (Gdx.input.isKeyPressed(Keys.E)) {
			rotate(-amt, 0, 1, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.Q)) {
			rotate(amt, 0, 1, 0);
		}

		if (Gdx.input.isKeyPressed(Keys.U)) {
			rotate(-amt, 1, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.J)) {
			rotate(amt, 1, 0, 0);
		}
		if (Gdx.input.isKeyPressed(Keys.H)) {
			rotate(amt, 0, 0, 1);
		}
		if (Gdx.input.isKeyPressed(Keys.K)) {
			rotate(-amt, 0, 0, 1);
		}

		// moves NEAR away from cam
		if (Gdx.input.isKeyPressed(Keys.PLUS)) {
			near += amt;
			if (near > CAM_FAR_INITIAL) {
				near = CAM_FAR_INITIAL;
			}
			Log.out("NEAR:" + near);
		}
		// moves NEAR closer to cam
		if (Gdx.input.isKeyPressed(Keys.MINUS)) {
			near -= amt;
			if (near < CAM_NEAR_INITIAL) {
				near = CAM_NEAR_INITIAL;
			}
			Log.out("NEAR:" + near);
		}

		// Zoom in
		if (Gdx.input.isKeyPressed(Keys.T)) {
			zoom(amt);
		}
		// Zoom out
		if (Gdx.input.isKeyPressed(Keys.G)) {
			zoom(-amt);
		}

		// Orbit around target left
		if (Gdx.input.isKeyPressed(Keys.N)) {
			orbit(relative_rotation_angle += amt / 100, targetVec);
		}
		// Orbit around target right
		if (Gdx.input.isKeyPressed(Keys.M)) {
			orbit(relative_rotation_angle -= amt / 100, targetVec);
		}

		// Reset scene / reload starting values
		if (Gdx.input.isKeyPressed(Keys.R)) {
			init();
		}

		if (Gdx.input.isKeyPressed(Keys.I)) {
			printInfo();
		}

		update();
	}

	private void printInfo() {
		Log.out("Key Info (I): \n ESC = quit demo");
		Log.out("A, D = move cam left / right");
		Log.out("W, S = move cam forward / backward");
		Log.out("Q, E = yaw (turn) cam left / right");
		Log.out("U, J = pitch cam up / down");
		Log.out("H, K = roll cam counter-clockwise / clockwise");
		Log.out("N, M = orbit cam around origin (TODO: player) counter-clockwise / clockwise");
		Log.out("C, SPACE = print cam / player position");
		Log.hr();
		Log.out("cam_pos:  " + position);
		Log.out("cam_up:   " + up);
		Log.out("cam_dir:  " + direction);
		Log.out("targetVec:  " + targetVec);
		Log.hr();
	}

	public void orbit(float amt_rotate, Vector3 target) {
		float altitude = 10f;
		float radius = 10f;

		// TODO WIP
		// TODO: target set to origin - can't get player pos yet
		/**
		 * Rotate camera around a target, cam looks always at target
		 */
		float x = (float) Math.sin((position.x + amt_rotate * 360) * DEG)
				* radius;
		float z = (float) Math.cos((position.z + amt_rotate * 360) * DEG)
				* radius;
		Log.out("amt value: " + amt_rotate);
		Log.out("orbit values: " + x + ", " + z);
		Log.out("position: " + position.x + ", " + position.y + ", "
				+ position.y);
		position.set(x, altitude, z);
		lookAt(target.x, target.y, target.z);
		update();
	}

	public void zoom(float amt_zoom) {
		// TODO WIP
		update();
	}

}