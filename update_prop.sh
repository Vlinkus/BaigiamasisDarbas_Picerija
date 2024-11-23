#!/bin/bash

# Define the file path
APPLICATION_PROPERTIES="src/main/resources/application.properties"

# Check if the file exists
if [[ -f "$APPLICATION_PROPERTIES" ]]; then
    sed -i 's#maxim23#postgres#' "$APPLICATION_PROPERTIES"
    sed -i 's#jdbc:postgresql://localhost:5432#jdbc:postgresql://192.168.10.250:5432#' "$APPLICATION_PROPERTIES"
    cat "./$APPLICATION_PROPERTIES"

    # Confirm the change
    if grep -q 'password=postgres' "$APPLICATION_PROPERTIES"; then
        echo "Successfully updated pw in $APPLICATION_PROPERTIES."
    else
        echo "Failed to update the pw in $APPLICATION_PROPERTIES."
        exit 1
    fi

    if grep -q 'jdbc:postgresql://192.168.10.250:5432/pizzeria' "$APPLICATION_PROPERTIES"; then
          echo "Successfully updated db link in $APPLICATION_PROPERTIES."
      else
          echo "Failed to update the db link in $APPLICATION_PROPERTIES."
          exit 1
      fi
else
    echo "File $APPLICATION_PROPERTIES does not exist. Please check the file path."
    exit 1
fi