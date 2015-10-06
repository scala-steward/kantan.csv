---
layout: default
---

[![Build Status](https://travis-ci.org/nrinaudo/tabulate.svg?branch=v0.1.4)](https://travis-ci.org/nrinaudo/tabulate)
[![codecov.io](http://codecov.io/github/nrinaudo/tabulate/coverage.svg?branch=v0.1.4)](http://codecov.io/github/nrinaudo/tabulate?branch=v0.1.4)

Tabulate is a library for CSV parsing written in the [Scala programming language](http://www.scala-lang.org).

## Motivation

CSV is an unreasonably popular data exchange format. It suffers from poor (or at the very least late) standardisation,
and is often a nightmare to work with when it contains more complex data than just lists of numerical values.

I started writing Tabulate when I realised I was spending more time dealing with the data _container_ than the
data itself. The goal of {{ site.name }} is to abstract CSV away as much as possible and allow developers to describe
their data, where it comes from and then just work with it.

Tabulate is meant to be [RFC](https://tools.ietf.org/html/rfc4180) compliant, but flexible enough that it should
parse any sane variation on the format. Should you find CSV files that don't parse, please file an issue and I'll look
into it.

While I'm pretty happy with Tabulate, or at least the direction it's headed, I do not pretend that it'll fit
all use cases. It fits mine, but might not work for everyone. I'm happy to hear suggestions on how this can be
addressed, though.
 

## Tutorials

The following tutorials are available:
{% for x in site.tut %}
* [{{ x.title }}]({{ site.baseurl }}{{ x.url }})
{% endfor %}