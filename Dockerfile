FROM ubuntu

RUN apt-get update && apt-get install -y openjdk-21-jdk maven && rm -rv /var/lib/apt/lists/*

WORKDIR /app

COPY pom.xml .

COPY src ./src

CMD ["mvn", "clean", "test"]