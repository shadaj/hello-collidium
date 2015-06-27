# hello-collidium

A repository containing branches that represent starting points for each of the videos in the "Hello Collidium!" YouTube series.

This repository is based on https://github.com/sjrd/scala-js-example-app

## Get started

To get started, open `sbt` in this example project, and execute the task
`fastOptJS`. This creates the file `target/scala-2.11/hello-collidium-fastopt.js`.
You can now open `index-fastopt.html` in your favorite Web browser!

During development, it is useful to use `~fastOptJS` in sbt, so that each
time you save a source file, a compilation of the project is triggered.
Hence only a refresh of your Web page is needed to see the effects of your
changes.

## The fully optimized version

For ultimate code size reduction, use `fullOptJS`. This will take several
seconds to execute, so typically you only use this for the final, production
version of your application. While `index-fastopt.html` refers to the
JavaScript emitted by `fastOptJS`, `index-fullopt.html` refers to the optimized
JavaScript emitted by `fullOptJS`.
