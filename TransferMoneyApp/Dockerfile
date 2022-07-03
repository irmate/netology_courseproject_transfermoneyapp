FROM adoptopenjdk:11-jdk-hotspot
EXPOSE 5500
ADD target/TransferMoneyApp-0.0.1-SNAPSHOT.jar TransferMoneyApp.jar
ENTRYPOINT ["java", "-jar", "TransferMoneyApp.jar"]