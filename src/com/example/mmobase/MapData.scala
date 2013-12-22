//package com.example.mmobase
//
//import scala.collection.mutable.ArrayBuffer
//
//object Chip extends Enumeration {
//  type Chip = Value
//  val Floor = Value
//}
//import Chip._
//
//case class Cell(chip: ArrayBuffer[Chip] = ArrayBuffer.fill(10)(Chip.Floor))
//object MapData {
//  val MapData = ArrayBuffer.fill(100 * 100)(Cell())
//}