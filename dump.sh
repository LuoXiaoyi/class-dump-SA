echo "attach pid: $1"
sudo java -cp $JAVA_HOME/lib/sa-jdi.jar:. -Dsun.jvm.hotspot.tools.jcore.filter=MyFilter sun.jvm.hotspot.tools.jcore.ClassDump $1

