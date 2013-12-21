//package com.example.mmobase
//
//import java.nio.ByteBuffer
//import java.nio.ByteOrder
//import java.nio.FloatBuffer
//import java.util.Hashtable
//import javax.microedition.khronos.opengles.GL10
//import android.content.res.Resources
//import android.graphics.Bitmap
//import android.graphics.Bitmap.Config
//import android.graphics.BitmapFactory
//import android.opengl.GLUtils;
//import scala.collection.mutable.ArrayBuffer
//
//object GraphicUtil {
//
//  // 配列オブジェクトを保持する
//  private val verticesPool = new Hashtable[Integer, ArrayBuffer[Float]]();
//  private val colorsPool = new Hashtable[Integer, ArrayBuffer[Float]]();
//  private val coordsPool = new Hashtable[Integer, ArrayBuffer[Float]]();
//
//  def getVertices(n: Int): ArrayBuffer[Float] = {
//    if (verticesPool.containsKey(n)) {
//      return verticesPool.get(n);
//    }
//    val vertices = new ArrayBuffer[Float](n)
//    verticesPool.put(n, vertices);
//    return vertices;
//  }
//
//  def getColors(n: Int): ArrayBuffer[Float] = {
//    if (colorsPool.containsKey(n)) {
//      return colorsPool.get(n);
//    }
//    val colors = new ArrayBuffer[Float](n)
//    colorsPool.put(n, colors);
//    return colors;
//  }
//
//  def getCoords(n: Int): ArrayBuffer[Float] = {
//    if (coordsPool.containsKey(n)) {
//      return coordsPool.get(n);
//    }
//    val coords = new ArrayBuffer[Float](n)
//    coordsPool.put(n, coords);
//    return coords;
//  }
//
//  // バッファオブジェクトを保持する
//  private val squareVerticesPool = new Hashtable[Integer, FloatBuffer]();
//  private val squareColorsPool = new Hashtable[Integer, FloatBuffer]();
//  private val texCoordsPool = new Hashtable[Integer, FloatBuffer]();
//
//  final def makeVerticesBuffer(arr: ArrayBuffer[Float]): FloatBuffer = {
//    var fb: FloatBuffer = null;
//    if (squareVerticesPool.containsKey(arr.length)) {
//      fb = squareVerticesPool.get(arr.length);
//      fb.clear();
//      fb.put(arr.toArray);
//      fb.position(0);
//      return fb;
//    }
//    fb = makeFloatBuffer(arr);
//    squareVerticesPool.put(arr.length, fb);
//    return fb;
//  }
//
//  final def makeColorsBuffer(arr: ArrayBuffer[Float]): FloatBuffer = {
//    var fb: FloatBuffer = null;
//    if (squareColorsPool.containsKey(arr.length)) {
//      fb = squareColorsPool.get(arr.length);
//      fb.clear();
//      fb.put(arr.toArray);
//      fb.position(0);
//      return fb;
//    }
//    fb = makeFloatBuffer(arr);
//    squareColorsPool.put(arr.length, fb);
//    return fb;
//  }
//
//  final def makeTexCoordsBuffer(arr: ArrayBuffer[Float]): FloatBuffer = {
//    var fb: FloatBuffer = null;
//    if (texCoordsPool.containsKey(arr.length)) {
//      fb = texCoordsPool.get(arr.length);
//      fb.clear();
//      fb.put(arr.toArray);
//      fb.position(0);
//      return fb;
//    }
//    fb = makeFloatBuffer(arr);
//    texCoordsPool.put(arr.length, fb);
//    return fb;
//  }
//  //
//  //	public static final void drawNumbers(GL10 gl, float x, float y, float w,
//  //			float h, int texture, int number, int figures, float r, float g,
//  //			float b, float a) {
//  //		float totalWidth = w * (float) figures;// n文字分の横幅
//  //		float rightX = x + (totalWidth * 0.5f);// 右端のx座標
//  //		float fig1X = rightX - w * 0.5f;// 一番右の桁の中心のx座標
//  //		for (int i = 0; i < figures; i++) {
//  //			float figNX = fig1X - (float) i * w;// n桁目の中心のx座標
//  //			int numberToDraw = number / (int) Math.pow(10.0, (double) i) % 10;
//  //			drawNumber(gl, figNX, y, w, h, texture, numberToDraw, 1.0f, 1.0f,
//  //					1.0f, 1.0f);
//  //		}
//  //	}
//  //
//  //	public static final void drawNumber(GL10 gl, float x, float y, float w,
//  //			float h, int texture, int number, float r, float g, float b, float a) {
//  //		float u = (float) (number % 4) * 0.25f;
//  //		float v = (float) (number / 4) * 0.25f;
//  //		drawTexture(gl, x, y, w, h, texture, u, v, 0.25f, 0.25f, r, g, b, a);
//  //	}
//
//  final def drawTexture(gl: GL10, x: Float, y: Float, w: Float,
//    h: Float, texture: Int, u: Float, v: Float, tex_w: Float, tex_h: Float,
//    r: Float, g: Float, b: Float, a: Float) {
//    val vertices = getVertices(8);
//    vertices(0) = -0.5f * w + x;
//    vertices(1) = -0.5f * h + y;
//    vertices(2) = 0.5f * w + x;
//    vertices(3) = -0.5f * h + y;
//    vertices(4) = -0.5f * w + x;
//    vertices(5) = 0.5f * h + y;
//    vertices(6) = 0.5f * w + x;
//    vertices(7) = 0.5f * h + y;
//
//    val colors = getColors(16);
//    (0 to 3).foreach { i =>
//      colors(i * 4 + 0) = r;
//      colors(i * 4 + 1) = g;
//      colors(i * 4 + 2) = b;
//      colors(i * 4 + 3) = a;
//    }
//
//    val coords = getCoords(8);
//    coords(0) = u;
//    coords(1) = v + tex_h;
//    coords(2) = u + tex_w;
//    coords(3) = v + tex_h;
//    coords(4) = u;
//    coords(5) = v;
//    coords(6) = u + tex_w;
//    coords(7) = v;
//
//    val squareVertices = makeVerticesBuffer(vertices)
//    val squareColors = makeColorsBuffer(colors)
//    val texCoords = makeTexCoordsBuffer(coords)
//
//    gl.glEnable(GL10.GL_TEXTURE_2D);
//    gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
//    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, squareVertices);
//    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//    gl.glColorPointer(4, GL10.GL_FLOAT, 0, squareColors);
//    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//    gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoords);
//    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//
//    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
//
//    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//    gl.glDisable(GL10.GL_TEXTURE_2D);
//  }
//
//  final def drawTexture(gl: GL10, x: Float, y: Float, w: Float, h: Float, texture: Int, r: Float, g: Float, b: Float, a: Float) {
//    drawTexture(gl, x, y, w, h, texture, 0.0f, 0.0f, 1.0f, 1.0f, r, g, b, a);
//  }
//
//      	final def loadTexture(gl:GL10 , resources:Resources , res:Int) :Int={
//      		val textures = new Array[Int](1);
//      
//      		// Bitmapの作成
//      		val bmp = BitmapFactory.decodeResource(resources, res, options);
//      		if (bmp == null) {
//      			return 0;
//      		}
//      
//      		// OpenGL用のテクスチャを生成します
//      		gl.glGenTextures(1, textures, 0);
//      		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures(0));
//      		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
//      		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
//      				GL10.GL_LINEAR);
//      		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
//      				GL10.GL_LINEAR);
//      		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);
//      
//      		// OpenGLへの転送が完了したので、VMメモリ上に作成したBitmapを破棄する
//      		bmp.recycle();
//      
//      		// TextureManagerに登録する
//      		TextureManager.addTexture(res, textures[0]);
//      
//      		return textures[0];
//      	}
//
//  private final val options = new BitmapFactory.Options();
//  options.inScaled = false; // リソースの自動リサイズをしない
//  options.inPreferredConfig = Config.ARGB_8888; // 32bit画像として読み込む
//
//  //  final def drawCircle(gl: GL10, center: Vector2D, divides: Int,
//  //    radius: Float, r: Float, g: Float, b: Float, a: Float) {
//  //    drawCircle(gl, center.mX, center.mY, divides, radius, r, g, b, a);
//  //  }
//
//  final def drawCircle(gl: GL10, x: Float, y: Float, divides: Int, radius: Float, r: Float, g: Float, b: Float, a: Float) {
//    val vertices = getVertices(divides * 3 * 2);
//
//    var vertexId = 0; // 頂点配列の要素の番号を記憶しておくための変数
//    var i = 0
//    while (i < divides) {
//      // i番目の頂点の角度(ラジアン)を計算します
//      val theta1 = 2.0f / divides.asInstanceOf[Float] * i.asInstanceOf[Float] * Math.PI.asInstanceOf[Float];
//
//      // (i + 1)番目の頂点の角度(ラジアン)を計算します
//      val theta2 = 2.0f / divides.asInstanceOf[Float] * (i + 1).asInstanceOf[Float] * Math.PI.asInstanceOf[Float];
//
//      // i番目の三角形の0番目の頂点情報をセットします
//      vertices(vertexId) = x;
//      vertexId += 1
//      vertices(vertexId) = y;
//      vertexId += 1
//      // i番目の三角形の1番目の頂点の情報をセットします (円周上のi番目の頂点)
//      vertices(vertexId) = (Math.cos(theta1.asInstanceOf[Double]) * radius + x).asInstanceOf[Float]; // x座標
//      vertexId += 1
//      vertices(vertexId) = (Math.sin(theta1.asInstanceOf[Double]) * radius + y).asInstanceOf[Float]; // y座標
//      vertexId += 1
//
//      // i番目の三角形の2番目の頂点の情報をセットします (円周上のi+1番目の頂点)
//      vertices(vertexId) = (Math.cos(theta2.asInstanceOf[Double]) * radius + x).asInstanceOf[Float]; // x座標
//      vertexId += 1
//      vertices(vertexId) = (Math.sin(theta2.asInstanceOf[Double]) * radius + y).asInstanceOf[Float]; // y座標
//      vertexId += 1
//      i += 1
//    }
//    val squareVertices = makeVerticesBuffer(vertices);
//
//    // ポリゴンの色を指定します
//    val colors = getColors(divides * 3 * 4);
//    (0 to 3).foreach { i =>
//      colors(i * 4 + 0) = r;
//      colors(i * 4 + 1) = g;
//      colors(i * 4 + 2) = b;
//      colors(i * 4 + 3) = a;
//    }
//    val c = makeColorsBuffer(colors);
//    gl.glColorPointer(4, GL10.GL_FLOAT, 0, c);
//    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//
//    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, squareVertices);
//    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//
//    gl.glDrawArrays(GL10.GL_TRIANGLES, 0, divides * 3);
//  }
//
//  final def drawRectangle(gl: GL10, x: Float, y: Float, w: Float, h: Float, r: Float, g: Float, b: Float, a: Float) {
//    val vertices = getVertices(8);
//    vertices(0) = -0.5f * w + x;
//    vertices(1) = -0.5f * h + y;
//    vertices(2) = 0.5f * w + x;
//    vertices(3) = -0.5f * h + y;
//    vertices(4) = -0.5f * w + x;
//    vertices(5) = 0.5f * h + y;
//    vertices(6) = 0.5f * w + x;
//    vertices(7) = 0.5f * h + y;
//
//    val colors = getColors(16);
//    (0 to 3).foreach { i =>
//      colors(i * 4 + 0) = r;
//      colors(i * 4 + 1) = g;
//      colors(i * 4 + 2) = b;
//      colors(i * 4 + 3) = a;
//    }
//
//    val squareVertices = makeVerticesBuffer(vertices);
//    val squareColors = makeColorsBuffer(colors);
//
//    gl.glVertexPointer(2, GL10.GL_FLOAT, 0, squareVertices);
//    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//    gl.glColorPointer(4, GL10.GL_FLOAT, 0, squareColors);
//    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//
//    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);
//  }
//  final def drawSquare(gl: GL10, x: Float, y: Float, r: Float, g: Float, b: Float, a: Float) {
//    drawRectangle(gl, x, y, 1.0f, 1.0f, r, g, b, a);
//  }
//
//  final def drawSquare(gl: GL10, r: Float, g: Float, b: Float, a: Float) {
//    drawSquare(gl, 0.0f, 0.0f, r, g, b, a);
//  }
//
//  final def makeFloatBuffer(arr: ArrayBuffer[Float]): FloatBuffer = {
//    val bb = ByteBuffer.allocateDirect(arr.length * 4);
//    bb.order(ByteOrder.nativeOrder());
//    val fb = bb.asFloatBuffer();
//    fb.put(arr.toArray);
//    fb.position(0);
//    return fb;
//  }
//}
