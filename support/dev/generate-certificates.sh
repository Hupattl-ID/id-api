#!/bin/sh

read -p "Delete previous files, press [Ctrl] + C to abort or any key to to continue" ign
rm ./privatekey.pem
rm ./publickey.pem
rm ./private.pcks8

echo "Generate RSA private key"
openssl genrsa -out ./privatekey.pem 4096
openssl rsa -in ./private.pem -pubout -out publickey.pem
openssl pkcs8 -topk8 -nocrypt -in ./privatekey.pem -out ./private.pcks8
