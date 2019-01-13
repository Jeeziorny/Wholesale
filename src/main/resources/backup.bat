SET name=%1
IF NOT DEFINED name SET name=tron.sql
SET file=C:\Users\Lenovo\Desktop\BD\%name%
start "C:\Program Files\MySQL\MySQL Workbench 8.0 CE" mysqldump -uroot -p1234 wholesale --result-file=%file%
