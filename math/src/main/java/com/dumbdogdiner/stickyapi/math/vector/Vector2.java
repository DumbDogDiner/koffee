/*
 * Copyright (c) 2020-2021 DumbDogDiner <dumbdogdiner.com>. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information...
 */
package com.dumbdogdiner.stickyapi.math.vector;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a two-dimensional immutable vector.
 */
public class Vector2 extends Vector<Vector2> {
	/**
	 * The x value of this vector.
	 */
	@Getter
	private final double x;

	/**
	 * The y value of this vector.
	 */
	@Getter
	private final double y;

	/**
	 * Construct a new 2D vector.
	 * @param x The x value
	 * @param y The y value
	 */
	public Vector2(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Construct a new 2D vector.
	 * @param x The x value
	 * @param y The y value
	 */
	public Vector2(@NotNull Number x, @NotNull Number y) {
		// ensure arguments are not null
		Preconditions.checkNotNull(x);
		Preconditions.checkNotNull(y);
		this.x = x.doubleValue();
		this.y = y.doubleValue();
	}

	@Override
	protected int getDimensions() {
		return 2;
	}

	@Override
	protected double[] getValues() {
		return new double[]{this.x, this.y};
	}

	@Override
	@NotNull
	Vector2 add(@NotNull Vector<Vector2> vector) {
		Preconditions.checkNotNull(vector);
		return new Vector2(this.x + vector.getDimension(0), this.y + vector.getDimension(1));
	}

	@Override
	@NotNull
	Vector2 subtract(@NotNull Vector<Vector2> vector) {
		Preconditions.checkNotNull(vector);
		return new Vector2(this.x - vector.getDimension(0), this.y - vector.getDimension(1));
	}

	@Override
	@NotNull
	Vector2 scale(@NotNull Number scalar) {
		Preconditions.checkNotNull(scalar);
		return new Vector2(this.x * scalar.doubleValue(), this.y * scalar.doubleValue());
	}
}
