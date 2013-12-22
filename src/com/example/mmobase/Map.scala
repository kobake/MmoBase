package com.example.mmobase

import scala.collection.mutable.ArrayBuffer
import javax.microedition.khronos.opengles.GL10
import android.content.Context
import scala.util.Random

// 描画先
// 純粋にpxだけを持っておいたほうが後々役に立つ
// 純粋に描画元の座標だけを持って置いたほうが後々役に立つ
case class Src(x: Int, y: Int);
case class CellPos(x: Int, y: Int);
// 描画先にセル位置を加算すると新しい描画先が得られる
case class Dst(x: Int, y: Int)
object Dst {
  def +(dst: Dst, cell_pos: CellPos) = Dst(
    dst.x + cell_pos.x * 16 + cell_pos.y * 16,
    dst.y - cell_pos.x * 8 + cell_pos.y * 8)
}

// チップ1個
case class Chip(var chip_number: Int) { // 数だから0始まり 
  val Cols = 8 // 個数
  val PixelWidth = 32
  val PixelHeight = 32
  def draw(gl: GL10, dst: Dst) {
    val tex_x = (chip_number % Cols) * PixelWidth //(chip_number / Cols)はIntでないといけない
    val tex_y = (chip_number / Cols) * PixelHeight //(chip_number / Cols)はIntでないといけない 
    Texture.draw(gl, dst, Src(tex_x, tex_y))
  }
}

// セル1マス (最大10チップが重なる)
case class Cell {
  lazy val rnd = new Random 
  var chips = ArrayBuffer.fill(rnd.nextInt(9))(Chip(4))
  def draw(gl: GL10, dst: Dst) = chips.zipWithIndex.foreach { v =>
    val (chip, i) = v
    chip.draw(gl, Dst(dst.x, dst.y - 16 * i)) // yを16上に
  }
}

// テクスチャ1個
object Texture {
  val res = MainRenderer.mMapTexture // テクスチャの番号
  val width = 1024;
  val height = 1024;
  def drawTexture(gl: GL10, x: Int, y: Int, w: Float, h: Float, texture: Int, u: Int, v: Int, tex_w: Int, tex_h: Int) = GraphicUtil.drawTexture(gl, x, y, w, h, texture, u / 1024.0f, v / 1024.0f, tex_w / 1024.0f, tex_h / 1024.0f, 1.0f, 1.0f, 1.0f, 1.0f)
  def draw(gl: GL10, dst: Dst, src: Src) {
    val drawSize = 32 //32px 
    val destSize = 32 //描画先のサイズ32px
    gl.glEnable(GL10.GL_BLEND);
    gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
    drawTexture(gl, dst.x, dst.y, destSize, destSize, res, src.x, src.y, drawSize, drawSize)
    gl.glDisable(GL10.GL_BLEND)
  }
}

// Dst 画面の描画先(-1にするとどんどん右が見える)
// 現在主人公がいるマス目の(0,0)を描画すべきdetはどこだなっていうラップ関数を作るとより使いやすくなる

// ひきすうが現在主人公
// 処理内容が現在居るマス目から実際描画すべきdesはどこなのかを算出して今回作ったmapdraw関数を間接的に呼ぶ

// マップ
object MyMap {
  val cells: ArrayBuffer[Cell] = ArrayBuffer.fill(10 * 10)(Cell())
  var width: Int = 9
  var height: Int = 9
  def cellAt(cell_pos: CellPos): Cell = cells(cell_pos.x * width + cell_pos.y)
  def draw(gl: GL10, dst: Dst) {
    var y = 0
    while (y < height) {
      var x = 0
      while (x < width) {
        val post = CellPos(width - x, y) // x軸の描画半角
        val cell = cellAt(post)
        cell.draw(gl, Dst.+(dst, post)) //TODO適当
        x += 1
      }
      y += 1
    }
  }
  def userUpdate(x: Int, y: Int) = {
    ArrayBuffer.fill(cellAt(CellPos(x, y)).chips.length)(cellAt(CellPos(x, y)).chips.head).append(Chip(10))
  }
}