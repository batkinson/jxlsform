# JXLSForm

[![Build Status](https://travis-ci.org/batkinson/jxlsform.svg?branch=master)](https://travis-ci.org/batkinson/jxlsform)
[![Coverage Status](https://coveralls.io/repos/github/batkinson/jxlsform/badge.svg?branch=master)](https://coveralls.io/github/batkinson/jxlsform?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.batkinson/jxlsform/badge.svg?style=flat)](http://mvnrepository.com/artifact/com.github.batkinson/jxlsform)

A library for comprehending and converting XLSForm documents, written in 100% Java.

## Why?

After looking for decent code to work with XLSForm definitions in Java, I could not find
an acceptable solution. I was looking for something that had the following properties:

  * Usable in in Java (and other JDK languages)
  * Minimal external dependencies
  * Reasonable test coverage
  * A commercial-friendly (non-viral), permissive license
  * Available from a major online repository, such as Maven Central

I could not find what I was looking for so I decided to create it.

## Requirements

To build this library, you'll need:

  * JDK 8+
  * Apache Maven

## Building

To build the library, you just need to run:

```
mvn clean install
```