magnolia-templating
===================

[Magnolia cms](http://www.magnolia-cms.com) templating kit based on [Foundation for Sites](http://foundation.zurb.com/)

Are we OK?

[![Build Status](https://travis-ci.org/gtenham/magnolia-templating.svg?branch=master)](https://travis-ci.org/gtenham/magnolia-templating)


## Prerequisites
* [git](http://git-scm.com/)
* [java 7](http://java.com)
* [Maven 3](http://maven.apache.org)

## Quickstart
```shell
git clone https://github.com/gtenham/magnolia-templating.git magnolia-templating
cd magnolia-templating
mvn clean install
```
This results in a war-file which can be deployed to any servlet container like Tomcat.

Rename war-file to "magnoliaAuthor" to have a Magnolia author instance using embedded Derby database.

For more information on running Magnolia look here:

[http://documentation.magnolia-cms.com/display/DOCS/Creating+a+custom+bundle][1]

[1]: http://documentation.magnolia-cms.com/display/DOCS/Creating+a+custom+bundle#Creatingacustombundle-Runtheproject

## Features
* Based on Foundation for Sites and Magnolia 5.4
* Site manager app
* Content tag manager app
* Page templates: Advanced page and Basic article page
* Basic component set eg. Section, Blockgrid, Header, Text, Link list etc
* Domain to Root page mapping
* Content search
* Remote Client side templates:
  * Handlebars pre-compiled javascript
  * Freemarker pre-processed html

##License
Copyright (c) 2015 Gerton ten Ham and contributors. Released under a [GNUv3 license](https://github.com/gtenham/magnolia-templating/blob/master/LICENSE).

[![Bitdeli Badge](https://d2weczhvl823v0.cloudfront.net/gtenham/magnolia-templating/trend.png)](https://bitdeli.com/free "Bitdeli Badge")

[![Join the chat at https://gitter.im/gtenham/magnolia-templating](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/gtenham/magnolia-templating?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)