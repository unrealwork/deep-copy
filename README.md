# deep-copy

[![Java CI with Maven](https://github.com/unrealwork/deep-copy/actions/workflows/maven.yml/badge.svg)](https://github.com/unrealwork/deep-copy/actions/workflows/maven.yml)

Deep Copy implement deep copy of an object via reflection according
to [task](https://github.com/Ecwid/new-job/blob/master/Deep-clone.md).

## Usage

Library itself is placed in `deep-copy-core` project and java `com.ecwid.dev.deep.copy` module.

Main utility methods are contained in [`CopyUtils`](deep-copy-core/src/main/java/com/ecwid/dev/util/CopyUtils.java)

Due to security of modular system in the latest JDKs closed classes of modules should be opened to the library module.

It's possible that access to class of a copied object is restricted, warning message is shown and the original object will be returned.

```
WARNING:Unable to access package java.util of module java.base. 
Consider to add `--add-opens=java.base/java.util=com.ecwid.dev.deep.copy`
VM option to make class java.util.HashSet available for copying.
```

In that case the message contains hint, how to provide access to some class.

## Demo App

- Example of library usage shown in `deep-copy-demo` project.
- Demo uses `deep-copy-core` library as dependency.
- Main class of the app is [com.ecwid.dev.deep.copy.demo.Demo](deep-copy-demo/src/main/java/com/ecwid/dev/deep/copy/demo/Demo.java)

### Run

- Demo could be packaged as an executable jar `demo.jar` and run via CLI.
  ```bash
  mvn package
  java -jar deep-copy-demo/target/demo.jar
  ```

### Output of the app

```bash
Feb 02, 2023 11:27:22 AM com.ecwid.dev.deep.copy.demo.Demo main
INFO: Deep copy is being started for object : Man@4783da3f{age=20, name@378fd1ac='test', favoriteBooks@49097b5d=[Lord of the Rings]}
Feb 02, 2023 11:27:23 AM com.ecwid.dev.deep.copy.demo.Demo logObjectClone
INFO: Clone completed : java.util.HashMap.Node@515f550a-> java.util.HashMap.Node@379619aa
Feb 02, 2023 11:27:23 AM com.ecwid.dev.deep.copy.demo.Demo logObjectClone
INFO: Clone completed : java.util.HashMap.Node[]@1eb44e46-> java.util.HashMap.Node[]@cac736f
Feb 02, 2023 11:27:23 AM com.ecwid.dev.deep.copy.demo.Demo logObjectClone
INFO: Clone completed : java.util.HashMap@75a1cd57-> java.util.HashMap@123a439b
Feb 02, 2023 11:27:23 AM com.ecwid.dev.deep.copy.demo.Demo logObjectClone
INFO: Clone completed : java.util.HashSet@c2e1f26-> java.util.HashSet@7de26db8
Feb 02, 2023 11:27:23 AM com.ecwid.dev.deep.copy.demo.Demo logObjectClone
INFO: Clone completed : byte[]@1175e2db-> byte[]@36aa7bc2
Feb 02, 2023 11:27:23 AM com.ecwid.dev.deep.copy.demo.Demo logObjectClone
INFO: Clone completed : java.lang.String@378fd1ac-> java.lang.String@26f0a63f
Feb 02, 2023 11:27:23 AM com.ecwid.dev.deep.copy.demo.Demo logObjectClone
INFO: Clone completed : byte[]@4926097b-> byte[]@762efe5d
Feb 02, 2023 11:27:23 AM com.ecwid.dev.deep.copy.demo.Demo logObjectClone
INFO: Clone completed : java.lang.String@7637f22-> java.lang.String@5d22bbb7
Feb 02, 2023 11:27:23 AM com.ecwid.dev.deep.copy.demo.Demo logObjectClone
INFO: Clone completed : java.util.ImmutableCollections.List12@49097b5d-> java.util.ImmutableCollections.List12@3830f1c0
Feb 02, 2023 11:27:23 AM com.ecwid.dev.deep.copy.demo.Demo logObjectClone
INFO: Clone completed : com.ecwid.dev.deep.copy.demo.classes.Man@4783da3f-> com.ecwid.dev.deep.copy.demo.classes.Man@39ed3c8d
Feb 02, 2023 11:27:23 AM com.ecwid.dev.deep.copy.demo.Demo main
INFO: Deep copied object: Man@39ed3c8d{age=20, name@26f0a63f='test', favoriteBooks@3830f1c0=[Lord of the Rings]}
```
