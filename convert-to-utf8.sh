#!/bin/bash

# 转换所有ASCII编码的配置文件为UTF-8编码
echo "开始转换配置文件编码为UTF-8..."

# 转换yml和yaml文件
find . -name "*.yml" -o -name "*.yaml" | grep -v target | while read file; do
    encoding=$(file "$file" | cut -d: -f2 | cut -d' ' -f1)
    if [ "$encoding" = "ASCII" ]; then
        echo "转换文件: $file"
        iconv -f ASCII -t UTF-8 "$file" > "$file.utf8" && mv "$file.utf8" "$file"
    fi
done

# 转换properties文件
find . -name "*.properties" | grep -v target | grep -v ".mvn" | while read file; do
    encoding=$(file "$file" | cut -d: -f2 | cut -d' ' -f1)
    if [ "$encoding" = "ASCII" ]; then
        echo "转换文件: $file"
        iconv -f ASCII -t UTF-8 "$file" > "$file.utf8" && mv "$file.utf8" "$file"
    fi
done

echo "编码转换完成!"