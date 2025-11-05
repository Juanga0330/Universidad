@echo off
echo ===================================
echo Compilando clases principales...
echo ===================================
javac -d bin Src/Main/Java/Admision/*.java Src/Main/Java/Curso/*.java Src/Main/Java/Main.java

echo.
echo ===================================
echo Compilando pruebas...
echo ===================================
javac -cp "bin;lib/junit-platform-console-standalone-1.10.0.jar" -d bin Test/Admision/*.java Test/Curso/*.java

echo.
echo ===================================
echo Ejecutando pruebas...
echo ===================================
java -jar lib/junit-platform-console-standalone-1.10.0.jar --class-path bin --scan-class-path

pause