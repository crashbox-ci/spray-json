# Super Simple JSON for Scala, Scala JS and Scala Native

*Note: this is a repackaging of Magnolia and Spray. It is a temporary
project. We'd like to backport some changes made to the build to the
bundled projects.*

Combining [Magnolia](https://github.com/propensive/magnolia) and
[Spray](https://github.com/spray/spray-json) makes dealing with JSON
fun! Bundling them together in one library is a quick and dirty fix
for projects requiring dependency-free builds for targeting latest
versions of Scala and/or platforms.

This bundle features:

- Automatic derivation of JSON formats
- Cross-compilation for Scala, Scala JS and Scala Native
- Zero external compile dependencies (other than compiler modules)
- Zero test dependencies
- Minimal build for fast iteration

It also has some limitations:

- Spray's product formats have been removed. Automatic derivation
  (provided by DerivedFormats which is mixed into DefaultJsonProtocol)
  is required.
- Most unit tests from both Magnolia and Spray have been discarded,
  use at your own risk!

## Credits

### Magnolia
Magnolia is developed by Jon Pretty.

It is released under the Apache 2.0 license.

### spray-json
Most of type-class (de)serialization code is nothing but a polished
copy of what Debasish Ghosh made available with his SJSON
library. These code parts therefore bear his copyright. Additionally
the JSON AST model is heavily inspired by the one contributed by Jorge
Ortiz to Databinder-Dispatch. [^1]

[1]: Copied rom the project's website

spray-json is licensed under the Apache 2.0 license.

## Getting Started
This project is published to maven central as a library.
```scala
libraryDependencies += "io.crashbox" %%  "spray-json" % "2.0.0"
```

## Documentation
Check out [spray's documentation](https://github.com/spray/spray-json)
on the basics on how to use formats. All the while noting that product
formats have been replaced with automatic derivation as [described in
this project](https://github.com/drivergroup/spray-json-derivation).

## Building
