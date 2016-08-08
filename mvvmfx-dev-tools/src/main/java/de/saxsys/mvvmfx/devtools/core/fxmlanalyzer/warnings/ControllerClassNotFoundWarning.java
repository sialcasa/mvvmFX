package de.saxsys.mvvmfx.devtools.core.fxmlanalyzer.warnings;

public class ControllerClassNotFoundWarning implements Warning {
	private final String classFQN;

	public ControllerClassNotFoundWarning(String classFQN) {
		this.classFQN = classFQN;
	}

	public String getClassFQN() {
		return classFQN;
	}
}
