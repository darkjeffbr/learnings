FROM httpd:2.4-bullseye
RUN apt update && \
  apt install build-essential cpanminus libssl-dev zlib1g-dev -y
RUN cpanm Email::Sender Email::Stuffer Try::Tiny 
RUN cpanm Net::SSLeay IO::Socket::SSL
RUN cpanm MIME::Base64 Authen::SASL
