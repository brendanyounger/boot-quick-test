# boot-quick-test

[](dependency)
```clojure
[brendanyounger/boot-quick-test "0.1.0"] ;; latest release
```
[](/dependency)

## How to use it

Essentially, this is a copy of Adzerk's [boot-test](https://github.com/adzerk-oss/boot-test) which runs tests directly rather than in a pod.

The advantage is that `boot watch quick-test` runs much faster than `boot watch test`.  The disadvantage is that you will likely run into namespace collisions when trying to run `(boot (quick-test))` from the repl due to the `boot.user` namespace being loaded.  Stick with `boot watch quick-test`.

## Differences from boot-test

The `quick-test` task will merge the `:test-paths` environment variable into `:source-paths`.  This allows you to keep your test code out of any built JARs by default.

Currently, `quick-test` does not support specifying or filtering namespaces to test, though it may in the future.

## Speed

For a speed comparison, you can run tests on this repository both ways:

`boot watch quick-test`

or

`boot -d adzerk/boot-test -d boot/core -s "test" watch test`

We trust you'll find that the first way is faster.  The difference is even more pronounced on a project with many dependencies.

## A recommendation

My preferred workflow is to keep a terminal open with `boot watch notify quick-test` while I work on writing unit tests.  If you haven't tried the excellent [boot-notify](https://github.com/jeluard/boot-notify) task, you should.

## License

Copyright 2015 Brendan Younger

Distributed under the Eclipse Public License, the same as Clojure.
