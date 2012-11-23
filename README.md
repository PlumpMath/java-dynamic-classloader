java-dynamic-classloader
========================

This project show an example of dynamic classloading in java: it aim to replace classes when jars are replaced.
In the example it need of a descriptor file (see dyna-cl-module1/sources/def) that contain information about the jar:
it's a properties file with three fields:
 - modulename: name of the jar
 - version: version number (it isn't parsed, just compared)
 - entrypoint: class that implements dyna.cl.EntryPoint and it's been executed when the program is launched or when the jar is replaced
