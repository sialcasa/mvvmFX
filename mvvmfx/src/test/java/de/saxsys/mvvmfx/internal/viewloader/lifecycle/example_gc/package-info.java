/**
 * The classes in this package are used to show a scenario were garbage collection
 * is performed in one of the lifecycle methods.
 * In the first prove-of-concept implementation of the lifecycle handling
 * this was producing a misbehavior: After the garbage collection, other following lifecycle methods
 * were not be invoked.
 */
package de.saxsys.mvvmfx.internal.viewloader.lifecycle.example_gc;