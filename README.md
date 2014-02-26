# hello-thrift - an example Maven project using Thrift

# EXAMPLE

```
$ vagrant up
$ vagrant ssh

vagrant$ mvn test
vagrant$ tree src/
vagrant$ tree target/generated-sources/
```

# REQUIREMENTS

* [Vagrant](http://www.vagrantup.com/)
* [VirtualBox](https://www.virtualbox.org/)

# DEVELOPMENT

## Linting

Keep the code tidy:

```
vagrant$ mvn compile
vagrant$ mvn test-compile
vagrant$ mvn checkstyle:checkstyle
vagrant$ mvn pmd:check
```
