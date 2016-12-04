# hello-thrift - an example Gradle project using Thrift

# HOMEPAGE

https://github.com/mcandre/hello-thrift

# INTRODUCTION

[Thrift](https://thrift.apache.org/) is a framework for building machine-to-machine services. Just as Ruby on Rails offers tools for building RESTful Web services, Thrift offers tools for low-level network services. Where Web services use text-based formats like JSON and XML, network services may opt for Thrift instead, for added robustness and efficiency.

Thrift provides several standard tools out of the box, saving developers from reinventing the wheel:

* `thrift`, a language-agnostic format and compiler for defining common data structures and service interfaces.
* Code generators for Getters, Setters, and Unsetters for Required, Optional, and Default data fields.
* Serializers and deserializers, for transporting Thrift data as byte arrays or JSON strings.

This project is a small demonstration of what Thrift has to offer. We will use a Maven Java project, although Thrift supports many more programming languages.

## Mad Men

In this scenario, we're developing a digital platform for selling headphones to command line users connected to ARPANET in the late '60s, a market often overlooked.

```
vagrant$ gradle shadowJar
...

vagrant$ bin/ad
This week only, buy Bose's elite headphones 50% off, at 125.0!
Hurry before we're sold out, we only have 10 left!
Get them now at http://www.bose.com/controller?url=/shop_online/headphones/wireless_headphones/ae2w_headphones/index.jsp
Customers remark, "Works on my machine." !
```

Check out the source if you like:

```
vagrant$ tree bin/
vagrant$ cat bin/ad
...

vagrant$ tree src/main/java/
...
vagrant$ cat src/main/java/us/yellosoft/hello/HeadphonesAdvertiser.java
...
```

# REQUIREMENTS

* [Vagrant](http://www.vagrantup.com/)
* [VirtualBox](https://www.virtualbox.org/)

## Optional

* [Sonar](http://www.sonarqube.org/)
* [Infer](http://fbinfer.com/)

# EXAMPLE

## Setup development environment

Once Vagrant and VirtualBox are installed, use Vagrant to setup a virtual machine (vm).

```
$ vagrant up
...

$ vagrant ssh

vagrant$
```

For the curious, you can examine `manifests/default.pp` to see the dependency packages that Vagrant installed in the vm.

```
vagrant$ cat manifests/default.pp
...
```

Feel free to use your host computer instead of Vagrant, installing the dependency packages manually, treating `vagrant$` as your computer's terminal prompt. Note, however, that the dependency package names may vary between package mangers. E.g. [Apt](https://wiki.debian.org/Apt) `thrift-compiler` = [Homebrew](http://brew.sh/) `thrift` = [Chocolatey](http://chocolatey.org/) `thrift`.

## Thrift project organization

We start by looking at the overall project organization.

```
vagrant$ tree src/
...
```

`src/main/thrift/` contains `.thrift` definition files, defining the fields of our network data. Although this project is in Java, we could copy the `.thrift` files into a project in a different language, like Ruby, and they would behave the same way.

Now that we have `.thrift` definitions for our data, we can convert them into `.java` code. A directory is created, with `.thrift` -> `.java` generated code in `build/generated-sources/`.

```
vagrant$ gradle classes
...

vagrant$ tree build/generated-sources/
build/generated-sources/
└── thrift
    └── gen-java
        └── us
            └── yellosoft
                └── hello
                    └── thrift
                        └── Headphones.java

6 directories, 1 file
```

We could have manually run `thrift --gen ...` to do this, though Gradle wouldn't know where to look for the generated `.java` code when it comes time to compile.

## API docs

Now that we've got fully realized Thrift structures in Java, how do we interact with them? We could generate Javadocs for the Thrift structures:

```
vagrant$ gradle javadoc
...
$ open build/docs/javadoc/index.html
...
```

## Unit tests

Or look at some example usage snippets in our main Java sources and JUnit tests:

```
vagrant$ gradle test
...

vagrant$ tree src/test/java/
...
vagrant$ cat src/test/java/us/yellosoft/thrift/HeadphonesTest.java
...
```

In particular, `HeadphonesTest.java` defines several test cases for using Thrift objects:

* `constructorTest()` - How do Thrift initialize data, and set/get/unset their attributes?
* `defaultsTest()` - How can Thrift initialize data concisely, with default values?
* `byteSerializationTest()` - How can Thrift convert between Thrift objects and byte arrays, and vice-versa?
* `jsonSerializationTest()` - How can Thrift convert between Thrift objects and JSON strings, and vice-versa?
* `optionalsTest()` - How does Thrift treat Required, Optional, and Default fields?

These interactions demonstrate how Thrift can define a high level model, interact with code in a programming language, and wrap and unwrap for network transport. When you get Thrift objects under your belt, you can try defining Thrift *services*, using the data for input/output.

As a bonus, we wrote some plugin configuration to show how linting and code coverage work with Thrift-generated Java code.

## Linting

The Java compiler `javac` offers a valuable option to check for additional warnings at compile time with `-Xlint:all`. This Maven project is configured to do this automatically.

We could manually lint each `.java` file with `javac -Xlint:all`, but for medium to large projects, it's better to use Maven to do this over all Java files:


```
vagrant$ gradle check
...
```

This also runs additional checks for scope and style, using Checkstyle and PMD.

We've configured the Gradle plugins to skip linting the Thrift-generated `.java` code, as we're not really in control of it, and the extra warnings could make it harder to see warnings in our normal `.java` code. Feel free to comment out some of the Maven configuration in `build.gradle` to see the warnings.

### Optional: FindBugs

```
$ gradle check
$ open build/reports/findbugs/main.html
```

### Optional: Sonar

```
$ sonar start
$ gradle check sonar
$ open http://localhost:9000/
```

## Optional: Infer

```
$ infer -- gradle clean build
```


## Coverage

Similarly, we've configured the Jacoco Gradle plugin to ignore code coverage for Thrift-generated `.java` code. We don't expect to execute some sections of low-level code, mostly calling setters and getters.

```
vagrant$ gradle test jacoco
...

$ open build/reports/jacoco/test/html/index.html
...
```

This code coverages only our handwritten `.java` code, `HeadphonesAdvertiser.java`. Take a look at `us.yellosoft.hello.HeadphonesAdvertiser` and see if you can spot the coverage mistake.

Hint: [Cucumber](https://github.com/cucumber) would be better for this sort of thing.)

## Git Hooks

See `hooks/`.
