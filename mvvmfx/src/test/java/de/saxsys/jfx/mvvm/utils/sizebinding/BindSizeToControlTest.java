package de.saxsys.jfx.mvvm.utils.sizebinding;

import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.bindHeight;
import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.bindSize;
import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.bindWidth;

import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;

import org.junit.Before;
import org.junit.Test;

public class BindSizeToControlTest extends SizeBindingsBuilderTestBase {
	
	private Control toControl;
	
	
	@Before
	public void setUp() {
		toControl = new ScrollPane();
	}
	
	// SIZE BINDINGS
	
	@Test
	public void bindSizeFromRegionToControl() {
		bindSize().from(fromRegion).to(toControl);
		assertCorrectSize(toControl);
	}
	
	@Test
	public void bindSizeFromControlToControl() {
		bindSize().from(fromControl).to(toControl);
		assertCorrectSize(toControl);
	}
	
	@Test
	public void bindSizeFromRectangleToControl() {
		bindSize().from(fromRectangle).to(toControl);
		assertCorrectSize(toControl);
	}
	
	@Test
	public void bindSizeFromImageViewToControl() {
		bindSize().from(fromImageView).to(toControl);
		assertCorrectSize(toControl);
	}
	
	// HEIGHT Bindings
	
	@Test
	public void bindHeightFromRegionToControl() {
		bindHeight().from(fromRegion).to(toControl);
		assertCorrectHeight(toControl);
	}
	
	@Test
	public void bindHeightFromControlToControl() {
		bindHeight().from(fromControl).to(toControl);
		assertCorrectHeight(toControl);
	}
	
	@Test
	public void bindHeightFromRectangleToControl() {
		bindHeight().from(fromRectangle).to(toControl);
		assertCorrectHeight(toControl);
	}
	
	@Test
	public void bindHeightFromImageViewToControl() {
		bindHeight().from(fromImageView).to(toControl);
		assertCorrectHeight(toControl);
	}
	
	// WIDTH Bindings
	
	@Test
	public void bindWidthFromRegionToControl() {
		bindWidth().from(fromRegion).to(toControl);
		assertCorrectWidth(toControl);
	}
	
	@Test
	public void bindWidthFromControlToControl() {
		bindWidth().from(fromControl).to(toControl);
		assertCorrectWidth(toControl);
	}
	
	@Test
	public void bindWidthFromRectangleToControl() {
		bindWidth().from(fromRectangle).to(toControl);
		assertCorrectWidth(toControl);
	}
	
	@Test
	public void bindWidthFromImageViewToControl() {
		bindWidth().from(fromImageView).to(toControl);
		assertCorrectWidth(toControl);
	}
}
