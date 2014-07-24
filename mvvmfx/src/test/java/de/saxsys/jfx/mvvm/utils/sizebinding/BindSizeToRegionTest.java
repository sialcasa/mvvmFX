package de.saxsys.jfx.mvvm.utils.sizebinding;

import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.bindHeight;
import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.bindSize;
import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.bindWidth;

import javafx.scene.layout.Region;

import org.junit.Before;
import org.junit.Test;

public class BindSizeToRegionTest extends SizeBindingsBuilderTestBase {
	
	private Region toRegion;
	
	
	@Before
	public void setUp() {
		toRegion = new Region();
	}
	
	// SIZE BINDINGS
	
	@Test
	public void bindSizeFromRegionToRegion() {
		bindSize().from(fromRegion).to(toRegion);
		assertCorrectSize(toRegion);
	}
	
	@Test
	public void bindSizeFromControlToRegion() {
		bindSize().from(fromControl).to(toRegion);
		assertCorrectSize(toRegion);
	}
	
	@Test
	public void bindSizeFromRectangleToRegion() {
		bindSize().from(fromRectangle).to(toRegion);
		assertCorrectSize(toRegion);
	}
	
	@Test
	public void bindSizeFromImageViewToRegion() {
		bindSize().from(fromImageView).to(toRegion);
		assertCorrectSize(toRegion);
	}
	
	// HEIGHT Bindings
	
	@Test
	public void bindHeightFromRegionToRegion() {
		bindHeight().from(fromRegion).to(toRegion);
		assertCorrectHeight(toRegion);
	}
	
	@Test
	public void bindHeightFromControlToRegion() {
		bindHeight().from(fromControl).to(toRegion);
		assertCorrectHeight(toRegion);
	}
	
	@Test
	public void bindHeightFromRectangleToRegion() {
		bindHeight().from(fromRectangle).to(toRegion);
		assertCorrectHeight(toRegion);
	}
	
	@Test
	public void bindHeightFromImageViewToRegion() {
		bindHeight().from(fromImageView).to(toRegion);
		assertCorrectHeight(toRegion);
	}
	
	// WIDTH Bindings
	
	@Test
	public void bindWidthFromRegionToRegion() {
		bindWidth().from(fromRegion).to(toRegion);
		assertCorrectWidth(toRegion);
	}
	
	@Test
	public void bindWidthFromControlToRegion() {
		bindWidth().from(fromControl).to(toRegion);
		assertCorrectWidth(toRegion);
	}
	
	@Test
	public void bindWidthFromRectangleToRegion() {
		bindWidth().from(fromRectangle).to(toRegion);
		assertCorrectWidth(toRegion);
	}
	
	@Test
	public void bindWidthFromImageViewToRegion() {
		bindWidth().from(fromImageView).to(toRegion);
		assertCorrectWidth(toRegion);
	}
}
