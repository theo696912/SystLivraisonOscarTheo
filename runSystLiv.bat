@echo off
:: 1. Configuration du PATH pour trouver javac et java
set PATH=C:\Users\oscar\.jdks\openjdk-25.0.2\bin;%PATH%

:: 2. On se place à la racine du projet (là où est ton dossier src)
cd /d "C:\Users\oscar\IdeaProjects\SystLivraisonOscarTheo"

echo Compilation en cours...
:: 3. Compilation : on prend tout dans src et on envoie dans out/production
:: On crée le dossier s'il n'existe pas
if not exist "out\production\SystLivraisonOscarTheo" mkdir "out\production\SystLivraisonOscarTheo"

javac -d "out\production\SystLivraisonOscarTheo" src\*.java

:: On vérifie si la compilation a réussi
if %errorlevel% neq 0 (
    echo.
    echo ERREUR DE COMPILATION ! Verifie ton code.
    pause
    exit /b
)

echo Lancement de l'application...
echo.

:: 4. On se déplace pour lancer le Main
cd "out\production\SystLivraisonOscarTheo"
start java Main

pause