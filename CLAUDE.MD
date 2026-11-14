# LoanLedger: learning project for a Toyota Financial Services SWE application

## Why this project exists
I'm building this to learn the skills in a Toyota Financial Services (Banking & Commercial
Lending Technology, Plano TX) full-stack software engineer posting, req 10338652, which closes
2026-11-20. Target skills: Java, Spring Boot, microservices, event-driven / domain-driven /
CQRS design, a Vue/Nuxt + PrimeVue (SSR) frontend in TypeScript, AWS, Agile practices (code
reviews, testing), relational + NoSQL data modeling, and multithreading/concurrency.

## How to work with me (important)
- **I'm learning. Teach me; don't write my code for me.** Explain concepts in plain language,
  give me the goal, the rules, the design decisions to make and any new syntax, then let me
  write the code. Review what I paste like a senior engineer reviewing a pull request.
- Only write code for me when I explicitly ask (I asked you to write the tests in step 7).
- Give hints rather than answers when I'm stuck.
- Break work into small steps, each with a clear "done when" check.
- Explain jargon the first time it comes up. Assume I'm new to Spring and Git workflows.
- I use VS Code (not IntelliJ).

## The app
An auto-loan servicing app: open a loan, make payments, see the balance and history.
Planned architecture:
- **loan-service** (Spring Boot): the command side. Stores every change as an event
  (`LoanOpened`, `PaymentPosted`) in a Postgres `loan_events` table with
  `UNIQUE (loan_id, version)` for optimistic locking.
- **account-view-service** (Spring Boot): the query side. Builds a read model in DynamoDB from
  events delivered through SNS/SQS.
- **Frontend**: Nuxt + PrimeVue + TypeScript with server-side rendering.
- **AWS**: ECS Fargate, RDS, DynamoDB, SNS/SQS, set up with AWS CDK. Locally: Docker Compose
  plus LocalStack.
- Money is always `long` cents, never `double`.

## Tech choices
Java 21 (Temurin), Spring Boot (default stable version from start.spring.io), Maven via
`./mvnw`, JUnit 5, Testcontainers, GitHub Actions. Package: `com.loanledger.loan_service`.
Add dependencies only when a step needs them.

## Repo layout
```
loanledger/
  requests.http            ← REST Client test requests
  loan-service/            ← Spring Boot app
```

## Git workflow (every step)
branch (`feat/...`) → code → test → commit → push → PR on GitHub → review my own diff →
merge → delete branch → `git checkout main` → `git pull`.

## Plan and progress

### Day 1
- [x] 1. Install tools (Java 21, Docker, Git, VS Code extensions)
- [x] 2. Generate project with Spring Web, run it
- [x] 3. Publish to GitHub
- [x] 4. `GET /hello` (first branch + PR)
- [x] 5. Return JSON with a record; `@PathVariable`
- [x] 6. POST + Bean Validation (`@Valid`, `@NotNull`, `@Positive`); practice endpoint
- [x] 7. `Loan` domain class (plain Java, no Spring) + 9 JUnit tests
- [ ] 8. **IN PROGRESS:** loan endpoints with in-memory storage
  - Classes in the `loan` package: `LoanService` (`@Service`, `ConcurrentHashMap<UUID, Loan>`),
    `LoanController` (`@RestController`, `@RequestMapping("/loans")`), `LoanNotFoundException`,
    `OpenLoanRequest`, `PaymentRequest` (moved here, `note` field removed), `LoanResponse` with
    a static `from(Loan)` method.
  - `ApiExceptionHandler` (`@RestControllerAdvice`) in the root package:
    `IllegalArgumentException` → 400, `LoanNotFoundException` → 404, using `ProblemDetail`.
  - Delete `PracticePaymentController`.
  - Done when: open → 201, get → 200, pay → 200, overpay → 400, unknown id → 404,
    non-UUID → 400, tests still pass.
  - Open question to discuss on Day 2: is the balance safe when two payments hit the same
    loan at once? (No: lost update. The 200-payment test will show it.)

### Day 2
- [ ] 9. Postgres via Docker Compose + JPA, PostgreSQL Driver, Flyway, Docker Compose Support
- [ ] 10. Flyway migration for `loan_events`
- [ ] 11. Save events instead of using the Map
- [ ] 12. Rebuild a loan from its events (switch `Loan` to static factories `open` / `rebuild`)
- [ ] 13. First Testcontainers test
- [ ] 14. 200 concurrent payments test, without protection, and watch it fail
- [ ] 15. Add retries on unique-constraint violations, and watch it pass
- [ ] 16. GitHub Actions CI

### Days 3–8
3–4: account-view-service, SNS/SQS through LocalStack, DynamoDB.
5–6: Nuxt + PrimeVue frontend.
7–8: AWS deploy with CDK, README with architecture diagram, demo, then tear down AWS resources.

## Decisions I've made (and why)
- `Loan` is a class, not a record: records are immutable and the balance changes.
- `id` and `amountCents` are `final`; only the balance changes.
- No setter for the balance: it changes only through `pay()`, which enforces the rules
  (encapsulation).
- Public constructor for now; switch to static factory methods (`Loan.open`, `Loan.rebuild`)
  on Day 2.
- All rule violations throw `IllegalArgumentException`. Paying a paid-off loan is already
  covered by the "payment exceeds balance" rule.
- Return `LoanResponse` DTOs, never `Loan`, so the API shape doesn't depend on domain internals.