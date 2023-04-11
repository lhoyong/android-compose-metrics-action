#! /bin/bash

MSG="Compose Metrics Summary\n";
MSG+="module,total count(composable),restartable,skippable\n";

array=();

while IFS= read -r -d $'\0';
do
  array+=("$REPLY");
  done < <(find . -name "$1" -type d | find . -name '*module.json' -print0)

for i in "${array[@]}";
do
  dir=$(echo $i | cut -d "/" -f2);

  TOTAL=$(cat $i | jq '.totalComposables');
  RESTARTABLE=$(cat $i | jq '.restartableComposables');
  SKIPPABLE=$(cat $i | jq '.skippableComposables');

  MSG+="$dir,$TOTAL,$RESTARTABLE,$SKIPPABLE\n";

done

echo -e $MSG | column -t -s ",";
