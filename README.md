# JXLSForm

[![Build Status](https://travis-ci.org/batkinson/jxlsform.svg?branch=master)](https://travis-ci.org/batkinson/jxlsform)
[![Coverage Status](https://coveralls.io/repos/batkinson/jxlsform/badge.svg?branch=master&service=github)](https://coveralls.io/github/batkinson/jxlsform?branch=master)

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