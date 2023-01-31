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
Jan 28, 2023 3:58:44 AM org.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : java.util.HashMap.Node@6ab55cbc-> java.util.HashMap.Node@2d4c8c11
Jan 28, 2023 3:58:44 AM org.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : java.util.HashMap.Node[]@52b5b1fc-> java.util.HashMap.Node[]@2a36f2ee
Jan 28, 2023 3:58:44 AM org.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : java.util.HashMap@6435bb7d-> java.util.HashMap@528712e1
Jan 28, 2023 3:58:44 AM org.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : java.util.HashSet@51140842-> java.util.HashSet@557dc8eb
Jan 28, 2023 3:58:44 AM org.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : byte[]@48c1e128-> byte[]@761796fb
Jan 28, 2023 3:58:44 AM org.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : java.lang.String@37fb7e05-> java.lang.String@60634640
Jan 28, 2023 3:58:44 AM org.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : byte[]@5a82c8f8-> byte[]@1c716d07
Jan 28, 2023 3:58:44 AM org.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : java.lang.String@2a09b4f9-> java.lang.String@66da698f
Jan 28, 2023 3:58:44 AM org.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : java.util.ImmutableCollections.List12@12c24604-> java.util.ImmutableCollections.List12@113b7d1
Jan 28, 2023 3:58:44 AM org.ecwid.dev.examples.Demo logObjectClone
INFO: Clone completed : org.ecwid.dev.examples.classes.Man@55c5fe5-> org.ecwid.dev.examples.classes.Man@4d9a7a03
Jan 28, 2023 3:58:44 AM org.ecwid.dev.examples.Demo main
INFO: Deep copied object: Man@4d9a7a03{age=20, name@60634640='test', favoriteBooks@113b7d1=[Lord of the Rings]}
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



