#!/bin/bash

echo "> Cleaning up Docker system..."
docker system prune -af --volumes
echo "> Docker cleanup completed"