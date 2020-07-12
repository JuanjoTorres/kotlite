#!/bin/bash

as printInt.s -o printInt.o
as printString.s -o printString.o
as readInt.s -o readInt.o
as readString.s -o readString.o

as executable.s -o executable.o
ld executable.o printInt.o printString.o readInt.o readString.o -o executable

./executable