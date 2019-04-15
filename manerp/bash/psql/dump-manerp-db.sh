#!/bin/bash

# TODO: change destination path in production
/usr/bin/pg_dump --host manerp --port 5432 --username "manerp" --no-password  --format plain --create --clean --section pre-data --verbose --file "/home/instantcrush/senior/manerp-client-server/manerp/sql/create/create-manerp-db.sql" "manerp"


