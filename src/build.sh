#!/bin/bash

echo "Ejecutando JFlex"

if java -jar lib/jflex-full-1.7.0.jar compiler/lexic/Scanner.flex; then
  echo "JFlex ejecutado correctamente."

  cd compiler/sintax

  echo "Ejecutando Java CUP"

  if java -jar ../../lib/java-cup-11b.jar Parser.cup; then
    echo "CUP ejecutado correctamente"
  else
    echo "Error de CUP"
  fi
else
  echo "Error de JFlex"
fi
