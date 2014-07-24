package de.saxsys.jfx.mvvm.utils.sizebinding;

import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.bindHeight;
import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.unbindHeight;

import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;

import org.junit.Before;
import org.junit.Test;

public class UnbindHeightTest extends SizeBindingsBuilderTestBase {
	
	private ImageView targetImageView;
	private Control targetControl;
	private Rectangle targetRectangle;
	private Region targetRegion;
	
	
	@Before
	public void setUp() {
		targetImageView = new ImageView();
		targetControl = new ScrollPane();
		targetRectangle = new Rectangle();
		targetRegion = new Region();
	}
	
	@Test
	public void unbindHeightOfImageView() {
		bindHeight().from(fromRectangle).to(targetImageView);
		
		unbindHeight().of(targetImageView);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		
		assertCorrectHeight(targetImageView); // still the old size
	}
	
	@Test
	public void unbindHeightOfControl() {
		bindHeight().from(fromRectangle).to(targetControl);
		
		unbindHeight().of(targetControl);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		
		assertCorrectHeight(targetControl); // still the old size
	}
	
	@Test
	public void unbindHeightOfRectangle() {
		bindHeight().from(fromRectangle).to(targetRectangle);
		
		unbindHeight().of(targetRectangle);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		
		assertCorrectHeight(targetRectangle); // still the old size
	}
	
	@Test
	public void unbindHeightOfRegion() {
		bindHeight().from(fromRectangle).to(targetRegion);
		
		unbindHeight().of(targetRegion);
		
		fromRectangle.setHeight(SIZEVAL + 100);
		
		assertCorrectHeight(targetRegion); // still the old size
	}
}
