# GradleAspectJ

Simple plugin for AspectJ.

## How to use.

```groovy
buildscript {
    repositories {
        jcenter()
        mavenCentral()
        maven { url "https://jitpack.io" }
    }
    dependencies {
        ...
        classpath 'com.github.sergeymild:gradleAspectJ:1.0.2'
    }
}

apply plugin: 'gradle-aspectj'
```

## Configuration.

You can enabled/disable it on a debug and/or release build. Default configuration looks like this.
```groovy
gradleAspectJ {
    enabled = true
}
```

## How to start using AspectJ.
1. Add `@AspectJ` annotation to your aspect class.
2. Define pointcut.