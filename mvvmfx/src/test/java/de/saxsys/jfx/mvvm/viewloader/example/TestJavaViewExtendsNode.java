package de.saxsys.jfx.mvvm.viewloader.example;

import javafx.scene.Node;

import com.sun.javafx.geom.BaseBounds;
import com.sun.javafx.geom.transform.BaseTransform;
import com.sun.javafx.jmx.MXNodeAlgorithm;
import com.sun.javafx.jmx.MXNodeAlgorithmContext;
import com.sun.javafx.sg.prism.NGNode;
import de.saxsys.jfx.mvvm.api.JavaView;

public class TestJavaViewExtendsNode extends Node implements JavaView<TestViewModel> {
	
	@Override
	protected NGNode impl_createPeer() {
		return null;
	}
	
	@Override
	public BaseBounds impl_computeGeomBounds(BaseBounds bounds, BaseTransform tx) {
		return null;
	}
	
	@Override
	protected boolean impl_computeContains(double localX, double localY) {
		return false;
	}
	
	@Override
	public Object impl_processMXNode(MXNodeAlgorithm alg, MXNodeAlgorithmContext ctx) {
		return null;
	}
}
