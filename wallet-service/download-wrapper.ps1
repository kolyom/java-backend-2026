$dir = Join-Path $PSScriptRoot ".mvn\wrapper"
New-Item -ItemType Directory -Force -Path $dir | Out-Null
$url = "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar"
$out = Join-Path $dir "maven-wrapper.jar"
Write-Host "Downloading $url"
Invoke-WebRequest -Uri $url -OutFile $out
Write-Host "Saved to $out"
