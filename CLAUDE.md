# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project overview

A small Spring Boot 3.2.5 (Java 17) REST API demo (`com.example.demo`), built with Maven. It exposes two
in-memory CRUD-style resources, "Item" and "Product", with no database/persistence layer.

## Commands

```bash
mvn test                                  # run the full test suite
mvn -Dtest=ItemControllerTest test        # run a single test class
mvn -Dtest=ProductControllerTest#getProducts_returnsSeededSampleProduct test  # run a single test method
mvn spring-boot:run                       # run the app (listens on port 8081, see application.properties)
```

**Environment note:** `mvn` is not always on the default shell PATH, and the default `JAVA_HOME` may point
at a JRE (Maven will fail with "No compiler is provided in this environment"). If `mvn test` fails for either
reason, set:
- `JAVA_HOME=C:\NOOM_PRO\program_noom2\program_noom2\microsoft-jdk-21.0.10-windows-x64\jdk-21.0.10+7`
- prepend `C:\NOOM_PRO\program_noom2\program_noom2\apache-maven-3.9.16-bin\apache-maven-3.9.16\bin` to PATH

## Architecture

Both resources follow the same simple pattern — there's no service layer or repository abstraction:

- **Model** (`src/main/java/com/example/demo/model/`): plain POJO with `id` + fields and getters/setters
  (`Item`, `Product`).
- **Controller** (`src/main/java/com/example/demo/controller/`): `@RestController` holding its own
  `ConcurrentHashMap<Long, T>` for storage and an `AtomicLong` for id generation, seeded with one sample
  row in the constructor (`ItemController`, `ProductController`).

When adding a new resource, mirror this exact model+controller pattern rather than introducing a
service/repository layer, unless asked to refactor.

Endpoints:
- `GET /api/items`, `POST /api/items`, `DELETE /api/items/{id}`
- `GET /api/products`, `POST /api/products`

## Tests

Tests under `src/test/java/com/example/demo/controller/` are **plain JUnit 5 + AssertJ unit tests** —
they instantiate the controller directly (`new ItemController()` / `new ProductController()`) with no
Spring context, `@SpringBootTest`, or `MockMvc`. Follow this same style for new controller tests; each
test should construct its own fresh controller instance to avoid shared in-memory state.

## Custom Claude Code tooling

This repo defines its own sub-agents and slash command:

- `.claude/agents/explorer.md` — read-only exploration agent (Glob/Grep/Read), use for locating code.
- `.claude/agents/code-reviewer.md` — review agent for Java/Spring changes; its description says it
  **must be used after any code change**.
- `.claude/agents/test-runner.md` — runs `mvn test` and reports only failures.
- `.claude/commands/fix-loop.md` — `/fix-loop` command that loops test-runner + a reviewer
  (code-reviewer, or `sql-reviewer` for SQL/migration failures — not yet defined in this repo) until
  tests pass, up to 3 iterations.
