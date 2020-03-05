需要在主build 中添加依赖，如下

buildscript {

    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.2.0'

        ...........
        //添加部分 start
        classpath 'com.github.dcendents:android-maven-gradle-plugin:1.4.1'
        classpath 'com.novoda:bintray-release:0.5.0'
        //黄油刀，控件绑定
        classpath 'com.jakewharton:butterknife-gradle-plugin:8.4.0'
        //添加部分 end
        ...........

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        ...........
        //添加部分 start
        mavenCentral()
        //zylibrary 所需依赖
        maven { url "https://jitpack.io" }
        //添加部分 end
        ...........
    }
}