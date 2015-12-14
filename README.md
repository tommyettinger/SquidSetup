# SquidSetup
A fork of libGDX's setup tool, preconfigured for SquidLib

SquidLib is a nice way to make roguelikes or other grid-based games in Java, but starting
with version `3.0.0-b1`, it is somewhat more difficult to set up a new project that uses
it, needing some setup of Maven, Gradle, or another JVM build tool. Thankfully, LibGDX
(which SquidLib's text display depends on completely now) provides a setup tool that does
the grunt work of configuring a sample Gradle project to know about LibGDX's dependencies,
automatically downloading them, and optionally setting up platforms other than desktop,
like iOS and Android. Since SquidLib had the same issue LibGDX solved with this setup tool,
a quick edit of the tool to depend on SquidLib version `3.0.0-b2` (to be updated in
lockstep with SquidLib) means we have this tool, SquidSetup, that lets you go through a few
simple steps in a GUI and get a pre-configured project set up to run on Linux, Windows, Mac
OS X, Android, and/or iOS. The project will include modules for whichever of Desktop,
Android, and/or iOS you selected plus an always-present Core module, and a sample set of
code (mostly in one file in the core module) that uses `squidlib` and `squidlib-util` and
shows how to use the libraries to handle dungeon generation, input handling, pathfinding, and
text-based display. It will also guarantee SquidLib's core in `squidlib-util` is available,
and optionally will depend on `squidlib` to provide text-based full-color display using
LibGDX (this dependency is needed to run the sample game, but if you don't intend to use the
text-based example code, the tool also lets you use LibGDX _a la carte_ if you want to
handle the graphics yourself).

## Usage

  - Get the latest `SquidSetup.jar` from the [Releases tab](https://github.com/tommyettinger/SquidSetup/releases) of this project.
  - Regardless of what platforms you intend to target, make sure the steps
    [described by the LibGDX wiki here](https://github.com/libgdx/libgdx/wiki/Setting-up-your-Development-Environment-%28Eclipse%2C-Intellij-IDEA%2C-NetBeans%29)
    are taken care of.
  - Run the JAR. Plug in whatever options you see fit:
    - iOS should not be checked if you aren't running Mac OS X.
    - Android should only be checked if you've set up your computer for Android development.
    - You need SquidLib checked if you want to use the text-based display. The core of
      SquidLib, `squidlib-util`, is always included regardless of checked boxes.
    - You don't need LibGDX checked if you have SquidLib checked (the tool is set up to
      download LibGDX and set it as a dependency whenever SquidLib is checked).
    - If you click Advanced, you can choose to generate project files for IntelliJ IDEA
      and/or Eclipse, which will speed up build times a bit, but requires you to do more
      work if your dependencies change. Gradle won't be available to download dependencies
      right away, so you could either make changes to build.gradle, then re-sync the project
      and in the process undo any hand-made changes to the IDE projects, OR you could
      manually download any JARs you depend on and add them to the IDE project. Neither is
      exactly ideal, so this option is only suggested if you don't expect to be adding
      more dependencies. Having a Gradle project with all the needed configuration in it
      makes an open-source game easier for contributors who don't use the same IDE to build
      your game.
  - Click generate, wait for the messages at the bottom of the window to report either
    `BUILD SUCCEEDED` or `BUILD FAILED`, and if it succeeded then you'll be given some quick
    instructions for how to open the project in those messages.
    
Now you'll have a project all set up with a sample.

  - If you had the Desktop and SquidLib options checked in the setup, you can try to run the
    Desktop module right away, with a simple pathfinding demo that responds to mouse and
    keyboard input in a random dungeon (the random number generator is seeded the same every
    time by default, but you can change the RNG constructor as a quick way to experiment).
  - If you had the Android and SquidLib options checked in the setup, you can try to run the
    Android module on an emulator or a connected Android device. It won't work quite as well
    as desktop will by default, mostly because the screen size and application "window" size
    aren't well-coordinated yet.
  - If you had the iOS and SquidLib options checked in the setup, you're running Mac OS X,
    you have jumped through Apple's now-infamous process described at the LibGDX wiki at the
    earlier link, you've made the blood sacrifice to your iAltar, and the Black Turtleneck
    of Cosmic Power and Ultimate Build Quality has manifested before you and informed you of
    your truename, you can attempt to run the iOS module. If it works, you'll have the
    record of being the first person to run SquidLib on an iOS device... In case you can't
    tell, I am not terribly confident in the ability of this tool to generate iOS projects
    that work on the first try, though it may be easy enough to modify things in the likely
    case they don't immediately work.
    
Good luck, and I hope you make something great!

