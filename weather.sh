#!/bin/bash

exec 2>/dev/null

arg=$1

if [[ "$1" = "" ]]; then
	arg="conditions"
fi

cd src/main/groovy
icon=$(/Users/ovi/.gvm/groovy/current/bin/groovy Weather.groovy icon)
if [[ -r /Users/ovi/etc/geektool/Weather/Icon/current.png ]]; then
	rm /Users/ovi/etc/geektool/Weather/Icon/current.png
fi

if [[ ${icon} != "" ]]; then
	cp /Users/ovi/etc/geektool/Weather/Icon/${icon}.png /Users/ovi/etc/geektool/Weather/Icon/current.png
else
	cp /Users/ovi/etc/geektool/Weather/Icon/error.png /Users/ovi/etc/geektool/Weather/Icon/current.png
fi
/Users/ovi/.gvm/groovy/current/bin/groovy Weather.groovy $arg
