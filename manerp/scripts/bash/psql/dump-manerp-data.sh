#!/bin/bash

# TODO: change destination path in production
/usr/bin/pg_dump --host manerp --port 5432 --username "manerp" --no-password  --format plain --section pre-data --section data --section post-data --inserts --column-inserts --verbose --file "/home/instantcrush/senior/manerp-client-server/manerp/sql/data/create-manerp-data.sql" "manerp"