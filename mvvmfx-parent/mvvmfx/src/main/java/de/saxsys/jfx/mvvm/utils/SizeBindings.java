/*******************************************************************************
 * Copyright 2013 Alexander Casall
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.saxsys.jfx.mvvm.utils;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

/**
 * This class is an helper for binding two components to the same size. It can
 * handle {@link Pane},{@link Node}, {@link ScrollPane}, {@link Rectangle},
 * {@link Control}
 * 
 * @author alexander.casall
 *
 * @deprecated Use {@link de.saxsys.jfx.mvvm.utils.SizeBindingsBuilder} instead.
 * This class is deprecated because the usage of overloaded methods for different types of components isn't flexible enough.
 * 
 */

@Deprecated
public final class SizeBindings {

	private SizeBindings() {
	}

	/**
	 * Binds the size of a {@link Pane} to a {@link Pane}.
	 * 
	 * @param fromPane
	 *            {@link Pane}
	 * @param toPane
	 *            {@link Pane}
	 */
	public static void bindSize(final Pane fromPane, final Pane toPane) {
		toPane.minWidthProperty().bind(fromPane.widthProperty());
		toPane.maxWidthProperty().bind(fromPane.widthProperty());
		toPane.minHeightProperty().bind(fromPane.heightProperty());
		toPane.maxHeightProperty().bind(fromPane.heightProperty());
	}

	/**
	 * Unbind the size of a {@link Region}.
	 * 
	 * @param toPane
	 *            {@link Region}
	 */
	public static void unbindSize(final Region toPane) {
		toPane.minWidthProperty().unbind();
		toPane.maxWidthProperty().unbind();
		toPane.minHeightProperty().unbind();
		toPane.maxHeightProperty().unbind();
	}

	/**
	 * Unbind the size of a {@link Control}.
	 * 
	 * @param toControl
	 *            {@link Control}
	 */
	public static void unbindSize(final Control toControl) {
		toControl.minWidthProperty().unbind();
		toControl.maxWidthProperty().unbind();
		toControl.minHeightProperty().unbind();
		toControl.maxHeightProperty().unbind();
	}

	/**
	 * Binds the size of a {@link ScrollPane} to a {@link Pane}.
	 * 
	 * @param from
	 *            {@link ScrollPane}
	 * @param toPane
	 *            {@link Pane}
	 */
	public static void bindSize(final ScrollPane from, final Pane toPane) {
		toPane.minWidthProperty().bind(from.widthProperty());
		toPane.maxWidthProperty().bind(from.widthProperty());
		toPane.minHeightProperty().bind(from.heightProperty());
		toPane.maxHeightProperty().bind(from.heightProperty());
	}

	/**
	 * Binds the size of a {@link Pane} to a {@link ScrollPane}.
	 * 
	 * @param from
	 *            {@link Pane}
	 * @param toPane
	 *            {@link ScrollPane}
	 */
	public static void bindSize(final Pane from, final ScrollPane toPane) {
		toPane.minWidthProperty().bind(from.widthProperty());
		toPane.maxWidthProperty().bind(from.widthProperty());
		toPane.minHeightProperty().bind(from.heightProperty());
		toPane.maxHeightProperty().bind(from.heightProperty());
	}

	/**
	 * Binds the size of a {@link Node} to a {@link Pane}.
	 * 
	 * @param from
	 *            {@link Node}
	 * @param toPane
	 *            {@link Pane}
	 */
	public static void bindSize(final Node from, final Pane toPane) {
		if (from instanceof Region) {
			toPane.minWidthProperty().bind(((Region) from).widthProperty());
			toPane.maxWidthProperty().bind(((Region) from).widthProperty());
			toPane.minHeightProperty().bind(((Region) from).heightProperty());
			toPane.maxHeightProperty().bind(((Region) from).heightProperty());
			return;
		}
		if (from instanceof Control) {
			toPane.minWidthProperty().bind(((Control) from).widthProperty());
			toPane.maxWidthProperty().bind(((Control) from).widthProperty());
			toPane.minHeightProperty().bind(((Control) from).heightProperty());
			toPane.maxHeightProperty().bind(((Control) from).heightProperty());
			return;
		}
		if (from instanceof Rectangle) {
			toPane.minWidthProperty().bind(((Rectangle) from).widthProperty());
			toPane.maxWidthProperty().bind(((Rectangle) from).widthProperty());
			toPane.minHeightProperty()
					.bind(((Rectangle) from).heightProperty());
			toPane.maxHeightProperty()
					.bind(((Rectangle) from).heightProperty());
			return;
		}

		if (from instanceof ImageView) {
			toPane.minWidthProperty().bind(
					((ImageView) from).fitWidthProperty());
			toPane.maxWidthProperty().bind(
					((ImageView) from).fitWidthProperty());
			toPane.minHeightProperty().bind(
					((ImageView) from).fitHeightProperty());
			toPane.maxHeightProperty().bind(
					((ImageView) from).fitHeightProperty());
			return;
		}

		throw new IllegalArgumentException(
				"Type of parameter is not mapped yet, please add mapping for type: "
						+ from.getClass());
	}

	/**
	 * Binds the size of a {@link Pane} to a {@link Node}.
	 * 
	 * @param from
	 *            {@link Pane}
	 * @param toNode
	 *            {@link Node}
	 */
	public static void bindSize(final Pane from, final Node toNode) {
		if (toNode instanceof Region) {
			((Region) toNode).minWidthProperty().bind(from.widthProperty());
			((Region) toNode).maxWidthProperty().bind(from.widthProperty());
			((Region) toNode).minHeightProperty().bind(from.heightProperty());
			((Region) toNode).maxHeightProperty().bind(from.heightProperty());
			return;
		}
		if (toNode instanceof Control) {
			((Control) toNode).minWidthProperty().bind(from.widthProperty());
			((Control) toNode).maxWidthProperty().bind(from.widthProperty());
			((Control) toNode).minHeightProperty().bind(from.heightProperty());
			((Control) toNode).maxHeightProperty().bind(from.heightProperty());
			return;
		}
		if (toNode instanceof Rectangle) {
			((Rectangle) toNode).widthProperty().bind(from.widthProperty());
			((Rectangle) toNode).widthProperty().bind(from.widthProperty());
			((Rectangle) toNode).heightProperty().bind(from.heightProperty());
			((Rectangle) toNode).heightProperty().bind(from.heightProperty());
			return;
		}

		throw new IllegalArgumentException(
				"Type of parameter is not mapped yet, please add mapping for type: "
						+ toNode.getClass());
	}

	/**
	 * Binds the with of a {@link Node} to a {@link Pane}.
	 * 
	 * @param from
	 *            node
	 * @param toPane
	 *            pane
	 */
	public static void bindWidth(final Node from, final Pane toPane) {
		if (from instanceof Region) {
			toPane.minWidthProperty().bind(((Region) from).widthProperty());
			toPane.maxWidthProperty().bind(((Region) from).widthProperty());
			return;
		}
		if (from instanceof Control) {
			toPane.minWidthProperty().bind(((Control) from).widthProperty());
			toPane.maxWidthProperty().bind(((Control) from).widthProperty());
			return;
		}
		if (from instanceof Rectangle) {
			toPane.minWidthProperty().bind(((Rectangle) from).widthProperty());
			toPane.maxWidthProperty().bind(((Rectangle) from).widthProperty());
			return;
		}
	}

	/**
	 * Binds the with of a {@link Node} to a {@link Control}.
	 * 
	 * @param from
	 *            node
	 * @param toControl
	 *            control
	 */
	public static void bindWidth(final Node from, final Control toControl) {
		if (from instanceof Region) {
			toControl.minWidthProperty().bind(((Region) from).widthProperty());
			toControl.maxWidthProperty().bind(((Region) from).widthProperty());
			return;
		}
		if (from instanceof Control) {
			toControl.minWidthProperty().bind(((Control) from).widthProperty());
			toControl.maxWidthProperty().bind(((Control) from).widthProperty());
			return;
		}
		if (from instanceof Rectangle) {
			toControl.minWidthProperty().bind(
					((Rectangle) from).widthProperty());
			toControl.maxWidthProperty().bind(
					((Rectangle) from).widthProperty());
			return;
		}
	}

}
