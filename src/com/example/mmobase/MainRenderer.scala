package com.example.mmobase;

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
    gl.glViewport(0, 0, width, height);
    loadTextures(gl)
  }

  override def onDrawFrame(gl: GL10) {
    gl.glMatrixMode(GL10.GL_PROJECTION);
    gl.glLoadIdentity();
    gl.glOrthof(0, mWidth, mHeight, 0, 50f, -50f);

    gl.glMatrixMode(GL10.GL_MODELVIEW);
    gl.glLoadIdentity();

    gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
    gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    renderMain(gl)
  }

  private def renderMain(gl: GL10) {
    val centerX = 0
    val centerY = 0
    val mActuallyPlayerSize = 100
    GraphicUtil.drawCircle(gl, mWidth, mHeight, 100, mActuallyPlayerSize, 1.0f, 1.0f, 1.0f, 1.0f)
    GraphicUtil.drawTexture(gl, 0.0f, 0.0f, mWidth, mHeight, mBgTexture, 1.0f, 1.0f, 1.0f, 1.0f)
  }

  private var mBgTexture: Int = _; // 背景用テクスチャ
  private var mTargetTexture: Int = _; // 標的用テクスチャ
  private var mNumberTexture: Int = _; // 数字用テクスチャ
  private def loadTextures(gl: GL10) {
    val res = context.getResources();
    this.mBgTexture = GraphicUtil.loadTexture(gl, res, R.drawable.ic_launcher);
    this.mTargetTexture = GraphicUtil.loadTexture(gl, res, R.drawable.ic_launcher);
    this.mNumberTexture = GraphicUtil.loadTexture(gl, res, R.drawable.ic_launcher);
  }
}