#!/bin/bash
# PostToolUse hook: after Edit/Write touches a .java file, run a quick
# `mvn compile` and feed any compile errors back to Claude (exit 2).

input=$(cat)
file_path=$(printf '%s' "$input" | grep -o '"file_path"[[:space:]]*:[[:space:]]*"[^"]*"' | sed -E 's/.*:[[:space:]]*"(.*)"$/\1/')

case "$file_path" in
  *.java)
    export JAVA_HOME="/c/NOOM_PRO/program_noom2/program_noom2/microsoft-jdk-21.0.10-windows-x64/jdk-21.0.10+7"
    export PATH="/c/NOOM_PRO/program_noom2/program_noom2/apache-maven-3.9.16-bin/apache-maven-3.9.16/bin:$PATH"
    cd "$CLAUDE_PROJECT_DIR" || exit 0
    output=$(mvn -q -o compile 2>&1)
    status=$?
    if [ $status -ne 0 ]; then
      echo "Compile check failed after editing $file_path:" >&2
      echo "$output" | grep -E '^\[ERROR\]' | grep -v 'Help 1\|full stack trace\|full debug logging\|read the following\|^\[ERROR\] $\|^\[ERROR\] ->' >&2
      exit 2
    fi
    ;;
esac

exit 0
