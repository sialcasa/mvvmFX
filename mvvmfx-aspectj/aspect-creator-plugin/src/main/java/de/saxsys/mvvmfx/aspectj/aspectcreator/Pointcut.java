package de.saxsys.mvvmfx.aspectj.aspectcreator;

class Pointcut {


    private String name;
    private String signature;
    private String primitive;

    Pointcut(String name, String primitive, String signature) {
        this.name = name;
        this.primitive = primitive;
        this.signature = signature;

    }

    @Override
    public String toString() {
        return "pointcut " + name + ": " +
                this.getExpression() + ";";
    }

    private String getExpression() {
        return primitive + "(" + signature + ")";
    }
}
