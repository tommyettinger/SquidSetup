# Assets for SquidLib

This folder contains all of the resources that SquidLib references in its DefaultResources class, in the squidlib
display module. You only need the files in here that your project actually uses, which is frequently just one .fnt
file for a font and its corresponding .png file. It is recommended that you move the files you don't need out of the
assets folder and into some other folder that won't be bundled into distributable JAR or APK files, so outside any src
directories as well. That way, if you find you want to change the font, you don't have to download the files again. It
is recommended that you run your program to make sure your program still looks as intended each time you move files.
