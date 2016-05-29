# JEA6-KwetterGO readme
## Description
This project is an extension of the JEA6 Kwetter implementation. This project implements sending a message through the Java Messaging System (JMS) to a specified queue from a web page.

## Some quick setup tasks
### Glassfish
This project requires Glassfish 4.0 or higher and requires a JMS queue to be defined.
Create a new JMS Destination resource under 'JMS Resources' named `jms/MyQueue` of type `javax.jms.Queue`.
