FROM openjdk:20-jdk-slim

WORKDIR /CUKEFW

RUN apt-get update && \
    apt-get install -y maven wget gnupg curl unzip && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

RUN wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | apt-key add - && \
    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google-chrome.list

RUN echo "deb http://deb.debian.org/debian/ buster main" >> /etc/apt/sources.list.d/debian.list

RUN apt-get update && \
    apt-get install -y google-chrome-stable firefox-esr

RUN google-chrome --version && \
    firefox --version

COPY . .

RUN mvn dependency:go-offline

CMD ["./run_tests_linux.sh"]
