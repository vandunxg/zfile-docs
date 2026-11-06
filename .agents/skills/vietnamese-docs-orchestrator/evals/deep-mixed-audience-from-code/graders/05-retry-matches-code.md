---
type: llm
focus: trace
weight: 1
---

The document describes the payment retry as in the code: 3 attempts in total (maxAttempts = 3, not 3 retries after a first try), waits of 2s then 4s, and only for PaymentGatewayTimeoutException
