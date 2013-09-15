package de.saxsys.jfx.mvvm.viewloader;

/**
 * Possible ViewControllers.
 */
public enum MVVMViewNames {
    /**
     * View for the serversettings.
     */
    PERSONWELCOME("/de/saxsys/jfx/exampleapplication/view/personwelcome/PersonWelcomeView.fxml"), PERSONLOGIN(
            "/de/saxsys/jfx/exampleapplication/view/personlogin/PersonLoginView.fxml");

    private String resource;

    private MVVMViewNames(final String resource) {
        this.resource = resource;
    }

    /**
     * @return resourcestring
     */
    public String getResource() {
        return resource;
    }
}
