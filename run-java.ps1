# Run one task from project root
# Example: .\run-java.ps1 BinarySearch

param(
    [Parameter(Mandatory = $true)]
    [string]$ClassName
)

$ErrorActionPreference = "Stop"
$root = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $root

New-Item -ItemType Directory -Force -Path bin | Out-Null

$src = Get-ChildItem -Path "algorithms\solutions", "labs", "labs\oop" -Filter "$ClassName.java" -Recurse -ErrorAction SilentlyContinue |
    Select-Object -First 1

if (-not $src) {
    Write-Error "File not found: $ClassName.java"
}

Write-Host "Compiling $($src.FullName) ..."
javac -d bin $src.FullName
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

Write-Host "Running $ClassName ..."
java -cp bin $ClassName
