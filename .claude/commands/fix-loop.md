---
description: Fix a failing feature by looping test-runner and reviewers until tests pass
---

You are orchestrating a fix loop. The user's task: $ARGUMENTS

Follow this loop, max 3 iterations:

1. Implement the change needed for the task (edit code directly).

2. Delegate to the test-runner subagent to run the relevant tests.
   Wait for its summary.

3. Evaluate the result:
   - If all tests PASS → stop. Report what changed and confirm green.
   - If tests FAIL → continue to step 4.

4. Delegate the failure to the appropriate reviewer:
   - If the failure involves SQL, queries, or migrations →
     sql-reviewer subagent
   - Otherwise → code-reviewer subagent
   Pass the FULL error message and the file:line in the prompt
   (the subagent starts with empty context).

5. Apply the reviewer's suggested fix, then go back to step 2.

Stopping rules:
- Stop after 3 iterations even if still failing, and report what
  you tried and where you're stuck.
- Never loop silently — announce each iteration: "Iteration N: ..."
