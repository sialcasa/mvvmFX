package de.saxsys.jfx.mvvm.utils.sizebinding;

import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static de.saxsys.jfx.mvvm.utils.sizebinding.SizeBindingsBuilder.*;


public class UnbindWidthTest extends SizeBindingsBuilderTestBase{


    private ImageView targetImageView;
    private Control targetControl;
    private Rectangle targetRectangle;
    private Region targetRegion;


    @Before
    public void setUp(){
        targetImageView = new ImageView();
        targetControl = new ScrollPane();
        targetRectangle = new Rectangle();
        targetRegion = new Region();
    }

    @Test
    public void unbindWidthOfImageView(){
        bindWidth().from(fromRectangle).to(targetImageView);

        unbindWidth().of(targetImageView);

        fromRectangle.setWidth(SIZEVAL+100);

        assertCorrectWidth(targetImageView); // still the old size
    }

    @Test
    public void unbindWidthOfControl(){
        bindWidth().from(fromRectangle).to(targetControl);

        unbindWidth().of(targetControl);

        fromRectangle.setWidth(SIZEVAL+100);

        assertCorrectWidth(targetControl); // still the old size
    }

    @Test
    public void unbindWidthOfRectangle(){
        bindWidth().from(fromRectangle).to(targetRectangle);

        unbindWidth().of(targetRectangle);

        fromRectangle.setWidth(SIZEVAL+100);

        assertCorrectWidth(targetRectangle); // still the old size
    }

    @Test
    public void unbindWidthOfRegion(){
        bindWidth().from(fromRectangle).to(targetRegion);

        unbindWidth().of(targetRegion);

        fromRectangle.setWidth(SIZEVAL+100);

        assertCorrectWidth(targetRegion); // still the old size
    }
}
