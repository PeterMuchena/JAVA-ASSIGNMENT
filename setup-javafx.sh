#!/bin/bash
# Downloads the JavaFX jars needed to compile and run the GUI.
mkdir -p lib/javafx
cd lib/javafx
for m in base graphics controls; do
  curl -fLO https://repo1.maven.org/maven2/org/openjfx/javafx-$m/25.0.1/javafx-$m-25.0.1-linux.jar
done
