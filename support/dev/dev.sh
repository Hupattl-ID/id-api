#!/bin/sh

cd ../..

export ID_PUBLIC_KEY=src/test/resources/public.key
export ID_PRIVATE_KEY=src/test/resources/private.key
mvn compile quarkus:dev
