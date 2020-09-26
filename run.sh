#!/usr/bin/env bash

SERVER_URL=maltebp.dk
SERVER_USER=onlinepong_user
SERVER_KEY=$SSH_KEY
TARGET_DIR=./OnlinePong
PROGRAM="target/OnlinePong-1.0.jar src/main/webapp"

mvn clean package &&\
ssh -i "$SERVER_KEY" "$SERVER_USER"@"$SERVER_URL" "mkdir -p '$TARGET_DIR'" &&\
scp -r -i "$SERVER_KEY" Dockerfile startContainer.sh $PROGRAM "$SERVER_USER"@"$SERVER_URL":"$TARGET_DIR" &&\
ssh -i "$SERVER_KEY" "$SERVER_USER"@"$SERVER_URL" "cd '$TARGET_DIR' &&  ./startContainer.sh"