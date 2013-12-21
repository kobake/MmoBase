package com.example.mmobase

import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10
import android.opengl.GLSurfaceView.Renderer
import android.content.Context

class MainRenderer(context: Context) extends Renderer {

  var mWidth: Int = _
  var mHeight: Int = _

  override def onSurfaceCreated(gl: GL10, config: EGLConfig) {
  }

  override def onSurfaceChanged(gl: GL10, width: Int, height: Int) {
    mWidth = width
    mHeight = height
    gl.glViewport(0, 0, width, height)
    loadTextures(gl)
  }

  override def onDrawFrame(gl: GL10) {
    gl.glMatrixMode(GL10.GL_PROJECTION)
    gl.glLoadIdentity()
    gl.glOrthof(0, mWidth, mHeight, 0, 50f, -50f)

    gl.glMatrixMode(GL10.GL_MODELVIEW)
    gl.glLoadIdentity()

    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f)
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT)
    renderMain(gl)
  }

  private def renderMain(gl: GL10) {
    val centerX = 0
    val centerY = 0
    val mActuallyPlayerSize = 100
    GraphicUtil.drawCircle(gl, mWidth, mHeight, 100, mActuallyPlayerSize, 1.0f, 1.0f, 1.0f, 1.0f)
    val extractedLocalValue = 1.0f
    //    GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 512, 512, mMapTexture, 1.0f, 1.0f, 1.0f, 1.0f)

    //    GL10 gl, float x, float y, float w, float h, // 描画先
    //		int texture, float u, float v, float tex_w, float tex_h, // 描画元
    //		float r, float g, float b, float a
    //    GraphicUtil.drawTexture(gl = gl, x = 0.0f, y = 0.0f, w = 100.f, h = 100.f,
    //        texture = mMapTexture, u = 0.0f, v = 0.0f, tex_w = 32.0f, tex_h = 32.0f, 
    //        r = 1.0f, g = 1.0f, b = 1.0f, a = 1.0f)
    GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 100.f, 100.f, mMapTexture, 0.0f, 0.0f, 32.0F / 1024.0f, 32.0f / 1024.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    //    GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 100.f, 100.f, mMapTexture, 0.0f, 0.0f, 32.0f / 1024.0f, 32.0f / 1024.0f, 1.0f, 1.0f, 1.0f, 1.0f)
    //        GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 100.f, 100.f, mMapTexture, 0.0f, 0.0f, 0.5f, 0.5f, 1.0f, 1.0f, 1.0f, 1.0f)
    //        GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 100.f, 100.f, mMapTexture, 0.0f, 0.0f, 1f, 1f, 1.0f, 1.0f, 1.0f, 1.0f)
    //    GraphicUtil.drawTexture(gl, 0.0f, 0.0f, 10.f, 10.f, mMapTexture, 0.0f, 0.0f, 32.0f / 1024.0f, 32.0f / 1024.0f, 1.0f, 1.0f, 1.0f, 1.0f)
  }

  private var mMapTexture: Int = _
  private def loadTextures(gl: GL10) {
    val res = context.getResources()
    this.mMapTexture = GraphicUtil.loadTexture(gl, res, R.drawable.maptip)
  }
}