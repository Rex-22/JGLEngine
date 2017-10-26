package core.graphics;

import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class Transform {

    private Vector3f position;
    private Quaternionf rotation;
    private Vector3f size;

    private Matrix4f MVP;

    public Transform(Vector3f position, Quaternionf rotation, Vector3f size) {
        this.position = position;
        this.rotation = rotation;
        this.size = size;

        this.MVP = new Matrix4f();
        MVP.translate(position);
        MVP.rotation(rotation);
        MVP.scale(size);
    }

    public void mul(Transform transform) {
        this.MVP.mul(transform.getMVP());
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setRotation(Quaternionf rotation) {
        this.rotation = rotation;
    }

    public void setSize(Vector3f size) {
        this.size = size;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Quaternionf getRotation() {
        return rotation;
    }

    public Vector3f getSize() {
        return size;
    }

    public Matrix4f getMVP() {
        return MVP;
    }
}
