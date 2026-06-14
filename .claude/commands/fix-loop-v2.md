---
description: Investigate, plan, fix, and verify a failing feature by looping subagents until tests pass
---

You are orchestrating a fix loop for a Spring Boot + PostgreSQL
project on AKS. The user's task: $ARGUMENTS

Work through these phases in order. Announce each phase clearly.

═══════════════════════════════════════════
PHASE 0 — INVESTIGATE (gather context first)
═══════════════════════════════════════════
Delegate to the explorer subagent to map the relevant code BEFORE
touching anything. Ask it to report:
- Which files/classes are involved in this task
- The request path (controller → service → repository → DB)
- Any existing tests covering this area
- Relevant config (application.yml, migrations, K8s manifests)

Wait for its summary. Do NOT edit code in this phase.

═══════════════════════════════════════════
PHASE 1 — PLAN (review before touching disk)
═══════════════════════════════════════════
Based on the explorer's findings, write a short plan:
- Root cause hypothesis
- Files you intend to change and why
- How you'll verify success (which tests)

Present the plan and pause for my approval before continuing.
If I approve, proceed. If I edit the plan, follow my version.

═══════════════════════════════════════════
PHASE 2 — FIX & VERIFY LOOP (max 3 iterations)
═══════════════════════════════════════════
Repeat up to 3 times. Announce "Iteration N:" each round.

  Step A — Implement the change (edit code directly).

  Step B — Delegate to the test-runner subagent.
           Pass the specific test class/method when known
           (e.g. mvn test -Dtest=InvoiceServiceTest).
           Wait for its pass/fail summary.

  Step C — Evaluate:
    • All tests PASS  → exit loop, go to PHASE 3.
    • Tests FAIL      → go to Step D.

  Step D — Route the failure to the right reviewer.
           The subagent starts with EMPTY context, so include in
           the prompt: the full error message, the failing
           test name, and the relevant file:line.
    • Failure involves SQL / queries / JPA / migrations / schema
        → sql-reviewer subagent
    • Otherwise
        → code-reviewer subagent

  Step E — Apply the reviewer's top suggested fix.
           Return to Step A.

═══════════════════════════════════════════
PHASE 3 — REPORT
═══════════════════════════════════════════
Summarize:
- Root cause (confirmed)
- What changed (files + one-line each)
- Final test status (green count)
- Any follow-ups or risks worth a human look

═══════════════════════════════════════════
STOPPING RULES (non-negotiable)
═══════════════════════════════════════════
- Hard stop after 3 iterations even if still failing. Report what
  you tried, what each reviewer said, and exactly where you're stuck.
- If the same test fails 2 iterations in a row with the same error,
  stop early — the loop isn't converging. Escalate to me with the
  full picture instead of trying a 3rd time.
- Never edit code in PHASE 0. Never skip PHASE 1 approval.