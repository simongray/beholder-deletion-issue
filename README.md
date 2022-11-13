# Beholder/directory-watcher deletion issue
This Clojure code demonstrates an issue with deleting files in [beholder](https://github.com/nextjournal/beholder) and the library it is wrapping, [directory-watcher](https://github.com/gmethvin/directory-watcher).

Run the [beholder-issue.jar](/beholder-issue.jar) file using

```shell
java -jar beholder-issue.jar
```

> I ran this with Java 19 on an Apple Silicon Macbook Air using macOS 13. The program depends on directory-watcher 0.17.0.

This will create `testdir/` along with two files, `before.txt` and `after.txt`, created before and after the watcher process is instantiated, respectively. Both files are then deleted.

* The state of the files created is printed after each significant change.
* The signals of the watcher are also printed as they arrive.
* I have inserted three 1 second pauses to make sure that the async nature of all this file-watching doesn't mess up the results.

On my machine the program outputs:

```
files:
   testdir
   testdir/before.txt
(starting watcher process..)
files:
   testdir
   testdir/before.txt
   testdir/after.txt
watcher: create /Users/simongray/testdir/after.txt
files:
   testdir
watcher: delete /Users/simongray/testdir/after.txt
```

... i.e. it only registers the deletion of the `after.txt` file, not the other file.
