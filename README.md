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
    - You probably want the latest possible code using the [3.0.0-JITPACK Release](https://github.com/tommyettinger/SquidSetup/releases/tag/v3.0.0-JITPACK),
      but you may want a more-stable beta version. The latest version will get a commit compiled by JitPack.io, while the beta and stable versions
      will be obtained from Maven Central. The latest commit is determined by the library `jcabi-github`, and involves an API call to GitHub when using
      the snapshot release (not any beta or stable releases); the API call may be a little slow or might not complete if GitHub is having issues. However,
      because the code at that commit is the same at any point in the future, the snapshot commits won't "suddenly stop working" like a dependency
      on a nightly build might; you update when you want to, and if something breaks, you can always go back to an older commit that you know works.
    - There is also a [3.0.0-SNAPSHOT Release](https://github.com/tommyettinger/SquidSetup/releases/tag/v3.0.0-SNAPSHOT); it uses an older Gradle version and
      is available in case something is failing in the project layout produced by 3.0.0-JITPACK.
  - Regardless of what platforms you intend to target, make sure the steps
    [described by the LibGDX wiki here](https://github.com/libgdx/libgdx/wiki/Setting-up-your-Development-Environment-%28Eclipse%2C-Intellij-IDEA%2C-NetBeans%29)
    are taken care of.
  - Run the JAR. Plug in whatever options you see fit:
    - For the Platforms tab, you can technically use the "Toggle Client Platforms" button to enable LWJGL3 (which works
      on all desktop/laptop platforms), Android, iOS (which will only build on a MacOS machine), and HTML. This
      isn't always a good idea because downloading iOS and HTML dependencies can take some time, so just check the
      platforms you want to target. You can re-run the setup, make a new project with the same settings (in a different
      folder), and copy in the existing code if you want to quickly add a platform or platforms.
      - Desktop and/or LWJGL3 should usually be checked, so you can test on the same computer
        you develop on.
        - LWJGL3 is almost the same as Desktop, but because it has better support for new hardware
          (such as high-DPI displays), it should probably be preferred. It also allows multiple windows and drag+drop.
      - iOS should probably not be checked if you aren't running MacOS and don't intend to later build an iOS
        app on a Mac. It needs some large dependencies to be downloaded when you first import the project.
      - Android should only be checked if you've set up your computer for Android development,
        and may cause some hassles even in other projects if you use Android Studio or IntelliJ
        IDEA. Personally, I avoid creating Android modules as part of a larger project unless they only target
        Android, since if there's only a core and an android module, nothing will interfere. You should
        absolutely make sure that IDEA has "configure on demand" disabled; under settings you can search for
        "demand" to find it. Android Studio and IDEA should always have it disabled now by default.
      - HTML is a more-involved target, with some perfectly-normal code on all other platforms acting completely
        different on HTML due to the tool used, Google Web Toolkit (GWT). It's almost always possible to work around
        these differences and make things like random seeds act the same on all platforms, but it takes work. Mostly,
        you need to be careful with the `long` and `int` number types, and relates to `int` not overflowing as it
        would on desktop, and `long` not being visible to reflection. See [this small guide to GWT](GWT.md) for more.
        - SquidSetup uses GWT 2.9.0 via [a custom backend](https://github.com/tommyettinger/gdx-backends), which enables
          using Java 11 features, such as `var`, but not JDK 11 library code. The backend is closely related to the
          libGDX version used, but SquidLib currently is only tested with libGDX 1.9.10.
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

  - The project will have a lot of font resources in the `assets/` folder. You should probably
    delete or move any you don't need. `Iosevka-Slab-msdf.fnt` and `Iosevka-Slab-msdf.png` are
    needed to run the generated basic demo, and if you intend to keep using them you should
    also include `Iosevka-License.md` to abide by Iosevka's license. You don't need any other
    files unless you intend to use a different font, such as `Iosevka-Family-msdf` to get bold
    and italic support, `NotoSerif-Family-msdf` to have a nice variable-width font that also
    has bold and italic support, `SourceHanCodeJP-Regular-distance` to get excellent support
    for Japanese text, or `Monty-4x10` if you want your users to go blind staring at one of the
    smallest fonts possible. The class `DefaultResources` has documentation on what exact files
    are needed to use which fonts and other assets, such as icons.
  - If you use Android Studio or IntelliJ IDEA and have an Android project, you'll need to use
    Gradle tasks to do any part of the build/run process, thanks to a long-standing issue in
    IDEA's Android plugin.  The simplest way to do this is in the IDE itself, via
    `View -> Tool Windows -> Gradle`, and selecting tasks to perform, such as
    `Desktop -> Tasks -> application -> run.` If you try to run a specific class' `main()`
    method, you may encounter strange issues, but this shouldn't happen with Gradle tasks.
    This also shouldn't happen if you avoid creating an Android module in the first place
    (if you didn't check Android in the setup, this problem shouldn't apply).
  - If you had the LWJGL3 (or Desktop) and SquidLib options checked in the setup, you can try to run the
    LWJGL3 or Desktop module right away, with a simple pathfinding/FOV/text-gen demo that responds to
    mouse and keyboard input in a random dungeon (the random number generator is seeded the
    same every time by default, but you can change the RNG constructor as a way to experiment).
  - If you had the Android option checked in the setup and are using the SquidLib Basic template,
    you can try to run the Android module on an emulator or a connected Android device.
  - If you had the GWT option checked in the setup and are using the SquidLib Basic template,
    you can go through the lengthy, but simple, build for GWT, probably using the `superDev`
    task for the `gwt` module, or also possibly the `dist` task in that module. 
  - If you had the iOS and SquidLib options checked in the setup, you're running Mac OS X,
    you have jumped through Apple's now-infamous process described at the LibGDX wiki at the
    earlier link, you've made the blood sacrifice to your iAltar, and the Black Turtleneck
    of Cosmic Power and Ultimate Build Quality has manifested before you and informed you of
    your truename, you can attempt to run the iOS module. If it works, you'll have the
    record of being the first person to run SquidLib on an iOS device... In case you can't
    tell, I am not terribly confident in the ability of this tool to generate iOS projects
    that work on the first try, though it may be easy enough to modify things in the likely
    case they don't immediately work.
  - All builds currently use Gradle 6.5.1 with the "api/implementation/compile fiasco" resolved.
    Java 13 and 14 work with SquidSetup because Gradle 6.5.1 supports Java from 8 to 14.
    Adding dependencies will use the `api` keyword instead of the `compile` keyword it used
    in earlier versions. All modules use the `java-library` plugin, which enables the `api` keyword
    for dependencies.
  - You may need to refresh the Gradle project after the initial import if some dependencies timed-out;
    JitPack takes a minute or two to build SquidLib, and it usually doesn't take long before the SquidLib
    dependencies can be downloaded in full. In IntelliJ IDEA, the `Reimport all Gradle projects` button is
    a pair of circling arrows in the Gradle tool window, which can be opened with `View -> Tool Windows -> Gradle`.
  - Out of an abundance of caution, [the dependency impersonation issue reported here by Márton
    Braun](https://blog.autsoft.hu/a-confusing-dependency/) is handled the way he handled it, by putting
    `jcenter()` last in the repositories lists. I don't know if any other tools have done the same, but it's
    an easy fix and I encourage them to do so.
    
Good luck, and I hope you make something great!

