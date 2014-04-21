#! /bin/bash

cd KeyPressService/KeyPressService/bin
java KeyPressService &
cd ../../..

sleep 3

cd chat
npm start
