#!/bin/sh

# Define the directory containing SQL files
sql_dir="/db_scripts"

# List SQL files in the desired execution order
sql_files="ffood_create_table.sql ffood_insert_row.sql ffood_setup_trigger.sql"

# Loop through and execute SQL files
for file in $sql_files; do
    sql_file="$sql_dir/$file"
    if [ -f "$sql_file" ]; then
        echo "Executing $file..."
        /opt/mssql-tools/bin/sqlcmd -S localhost -U sa -P sa@123456 -i "$sql_file"
    else
        echo "SQL file $file not found."
    fi
done
