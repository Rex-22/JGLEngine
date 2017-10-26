package core.graphics.shader;

import core.util.FileUtils;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private int shaderID;
    private boolean enabled;
    private Map<String, Integer> locationCache;

    public Shader(String shaderName) {
        shaderID = load(shaderName);
        locationCache = new HashMap<>();
    }

    private int load(String shaderName){
        int programID = glCreateProgram();

        String vertexSource = FileUtils.loadAsString("shader/"+shaderName+".vert");
        String fragmentSource = FileUtils.loadAsString("shader/"+shaderName+".frag");

        int vertexID = addShader(GL_VERTEX_SHADER, vertexSource);
        int fragmentID = addShader(GL_FRAGMENT_SHADER, fragmentSource);

        glAttachShader(programID, vertexID);
        glAttachShader(programID, fragmentID);

        glLinkProgram(programID);
        glValidateProgram(programID);

        glDeleteShader(vertexID);
        glDeleteShader(fragmentID);

        return programID;
    }

    private int addShader(int type, String source){
        int id = glCreateShader(type);
        glShaderSource(id, source);
        glCompileShader(id);

        glCompileShader(id);
        if (glGetShaderi(id, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile " + type + " shader!");
            System.err.println(glGetShaderInfoLog(id));
            return -1;
        }

        return id;
    }

    public void enable(){
        enabled = true;
        glUseProgram(shaderID);
    }

    public void disable(){
        enabled = false;
        glUseProgram(0);
    }

    public int getUniform(String name) {
        if (locationCache.containsKey(name))
            return locationCache.get(name);

        int result = glGetUniformLocation(shaderID, name);
        if (result == -1)
            System.err.println("Could not find uniform variable '" + name + "'!");
        else
            locationCache.put(name, result);
        return result;
    }

    public void setUniform1i(String name, int value) {
        if (!enabled) enable();
        glUniform1i(getUniform(name), value);
    }

    public void setUniform1f(String name, float value) {
        if (!enabled) enable();
        glUniform1f(getUniform(name), value);
    }

    public void setUniform2f(String name, Vector2f vector) {
        if (!enabled) enable();
        glUniform2f(getUniform(name), vector.x, vector.y);
    }

    public void setUniform3f(String name, Vector3f vector) {
        if (!enabled) enable();
        glUniform3f(getUniform(name), vector.x, vector.y, vector.z);
    }

    public void setUniform4f(String name, Vector4f vector) {
        if (!enabled) enable();
        glUniform4f(getUniform(name), vector.x, vector.y, vector.z, vector.w);
    }

    public void setUniformMat4f(String name, Matrix4f matrix) {
        if (!enabled) enable();
        FloatBuffer fb = BufferUtils.createFloatBuffer(16);
        matrix.get(fb);
        glUniformMatrix4fv(getUniform(name), false, fb);
    }
}
