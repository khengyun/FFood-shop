#!/bin/sh

# Define the order in which SQL files should be executed
ordered_sql_files=("ffood_create_table.sql" "ffood_insert_row.sql" "ffood_setup_trigger.sql")

for file in "${ordered_sql_files[@]}"; do
    sql_file="/db_scripts/$file"
    if [ -f "$sql_file" ]; then
        echo "Executing $file..."
        /opt/mssql-tools/bin/sqlcmd -S sql-server -U sa -P sa@123456 -i $sql_file
    else
        echo "SQL file $file not found."
    fi
done