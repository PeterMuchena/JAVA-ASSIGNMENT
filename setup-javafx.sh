#!/bin/bash
# Downloads the JavaFX jars needed to compile and run the GUI.
mkdir -p lib/javafx
cd lib/javafx
for m in base graphics controls; do
  curl -fLO https://repo1.maven.org/maven2/org/openjfx/javafx-$m/25.0.1/javafx-$m-25.0.1-linux.jar
done
cd ../..
mkdir -p lib/h2
curl -fL -o lib/h2/h2-2.4.240.jar https://repo1.maven.org/maven2/com/h2database/h2/2.4.240/h2-2.4.240.jar
