# BrucieLib

## How to build and execute

I guess the quickest method:
```
gradlew desktop:run
```

## How to hack

See desktop/src/com/rixonsoft/brucielib/desktop/DesktopLauncher for the entry point.

In the brucielib package, examine the following files:

  * BrucieGame
  * BrucieConfig
  * test/GameSelector
  * test/TestScene
  * test/TestBundle
  * AssetBag

## Contributors:

Please add:
```
    private static final String TAG = "<YOURCLASSNAME>";
```
to all classes with nontrivial code.

Thereafter, use:
```
    Gdx.app.log(TAG,"log message");
```
to log messages to the application log.

