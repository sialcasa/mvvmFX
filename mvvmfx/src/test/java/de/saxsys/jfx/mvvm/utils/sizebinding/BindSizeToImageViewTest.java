package de.saxsys.jfx.mvvm.utils.sizebinding;

import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.bindHeight;
import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.bindSize;
import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.bindWidth;

import javafx.scene.image.ImageView;

import org.junit.Before;
import org.junit.Test;

public class BindSizeToImageViewTest extends SizeBindingsBuilderTestBase {
	private ImageView toImageView;
	
	
	@Before
	public void setUp() {
		toImageView = new ImageView();
	}
	
	
	// SIZE BINDINGS
	
	@Test
	public void bindSizeFromRegionToImageView() {
		bindSize().from(fromRegion).to(toImageView);
		assertCorrectSize(toImageView);
	}
	
	@Test
	public void bindSizeFromControlToImageView() {
		bindSize().from(fromControl).to(toImageView);
		assertCorrectSize(toImageView);
	}
	
	@Test
	public void bindSizeFromRectangleToImageView() {
		bindSize().from(fromRectangle).to(toImageView);
		assertCorrectSize(toImageView);
	}
	
	@Test
	public void bindSizeFromImageViewToImageView() {
		bindSize().from(fromImageView).to(toImageView);
		assertCorrectSize(toImageView);
	}
	
	
	
	// HEIGHT Bindings
	@Test
	public void bindHeightFromRegionToImageView() {
		bindHeight().from(fromRegion).to(toImageView);
		assertCorrectHeight(toImageView);
	}
	
	@Test
	public void bindHeightFromControlToImageView() {
		bindHeight().from(fromControl).to(toImageView);
		assertCorrectHeight(toImageView);
	}
	
	@Test
	public void bindHeightFromRectangleToImageView() {
		bindHeight().from(fromRectangle).to(toImageView);
		assertCorrectHeight(toImageView);
	}
	
	@Test
	public void bindHeightFromImageViewToImageView() {
		bindHeight().from(fromImageView).to(toImageView);
		assertCorrectHeight(toImageView);
	}
	
	
	// WIDTH Bindings
	
	@Test
	public void bindWidthFromRegionToImageView() {
		bindWidth().from(fromRegion).to(toImageView);
		assertCorrectWidth(toImageView);
	}
	
	@Test
	public void bindWidthFromControlToImageView() {
		bindWidth().from(fromControl).to(toImageView);
		assertCorrectWidth(toImageView);
	}
	
	@Test
	public void bindWidthFromRectangleToImageView() {
		bindWidth().from(fromRectangle).to(toImageView);
		assertCorrectWidth(toImageView);
	}
	
	@Test
	public void bindWidthFromImageViewToImageView() {
		bindWidth().from(fromImageView).to(toImageView);
		assertCorrectWidth(toImageView);
	}
}
