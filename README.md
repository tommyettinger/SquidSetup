# SquidSetup
A fork of czyzby/gdx-setup, preconfigured for SquidLib

SquidLib is a nice way to make roguelikes or other grid-based games in Java, but in the
current version, it has the problem that all libGDX-based libraries have: it needs some
challenging setup to be done to get Gradle, a JVM build tool, to build the project.
Thankfully, both the official libGDX team and the unofficial group working with czyzby
have provided a setup tool that does the grunt work of configuring a sample Gradle
project to know about LibGDX's dependencies, automatically downloading them, and optionally
setting up platforms other than desktop, like HTML, iOS and Android. Since SquidLib had the
same issue that was solved for libGDX with this setup tool, a quick edit of czyzby's setup
to depend on the latest commit of SquidLib means we have this tool, SquidSetup, that lets
you go through a few simple steps in a GUI and get a pre-configured project set up to run
on Linux, Windows, MacOS, GWT/HTML, Android, and/or iOS. The project will include modules
for whichever of Desktop, GWT, Android, and/or iOS you selected plus an always-present Core
module, and a sample set of code (mostly in one file in the core module) that uses `squidlib`
and `squidlib-util` and shows how to use the libraries to handle dungeon generation, input
handling, pathfinding, text-based display, field of vision, fictional language generation,
and the kitchen sink. It will also guarantee SquidLib's core in `squidlib-util` is available,
and optionally will depend on `squidlib` to provide text-based full-color display using
LibGDX (this dependency is needed to run the sample game, but if you don't intend to use the
text-based example code, the tool also lets you use LibGDX _a la carte_ if you want to
handle the graphics yourself).

## Usage

  - Get the latest `SquidSetup.jar` from the [Releases tab](https://github.com/tommyettinger/SquidSetup/releases) of this project.
    - You may want the latest possible code using the [3.0.0-SNAPSHOT Release](https://github.com/tommyettinger/SquidSetup/releases/tag/v3.0.0-SNAPSHOT),
      or you may want a more-stable beta version. The latest version will get a commit compiled by JitPack.io, while the beta and stable versions
      will be obtained from Maven Central. The latest commit is determined by the library `jcabi-github`, and involves an API call to GitHub when using
      the snapshot release (not any beta or stable releases); the API call may be slow or might not complete if GitHub is having issues.
  - Regardless of what platforms you intend to target, make sure the steps
    [described by the LibGDX wiki here](https://github.com/libgdx/libgdx/wiki/Setting-up-your-Development-Environment-%28Eclipse%2C-Intellij-IDEA%2C-NetBeans%29)
    are taken care of.
  - Run the JAR. Plug in whatever options you see fit:
    - Desktop and/or LWJGL3 should usually be checked, so you can test on the same computer
      you develop on.
    - iOS should probably not be checked if you aren't running MacOS.
    - Android should only be checked if you've set up your computer for Android development,
      and may cause some hassles even in other projects if you use Android Studio or IntelliJ
      IDEA before the upcoming version 2017.3, when a bug is expected to be fixed.
    - If the "Templates" tab has "SquidLib Basic" checked, then dependencies will be added
      for `squidlib-util` and `squidlib`. If that template isn't checked, no dependencies
      will be added beyond libGDX. It is recommended that you use the SquidLib Basic template
      just so everything is set up correctly, even if you remove the `squidlib` display to
      only use `squidlib-util` for logic handling.
    - You don't need LibGDX checked (the tool is set up to download LibGDX and set it as a
      dependency in all cases).
    - If you click Advanced, you can choose to generate project files for IntelliJ IDEA
      and/or Eclipse, which has some advantages but probably more disadvantages. If you
      know what you're doing, you might want to try it, but be prepared for frustration.
  - Click generate, and very soon a window should pop up with instructions for what to do.
    
Now you'll have a project all set up with a sample.

  - If you use Android Studio or IntelliJ IDEA and have an Android project, you'll need to use
    Gradle tasks to do any part of the build/run process, thanks to a long-standing issue in
    IDEA's Android plugin.  The simplest way to do this is in the IDE itself, via
    `View -> Tool Windows -> Gradle`, and selecting tasks to perform, such as
    `Desktop -> Tasks -> application -> run.` If you try to run a specific class' `main()`
    method, you may encounter strange issues, but this shouldn't happen with Gradle tasks.
    This also shouldn't happen if you avoid creating an Android module in the first place
    (if you didn't check Android in the setup, this problem shouldn't apply).
  - If you had the Desktop and SquidLib options checked in the setup, you can try to run the
    Desktop module right away, with a simple pathfinding/FOV/text-gen demo that responds to
    mouse and keyboard input in a random dungeon (the random number generator is seeded the
    same every time by default, but you can change the RNG constructor as a way to experiment).
  - If you had the Android option checked in the setup and are using the SquidLib Basic template,
    you can try to run the Android module on an emulator or a connected Android device.
  - If you had the GWT option checked in the setup and are using the SquidLib Basic template,
    you can go through the lengthy, but simple, build for GWT, probably using the `superDev`
    task for the `gwt` module, or also possibly the `dist` task in that module. Using superDev
    defaults to printing a message about starting the code server at a specific URL on a specific
    port. This isn't enough info, because the code server doesn't really link to your running
    game. That would be, by default, at http://127.0.0.1:9090/index.html , and you may need to
    click the circular arrow button in the upper left to reload changes (which you can do after
    just saving your files in your editor, it doesn't need you to stop and re-run a task if you
    recompile using the button in the webpage).
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

