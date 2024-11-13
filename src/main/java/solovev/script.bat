@echo off
REM Check if arguments are provided
if "%1"=="" (
    echo No arguments provided.
    exit /b 1
)

REM Loop through each argument
echo List of provided arguments:
setlocal enabledelayedexpansion
set counter=1
for %%A in (%*) do (
    echo Argument !counter!: %%A
    set /a counter+=1
)

REM End script
endlocal
echo Script execution finished.