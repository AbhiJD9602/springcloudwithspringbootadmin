#!/usr/bin/env bash

set -eo pipefail

modules=( authenticationserver bookservice configurationserver discoveryserver gateway ratingservice springbootadminserver hystrix )

for module in "${modules[@]}"; do
    docker build -t "springcloudwithspringbootadmin/${module}:latest" ${module}
done