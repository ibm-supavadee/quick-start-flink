FROM flink:1.18.0-scala_2.12-java11

COPY /flink-conf.yaml /opt/flink/conf/flink-conf.yaml

CMD ["bash", "-c", "/opt/flink/bin/start-cluster.sh && tail -f /dev/null"]

