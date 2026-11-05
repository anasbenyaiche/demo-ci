FROM jenkins/inbound-agent:jdk21

USER root

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
       docker-cli \
       ca-certificates \
    && docker --version \
    && rm -rf /var/lib/apt/lists/*

USER jenkins