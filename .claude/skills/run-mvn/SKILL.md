---
name: run-mvn
description: Use whenever running mvn (test, compile, package, spring-boot:run) in this project. The default JAVA_HOME points to a JRE (not a JDK) and mvn is not on PATH, causing "No compiler is provided in this environment" errors.
---

# Running Maven in this project

`mvn` is not on the default PATH, and the default `JAVA_HOME` points at a JRE
(Maven will fail with "No compiler is provided in this environment. Perhaps
you are running on a JRE rather than a JDK?"). Always set `JAVA_HOME` and
`PATH` in the same command before invoking `mvn`.

**Path format depends on which shell tool you use** — this is the part that's
easy to get wrong:

## Bash tool (Git Bash)

Use POSIX-style `/c/...` paths. Windows-style `C:\...` paths are silently
ignored for `JAVA_HOME`/`PATH` in Git Bash and `mvn` falls back to the system
JRE.

```bash
export JAVA_HOME="/c/NOOM_PRO/program_noom2/program_noom2/microsoft-jdk-21.0.10-windows-x64/jdk-21.0.10+7"
export PATH="/c/NOOM_PRO/program_noom2/program_noom2/apache-maven-3.9.16-bin/apache-maven-3.9.16/bin:$PATH"
mvn test
```

## PowerShell tool

Use Windows-style `C:\...` paths.

```powershell
$env:JAVA_HOME = "C:\NOOM_PRO\program_noom2\program_noom2\microsoft-jdk-21.0.10-windows-x64\jdk-21.0.10+7"
$env:PATH = "C:\NOOM_PRO\program_noom2\program_noom2\apache-maven-3.9.16-bin\apache-maven-3.9.16\bin;" + $env:PATH
mvn test
```

## Common commands

- `mvn test` — run the full test suite
- `mvn -Dtest=ItemControllerTest test` — run a single test class
- `mvn -Dtest=ProductControllerTest#getProducts_returnsSeededSampleProduct test` — run a single test method
- `mvn -q -o compile` — quick offline compile check (used by the `PostToolUse` hook in `.claude/hooks/check-java-compile.sh`)
- `mvn spring-boot:run` — run the app (listens on port 8081)

## Symptom of missing/wrong env

```
[ERROR] No compiler is provided in this environment. Perhaps you are running on a JRE rather than a JDK?
```

→ `JAVA_HOME` wasn't exported, was exported with the wrong path style for the
shell in use, or `PATH` still resolves to the system `java`/`mvn`.
