# MvvmFX Archetype

This module is a maven archetype to create a simple mvvmFX application. 

To use this archetype:
    
    mvn archetype:generate
        -DarchetypeGroupId=de.saxsys
        -DarchetypeArtifactId=mvvmfx-archetype
        -DarchetypeVersion=<mvvmFX version>
        -DgroupId=your.group.id
        -DartifactId=your.artifact.id
        

This creates an example mvvmfx project similar to the [mvvmfx-helloworld example](/examples/mvvmfx-helloworld).

*Hint:* At the moment the archetype isn't available in the central archetype catalogue. This means that you have to `mvn install` the archetype into your local repository to be able to use it.
