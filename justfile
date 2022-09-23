gen:
    ./gradlew genSources

build:
    ./gradlew build
    ls build/libs/*.jar

versions:
    firefox "https://fabricmc.net/develop/"

# Reset mapping to dependencies, mapping in vscode
fix:
    ./gradlew --refresh-dependencies
    ./gradlew vscode
    echo "Reload build.gradle in vscode!!!"

# Deletes the loom cache (source files)
clean-loom:
    trash .gradle/loom-cache

test:
    ./gradlew runClient

clean:
    ./gradlew clean
