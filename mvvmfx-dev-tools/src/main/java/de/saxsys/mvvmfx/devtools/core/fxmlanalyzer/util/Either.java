package de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.util;

import java.util.Optional;
import java.util.function.Function;


/**
 * Either class represents a result that can be one of two possible result types.
 * <p/>
 * A typical usage case for the Either type is as a result type for methods that may be successful or fail
 * and when no exceptions are to be used.
 * In this scenario the {@link #left} type represents the result of the method when the operation
 * was successful. The {@link #right} type represents the result of the method when the operation
 * has failed. In this case {@link #right} typically contains a failure message or some similar type that
 * represents the reason for failure.
 *
 * @param <L>
 * @param <R>
 */
public class Either<L, R> {

	private final L left;
	private final R right;

	private Either(L left, R right) {
		this.left = left;
		this.right = right;
	}

	public static <L, R> Either<L,R> left(L left) {
		return new Either<>(left, null);
	}

	public static <L, R> Either<L,R> right(R right) {
		return new Either<>(null, right);
	}

	public L getLeftUnsafe() {
		return left;
	}

	public R getRightUnsafe() {
		return right;
	}

	public Optional<L> getLeft() {
		return Optional.ofNullable(left);
	}

	public Optional<R> getRight() {
		return Optional.ofNullable(right);
	}

	public boolean isLeft() {
		return left != null;
	}

	public boolean isRight() {
		return right != null;
	}

}
