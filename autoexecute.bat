@echo off

echo Current directory: %cd%

cd .\infrastructure\internal\src\main\resources

if exist static (
    rmdir /s /q static
)

if not exist static (
    mkdir static
)

move /y assets .\static\assets