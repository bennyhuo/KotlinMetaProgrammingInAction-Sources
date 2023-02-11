# 1-12
cd $ws
git clone https://github.com/JetBrains/kotlin.git

# 1-13
git config core.longpaths true

# 1-14
kotlin.build.isObsoleteJdkOverrideEnabled=true

# 1-15
./gradlew dist
