package com.builtbroken.jlib.model;

import com.builtbroken.mc.lib.transform.vector.Point;
import com.builtbroken.mc.lib.transform.vector.Pos;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dark on 6/10/2015.
 * https://github.com/OskarVeerhoek/YouTube-tutorials/blob/master/src/episode_24/ModelDemo.java
 */
public class Model
{
    public final List<Mesh> meshes = new ArrayList();
    public boolean render_wireframe = false;
    public boolean render_normals = false;

    public void render()
    {
        if (render_wireframe)
            GL11.glBegin(GL11.GL_LINE_LOOP);
        else
            GL11.glBegin(GL11.GL_TRIANGLES);


        for (Mesh m : meshes)
        {
            for (Face face : m.getFaces())
            {
                try
                {

                    //One
                    renderVert(m, face, 0);

                    //Two
                    renderVert(m, face, 1);

                    //Three
                    renderVert(m, face, 2);
                }
                catch (ArrayIndexOutOfBoundsException e)
                {
                    e.printStackTrace();
                }
            }
        }
        GL11.glEnd();

        if(render_normals)
        {
            GL11.glColor3f(Color.BLUE.getRed(), Color.BLUE.getGreen(), Color.BLUE.getBlue());
            for (Mesh m : meshes)
            {
                for (Face face : m.getFaces())
                {
                    Pos center = center2(m.vertices.get(face.vertexIndices[0]), m.vertices.get(face.vertexIndices[1]), m.vertices.get(face.vertexIndices[2]));
                    Pos out = center.add(m.normals.get(face.normalIndices[0]));

                    GL11.glBegin(GL11.GL_LINE_LOOP);
                    GL11.glVertex3f(center.xf(), center.yf(), center.zf());
                    GL11.glVertex3f(out.xf(), out.yf(), out.zf());
                    GL11.glEnd();
                }
            }
        }
    }

    protected void renderVert(Mesh m, Face face, int i)
    {
        Pos n1 = m.normals.get(face.normalIndices[i]);
        GL11.glNormal3f(n1.xf(), n1.yf(), n1.zf());

        Pos v1 = m.getVertices().get(face.vertexIndices[i]);
        GL11.glVertex3f(v1.xf(), v1.yf(), v1.zf());

        if(m.textureCoordinates.size() >= face.textureCoordinateIndices[i])
        {
            Point t1 = m.textureCoordinates.get(face.textureCoordinateIndices[i]);
            GL11.glTexCoord2f(t1.xf(), t1.yf());
        }
    }

    static Pos center2(Pos v1, Pos v2, Pos v3)
    {
        return v1.add(v2).add(v3).divide(3);
    }
}
