# JaCoCoReporter
A txt report generator for JaCoCo binaries

usage:
` java -jar JacocoReporter-version-jar bundleName projectDirectory reportStyle`
Branches roadmap:
* alfa-1 - First operational version. Still needs dealing with exceptions better
* alfa-2 - Tries to substitute alfa-1's O(n^4) loop with an n-ary tree with a variable n