# Sales Analytics — UI + API tests

## Run

Prerequisites: JDK 17+, Maven 3.9+, Node.js 18+ (for the app).

```bash
# 1. the app under test, in its own folder (keep it running)
cd sales-analytics-qa-task && npm start          # http://localhost:3000

# 2. once: the browser Playwright drives
mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install chromium"

# 3. the tests
mvn test
```
