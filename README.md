spring-mail-example
===================

Simple example of sending mail with *Spring* and *Java Mail*.

# Overview

This project tries to provide an example for sending a `text/html` email using *Spring*.

One goal is to hide the internals of configuring *Java Mail* as much as possible from the user.

# Testing

An integration test can be run by activiating the `it` (integration testing) Maven profile:
```
$ mvn -Dit verify
```

The integration test, `EmailSenderIntegrationSpec`, will use the properties defined in `EmailSenderIntegrationSpec-context.xml` to send the emails.  Update this file first with your desired properties for the email session before running the test.
