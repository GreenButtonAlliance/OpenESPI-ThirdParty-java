#!/bin/sh

mvn -Dcucumber.options="--tags ~@oauth" clean verify -P ci
