##Installation

Step 1: Add this in your root build.gradle at the end of repositories:

    allprojects {
		    repositories {
			      ...
			      maven { url "https://jitpack.io" }
		    }
	 }
	 
Step 2. Add the dependency

    dependencies {
	     compile 'com.github.intellum:neeman-android:0.1-alpha'
    }
