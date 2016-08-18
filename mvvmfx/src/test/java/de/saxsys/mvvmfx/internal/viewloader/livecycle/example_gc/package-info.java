/**
 * The classes in this package are used to show a scenario were garbage collection
 * is performed in one of the livecycle methods.
 * In the first prove-of-concept implementation of the livecycle handling
 * this was producing a misbehavior: After the garbage collection, other following livecycle methods
 * were not be invoked.
 */
package de.saxsys.mvvmfx.internal.viewloader.livecycle.example_gc;