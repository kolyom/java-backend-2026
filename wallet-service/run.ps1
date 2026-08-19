# Запуск wallet-service без VS Code Run
Set-Location $PSScriptRoot

if (-not $env:JAVA_HOME) {
    $env:JAVA_HOME = "C:\Program Files\Eclipse Adoptium\jdk-21.0.3.9-hotspot"
}

Write-Host "Starting wallet-service via Maven Wrapper..."
Write-Host "First run may take 1-3 min (downloads Maven + dependencies)."
Write-Host ""

& .\mvnw.cmd spring-boot:run
if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "Failed. Try: .\mvnw.cmd -v"
    exit $LASTEXITCODE
}
