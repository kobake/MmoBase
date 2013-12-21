package com.example.mmobase;

import java.util.Hashtable;

public class Vector2D {

	public float mX, mY;

	public Vector2D() {
		this.mX = 0.0f;
		this.mY = 0.0f;
	}

	public Vector2D(float x, float y) {
		this.mX = x;
		this.mY = y;
	}

	// 足し算
	public Vector2D add(Vector2D v) {
		return new Vector2D(mX + v.mX, mY + v.mY);
	}

	// 引き算
	public Vector2D subtract(Vector2D v) {
		return new Vector2D(mX - v.mX, mY - v.mY);
	}

	// かけ算
	public Vector2D multiply(float v) {
		return new Vector2D(mX * v, mY * v);
	}

	// 割り算
	public Vector2D divide(float v) {
		if (v == 0.0f) {
			return this;
		}
		return new Vector2D(mX * (1.0f / v), mY * (1.0f / v));
	}

	// 長さの二乗を計算します
	public float getSquareLength() {
		return (mX * mX + mY * mY);
	}

	// 単位ベクトルを返す
	public Vector2D unitVector() {
		float length = (float) Math.sqrt(getSquareLength());
		return divide(length);
	}

	// 内積を求める
	public float dotProduct(Vector2D v) {
		return mX * v.mX + mY * v.mY;
	}
}
