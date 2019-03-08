![image](http://www.buildpath.de/mvvm/mvvmfx.png)

**mvvmFX** is an application framework which provides you necessary components to implement the [MVVM](../../wiki/MVVM-Overview "MVVM") pattern with JavaFX.

**MVVM** is the enhanced version of the [Presentation Model](http://martinfowler.com/eaaDev/PresentationModel.html "Presentation Model") pattern and was created by Microsoft engineers for [WPF](http://msdn.microsoft.com/en-us/library/ms754130.aspx "WPF"). JavaFX and WPF does have similarities like data binding and descriptive UI declaration (FXML/XAML). Because of this fact we adopted best practices of the development with the Microsoft technology and introduced new helpers to support the development of applications with JavaFX and MVVM.

[![Commercial Support](https://img.shields.io/badge/Commercial%20Support%20-by%20Saxonia%20Systems-brightgreen.svg)](http://goo.gl/forms/WVBG3SWHuL)
[![Build Status](https://api.travis-ci.org/sialcasa/mvvmFX.svg?branch=develop)](https://travis-ci.org/sialcasa/mvvmFX)


### [Howto](../../wiki "Howto")

### Maven dependency

#### Stable Release

This is the stable release that can be used in production.

```xml
<dependency>
		<groupId>de.saxsys</groupId>
		<artifactId>mvvmfx</artifactId>
		<version>1.8.0</version>
</dependency>
```

#### Bugfix Development Snapshot

Here we make bug fixes for the current stable release.

```xml
<dependency>
		<groupId>de.saxsys</groupId>
		<artifactId>mvvmfx</artifactId>
		<version>1.8.1-SNAPSHOT</version>
</dependency>
```

#### Development Snapshot

Here we develop new features. This release is unstable and shouldn't be used in production. 

```xml
<dependency>
		<groupId>de.saxsys</groupId>
		<artifactId>mvvmfx</artifactId>
		<version>1.9.0-SNAPSHOT</version>
</dependency>
```

#### Snapshot repository

We use the Sonatype snapshot repository.
 Maybe you have to add this repository to your \<repository> section of the pom.xml.

```xml
<repository>
		<id>sonatype-snapshots</id>
		<url>https://oss.sonatype.org/content/repositories/snapshots/</url>
		<snapshots>
			<enabled>true</enabled>
		</snapshots>
</repository>
```


### Get Help

The best way to get help with mvvmFX is to either ask questions on StackOverflow using the [tag "mvvmfx"](https://stackoverflow.com/questions/tagged/mvvmfx) or to use our [Google Groups](https://groups.google.com/forum/#!forum/mvvmfx-dev) mailing list. Additionally you can create issues, report bugs and add feature requests on the issue tracker at [github](https://github.com/sialcasa/mvvmFX/issues).

### Links

- [Project Page](http://sialcasa.github.io/mvvmFX/)
- [javadoc mvvmfx core](http://sialcasa.github.io/mvvmFX/javadoc/1.7.0/mvvmfx/)
- [javadoc mvvmfx-cdi](http://sialcasa.github.io/mvvmFX/javadoc/1.7.0/mvvmfx-cdi/)
- [javadoc mvvmfx-guice](http://sialcasa.github.io/mvvmFX/javadoc/1.7.0/mvvmfx-guice/)
- [javadoc mvvmfx-easydi](http://sialcasa.github.io/mvvmFX/javadoc/1.7.0/mvvmfx-easydi/)
- [javadoc mvvmfx-validation](http://sialcasa.github.io/mvvmFX/javadoc/1.7.0/mvvmfx-validation/)
- [javadoc mvvmfx-utils](http://sialcasa.github.io/mvvmFX/javadoc/1.7.0/mvvmfx-utils/)
- [javadoc mvvmfx-testing-utils](http://sialcasa.github.io/mvvmFX/javadoc/1.7.0/mvvmfx-testing-utils/)

