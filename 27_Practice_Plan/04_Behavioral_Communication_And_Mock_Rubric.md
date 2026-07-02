# Section 27d — Behavioral, Communication & Mock Interview System

> The missing "human layer" of interview prep. Coding skill gets you *invited*; communication and behavioral performance get you *hired*. This file makes the roadmap a complete interview system, not just an algorithm textbook.

---

## Part 1 — Communicating While You Code

Interviewers score **how you think**, not just whether you finish. Silence is the most common reason strong coders fail. Use a spoken framework so your thought process is always audible.

### The UMPIRE loop (say each phase out loud)

| Phase | What you say | Why it scores |
|-------|--------------|---------------|
| **U — Understand** | Restate the problem, ask about input size, types, duplicates, empty input, sorted-ness. | Shows you don't code on assumptions. |
| **M — Match** | "This looks like a [sliding window / graph BFS / DP] problem because…" | Demonstrates pattern recognition. |
| **P — Plan** | Describe the approach in plain English + complexity BEFORE coding. | Lets the interviewer redirect early. |
| **I — Implement** | Narrate as you code: "I'll use a hashmap here to get O(1) lookup." | Keeps them engaged; reveals reasoning. |
| **R — Review** | Dry-run your code on a small example, out loud, line by line. | Catches bugs before they do. |
| **E — Evaluate** | State final time/space complexity and possible optimizations. | Ends on a strong, senior note. |

### Talk-track templates (memorize these)

- **Opening:** "Before I code, let me make sure I understand. The input is ___, and I should return ___. Can it be empty? Are values unique? What's the size range?"
- **Choosing an approach:** "The brute force is O(n²) by ___. I can do better with ___, bringing it to O(n log n). Let me go with that unless you'd prefer I start from brute force."
- **When stuck (never go silent):** "Let me think out loud. I know ___ works but is too slow. The bottleneck is ___. What if I precompute ___ / sort first / use a different data structure?"
- **When you spot your own bug:** "Wait — this fails when ___. Let me fix the boundary here." *(Self-correction scores higher than a clean run.)*
- **Trade-off:** "I'm trading O(n) extra space for O(n) time. If memory were constrained, I'd instead ___."

### The 6 rules of interview communication
1. **Never code in silence** for more than ~20 seconds.
2. **State complexity before and after** every approach.
3. **Ask before assuming** — clarifying questions are a positive signal, not weakness.
4. **Think out loud when stuck** — the interviewer can only help if they hear your reasoning.
5. **Drive the whiteboard** — you lead; don't wait to be told what to do next.
6. **Confirm the example** — walk one concrete input through your final code.

---

## Part 2 — Behavioral Interviews (the STAR method)

Roughly half of MAANG loops include a behavioral round (Amazon's "Bar Raiser," Google's "Googleyness," Meta's "Jedi"). Prepare stories, not adjectives.

### STAR structure
- **S — Situation:** 1–2 sentences of context. *When, where, what team.*
- **T — Task:** What was your specific responsibility or the goal?
- **A — Action:** What **you** did (use "I", not "we"). This is 60% of the answer.
- **R — Result:** The measurable outcome + what you learned. Quantify it.

> Keep each story to **~2 minutes**. Rehearse aloud; don't memorize word-for-word (it sounds robotic) — memorize the beats.

### Build a story bank (aim for 8–10 reusable stories)
Cover these themes; one strong story can often answer several prompts:

| Theme | Prompt it answers |
|-------|-------------------|
| Conflict with a teammate | "Tell me about a disagreement." |
| Missed deadline / failure | "Tell me about a time you failed." |
| Ambiguity / no clear direction | "A time you had to decide without enough data." |
| Leadership / influence without authority | "A time you led without being the manager." |
| Going above and beyond | "Most challenging project." |
| Handling feedback / criticism | "A time you received tough feedback." |
| Data-driven decision | "A decision you made using data." |
| Prioritization under pressure | "Too many things at once — how did you choose?" |

### Company principle mapping (tailor the same story)
- **Amazon** → map explicitly to Leadership Principles (Ownership, Customer Obsession, Dive Deep, Bias for Action, Deliver Results). Interviewers literally score against these.
- **Meta** → Move Fast, Impact, Be Bold, Open.
- **Google** → Googleyness (collaboration, comfort with ambiguity), General Cognitive Ability.
- **Netflix** → Judgment, Selflessness, Candor.

### Behavioral answer template
> "**(S)** On my payments team we had ___. **(T)** I was responsible for ___. **(A)** First I ___, then I ___, and I made the call to ___ because ___. I also ___ to bring the team along. **(R)** As a result, ___ improved by ___%. What I took away was ___."

### Behavioral anti-patterns
- Saying "we" so much the interviewer can't tell what **you** did.
- No measurable result ("it went well").
- Blaming others for a failure story (own your part).
- A 6-minute rambling story with no structure.

---

## Part 3 — Mock Interview Scoring Rubric

Run mocks with a peer (or self-record) and score against this rubric. **Below 3 on any dimension = focus area.** MAANG interviewers grade on very similar axes.

| Dimension | 1 (No hire) | 3 (Borderline) | 5 (Strong hire) |
|-----------|-------------|----------------|-----------------|
| **Problem understanding** | Codes on wrong assumptions | Asks some clarifying questions | Nails constraints & edge cases up front |
| **Approach / pattern ID** | Jumps to brute force, stuck | Finds a working approach slowly | Quickly identifies optimal pattern + justifies |
| **Coding fluency** | Syntax errors, long pauses | Works with hints | Clean, idiomatic, few bugs |
| **Communication** | Long silences | Explains when asked | Continuous, clear narration |
| **Complexity analysis** | Can't analyze own code | Correct with prompting | States tight bounds unprompted |
| **Testing / debugging** | Doesn't test | Tests with prompting | Proactively dry-runs & handles edge cases |
| **Behavioral (if applicable)** | Vague, no structure | STAR with gaps | Crisp STAR, quantified, self-aware |

### Post-mock feedback template (fill after every mock)
```
Problem: ____________________   Date: ______   Time used: ____ / 45 min
Outcome: [ Solved clean | Solved w/ hints | Partial | Stuck ]

Scores (1–5): Understand __  Approach __  Coding __  Comm __  Complexity __  Testing __

What went well:
  -
Where I lost time / points:
  -
Specific fix for next time (ONE thing):
  -
Pattern to re-drill:
```

### Mock cadence
- **2 mocks/week** minimum in the final 4–6 weeks.
- Alternate: 1 coding mock + 1 behavioral mock each week.
- Record at least one and re-watch — you'll catch filler words, silences, and rushed explanations you don't notice live.

---

## Part 4 — Spaced Repetition Tracker

Solving a problem once ≠ retaining it. Re-solve on an expanding schedule so patterns move to long-term memory. Revisit a problem on **Day 1, Day 3, Day 7, Day 21** after first solving it.

| Metric per problem | Track it |
|--------------------|----------|
| First solved date | ▢ |
| Re-solve D+1 (from memory) | ▢ |
| Re-solve D+3 | ▢ |
| Re-solve D+7 | ▢ |
| Re-solve D+21 | ▢ |
| Confidence (1–5) | ▢ |

**Rule:** if you can't re-derive the approach from memory in ≤ 5 minutes, reset that problem's clock to Day 1. Prioritize re-drilling any problem scored ≤ 3 confidence and any pattern you missed in a mock.

### Simple weekly loop
```
Mon–Fri : 2 new problems/day (from the staged plan) + narrate every solution aloud
Sat      : 1 timed coding mock (45 min) + score with the rubric above
Sun      : 1 behavioral mock (STAR) + re-solve the week's D+7/D+21 due problems
```

---

## Part 5 — Interview-Day Checklist

- [ ] Restate the problem and confirm constraints before coding.
- [ ] State brute force + complexity, then the optimized plan.
- [ ] Narrate continuously; never go silent.
- [ ] Write clean code; name variables meaningfully.
- [ ] Dry-run one concrete example line by line.
- [ ] State final time & space complexity unprompted.
- [ ] Mention one further optimization or trade-off.
- [ ] For behavioral: answer in STAR, quantify results, use "I".

---

**Related:** `03_Hot_Topic_Interview_Drills.md` (timed drills) · `../28_MAANG_Hot_Problem_Solutions/01_Hot_150_Index.md` (problem bank) · `01_Staged_Practice_Plan.md` (weekly progression)
