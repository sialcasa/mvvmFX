# MvvmFX DevTools


###Usage
add following code to your application start method.

		DevTool devTool = new DevTool();
		devTool.analyze(MainView.class);
		devTool.start();
MainView should be replaced with the class or classes you want to analyze.		