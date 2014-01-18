#!/bin/sh
. ./classpath.sh
java -cp bin:$MYCLASSPATH net.zhuoweizhang.similegen.SimileGen $@
