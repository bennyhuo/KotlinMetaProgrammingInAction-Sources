# 1-19
test -d ~/bin || mkdir ~/bin
curl https://storage.googleapis.com/git-repo-downloads/repo \
  > ~/bin/repo && chmod 700 ~/bin/repo

# 1-20
export PATH=~/bin:$PATH

# 1-21
function repo() {
  command python3 ~/bin/repo $@
}

# 1-22
cd $ws
mkdir androidx-main && cd androidx-main
# 初始化仓库
repo init -u https://android.googlesource.com/platform/manifest \
    -b androidx-main --partial-clone --clone-filter=blob:limit=10M
# 下载源码
repo sync -c -j8

# 1-23
git config --global merge.renameLimit 999999
git config --global diff.renameLimit 999999

# 1-24
cd $ws/androidx-main/frameworks/support
ANDROIDX_PROJECTS=COMPOSE ./gradlew studio
