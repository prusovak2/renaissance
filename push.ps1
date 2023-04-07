param (
    [Parameter(position=0)]
    [string] $commitMsg = "Fix style & update readme",

    [string] $version = "0.15.0"
)

$ErrorActionPreference = "Stop"

Write-Output "Committing crimes, sorry, file changes with the message '${commitMsg}'`n"

& ./tools/sbt/bin/sbt renaissanceFormat
& ./tools/sbt/bin/sbt "-Dproject.version=${version}" renaissancePackage
java -jar target/renaissance-gpl-${version}.jar --readme

git add .
git commit -am ${commitMsg}
git tag -d v${version}
git tag v${version} -m "WIP"

git push --set-upstream origin $(git rev-parse --abbrev-ref HEAD)