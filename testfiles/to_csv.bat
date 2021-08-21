#!/bin/bash
PATH=/bin:/usr/bin
for file in *.xlsx; do in2csv $file > "${file%.xlsx}.csv"; done