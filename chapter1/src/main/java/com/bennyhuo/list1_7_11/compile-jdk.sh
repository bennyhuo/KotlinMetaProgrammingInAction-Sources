# 1-7
cd $ws
git clone https://github.com/openjdk/jdk.git

# 1-8
cd $ws/jdk
git checkout jdk-17+35

# 1-9
bash configure

# 1-10
make images

# 1-11
bash bin/idea.sh
