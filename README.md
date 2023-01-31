# deep-copy

[![Java CI with Maven](https://github.com/unrealwork/deep-copy/actions/workflows/maven.yml/badge.svg)](https://github.com/unrealwork/deep-copy/actions/workflows/maven.yml)

Deep Copy implement deep copy of an object via reflection according to [task](https://github.com/Ecwid/new-job/blob/master/Deep-clone.md).
## Usage

`CopyUtils` class is provided in `com.ecwid.dev.deep.copy` module.

Due to security of modular system in the latest JDKs closed classes of modules should be opened to the library module.

It could be done via `--add-opens=<module>/<package>` option of java executable, for example:

```bash
java --add-opens=java.base/java.lang=com.ecwid.dev.deep.copy \
--add-opens=java.base/java.util=com.ecwid.dev.deep.copy \
-p target/classes \
-m com.ecwid.dev.deep.copy/com.ecwid.dev.examples.Demo
```

Example of usage is shown in [`com.ecwid.dev.examples.Demo`](https://github.com/unrealwork/deep-copy/blob/master/src/main/java/com/ecwid/dev/examples/Demo.java) class.


### App

```java
INFO: Deep copy is being started for object : Man@573f2bb1{age=20, name@5ae9a829='test', favoriteBooks@6d8a00e3=[Lord of the Rings]}
Jan 31, 2023 8:20:35 PM com.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : java.util.HashMap.Node@4493d195-> java.util.HashMap.Node@4232c52b
Jan 31, 2023 8:20:35 PM com.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : java.util.HashMap.Node[]@704a52ec-> java.util.HashMap.Node[]@69a3d1d
Jan 31, 2023 8:20:35 PM com.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : java.util.HashMap@76f2b07d-> java.util.HashMap@2a556333
Jan 31, 2023 8:20:35 PM com.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : java.util.HashSet@536aaa8d-> java.util.HashSet@7d70d1b1
Jan 31, 2023 8:20:35 PM com.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : byte[]@2a742aa2-> byte[]@3cb1ffe6
Jan 31, 2023 8:20:35 PM com.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : java.lang.String@5ae9a829-> java.lang.String@467aecef
Jan 31, 2023 8:20:35 PM com.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : byte[]@7e2d773b-> byte[]@2173f6d9
Jan 31, 2023 8:20:35 PM com.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : java.lang.String@4d50efb8-> java.lang.String@307f6b8c
Jan 31, 2023 8:20:35 PM com.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : java.util.ImmutableCollections.List12@6d8a00e3-> java.util.ImmutableCollections.List12@7a187f14
Jan 31, 2023 8:20:35 PM com.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : com.ecwid.dev.examples.classes.Man@573f2bb1-> com.ecwid.dev.examples.classes.Man@6f195bc3
Jan 31, 2023 8:20:35 PM com.ecwid.dev.examples.Demo main
INFO: Deep copied object: Man@6f195bc3{age=20, name@467aecef='test', favoriteBooks@7a187f14=[Lord of the Rings]}
```

### Run
Demo could be executed via maven

```bash
mvn compile exec:exec
```

Or via java executable

```bash
java --add-opens=java.base/java.lang=com.ecwid.dev.deep.copy --add-opens=java.base/java.util=com.ecwid.dev.deep.copy -p target/classes -m com.ecwid.dev.deep.copy/com.ecwid.dev.examples.Demo
```



