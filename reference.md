# Reference
<details><summary><code>client.putPlanAudiencesPlanAudienceId(planAudienceId)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.putPlanAudiencesPlanAudienceId("plan_audience_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planAudienceId:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.deletePlanAudiencesPlanAudienceId(planAudienceId)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deletePlanAudiencesPlanAudienceId("plan_audience_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planAudienceId:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## accounts
<details><summary><code>client.accounts.listApiKeys() -> ListApiKeysResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().listApiKeys(
    ListApiKeysRequest
        .builder()
        .requireEnvironment(true)
        .environmentId("environment_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**environmentId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**requireEnvironment:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.createApiKey(request) -> CreateApiKeyResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().createApiKey(
    CreateApiKeyRequestBody
        .builder()
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**description:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**environmentId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.getApiKey(apiKeyId) -> GetApiKeyResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().getApiKey("api_key_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**apiKeyId:** `String` — api_key_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.updateApiKey(apiKeyId, request) -> UpdateApiKeyResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().updateApiKey(
    "api_key_id",
    UpdateApiKeyRequestBody
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**apiKeyId:** `String` — api_key_id
    
</dd>
</dl>

<dl>
<dd>

**description:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.deleteApiKey(apiKeyId) -> DeleteApiKeyResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().deleteApiKey("api_key_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**apiKeyId:** `String` — api_key_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.countApiKeys() -> CountApiKeysResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().countApiKeys(
    CountApiKeysRequest
        .builder()
        .requireEnvironment(true)
        .environmentId("environment_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**environmentId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**requireEnvironment:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.listApiRequests() -> ListApiRequestsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().listApiRequests(
    ListApiRequestsRequest
        .builder()
        .q("q")
        .requestType("request_type")
        .environmentId("environment_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**requestType:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**environmentId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.getApiRequest(apiRequestId) -> GetApiRequestResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().getApiRequest("api_request_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**apiRequestId:** `String` — api_request_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.countApiRequests() -> CountApiRequestsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().countApiRequests(
    CountApiRequestsRequest
        .builder()
        .q("q")
        .requestType("request_type")
        .environmentId("environment_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**requestType:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**environmentId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.listAuditLogs() -> ListAuditLogsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().listAuditLogs(
    ListAuditLogsRequest
        .builder()
        .actorType(ActorType.API_KEY)
        .environmentId("environment_id")
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**actorType:** `Optional<ActorType>` 
    
</dd>
</dl>

<dl>
<dd>

**environmentId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.getAuditLog(auditLogId) -> GetAuditLogResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().getAuditLog("audit_log_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**auditLogId:** `String` — audit_log_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.countAuditLogs() -> CountAuditLogsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().countAuditLogs(
    CountAuditLogsRequest
        .builder()
        .actorType(ActorType.API_KEY)
        .environmentId("environment_id")
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**actorType:** `Optional<ActorType>` 
    
</dd>
</dl>

<dl>
<dd>

**environmentId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.listEnvironments() -> ListEnvironmentsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().listEnvironments(
    ListEnvironmentsRequest
        .builder()
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.createEnvironment(request) -> CreateEnvironmentResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().createEnvironment(
    CreateEnvironmentRequestBody
        .builder()
        .environmentType(EnvironmentType.DEVELOPMENT)
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**environmentType:** `EnvironmentType` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.getEnvironment(environmentId) -> GetEnvironmentResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().getEnvironment("environment_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**environmentId:** `String` — environment_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.updateEnvironment(environmentId, request) -> UpdateEnvironmentResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().updateEnvironment(
    "environment_id",
    UpdateEnvironmentRequestBody
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**environmentId:** `String` — environment_id
    
</dd>
</dl>

<dl>
<dd>

**environmentType:** `Optional<EnvironmentType>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.deleteEnvironment(environmentId) -> DeleteEnvironmentResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().deleteEnvironment("environment_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**environmentId:** `String` — environment_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.accounts.quickstart() -> QuickstartResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accounts().quickstart();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## billing
<details><summary><code>client.billing.listCoupons() -> ListCouponsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().listCoupons(
    ListCouponsRequest
        .builder()
        .isActive(true)
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**isActive:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.upsertBillingCoupon(request) -> UpsertBillingCouponResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().upsertBillingCoupon(
    CreateCouponRequestBody
        .builder()
        .amountOff(1)
        .duration("duration")
        .durationInMonths(1)
        .externalId("external_id")
        .maxRedemptions(1)
        .name("name")
        .percentOff(1.1)
        .timesRedeemed(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**amountOff:** `Integer` 
    
</dd>
</dl>

<dl>
<dd>

**currency:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**duration:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**durationInMonths:** `Integer` 
    
</dd>
</dl>

<dl>
<dd>

**externalId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**maxRedemptions:** `Integer` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**percentOff:** `Double` 
    
</dd>
</dl>

<dl>
<dd>

**timesRedeemed:** `Integer` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.upsertBillingCustomer(request) -> UpsertBillingCustomerResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().upsertBillingCustomer(
    CreateBillingCustomerRequestBody
        .builder()
        .email("email")
        .externalId("external_id")
        .meta(
            new HashMap<String, String>() {{
                put("key", "value");
            }}
        )
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**defaultPaymentMethodId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**email:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**externalId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**meta:** `Map<String, String>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**providerType:** `Optional<BillingProviderType>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.listCustomersWithSubscriptions() -> ListCustomersWithSubscriptionsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().listCustomersWithSubscriptions(
    ListCustomersWithSubscriptionsRequest
        .builder()
        .name("name")
        .providerType(BillingProviderType.SCHEMATIC)
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyIds:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**providerType:** `Optional<BillingProviderType>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.countCustomers() -> CountCustomersResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().countCustomers(
    CountCustomersRequest
        .builder()
        .name("name")
        .providerType(BillingProviderType.SCHEMATIC)
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyIds:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**providerType:** `Optional<BillingProviderType>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.listInvoices() -> ListInvoicesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().listInvoices(
    ListInvoicesRequest
        .builder()
        .customerExternalId("customer_external_id")
        .subscriptionExternalId("subscription_external_id")
        .companyId("company_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**customerExternalId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**subscriptionExternalId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.upsertInvoice(request) -> UpsertInvoiceResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().upsertInvoice(
    CreateInvoiceRequestBody
        .builder()
        .amountDue(1)
        .amountPaid(1)
        .amountRemaining(1)
        .collectionMethod("collection_method")
        .currency("currency")
        .customerExternalId("customer_external_id")
        .subtotal(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**amountDue:** `Integer` 
    
</dd>
</dl>

<dl>
<dd>

**amountPaid:** `Integer` 
    
</dd>
</dl>

<dl>
<dd>

**amountRemaining:** `Integer` 
    
</dd>
</dl>

<dl>
<dd>

**collectionMethod:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**currency:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**customerExternalId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**dueDate:** `Optional<OffsetDateTime>` 
    
</dd>
</dl>

<dl>
<dd>

**externalId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**paymentMethodExternalId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**subscriptionExternalId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**subtotal:** `Integer` 
    
</dd>
</dl>

<dl>
<dd>

**url:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.listMeters() -> ListMetersResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().listMeters(
    ListMetersRequest
        .builder()
        .displayName("display_name")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**displayName:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.upsertBillingMeter(request) -> UpsertBillingMeterResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().upsertBillingMeter(
    CreateMeterRequestBody
        .builder()
        .displayName("display_name")
        .eventName("event_name")
        .eventPayloadKey("event_payload_key")
        .externalId("external_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**displayName:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**eventName:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**eventPayloadKey:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**externalId:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.listPaymentMethods() -> ListPaymentMethodsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().listPaymentMethods(
    ListPaymentMethodsRequest
        .builder()
        .customerExternalId("customer_external_id")
        .companyId("company_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**customerExternalId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.upsertPaymentMethod(request) -> UpsertPaymentMethodResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().upsertPaymentMethod(
    CreatePaymentMethodRequestBody
        .builder()
        .customerExternalId("customer_external_id")
        .externalId("external_id")
        .paymentMethodType("payment_method_type")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**accountLast4:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**accountName:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**bankName:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**billingEmail:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**billingName:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**cardBrand:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**cardExpMonth:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**cardExpYear:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**cardLast4:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**customerExternalId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**externalId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**paymentMethodType:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.listBillingPrices() -> ListBillingPricesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().listBillingPrices(
    ListBillingPricesRequest
        .builder()
        .forInitialPlan(true)
        .forTrialExpiryPlan(true)
        .interval("interval")
        .isActive(true)
        .price(1)
        .productId("product_id")
        .providerType(BillingProviderType.SCHEMATIC)
        .q("q")
        .tiersMode(BillingTiersMode.GRADUATED)
        .usageType(BillingPriceUsageType.LICENSED)
        .withMeter(true)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**forInitialPlan:** `Optional<Boolean>` — Filter for prices valid for initial plans (free prices only)
    
</dd>
</dl>

<dl>
<dd>

**forTrialExpiryPlan:** `Optional<Boolean>` — Filter for prices valid for trial expiry plans (free prices only)
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**interval:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**isActive:** `Optional<Boolean>` — Filter for active prices on active products (defaults to true if not specified)
    
</dd>
</dl>

<dl>
<dd>

**price:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**productId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**productIds:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**providerType:** `Optional<BillingProviderType>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**tiersMode:** `Optional<BillingTiersMode>` 
    
</dd>
</dl>

<dl>
<dd>

**usageType:** `Optional<BillingPriceUsageType>` 
    
</dd>
</dl>

<dl>
<dd>

**withMeter:** `Optional<Boolean>` — Filter for prices with a meter
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.upsertBillingPrice(request) -> UpsertBillingPriceResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().upsertBillingPrice(
    CreateBillingPriceRequestBody
        .builder()
        .billingScheme(BillingPriceScheme.PER_UNIT)
        .currency("currency")
        .externalAccountId("external_account_id")
        .interval("interval")
        .isActive(true)
        .price(1)
        .priceExternalId("price_external_id")
        .priceTiers(
            Arrays.asList(
                CreateBillingPriceTierRequestBody
                    .builder()
                    .priceExternalId("price_external_id")
                    .build()
            )
        )
        .productExternalId("product_external_id")
        .usageType(BillingPriceUsageType.LICENSED)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**billingScheme:** `BillingPriceScheme` 
    
</dd>
</dl>

<dl>
<dd>

**currency:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**externalAccountId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**interval:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**isActive:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**meterId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**packageSize:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**price:** `Integer` 
    
</dd>
</dl>

<dl>
<dd>

**priceDecimal:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**priceExternalId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**priceTiers:** `List<CreateBillingPriceTierRequestBody>` 
    
</dd>
</dl>

<dl>
<dd>

**productExternalId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**providerType:** `Optional<BillingProviderType>` 
    
</dd>
</dl>

<dl>
<dd>

**tiersMode:** `Optional<BillingTiersMode>` 
    
</dd>
</dl>

<dl>
<dd>

**usageType:** `BillingPriceUsageType` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.deleteBillingProduct(billingId) -> DeleteBillingProductResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().deleteBillingProduct("billing_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**billingId:** `String` — billing_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.listBillingProductPrices() -> ListBillingProductPricesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().listBillingProductPrices(
    ListBillingProductPricesRequest
        .builder()
        .forInitialPlan(true)
        .forTrialExpiryPlan(true)
        .interval("interval")
        .isActive(true)
        .price(1)
        .productId("product_id")
        .providerType(BillingProviderType.SCHEMATIC)
        .q("q")
        .tiersMode(BillingTiersMode.GRADUATED)
        .usageType(BillingPriceUsageType.LICENSED)
        .withMeter(true)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**forInitialPlan:** `Optional<Boolean>` — Filter for prices valid for initial plans (free prices only)
    
</dd>
</dl>

<dl>
<dd>

**forTrialExpiryPlan:** `Optional<Boolean>` — Filter for prices valid for trial expiry plans (free prices only)
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**interval:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**isActive:** `Optional<Boolean>` — Filter for active prices on active products (defaults to true if not specified)
    
</dd>
</dl>

<dl>
<dd>

**price:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**productId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**productIds:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**providerType:** `Optional<BillingProviderType>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**tiersMode:** `Optional<BillingTiersMode>` 
    
</dd>
</dl>

<dl>
<dd>

**usageType:** `Optional<BillingPriceUsageType>` 
    
</dd>
</dl>

<dl>
<dd>

**withMeter:** `Optional<Boolean>` — Filter for prices with a meter
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.deleteProductPrice(billingId) -> DeleteProductPriceResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().deleteProductPrice("billing_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**billingId:** `String` — billing_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.upsertBillingProduct(request) -> UpsertBillingProductResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().upsertBillingProduct(
    CreateBillingProductRequestBody
        .builder()
        .externalId("external_id")
        .name("name")
        .price(1.1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**externalId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**isActive:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**price:** `Double` 
    
</dd>
</dl>

<dl>
<dd>

**providerType:** `Optional<BillingProviderType>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.listBillingProducts() -> ListBillingProductsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().listBillingProducts(
    ListBillingProductsRequest
        .builder()
        .isActive(true)
        .name("name")
        .priceUsageType(BillingPriceUsageType.LICENSED)
        .providerType(BillingProviderType.SCHEMATIC)
        .q("q")
        .withOneTimeCharges(true)
        .withPricesOnly(true)
        .withZeroPrice(true)
        .withoutLinkedToPlan(true)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**isActive:** `Optional<Boolean>` — Filter products that are active. Defaults to true if not specified
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**priceUsageType:** `Optional<BillingPriceUsageType>` 
    
</dd>
</dl>

<dl>
<dd>

**providerType:** `Optional<BillingProviderType>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**withOneTimeCharges:** `Optional<Boolean>` — Filter products that are one time charges
    
</dd>
</dl>

<dl>
<dd>

**withPricesOnly:** `Optional<Boolean>` — Filter products that have prices
    
</dd>
</dl>

<dl>
<dd>

**withZeroPrice:** `Optional<Boolean>` — Filter products that have zero price for free subscription type
    
</dd>
</dl>

<dl>
<dd>

**withoutLinkedToPlan:** `Optional<Boolean>` — Filter products that are not linked to any plan
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.countBillingProducts() -> CountBillingProductsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().countBillingProducts(
    CountBillingProductsRequest
        .builder()
        .isActive(true)
        .name("name")
        .priceUsageType(BillingPriceUsageType.LICENSED)
        .providerType(BillingProviderType.SCHEMATIC)
        .q("q")
        .withOneTimeCharges(true)
        .withPricesOnly(true)
        .withZeroPrice(true)
        .withoutLinkedToPlan(true)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**isActive:** `Optional<Boolean>` — Filter products that are active. Defaults to true if not specified
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**priceUsageType:** `Optional<BillingPriceUsageType>` 
    
</dd>
</dl>

<dl>
<dd>

**providerType:** `Optional<BillingProviderType>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**withOneTimeCharges:** `Optional<Boolean>` — Filter products that are one time charges
    
</dd>
</dl>

<dl>
<dd>

**withPricesOnly:** `Optional<Boolean>` — Filter products that have prices
    
</dd>
</dl>

<dl>
<dd>

**withZeroPrice:** `Optional<Boolean>` — Filter products that have zero price for free subscription type
    
</dd>
</dl>

<dl>
<dd>

**withoutLinkedToPlan:** `Optional<Boolean>` — Filter products that are not linked to any plan
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.billing.upsertBillingSubscription(request) -> UpsertBillingSubscriptionResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.billing().upsertBillingSubscription(
    CreateBillingSubscriptionRequestBody
        .builder()
        .cancelAtPeriodEnd(true)
        .currency("currency")
        .customerExternalId("customer_external_id")
        .discounts(
            Arrays.asList(
                BillingSubscriptionDiscount
                    .builder()
                    .couponExternalId("coupon_external_id")
                    .externalId("external_id")
                    .isActive(true)
                    .startedAt(OffsetDateTime.parse("2024-01-15T09:30:00Z"))
                    .build()
            )
        )
        .expiredAt(OffsetDateTime.parse("2024-01-15T09:30:00Z"))
        .productExternalIds(
            Arrays.asList(
                BillingProductPricing
                    .builder()
                    .currency("currency")
                    .interval("interval")
                    .price(1)
                    .priceExternalId("price_external_id")
                    .productExternalId("product_external_id")
                    .quantity(1)
                    .usageType(BillingPriceUsageType.LICENSED)
                    .build()
            )
        )
        .subscriptionExternalId("subscription_external_id")
        .totalPrice(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**applicationId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**cancelAt:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**cancelAtPeriodEnd:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**currency:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**customerExternalId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**defaultPaymentMethodExternalId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**defaultPaymentMethodId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**discounts:** `List<BillingSubscriptionDiscount>` 
    
</dd>
</dl>

<dl>
<dd>

**expiredAt:** `OffsetDateTime` 
    
</dd>
</dl>

<dl>
<dd>

**interval:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**metadata:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>

<dl>
<dd>

**periodEnd:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**periodStart:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**productExternalIds:** `List<BillingProductPricing>` 
    
</dd>
</dl>

<dl>
<dd>

**status:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**subscriptionExternalId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**totalPrice:** `Integer` 
    
</dd>
</dl>

<dl>
<dd>

**trialEnd:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**trialEndSetting:** `Optional<BillingSubscriptionTrialEndSetting>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## credits
<details><summary><code>client.credits.listBillingCredits() -> ListBillingCreditsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().listBillingCredits(
    ListBillingCreditsRequest
        .builder()
        .name("name")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.createBillingCredit(request) -> CreateBillingCreditResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().createBillingCredit(
    CreateBillingCreditRequestBody
        .builder()
        .currency("currency")
        .description("description")
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**burnStrategy:** `Optional<BillingCreditBurnStrategy>` 
    
</dd>
</dl>

<dl>
<dd>

**currency:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**defaultExpiryUnit:** `Optional<BillingCreditExpiryUnit>` 
    
</dd>
</dl>

<dl>
<dd>

**defaultExpiryUnitCount:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**defaultRolloverPolicy:** `Optional<BillingCreditRolloverPolicy>` 
    
</dd>
</dl>

<dl>
<dd>

**description:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**icon:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**perUnitPrice:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**perUnitPriceDecimal:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**pluralName:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**singularName:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.getSingleBillingCredit(creditId) -> GetSingleBillingCreditResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().getSingleBillingCredit("credit_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**creditId:** `String` — credit_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.updateBillingCredit(creditId, request) -> UpdateBillingCreditResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().updateBillingCredit(
    "credit_id",
    UpdateBillingCreditRequestBody
        .builder()
        .description("description")
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**creditId:** `String` — credit_id
    
</dd>
</dl>

<dl>
<dd>

**burnStrategy:** `Optional<BillingCreditBurnStrategy>` 
    
</dd>
</dl>

<dl>
<dd>

**defaultExpiryUnit:** `Optional<BillingCreditExpiryUnit>` 
    
</dd>
</dl>

<dl>
<dd>

**defaultExpiryUnitCount:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**defaultRolloverPolicy:** `Optional<BillingCreditRolloverPolicy>` 
    
</dd>
</dl>

<dl>
<dd>

**description:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**icon:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**perUnitPrice:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**perUnitPriceDecimal:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**pluralName:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**singularName:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.softDeleteBillingCredit(creditId) -> SoftDeleteBillingCreditResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().softDeleteBillingCredit("credit_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**creditId:** `String` — credit_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.listCreditBundles() -> ListCreditBundlesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().listCreditBundles(
    ListCreditBundlesRequest
        .builder()
        .creditId("credit_id")
        .status(BillingCreditBundleStatus.ACTIVE)
        .bundleType("fixed")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**creditId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**status:** `Optional<BillingCreditBundleStatus>` 
    
</dd>
</dl>

<dl>
<dd>

**bundleType:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.createCreditBundle(request) -> CreateCreditBundleResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().createCreditBundle(
    CreateCreditBundleRequestBody
        .builder()
        .bundleName("bundle_name")
        .creditId("credit_id")
        .currency("currency")
        .pricePerUnit(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**bundleName:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**bundleType:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**creditId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**currency:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**expiryType:** `Optional<BillingCreditExpiryType>` 
    
</dd>
</dl>

<dl>
<dd>

**expiryUnit:** `Optional<BillingCreditExpiryUnit>` 
    
</dd>
</dl>

<dl>
<dd>

**expiryUnitCount:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**pricePerUnit:** `Integer` 
    
</dd>
</dl>

<dl>
<dd>

**pricePerUnitDecimal:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**quantity:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**status:** `Optional<BillingCreditBundleStatus>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.getCreditBundle(bundleId) -> GetCreditBundleResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().getCreditBundle("bundle_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**bundleId:** `String` — bundle_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.updateCreditBundleDetails(bundleId, request) -> UpdateCreditBundleDetailsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().updateCreditBundleDetails(
    "bundle_id",
    UpdateCreditBundleDetailsRequestBody
        .builder()
        .bundleName("bundle_name")
        .pricePerUnit(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**bundleId:** `String` — bundle_id
    
</dd>
</dl>

<dl>
<dd>

**bundleName:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**expiryType:** `Optional<BillingCreditExpiryType>` 
    
</dd>
</dl>

<dl>
<dd>

**expiryUnit:** `Optional<BillingCreditExpiryUnit>` 
    
</dd>
</dl>

<dl>
<dd>

**expiryUnitCount:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**pricePerUnit:** `Integer` 
    
</dd>
</dl>

<dl>
<dd>

**pricePerUnitDecimal:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**quantity:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**status:** `Optional<BillingCreditBundleStatus>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.deleteCreditBundle(bundleId) -> DeleteCreditBundleResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().deleteCreditBundle("bundle_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**bundleId:** `String` — bundle_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.countCreditBundles() -> CountCreditBundlesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().countCreditBundles(
    CountCreditBundlesRequest
        .builder()
        .creditId("credit_id")
        .status(BillingCreditBundleStatus.ACTIVE)
        .bundleType("fixed")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**creditId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**status:** `Optional<BillingCreditBundleStatus>` 
    
</dd>
</dl>

<dl>
<dd>

**bundleType:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.countBillingCredits() -> CountBillingCreditsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().countBillingCredits(
    CountBillingCreditsRequest
        .builder()
        .name("name")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.zeroOutGrant(grantId, request) -> ZeroOutGrantResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().zeroOutGrant(
    "grant_id",
    ZeroOutGrantRequestBody
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**grantId:** `String` — grant_id
    
</dd>
</dl>

<dl>
<dd>

**reason:** `Optional<BillingCreditGrantZeroedOutReason>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.grantBillingCreditsToCompany(request) -> GrantBillingCreditsToCompanyResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().grantBillingCreditsToCompany(
    CreateCompanyCreditGrant
        .builder()
        .companyId("company_id")
        .creditId("credit_id")
        .quantity(1)
        .reason(BillingCreditGrantReason.BILLING_CREDIT_AUTO_TOPUP)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**billingPeriodsCount:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**companyId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**creditId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**expiresAt:** `Optional<OffsetDateTime>` 
    
</dd>
</dl>

<dl>
<dd>

**expiryType:** `Optional<BillingCreditExpiryType>` 
    
</dd>
</dl>

<dl>
<dd>

**expiryUnit:** `Optional<BillingCreditExpiryUnit>` 
    
</dd>
</dl>

<dl>
<dd>

**expiryUnitCount:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**quantity:** `Integer` 
    
</dd>
</dl>

<dl>
<dd>

**reason:** `BillingCreditGrantReason` 
    
</dd>
</dl>

<dl>
<dd>

**renewalEnabled:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**renewalPeriod:** `Optional<BillingPlanCreditGrantResetStart>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.countCompanyGrants() -> CountCompanyGrantsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().countCompanyGrants(
    CountCompanyGrantsRequest
        .builder()
        .companyId("company_id")
        .order(CreditGrantSortOrder.CREATED_AT)
        .dir(SortDirection.ASC)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**order:** `Optional<CreditGrantSortOrder>` 
    
</dd>
</dl>

<dl>
<dd>

**dir:** `Optional<SortDirection>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.listCompanyGrants() -> ListCompanyGrantsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().listCompanyGrants(
    ListCompanyGrantsRequest
        .builder()
        .companyId("company_id")
        .order(CreditGrantSortOrder.CREATED_AT)
        .dir(SortDirection.ASC)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**order:** `Optional<CreditGrantSortOrder>` 
    
</dd>
</dl>

<dl>
<dd>

**dir:** `Optional<SortDirection>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.countBillingCreditsGrants() -> CountBillingCreditsGrantsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().countBillingCreditsGrants(
    CountBillingCreditsGrantsRequest
        .builder()
        .creditId("credit_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**creditId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.listGrantsForCredit() -> ListGrantsForCreditResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().listGrantsForCredit(
    ListGrantsForCreditRequest
        .builder()
        .creditId("credit_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**creditId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.getEnrichedCreditLedger() -> GetEnrichedCreditLedgerResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().getEnrichedCreditLedger(
    GetEnrichedCreditLedgerRequest
        .builder()
        .companyId("company_id")
        .period(CreditLedgerPeriod.DAILY)
        .billingCreditId("billing_credit_id")
        .featureId("feature_id")
        .startTime("start_time")
        .endTime("end_time")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**billingCreditId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**featureId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**period:** `CreditLedgerPeriod` 
    
</dd>
</dl>

<dl>
<dd>

**startTime:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**endTime:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.countCreditLedger() -> CountCreditLedgerResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().countCreditLedger(
    CountCreditLedgerRequest
        .builder()
        .companyId("company_id")
        .period(CreditLedgerPeriod.DAILY)
        .billingCreditId("billing_credit_id")
        .featureId("feature_id")
        .startTime("start_time")
        .endTime("end_time")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**billingCreditId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**featureId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**period:** `CreditLedgerPeriod` 
    
</dd>
</dl>

<dl>
<dd>

**startTime:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**endTime:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.listBillingPlanCreditGrants() -> ListBillingPlanCreditGrantsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().listBillingPlanCreditGrants(
    ListBillingPlanCreditGrantsRequest
        .builder()
        .creditId("credit_id")
        .planId("plan_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**creditId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**planId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**planIds:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.createBillingPlanCreditGrant(request) -> CreateBillingPlanCreditGrantResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().createBillingPlanCreditGrant(
    CreateBillingPlanCreditGrantRequestBody
        .builder()
        .creditAmount(1)
        .creditId("credit_id")
        .planId("plan_id")
        .resetCadence(BillingPlanCreditGrantResetCadence.DAILY)
        .resetStart(BillingPlanCreditGrantResetStart.BILLING_PERIOD)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `CreateBillingPlanCreditGrantRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.updateBillingPlanCreditGrant(planGrantId, request) -> UpdateBillingPlanCreditGrantResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().updateBillingPlanCreditGrant(
    "plan_grant_id",
    UpdateBillingPlanCreditGrantRequestBody
        .builder()
        .resetCadence(BillingPlanCreditGrantResetCadence.DAILY)
        .resetStart(BillingPlanCreditGrantResetStart.BILLING_PERIOD)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planGrantId:** `String` — plan_grant_id
    
</dd>
</dl>

<dl>
<dd>

**request:** `UpdateBillingPlanCreditGrantRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.deleteBillingPlanCreditGrant(planGrantId) -> DeleteBillingPlanCreditGrantResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().deleteBillingPlanCreditGrant(
    "plan_grant_id",
    DeleteBillingPlanCreditGrantRequest
        .builder()
        .applyToExisting(true)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planGrantId:** `String` — plan_grant_id
    
</dd>
</dl>

<dl>
<dd>

**applyToExisting:** `Optional<Boolean>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.credits.countBillingPlanCreditGrants() -> CountBillingPlanCreditGrantsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.credits().countBillingPlanCreditGrants(
    CountBillingPlanCreditGrantsRequest
        .builder()
        .creditId("credit_id")
        .planId("plan_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**creditId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**planId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**planIds:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## checkout
<details><summary><code>client.checkout.internal(request) -> CheckoutInternalResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.checkout().internal(
    ChangeSubscriptionInternalRequestBody
        .builder()
        .addOnIds(
            Arrays.asList(
                UpdateAddOnRequestBody
                    .builder()
                    .addOnId("add_on_id")
                    .priceId("price_id")
                    .build()
            )
        )
        .companyId("company_id")
        .creditBundles(
            Arrays.asList(
                UpdateCreditBundleRequestBody
                    .builder()
                    .bundleId("bundle_id")
                    .quantity(1)
                    .build()
            )
        )
        .newPlanId("new_plan_id")
        .newPriceId("new_price_id")
        .payInAdvance(
            Arrays.asList(
                UpdatePayInAdvanceRequestBody
                    .builder()
                    .priceId("price_id")
                    .quantity(1)
                    .build()
            )
        )
        .skipTrial(true)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `ChangeSubscriptionInternalRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.checkout.getCheckoutData(request) -> GetCheckoutDataResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.checkout().getCheckoutData(
    CheckoutDataRequestBody
        .builder()
        .companyId("company_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**selectedPlanId:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.checkout.previewCheckoutInternal(request) -> PreviewCheckoutInternalResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.checkout().previewCheckoutInternal(
    ChangeSubscriptionInternalRequestBody
        .builder()
        .addOnIds(
            Arrays.asList(
                UpdateAddOnRequestBody
                    .builder()
                    .addOnId("add_on_id")
                    .priceId("price_id")
                    .build()
            )
        )
        .companyId("company_id")
        .creditBundles(
            Arrays.asList(
                UpdateCreditBundleRequestBody
                    .builder()
                    .bundleId("bundle_id")
                    .quantity(1)
                    .build()
            )
        )
        .newPlanId("new_plan_id")
        .newPriceId("new_price_id")
        .payInAdvance(
            Arrays.asList(
                UpdatePayInAdvanceRequestBody
                    .builder()
                    .priceId("price_id")
                    .quantity(1)
                    .build()
            )
        )
        .skipTrial(true)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `ChangeSubscriptionInternalRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.checkout.managePlan(request) -> ManagePlanResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.checkout().managePlan(
    ManagePlanRequest
        .builder()
        .addOnSelections(
            Arrays.asList(
                PlanSelection
                    .builder()
                    .planId("plan_id")
                    .build()
            )
        )
        .companyId("company_id")
        .creditBundles(
            Arrays.asList(
                UpdateCreditBundleRequestBody
                    .builder()
                    .bundleId("bundle_id")
                    .quantity(1)
                    .build()
            )
        )
        .payInAdvanceEntitlements(
            Arrays.asList(
                UpdatePayInAdvanceRequestBody
                    .builder()
                    .priceId("price_id")
                    .quantity(1)
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `ManagePlanRequest` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.checkout.previewManagePlan(request) -> PreviewManagePlanResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.checkout().previewManagePlan(
    ManagePlanRequest
        .builder()
        .addOnSelections(
            Arrays.asList(
                PlanSelection
                    .builder()
                    .planId("plan_id")
                    .build()
            )
        )
        .companyId("company_id")
        .creditBundles(
            Arrays.asList(
                UpdateCreditBundleRequestBody
                    .builder()
                    .bundleId("bundle_id")
                    .quantity(1)
                    .build()
            )
        )
        .payInAdvanceEntitlements(
            Arrays.asList(
                UpdatePayInAdvanceRequestBody
                    .builder()
                    .priceId("price_id")
                    .quantity(1)
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `ManagePlanRequest` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.checkout.cancelSubscription(request) -> CancelSubscriptionResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.checkout().cancelSubscription(
    CancelSubscriptionRequest
        .builder()
        .companyId("company_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**cancelImmediately:** `Optional<Boolean>` — If false, subscription cancels at period end. Defaults to true.
    
</dd>
</dl>

<dl>
<dd>

**companyId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**prorate:** `Optional<Boolean>` — If true and cancel_immediately is true, issue prorated credit. Defaults to true.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.checkout.updateCustomerSubscriptionTrialEnd(subscriptionId, request) -> UpdateCustomerSubscriptionTrialEndResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.checkout().updateCustomerSubscriptionTrialEnd(
    "subscription_id",
    UpdateTrialEndRequestBody
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**subscriptionId:** `String` — subscription_id
    
</dd>
</dl>

<dl>
<dd>

**trialEnd:** `Optional<OffsetDateTime>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## companies
<details><summary><code>client.companies.listCompanies() -> ListCompaniesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().listCompanies(
    ListCompaniesRequest
        .builder()
        .monetizedSubscriptions(true)
        .planId("plan_id")
        .q("q")
        .sortOrderColumn("sort_order_column")
        .sortOrderDirection(SortDirection.ASC)
        .withoutFeatureOverrideFor("without_feature_override_for")
        .withoutPlan(true)
        .withoutSubscription(true)
        .withSubscription(true)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**creditTypeIds:** `Optional<String>` — Filter companies by one or more credit type IDs (each ID starts with bcrd_)
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` — Filter companies by multiple company IDs (starts with comp_)
    
</dd>
</dl>

<dl>
<dd>

**monetizedSubscriptions:** `Optional<Boolean>` — Filter companies that have monetized subscriptions
    
</dd>
</dl>

<dl>
<dd>

**planId:** `Optional<String>` — Filter companies by plan ID (starts with plan_)
    
</dd>
</dl>

<dl>
<dd>

**planIds:** `Optional<String>` — Filter companies by one or more plan IDs (each ID starts with plan_)
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Search for companies by name, keys or string traits
    
</dd>
</dl>

<dl>
<dd>

**sortOrderColumn:** `Optional<String>` — Column to sort by (e.g. name, created_at, last_seen_at)
    
</dd>
</dl>

<dl>
<dd>

**sortOrderDirection:** `Optional<SortDirection>` — Direction to sort by (asc or desc)
    
</dd>
</dl>

<dl>
<dd>

**subscriptionStatuses:** `Optional<SubscriptionStatus>` — Filter companies by one or more subscription statuses
    
</dd>
</dl>

<dl>
<dd>

**subscriptionTypes:** `Optional<SubscriptionType>` — Filter companies by one or more subscription types
    
</dd>
</dl>

<dl>
<dd>

**withoutFeatureOverrideFor:** `Optional<String>` — Filter out companies that already have a company override for the specified feature ID
    
</dd>
</dl>

<dl>
<dd>

**withoutPlan:** `Optional<Boolean>` — Filter out companies that have a plan
    
</dd>
</dl>

<dl>
<dd>

**withoutSubscription:** `Optional<Boolean>` — Filter out companies that have a subscription
    
</dd>
</dl>

<dl>
<dd>

**withSubscription:** `Optional<Boolean>` — Filter companies that have a subscription
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.upsertCompany(request) -> UpsertCompanyResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().upsertCompany(
    UpsertCompanyRequestBody
        .builder()
        .keys(
            new HashMap<String, String>() {{
                put("key", "value");
            }}
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `UpsertCompanyRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.getCompany(companyId) -> GetCompanyResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().getCompany("company_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `String` — company_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.deleteCompany(companyId) -> DeleteCompanyResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().deleteCompany(
    "company_id",
    DeleteCompanyRequest
        .builder()
        .cancelSubscription(true)
        .prorate(true)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `String` — company_id
    
</dd>
</dl>

<dl>
<dd>

**cancelSubscription:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**prorate:** `Optional<Boolean>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.countCompanies() -> CountCompaniesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().countCompanies(
    CountCompaniesRequest
        .builder()
        .monetizedSubscriptions(true)
        .planId("plan_id")
        .q("q")
        .sortOrderColumn("sort_order_column")
        .sortOrderDirection(SortDirection.ASC)
        .withoutFeatureOverrideFor("without_feature_override_for")
        .withoutPlan(true)
        .withoutSubscription(true)
        .withSubscription(true)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**creditTypeIds:** `Optional<String>` — Filter companies by one or more credit type IDs (each ID starts with bcrd_)
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` — Filter companies by multiple company IDs (starts with comp_)
    
</dd>
</dl>

<dl>
<dd>

**monetizedSubscriptions:** `Optional<Boolean>` — Filter companies that have monetized subscriptions
    
</dd>
</dl>

<dl>
<dd>

**planId:** `Optional<String>` — Filter companies by plan ID (starts with plan_)
    
</dd>
</dl>

<dl>
<dd>

**planIds:** `Optional<String>` — Filter companies by one or more plan IDs (each ID starts with plan_)
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Search for companies by name, keys or string traits
    
</dd>
</dl>

<dl>
<dd>

**sortOrderColumn:** `Optional<String>` — Column to sort by (e.g. name, created_at, last_seen_at)
    
</dd>
</dl>

<dl>
<dd>

**sortOrderDirection:** `Optional<SortDirection>` — Direction to sort by (asc or desc)
    
</dd>
</dl>

<dl>
<dd>

**subscriptionStatuses:** `Optional<SubscriptionStatus>` — Filter companies by one or more subscription statuses
    
</dd>
</dl>

<dl>
<dd>

**subscriptionTypes:** `Optional<SubscriptionType>` — Filter companies by one or more subscription types
    
</dd>
</dl>

<dl>
<dd>

**withoutFeatureOverrideFor:** `Optional<String>` — Filter out companies that already have a company override for the specified feature ID
    
</dd>
</dl>

<dl>
<dd>

**withoutPlan:** `Optional<Boolean>` — Filter out companies that have a plan
    
</dd>
</dl>

<dl>
<dd>

**withoutSubscription:** `Optional<Boolean>` — Filter out companies that have a subscription
    
</dd>
</dl>

<dl>
<dd>

**withSubscription:** `Optional<Boolean>` — Filter companies that have a subscription
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.countCompaniesForAdvancedFilter() -> CountCompaniesForAdvancedFilterResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().countCompaniesForAdvancedFilter(
    CountCompaniesForAdvancedFilterRequest
        .builder()
        .monetizedSubscriptions(true)
        .q("q")
        .withoutPlan(true)
        .withoutSubscription(true)
        .sortOrderColumn("sort_order_column")
        .sortOrderDirection(SortDirection.ASC)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` — Filter companies by multiple company IDs (starts with comp_)
    
</dd>
</dl>

<dl>
<dd>

**planIds:** `Optional<String>` — Filter companies by one or more plan IDs (each ID starts with plan_)
    
</dd>
</dl>

<dl>
<dd>

**featureIds:** `Optional<String>` — Filter companies by one or more feature IDs (each ID starts with feat_)
    
</dd>
</dl>

<dl>
<dd>

**creditTypeIds:** `Optional<String>` — Filter companies by one or more credit type IDs (each ID starts with bcrd_)
    
</dd>
</dl>

<dl>
<dd>

**subscriptionStatuses:** `Optional<SubscriptionStatus>` — Filter companies by one or more subscription statuses (active, canceled, expired, incomplete, incomplete_expired, past_due, paused, trialing, unpaid)
    
</dd>
</dl>

<dl>
<dd>

**subscriptionTypes:** `Optional<SubscriptionType>` — Filter companies by one or more subscription types (paid, free, trial)
    
</dd>
</dl>

<dl>
<dd>

**monetizedSubscriptions:** `Optional<Boolean>` — Filter companies that have monetized subscriptions
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Search for companies by name, keys or string traits
    
</dd>
</dl>

<dl>
<dd>

**withoutPlan:** `Optional<Boolean>` — Filter out companies that have a plan
    
</dd>
</dl>

<dl>
<dd>

**withoutSubscription:** `Optional<Boolean>` — Filter out companies that have a subscription
    
</dd>
</dl>

<dl>
<dd>

**sortOrderColumn:** `Optional<String>` — Column to sort by (e.g. name, created_at, last_seen_at)
    
</dd>
</dl>

<dl>
<dd>

**sortOrderDirection:** `Optional<SortDirection>` — Direction to sort by (asc or desc)
    
</dd>
</dl>

<dl>
<dd>

**displayProperties:** `Optional<String>` — Select the display columns to return (e.g. plan, subscription, users, last_seen_at)
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.createCompany(request) -> CreateCompanyResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().createCompany(
    UpsertCompanyRequestBody
        .builder()
        .keys(
            new HashMap<String, String>() {{
                put("key", "value");
            }}
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `UpsertCompanyRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.deleteCompanyByKeys(request) -> DeleteCompanyByKeysResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().deleteCompanyByKeys(
    KeysRequestBody
        .builder()
        .keys(
            new HashMap<String, String>() {{
                put("key", "value");
            }}
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `KeysRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.listCompaniesForAdvancedFilter() -> ListCompaniesForAdvancedFilterResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().listCompaniesForAdvancedFilter(
    ListCompaniesForAdvancedFilterRequest
        .builder()
        .monetizedSubscriptions(true)
        .q("q")
        .withoutPlan(true)
        .withoutSubscription(true)
        .sortOrderColumn("sort_order_column")
        .sortOrderDirection(SortDirection.ASC)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` — Filter companies by multiple company IDs (starts with comp_)
    
</dd>
</dl>

<dl>
<dd>

**planIds:** `Optional<String>` — Filter companies by one or more plan IDs (each ID starts with plan_)
    
</dd>
</dl>

<dl>
<dd>

**featureIds:** `Optional<String>` — Filter companies by one or more feature IDs (each ID starts with feat_)
    
</dd>
</dl>

<dl>
<dd>

**creditTypeIds:** `Optional<String>` — Filter companies by one or more credit type IDs (each ID starts with bcrd_)
    
</dd>
</dl>

<dl>
<dd>

**subscriptionStatuses:** `Optional<SubscriptionStatus>` — Filter companies by one or more subscription statuses (active, canceled, expired, incomplete, incomplete_expired, past_due, paused, trialing, unpaid)
    
</dd>
</dl>

<dl>
<dd>

**subscriptionTypes:** `Optional<SubscriptionType>` — Filter companies by one or more subscription types (paid, free, trial)
    
</dd>
</dl>

<dl>
<dd>

**monetizedSubscriptions:** `Optional<Boolean>` — Filter companies that have monetized subscriptions
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Search for companies by name, keys or string traits
    
</dd>
</dl>

<dl>
<dd>

**withoutPlan:** `Optional<Boolean>` — Filter out companies that have a plan
    
</dd>
</dl>

<dl>
<dd>

**withoutSubscription:** `Optional<Boolean>` — Filter out companies that have a subscription
    
</dd>
</dl>

<dl>
<dd>

**sortOrderColumn:** `Optional<String>` — Column to sort by (e.g. name, created_at, last_seen_at)
    
</dd>
</dl>

<dl>
<dd>

**sortOrderDirection:** `Optional<SortDirection>` — Direction to sort by (asc or desc)
    
</dd>
</dl>

<dl>
<dd>

**displayProperties:** `Optional<String>` — Select the display columns to return (e.g. plan, subscription, users, last_seen_at)
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.lookupCompany() -> LookupCompanyResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().lookupCompany(
    LookupCompanyRequest
        .builder()
        .keys(
            new HashMap<String, String>() {{
                put("keys", "keys");
            }}
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**keys:** `Map<String, String>` — Key/value pairs
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.listCompanyMemberships() -> ListCompanyMembershipsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().listCompanyMemberships(
    ListCompanyMembershipsRequest
        .builder()
        .companyId("company_id")
        .userId("user_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**userId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.getOrCreateCompanyMembership(request) -> GetOrCreateCompanyMembershipResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().getOrCreateCompanyMembership(
    GetOrCreateCompanyMembershipRequestBody
        .builder()
        .companyId("company_id")
        .userId("user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**userId:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.deleteCompanyMembership(companyMembershipId) -> DeleteCompanyMembershipResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().deleteCompanyMembership("company_membership_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyMembershipId:** `String` — company_membership_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.getActiveCompanySubscription() -> GetActiveCompanySubscriptionResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().getActiveCompanySubscription(
    GetActiveCompanySubscriptionRequest
        .builder()
        .companyId("company_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**companyIds:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.upsertCompanyTrait(request) -> UpsertCompanyTraitResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().upsertCompanyTrait(
    UpsertTraitRequestBody
        .builder()
        .keys(
            new HashMap<String, String>() {{
                put("key", "value");
            }}
        )
        .trait("trait")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `UpsertTraitRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.listEntityKeyDefinitions() -> ListEntityKeyDefinitionsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().listEntityKeyDefinitions(
    ListEntityKeyDefinitionsRequest
        .builder()
        .entityType(EntityType.COMPANY)
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**entityType:** `Optional<EntityType>` 
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.countEntityKeyDefinitions() -> CountEntityKeyDefinitionsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().countEntityKeyDefinitions(
    CountEntityKeyDefinitionsRequest
        .builder()
        .entityType(EntityType.COMPANY)
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**entityType:** `Optional<EntityType>` 
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.listEntityTraitDefinitions() -> ListEntityTraitDefinitionsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().listEntityTraitDefinitions(
    ListEntityTraitDefinitionsRequest
        .builder()
        .entityType(EntityType.COMPANY)
        .q("q")
        .traitType(TraitType.BOOLEAN)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**entityType:** `Optional<EntityType>` 
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**traitType:** `Optional<TraitType>` 
    
</dd>
</dl>

<dl>
<dd>

**traitTypes:** `Optional<TraitType>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.getOrCreateEntityTraitDefinition(request) -> GetOrCreateEntityTraitDefinitionResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().getOrCreateEntityTraitDefinition(
    CreateEntityTraitDefinitionRequestBody
        .builder()
        .entityType(EntityType.COMPANY)
        .hierarchy(
            Arrays.asList("hierarchy")
        )
        .traitType(TraitType.BOOLEAN)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**displayName:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**entityType:** `EntityType` 
    
</dd>
</dl>

<dl>
<dd>

**hierarchy:** `List<String>` 
    
</dd>
</dl>

<dl>
<dd>

**traitType:** `TraitType` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.getEntityTraitDefinition(entityTraitDefinitionId) -> GetEntityTraitDefinitionResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().getEntityTraitDefinition("entity_trait_definition_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**entityTraitDefinitionId:** `String` — entity_trait_definition_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.updateEntityTraitDefinition(entityTraitDefinitionId, request) -> UpdateEntityTraitDefinitionResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().updateEntityTraitDefinition(
    "entity_trait_definition_id",
    UpdateEntityTraitDefinitionRequestBody
        .builder()
        .traitType(TraitType.BOOLEAN)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**entityTraitDefinitionId:** `String` — entity_trait_definition_id
    
</dd>
</dl>

<dl>
<dd>

**displayName:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**traitType:** `TraitType` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.countEntityTraitDefinitions() -> CountEntityTraitDefinitionsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().countEntityTraitDefinitions(
    CountEntityTraitDefinitionsRequest
        .builder()
        .entityType(EntityType.COMPANY)
        .q("q")
        .traitType(TraitType.BOOLEAN)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**entityType:** `Optional<EntityType>` 
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**traitType:** `Optional<TraitType>` 
    
</dd>
</dl>

<dl>
<dd>

**traitTypes:** `Optional<TraitType>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.getEntityTraitValues() -> GetEntityTraitValuesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().getEntityTraitValues(
    GetEntityTraitValuesRequest
        .builder()
        .definitionId("definition_id")
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**definitionId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.listPlanChanges() -> ListPlanChangesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().listPlanChanges(
    ListPlanChangesRequest
        .builder()
        .action("action")
        .basePlanAction("base_plan_action")
        .companyId("company_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**action:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**basePlanAction:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**companyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**companyIds:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**planIds:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.getPlanChange(planChangeId) -> GetPlanChangeResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().getPlanChange("plan_change_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planChangeId:** `String` — plan_change_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.listPlanTraits() -> ListPlanTraitsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().listPlanTraits(
    ListPlanTraitsRequest
        .builder()
        .planId("plan_id")
        .traitId("trait_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**planId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**traitId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**traitIds:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.createPlanTrait(request) -> CreatePlanTraitResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().createPlanTrait(
    CreatePlanTraitRequestBody
        .builder()
        .planId("plan_id")
        .traitId("trait_id")
        .traitValue("trait_value")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**traitId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**traitValue:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.getPlanTrait(planTraitId) -> GetPlanTraitResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().getPlanTrait("plan_trait_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planTraitId:** `String` — plan_trait_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.updatePlanTrait(planTraitId, request) -> UpdatePlanTraitResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().updatePlanTrait(
    "plan_trait_id",
    UpdatePlanTraitRequestBody
        .builder()
        .planId("plan_id")
        .traitValue("trait_value")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planTraitId:** `String` — plan_trait_id
    
</dd>
</dl>

<dl>
<dd>

**planId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**traitValue:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.deletePlanTrait(planTraitId) -> DeletePlanTraitResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().deletePlanTrait("plan_trait_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planTraitId:** `String` — plan_trait_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.updatePlanTraitsBulk(request) -> UpdatePlanTraitsBulkResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().updatePlanTraitsBulk(
    UpdatePlanTraitBulkRequestBody
        .builder()
        .applyToExistingCompanies(true)
        .planId("plan_id")
        .traits(
            Arrays.asList(
                UpdatePlanTraitTraitRequestBody
                    .builder()
                    .traitId("trait_id")
                    .traitValue("trait_value")
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**applyToExistingCompanies:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**planId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**traits:** `List<UpdatePlanTraitTraitRequestBody>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.countPlanTraits() -> CountPlanTraitsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().countPlanTraits(
    CountPlanTraitsRequest
        .builder()
        .planId("plan_id")
        .traitId("trait_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**planId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**traitId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**traitIds:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.upsertUserTrait(request) -> UpsertUserTraitResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().upsertUserTrait(
    UpsertTraitRequestBody
        .builder()
        .keys(
            new HashMap<String, String>() {{
                put("key", "value");
            }}
        )
        .trait("trait")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `UpsertTraitRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.listUsers() -> ListUsersResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().listUsers(
    ListUsersRequest
        .builder()
        .companyId("company_id")
        .planId("plan_id")
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` — Filter users by company ID (starts with comp_)
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` — Filter users by multiple user IDs (starts with user_)
    
</dd>
</dl>

<dl>
<dd>

**planId:** `Optional<String>` — Filter users by plan ID (starts with plan_)
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Search for users by name, keys or string traits
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.upsertUser(request) -> UpsertUserResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().upsertUser(
    UpsertUserRequestBody
        .builder()
        .keys(
            new HashMap<String, String>() {{
                put("key", "value");
            }}
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `UpsertUserRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.getUser(userId) -> GetUserResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().getUser("user_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**userId:** `String` — user_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.deleteUser(userId) -> DeleteUserResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().deleteUser("user_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**userId:** `String` — user_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.countUsers() -> CountUsersResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().countUsers(
    CountUsersRequest
        .builder()
        .companyId("company_id")
        .planId("plan_id")
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` — Filter users by company ID (starts with comp_)
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` — Filter users by multiple user IDs (starts with user_)
    
</dd>
</dl>

<dl>
<dd>

**planId:** `Optional<String>` — Filter users by plan ID (starts with plan_)
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Search for users by name, keys or string traits
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.createUser(request) -> CreateUserResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().createUser(
    UpsertUserRequestBody
        .builder()
        .keys(
            new HashMap<String, String>() {{
                put("key", "value");
            }}
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `UpsertUserRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.deleteUserByKeys(request) -> DeleteUserByKeysResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().deleteUserByKeys(
    KeysRequestBody
        .builder()
        .keys(
            new HashMap<String, String>() {{
                put("key", "value");
            }}
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `KeysRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.companies.lookupUser() -> LookupUserResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.companies().lookupUser(
    LookupUserRequest
        .builder()
        .keys(
            new HashMap<String, String>() {{
                put("keys", "keys");
            }}
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**keys:** `Map<String, String>` — Key/value pairs
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## entitlements
<details><summary><code>client.entitlements.listCompanyOverrides() -> ListCompanyOverridesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().listCompanyOverrides(
    ListCompanyOverridesRequest
        .builder()
        .companyId("company_id")
        .featureId("feature_id")
        .withoutExpired(true)
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` — Filter company overrides by a single company ID (starting with comp_)
    
</dd>
</dl>

<dl>
<dd>

**companyIds:** `Optional<String>` — Filter company overrides by multiple company IDs (starting with comp_)
    
</dd>
</dl>

<dl>
<dd>

**featureId:** `Optional<String>` — Filter company overrides by a single feature ID (starting with feat_)
    
</dd>
</dl>

<dl>
<dd>

**featureIds:** `Optional<String>` — Filter company overrides by multiple feature IDs (starting with feat_)
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` — Filter company overrides by multiple company override IDs (starting with cmov_)
    
</dd>
</dl>

<dl>
<dd>

**withoutExpired:** `Optional<Boolean>` — Filter company overrides by whether they have not expired
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Search for company overrides by feature or company name
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.createCompanyOverride(request) -> CreateCompanyOverrideResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().createCompanyOverride(
    CreateCompanyOverrideRequestBody
        .builder()
        .companyId("company_id")
        .featureId("feature_id")
        .valueType(EntitlementValueType.BOOLEAN)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**creditConsumptionRate:** `Optional<Double>` 
    
</dd>
</dl>

<dl>
<dd>

**expirationDate:** `Optional<OffsetDateTime>` 
    
</dd>
</dl>

<dl>
<dd>

**featureId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**metricPeriod:** `Optional<CreateCompanyOverrideRequestBodyMetricPeriod>` 
    
</dd>
</dl>

<dl>
<dd>

**metricPeriodMonthReset:** `Optional<CreateCompanyOverrideRequestBodyMetricPeriodMonthReset>` 
    
</dd>
</dl>

<dl>
<dd>

**note:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**valueBool:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**valueCreditId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**valueNumeric:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**valueTraitId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**valueType:** `EntitlementValueType` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.getCompanyOverride(companyOverrideId) -> GetCompanyOverrideResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().getCompanyOverride("company_override_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyOverrideId:** `String` — company_override_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.updateCompanyOverride(companyOverrideId, request) -> UpdateCompanyOverrideResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().updateCompanyOverride(
    "company_override_id",
    UpdateCompanyOverrideRequestBody
        .builder()
        .valueType(EntitlementValueType.BOOLEAN)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyOverrideId:** `String` — company_override_id
    
</dd>
</dl>

<dl>
<dd>

**creditConsumptionRate:** `Optional<Double>` 
    
</dd>
</dl>

<dl>
<dd>

**expirationDate:** `Optional<OffsetDateTime>` 
    
</dd>
</dl>

<dl>
<dd>

**metricPeriod:** `Optional<UpdateCompanyOverrideRequestBodyMetricPeriod>` 
    
</dd>
</dl>

<dl>
<dd>

**metricPeriodMonthReset:** `Optional<UpdateCompanyOverrideRequestBodyMetricPeriodMonthReset>` 
    
</dd>
</dl>

<dl>
<dd>

**note:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**valueBool:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**valueCreditId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**valueNumeric:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**valueTraitId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**valueType:** `EntitlementValueType` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.deleteCompanyOverride(companyOverrideId) -> DeleteCompanyOverrideResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().deleteCompanyOverride("company_override_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyOverrideId:** `String` — company_override_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.countCompanyOverrides() -> CountCompanyOverridesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().countCompanyOverrides(
    CountCompanyOverridesRequest
        .builder()
        .companyId("company_id")
        .featureId("feature_id")
        .withoutExpired(true)
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` — Filter company overrides by a single company ID (starting with comp_)
    
</dd>
</dl>

<dl>
<dd>

**companyIds:** `Optional<String>` — Filter company overrides by multiple company IDs (starting with comp_)
    
</dd>
</dl>

<dl>
<dd>

**featureId:** `Optional<String>` — Filter company overrides by a single feature ID (starting with feat_)
    
</dd>
</dl>

<dl>
<dd>

**featureIds:** `Optional<String>` — Filter company overrides by multiple feature IDs (starting with feat_)
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` — Filter company overrides by multiple company override IDs (starting with cmov_)
    
</dd>
</dl>

<dl>
<dd>

**withoutExpired:** `Optional<Boolean>` — Filter company overrides by whether they have not expired
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Search for company overrides by feature or company name
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.listFeatureCompanies() -> ListFeatureCompaniesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().listFeatureCompanies(
    ListFeatureCompaniesRequest
        .builder()
        .featureId("feature_id")
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**featureId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.countFeatureCompanies() -> CountFeatureCompaniesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().countFeatureCompanies(
    CountFeatureCompaniesRequest
        .builder()
        .featureId("feature_id")
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**featureId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.listFeatureUsage() -> ListFeatureUsageResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().listFeatureUsage(
    ListFeatureUsageRequest
        .builder()
        .companyId("company_id")
        .includeUsageAggregation(true)
        .q("q")
        .withoutNegativeEntitlements(true)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**companyKeys:** `Optional<Map<String, String>>` 
    
</dd>
</dl>

<dl>
<dd>

**featureIds:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**includeUsageAggregation:** `Optional<Boolean>` — Include time-bucketed usage aggregation (today, this week, this month, billing period) for credit-based entitlements. Defaults to false for performance.
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**withoutNegativeEntitlements:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.getFeatureUsageTimeSeries() -> GetFeatureUsageTimeSeriesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().getFeatureUsageTimeSeries(
    GetFeatureUsageTimeSeriesRequest
        .builder()
        .companyId("company_id")
        .endTime(OffsetDateTime.parse("2024-01-15T09:30:00Z"))
        .featureId("feature_id")
        .startTime(OffsetDateTime.parse("2024-01-15T09:30:00Z"))
        .granularity(TimeSeriesGranularity.DAILY)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**endTime:** `OffsetDateTime` 
    
</dd>
</dl>

<dl>
<dd>

**featureId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**granularity:** `Optional<TimeSeriesGranularity>` 
    
</dd>
</dl>

<dl>
<dd>

**startTime:** `OffsetDateTime` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.countFeatureUsage() -> CountFeatureUsageResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().countFeatureUsage(
    CountFeatureUsageRequest
        .builder()
        .companyId("company_id")
        .includeUsageAggregation(true)
        .q("q")
        .withoutNegativeEntitlements(true)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**companyKeys:** `Optional<Map<String, String>>` 
    
</dd>
</dl>

<dl>
<dd>

**featureIds:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**includeUsageAggregation:** `Optional<Boolean>` — Include time-bucketed usage aggregation (today, this week, this month, billing period) for credit-based entitlements. Defaults to false for performance.
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**withoutNegativeEntitlements:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.listFeatureUsers() -> ListFeatureUsersResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().listFeatureUsers(
    ListFeatureUsersRequest
        .builder()
        .featureId("feature_id")
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**featureId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.countFeatureUsers() -> CountFeatureUsersResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().countFeatureUsers(
    CountFeatureUsersRequest
        .builder()
        .featureId("feature_id")
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**featureId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.listPlanEntitlements() -> ListPlanEntitlementsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().listPlanEntitlements(
    ListPlanEntitlementsRequest
        .builder()
        .featureId("feature_id")
        .planId("plan_id")
        .planVersionId("plan_version_id")
        .q("q")
        .withMeteredProducts(true)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**featureId:** `Optional<String>` — Filter plan entitlements by a single feature ID (starting with feat_)
    
</dd>
</dl>

<dl>
<dd>

**featureIds:** `Optional<String>` — Filter plan entitlements by multiple feature IDs (starting with feat_)
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` — Filter plan entitlements by multiple plan entitlement IDs (starting with pltl_)
    
</dd>
</dl>

<dl>
<dd>

**planId:** `Optional<String>` — Filter plan entitlements by a single plan ID (starting with plan_)
    
</dd>
</dl>

<dl>
<dd>

**planIds:** `Optional<String>` — Filter plan entitlements by multiple plan IDs (starting with plan_)
    
</dd>
</dl>

<dl>
<dd>

**planVersionId:** `Optional<String>` — Filter plan entitlements by a single plan version ID (starting with plvr_)
    
</dd>
</dl>

<dl>
<dd>

**planVersionIds:** `Optional<String>` — Filter plan entitlements by multiple plan version IDs (starting with plvr_)
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Search for plan entitlements by feature or company name
    
</dd>
</dl>

<dl>
<dd>

**withMeteredProducts:** `Optional<Boolean>` — Filter plan entitlements only with metered products
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.createPlanEntitlement(request) -> CreatePlanEntitlementResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().createPlanEntitlement(
    CreatePlanEntitlementRequestBody
        .builder()
        .featureId("feature_id")
        .planId("plan_id")
        .valueType(EntitlementValueType.BOOLEAN)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**billingProductId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**billingThreshold:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**creditConsumptionRate:** `Optional<Double>` 
    
</dd>
</dl>

<dl>
<dd>

**currency:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**featureId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**metricPeriod:** `Optional<CreatePlanEntitlementRequestBodyMetricPeriod>` 
    
</dd>
</dl>

<dl>
<dd>

**metricPeriodMonthReset:** `Optional<CreatePlanEntitlementRequestBodyMetricPeriodMonthReset>` 
    
</dd>
</dl>

<dl>
<dd>

**monthlyMeteredPriceId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**monthlyPriceTiers:** `Optional<List<CreatePriceTierRequestBody>>` 
    
</dd>
</dl>

<dl>
<dd>

**monthlyUnitPrice:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**monthlyUnitPriceDecimal:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**overageBillingProductId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**planId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**priceBehavior:** `Optional<EntitlementPriceBehavior>` 
    
</dd>
</dl>

<dl>
<dd>

**priceTiers:** `Optional<List<CreatePriceTierRequestBody>>` — Use MonthlyPriceTiers or YearlyPriceTiers instead
    
</dd>
</dl>

<dl>
<dd>

**softLimit:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**tierMode:** `Optional<BillingTiersMode>` 
    
</dd>
</dl>

<dl>
<dd>

**valueBool:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**valueCreditId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**valueNumeric:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**valueTraitId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**valueType:** `EntitlementValueType` 
    
</dd>
</dl>

<dl>
<dd>

**yearlyMeteredPriceId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**yearlyPriceTiers:** `Optional<List<CreatePriceTierRequestBody>>` 
    
</dd>
</dl>

<dl>
<dd>

**yearlyUnitPrice:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**yearlyUnitPriceDecimal:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.getPlanEntitlement(planEntitlementId) -> GetPlanEntitlementResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().getPlanEntitlement("plan_entitlement_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planEntitlementId:** `String` — plan_entitlement_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.updatePlanEntitlement(planEntitlementId, request) -> UpdatePlanEntitlementResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().updatePlanEntitlement(
    "plan_entitlement_id",
    UpdatePlanEntitlementRequestBody
        .builder()
        .valueType(EntitlementValueType.BOOLEAN)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planEntitlementId:** `String` — plan_entitlement_id
    
</dd>
</dl>

<dl>
<dd>

**billingProductId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**billingThreshold:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**creditConsumptionRate:** `Optional<Double>` 
    
</dd>
</dl>

<dl>
<dd>

**currency:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**metricPeriod:** `Optional<UpdatePlanEntitlementRequestBodyMetricPeriod>` 
    
</dd>
</dl>

<dl>
<dd>

**metricPeriodMonthReset:** `Optional<UpdatePlanEntitlementRequestBodyMetricPeriodMonthReset>` 
    
</dd>
</dl>

<dl>
<dd>

**monthlyMeteredPriceId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**monthlyPriceTiers:** `Optional<List<CreatePriceTierRequestBody>>` 
    
</dd>
</dl>

<dl>
<dd>

**monthlyUnitPrice:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**monthlyUnitPriceDecimal:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**overageBillingProductId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**priceBehavior:** `Optional<EntitlementPriceBehavior>` 
    
</dd>
</dl>

<dl>
<dd>

**priceTiers:** `Optional<List<CreatePriceTierRequestBody>>` — Use MonthlyPriceTiers or YearlyPriceTiers instead
    
</dd>
</dl>

<dl>
<dd>

**softLimit:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**tierMode:** `Optional<BillingTiersMode>` 
    
</dd>
</dl>

<dl>
<dd>

**valueBool:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**valueCreditId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**valueNumeric:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**valueTraitId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**valueType:** `EntitlementValueType` 
    
</dd>
</dl>

<dl>
<dd>

**yearlyMeteredPriceId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**yearlyPriceTiers:** `Optional<List<CreatePriceTierRequestBody>>` 
    
</dd>
</dl>

<dl>
<dd>

**yearlyUnitPrice:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**yearlyUnitPriceDecimal:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.deletePlanEntitlement(planEntitlementId) -> DeletePlanEntitlementResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().deletePlanEntitlement("plan_entitlement_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planEntitlementId:** `String` — plan_entitlement_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.countPlanEntitlements() -> CountPlanEntitlementsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().countPlanEntitlements(
    CountPlanEntitlementsRequest
        .builder()
        .featureId("feature_id")
        .planId("plan_id")
        .planVersionId("plan_version_id")
        .q("q")
        .withMeteredProducts(true)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**featureId:** `Optional<String>` — Filter plan entitlements by a single feature ID (starting with feat_)
    
</dd>
</dl>

<dl>
<dd>

**featureIds:** `Optional<String>` — Filter plan entitlements by multiple feature IDs (starting with feat_)
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` — Filter plan entitlements by multiple plan entitlement IDs (starting with pltl_)
    
</dd>
</dl>

<dl>
<dd>

**planId:** `Optional<String>` — Filter plan entitlements by a single plan ID (starting with plan_)
    
</dd>
</dl>

<dl>
<dd>

**planIds:** `Optional<String>` — Filter plan entitlements by multiple plan IDs (starting with plan_)
    
</dd>
</dl>

<dl>
<dd>

**planVersionId:** `Optional<String>` — Filter plan entitlements by a single plan version ID (starting with plvr_)
    
</dd>
</dl>

<dl>
<dd>

**planVersionIds:** `Optional<String>` — Filter plan entitlements by multiple plan version IDs (starting with plvr_)
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Search for plan entitlements by feature or company name
    
</dd>
</dl>

<dl>
<dd>

**withMeteredProducts:** `Optional<Boolean>` — Filter plan entitlements only with metered products
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.duplicatePlanEntitlements(request) -> DuplicatePlanEntitlementsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().duplicatePlanEntitlements(
    DuplicatePlanEntitlementsRequestBody
        .builder()
        .sourcePlanId("source_plan_id")
        .targetPlanId("target_plan_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**sourcePlanId:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**targetPlanId:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.entitlements.getFeatureUsageByCompany() -> GetFeatureUsageByCompanyResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.entitlements().getFeatureUsageByCompany(
    GetFeatureUsageByCompanyRequest
        .builder()
        .keys(
            new HashMap<String, String>() {{
                put("keys", "keys");
            }}
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**keys:** `Map<String, String>` — Key/value pairs
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## plans
<details><summary><code>client.plans.updateCompanyPlans(companyPlanId, request) -> UpdateCompanyPlansResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.plans().updateCompanyPlans(
    "company_plan_id",
    UpdateCompanyPlansRequestBody
        .builder()
        .addOnIds(
            Arrays.asList("add_on_ids")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyPlanId:** `String` — company_plan_id
    
</dd>
</dl>

<dl>
<dd>

**addOnIds:** `List<String>` 
    
</dd>
</dl>

<dl>
<dd>

**basePlanId:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.plans.listPlans() -> ListPlansResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.plans().listPlans(
    ListPlansRequest
        .builder()
        .companyId("company_id")
        .forFallbackPlan(true)
        .forInitialPlan(true)
        .forTrialExpiryPlan(true)
        .hasProductId(true)
        .planType(PlanType.PLAN)
        .q("q")
        .withoutEntitlementFor("without_entitlement_for")
        .withoutPaidProductId(true)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**forFallbackPlan:** `Optional<Boolean>` — Filter for plans valid as fallback plans (not linked to billing)
    
</dd>
</dl>

<dl>
<dd>

**forInitialPlan:** `Optional<Boolean>` — Filter for plans valid as initial plans (not linked to billing, free, or auto-cancelling trial)
    
</dd>
</dl>

<dl>
<dd>

**forTrialExpiryPlan:** `Optional<Boolean>` — Filter for plans valid as trial expiry plans (not linked to billing or free)
    
</dd>
</dl>

<dl>
<dd>

**hasProductId:** `Optional<Boolean>` — Filter out plans that do not have a billing product ID
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**planType:** `Optional<PlanType>` — Filter by plan type
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**withoutEntitlementFor:** `Optional<String>` — Filter out plans that already have a plan entitlement for the specified feature ID
    
</dd>
</dl>

<dl>
<dd>

**withoutPaidProductId:** `Optional<Boolean>` — Filter out plans that have a paid billing product ID
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.plans.createPlan(request) -> CreatePlanResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.plans().createPlan(
    CreatePlanRequestBody
        .builder()
        .description("description")
        .name("name")
        .planType(PlanType.PLAN)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `CreatePlanRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.plans.getPlan(planId) -> GetPlanResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.plans().getPlan("plan_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planId:** `String` — plan_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.plans.updatePlan(planId, request) -> UpdatePlanResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.plans().updatePlan(
    "plan_id",
    UpdatePlanRequestBody
        .builder()
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planId:** `String` — plan_id
    
</dd>
</dl>

<dl>
<dd>

**request:** `UpdatePlanRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.plans.deletePlan(planId) -> DeletePlanResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.plans().deletePlan("plan_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planId:** `String` — plan_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.plans.upsertBillingProductPlan(planId, request) -> UpsertBillingProductPlanResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.plans().upsertBillingProductPlan(
    "plan_id",
    UpsertBillingProductRequestBody
        .builder()
        .chargeType(ChargeType.FREE)
        .isTrialable(true)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planId:** `String` — plan_id
    
</dd>
</dl>

<dl>
<dd>

**request:** `UpsertBillingProductRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.plans.countPlans() -> CountPlansResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.plans().countPlans(
    CountPlansRequest
        .builder()
        .companyId("company_id")
        .forFallbackPlan(true)
        .forInitialPlan(true)
        .forTrialExpiryPlan(true)
        .hasProductId(true)
        .planType(PlanType.PLAN)
        .q("q")
        .withoutEntitlementFor("without_entitlement_for")
        .withoutPaidProductId(true)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**forFallbackPlan:** `Optional<Boolean>` — Filter for plans valid as fallback plans (not linked to billing)
    
</dd>
</dl>

<dl>
<dd>

**forInitialPlan:** `Optional<Boolean>` — Filter for plans valid as initial plans (not linked to billing, free, or auto-cancelling trial)
    
</dd>
</dl>

<dl>
<dd>

**forTrialExpiryPlan:** `Optional<Boolean>` — Filter for plans valid as trial expiry plans (not linked to billing or free)
    
</dd>
</dl>

<dl>
<dd>

**hasProductId:** `Optional<Boolean>` — Filter out plans that do not have a billing product ID
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**planType:** `Optional<PlanType>` — Filter by plan type
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**withoutEntitlementFor:** `Optional<String>` — Filter out plans that already have a plan entitlement for the specified feature ID
    
</dd>
</dl>

<dl>
<dd>

**withoutPaidProductId:** `Optional<Boolean>` — Filter out plans that have a paid billing product ID
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.plans.listPlanIssues() -> ListPlanIssuesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.plans().listPlanIssues(
    ListPlanIssuesRequest
        .builder()
        .planId("plan_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planId:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## components
<details><summary><code>client.components.listComponents() -> ListComponentsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().listComponents(
    ListComponentsRequest
        .builder()
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.components.createComponent(request) -> CreateComponentResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().createComponent(
    CreateComponentRequestBody
        .builder()
        .entityType(ComponentEntityType.BILLING)
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ast:** `Optional<Map<String, Double>>` 
    
</dd>
</dl>

<dl>
<dd>

**entityType:** `ComponentEntityType` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.components.getComponent(componentId) -> GetComponentResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().getComponent("component_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**componentId:** `String` — component_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.components.updateComponent(componentId, request) -> UpdateComponentResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().updateComponent(
    "component_id",
    UpdateComponentRequestBody
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**componentId:** `String` — component_id
    
</dd>
</dl>

<dl>
<dd>

**ast:** `Optional<Map<String, Double>>` 
    
</dd>
</dl>

<dl>
<dd>

**entityType:** `Optional<ComponentEntityType>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**state:** `Optional<ComponentState>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.components.deleteComponent(componentId) -> DeleteComponentResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().deleteComponent("component_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**componentId:** `String` — component_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.components.countComponents() -> CountComponentsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().countComponents(
    CountComponentsRequest
        .builder()
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.components.previewComponentData() -> PreviewComponentDataResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.components().previewComponentData(
    PreviewComponentDataRequest
        .builder()
        .companyId("company_id")
        .componentId("component_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**componentId:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## dataexports
<details><summary><code>client.dataexports.createDataExport(request) -> CreateDataExportResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.dataexports().createDataExport(
    CreateDataExportRequestBody
        .builder()
        .exportType("company-feature-usage")
        .metadata("metadata")
        .outputFileType("csv")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**exportType:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**metadata:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**outputFileType:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.dataexports.getDataExportArtifact(dataExportId) -> InputStream</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.dataexports().getDataExportArtifact("data_export_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**dataExportId:** `String` — data_export_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## events
<details><summary><code>client.events.createEventBatch(request) -> CreateEventBatchResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.events().createEventBatch(
    CreateEventBatchRequestBody
        .builder()
        .events(
            Arrays.asList(
                CreateEventRequestBody
                    .builder()
                    .eventType(EventType.FLAG_CHECK)
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**events:** `List<CreateEventRequestBody>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.events.getEventSummaries() -> GetEventSummariesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.events().getEventSummaries(
    GetEventSummariesRequest
        .builder()
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**eventSubtypes:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.events.listEvents() -> ListEventsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.events().listEvents(
    ListEventsRequest
        .builder()
        .companyId("company_id")
        .eventSubtype("event_subtype")
        .flagId("flag_id")
        .userId("user_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**companyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**eventSubtype:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**eventTypes:** `Optional<EventType>` 
    
</dd>
</dl>

<dl>
<dd>

**flagId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**userId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.events.createEvent(request) -> CreateEventResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.events().createEvent(
    CreateEventRequestBody
        .builder()
        .eventType(EventType.FLAG_CHECK)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `CreateEventRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.events.getEvent(eventId) -> GetEventResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.events().getEvent("event_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**eventId:** `String` — event_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.events.getSegmentIntegrationStatus() -> GetSegmentIntegrationStatusResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.events().getSegmentIntegrationStatus();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## features
<details><summary><code>client.features.listFeatures() -> ListFeaturesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().listFeatures(
    ListFeaturesRequest
        .builder()
        .q("q")
        .withoutCompanyOverrideFor("without_company_override_for")
        .withoutPlanEntitlementFor("without_plan_entitlement_for")
        .booleanRequireEvent(true)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Search by feature name or ID
    
</dd>
</dl>

<dl>
<dd>

**withoutCompanyOverrideFor:** `Optional<String>` — Filter out features that already have a company override for the specified company ID
    
</dd>
</dl>

<dl>
<dd>

**withoutPlanEntitlementFor:** `Optional<String>` — Filter out features that already have a plan entitlement for the specified plan ID
    
</dd>
</dl>

<dl>
<dd>

**featureType:** `Optional<FeatureType>` — Filter by one or more feature types (boolean, event, trait)
    
</dd>
</dl>

<dl>
<dd>

**booleanRequireEvent:** `Optional<Boolean>` — Only return boolean features if there is an associated event. Automatically includes boolean in the feature types filter.
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.createFeature(request) -> CreateFeatureResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().createFeature(
    CreateFeatureRequestBody
        .builder()
        .description("description")
        .featureType(FeatureType.BOOLEAN)
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**description:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**eventSubtype:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**featureType:** `FeatureType` 
    
</dd>
</dl>

<dl>
<dd>

**flag:** `Optional<CreateOrUpdateFlagRequestBody>` 
    
</dd>
</dl>

<dl>
<dd>

**icon:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**lifecyclePhase:** `Optional<FeatureLifecyclePhase>` 
    
</dd>
</dl>

<dl>
<dd>

**maintainerId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**pluralName:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**singularName:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**traitId:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.getFeature(featureId) -> GetFeatureResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().getFeature("feature_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**featureId:** `String` — feature_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.updateFeature(featureId, request) -> UpdateFeatureResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().updateFeature(
    "feature_id",
    UpdateFeatureRequestBody
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**featureId:** `String` — feature_id
    
</dd>
</dl>

<dl>
<dd>

**description:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**eventSubtype:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**featureType:** `Optional<FeatureType>` 
    
</dd>
</dl>

<dl>
<dd>

**flag:** `Optional<CreateOrUpdateFlagRequestBody>` 
    
</dd>
</dl>

<dl>
<dd>

**icon:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**lifecyclePhase:** `Optional<FeatureLifecyclePhase>` 
    
</dd>
</dl>

<dl>
<dd>

**maintainerId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**pluralName:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**singularName:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**traitId:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.deleteFeature(featureId) -> DeleteFeatureResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().deleteFeature("feature_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**featureId:** `String` — feature_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.countFeatures() -> CountFeaturesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().countFeatures(
    CountFeaturesRequest
        .builder()
        .q("q")
        .withoutCompanyOverrideFor("without_company_override_for")
        .withoutPlanEntitlementFor("without_plan_entitlement_for")
        .booleanRequireEvent(true)
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Search by feature name or ID
    
</dd>
</dl>

<dl>
<dd>

**withoutCompanyOverrideFor:** `Optional<String>` — Filter out features that already have a company override for the specified company ID
    
</dd>
</dl>

<dl>
<dd>

**withoutPlanEntitlementFor:** `Optional<String>` — Filter out features that already have a plan entitlement for the specified plan ID
    
</dd>
</dl>

<dl>
<dd>

**featureType:** `Optional<FeatureType>` — Filter by one or more feature types (boolean, event, trait)
    
</dd>
</dl>

<dl>
<dd>

**booleanRequireEvent:** `Optional<Boolean>` — Only return boolean features if there is an associated event. Automatically includes boolean in the feature types filter.
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.listFlags() -> ListFlagsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().listFlags(
    ListFlagsRequest
        .builder()
        .featureId("feature_id")
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**featureId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Search by flag name, key, or ID
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.createFlag(request) -> CreateFlagResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().createFlag(
    CreateFlagRequestBody
        .builder()
        .defaultValue(true)
        .description("description")
        .flagType("boolean")
        .key("key")
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `CreateFlagRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.getFlag(flagId) -> GetFlagResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().getFlag("flag_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**flagId:** `String` — flag_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.updateFlag(flagId, request) -> UpdateFlagResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().updateFlag(
    "flag_id",
    CreateFlagRequestBody
        .builder()
        .defaultValue(true)
        .description("description")
        .flagType("boolean")
        .key("key")
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**flagId:** `String` — flag_id
    
</dd>
</dl>

<dl>
<dd>

**request:** `CreateFlagRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.deleteFlag(flagId) -> DeleteFlagResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().deleteFlag("flag_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**flagId:** `String` — flag_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.updateFlagRules(flagId, request) -> UpdateFlagRulesResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().updateFlagRules(
    "flag_id",
    UpdateFlagRulesRequestBody
        .builder()
        .rules(
            Arrays.asList(
                CreateOrUpdateRuleRequestBody
                    .builder()
                    .conditionGroups(
                        Arrays.asList(
                            CreateOrUpdateConditionGroupRequestBody
                                .builder()
                                .conditions(
                                    Arrays.asList(
                                        CreateOrUpdateConditionRequestBody
                                            .builder()
                                            .conditionType(CreateOrUpdateConditionRequestBodyConditionType.COMPANY)
                                            .operator(CreateOrUpdateConditionRequestBodyOperator.EQ)
                                            .resourceIds(
                                                Arrays.asList("resource_ids")
                                            )
                                            .build()
                                    )
                                )
                                .build()
                        )
                    )
                    .conditions(
                        Arrays.asList(
                            CreateOrUpdateConditionRequestBody
                                .builder()
                                .conditionType(CreateOrUpdateConditionRequestBodyConditionType.COMPANY)
                                .operator(CreateOrUpdateConditionRequestBodyOperator.EQ)
                                .resourceIds(
                                    Arrays.asList("resource_ids")
                                )
                                .build()
                        )
                    )
                    .name("name")
                    .priority(1)
                    .value(true)
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**flagId:** `String` — flag_id
    
</dd>
</dl>

<dl>
<dd>

**rules:** `List<CreateOrUpdateRuleRequestBody>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.checkFlag(key, request) -> CheckFlagResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().checkFlag(
    "key",
    CheckFlagRequestBody
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**key:** `String` — key
    
</dd>
</dl>

<dl>
<dd>

**request:** `CheckFlagRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.checkFlags(request) -> CheckFlagsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().checkFlags(
    CheckFlagRequestBody
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `CheckFlagRequestBody` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.checkFlagsBulk(request) -> CheckFlagsBulkResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().checkFlagsBulk(
    CheckFlagsBulkRequestBody
        .builder()
        .contexts(
            Arrays.asList(
                CheckFlagRequestBody
                    .builder()
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**contexts:** `List<CheckFlagRequestBody>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.features.countFlags() -> CountFlagsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.features().countFlags(
    CountFlagsRequest
        .builder()
        .featureId("feature_id")
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**featureId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Search by flag name, key, or ID
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## planbundle
<details><summary><code>client.planbundle.createPlanBundle(request) -> CreatePlanBundleResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.planbundle().createPlanBundle(
    CreatePlanBundleRequestBody
        .builder()
        .entitlements(
            Arrays.asList(
                PlanBundleEntitlementRequestBody
                    .builder()
                    .action(PlanBundleAction.CREATE)
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**billingProduct:** `Optional<UpsertBillingProductRequestBody>` 
    
</dd>
</dl>

<dl>
<dd>

**creditGrants:** `Optional<List<PlanBundleCreditGrantRequestBody>>` 
    
</dd>
</dl>

<dl>
<dd>

**entitlements:** `List<PlanBundleEntitlementRequestBody>` 
    
</dd>
</dl>

<dl>
<dd>

**plan:** `Optional<CreatePlanRequestBody>` 
    
</dd>
</dl>

<dl>
<dd>

**traits:** `Optional<List<UpdatePlanTraitTraitRequestBody>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.planbundle.updatePlanBundle(planBundleId, request) -> UpdatePlanBundleResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.planbundle().updatePlanBundle(
    "plan_bundle_id",
    UpdatePlanBundleRequestBody
        .builder()
        .entitlements(
            Arrays.asList(
                PlanBundleEntitlementRequestBody
                    .builder()
                    .action(PlanBundleAction.CREATE)
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planBundleId:** `String` — plan_bundle_id
    
</dd>
</dl>

<dl>
<dd>

**billingProduct:** `Optional<UpsertBillingProductRequestBody>` 
    
</dd>
</dl>

<dl>
<dd>

**creditGrants:** `Optional<List<PlanBundleCreditGrantRequestBody>>` 
    
</dd>
</dl>

<dl>
<dd>

**entitlements:** `List<PlanBundleEntitlementRequestBody>` 
    
</dd>
</dl>

<dl>
<dd>

**plan:** `Optional<UpdatePlanRequestBody>` 
    
</dd>
</dl>

<dl>
<dd>

**traits:** `Optional<List<UpdatePlanTraitTraitRequestBody>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## plangroups
<details><summary><code>client.plangroups.getPlanGroup() -> GetPlanGroupResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.plangroups().getPlanGroup();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.plangroups.createPlanGroup(request) -> CreatePlanGroupResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.plangroups().createPlanGroup(
    CreatePlanGroupRequestBody
        .builder()
        .addOnIds(
            Arrays.asList("add_on_ids")
        )
        .checkoutCollectAddress(true)
        .checkoutCollectEmail(true)
        .checkoutCollectPhone(true)
        .enableTaxCollection(true)
        .orderedAddOns(
            Arrays.asList(
                OrderedPlansInGroup
                    .builder()
                    .planId("plan_id")
                    .build()
            )
        )
        .orderedBundleList(
            Arrays.asList(
                PlanGroupBundleOrder
                    .builder()
                    .bundleId("bundleId")
                    .build()
            )
        )
        .orderedPlans(
            Arrays.asList(
                OrderedPlansInGroup
                    .builder()
                    .planId("plan_id")
                    .build()
            )
        )
        .preventDowngradesWhenOverLimit(true)
        .preventSelfServiceDowngrade(true)
        .prorationBehavior(ProrationBehavior.CREATE_PRORATIONS)
        .showAsMonthlyPrices(true)
        .showCredits(true)
        .showFeatureDescription(true)
        .showPeriodToggle(true)
        .showZeroPriceAsFree(true)
        .syncCustomerBillingDetails(true)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**addOnCompatibilities:** `Optional<List<CompatiblePlans>>` 
    
</dd>
</dl>

<dl>
<dd>

**addOnIds:** `List<String>` — Use OrderedAddOns instead
    
</dd>
</dl>

<dl>
<dd>

**checkoutCollectAddress:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**checkoutCollectEmail:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**checkoutCollectPhone:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**customPlanConfig:** `Optional<CustomPlanConfig>` 
    
</dd>
</dl>

<dl>
<dd>

**customPlanId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**enableTaxCollection:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**fallbackPlanId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**initialPlanId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**initialPlanPriceId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**orderedAddOns:** `List<OrderedPlansInGroup>` 
    
</dd>
</dl>

<dl>
<dd>

**orderedBundleList:** `List<PlanGroupBundleOrder>` 
    
</dd>
</dl>

<dl>
<dd>

**orderedPlans:** `List<OrderedPlansInGroup>` 
    
</dd>
</dl>

<dl>
<dd>

**preventDowngradesWhenOverLimit:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**preventSelfServiceDowngrade:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**preventSelfServiceDowngradeButtonText:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**preventSelfServiceDowngradeUrl:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**prorationBehavior:** `ProrationBehavior` 
    
</dd>
</dl>

<dl>
<dd>

**scheduledDowngradeBehavior:** `Optional<ScheduledDowngradeConfigBehavior>` 
    
</dd>
</dl>

<dl>
<dd>

**scheduledDowngradePreventWhenOverLimit:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**showAsMonthlyPrices:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**showCredits:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**showFeatureDescription:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**showPeriodToggle:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**showZeroPriceAsFree:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**syncCustomerBillingDetails:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**trialDays:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**trialExpiryPlanId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**trialExpiryPlanPriceId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**trialPaymentMethodRequired:** `Optional<Boolean>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.plangroups.updatePlanGroup(planGroupId, request) -> UpdatePlanGroupResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.plangroups().updatePlanGroup(
    "plan_group_id",
    UpdatePlanGroupRequestBody
        .builder()
        .addOnIds(
            Arrays.asList("add_on_ids")
        )
        .checkoutCollectAddress(true)
        .checkoutCollectEmail(true)
        .checkoutCollectPhone(true)
        .enableTaxCollection(true)
        .orderedAddOns(
            Arrays.asList(
                OrderedPlansInGroup
                    .builder()
                    .planId("plan_id")
                    .build()
            )
        )
        .orderedBundleList(
            Arrays.asList(
                PlanGroupBundleOrder
                    .builder()
                    .bundleId("bundleId")
                    .build()
            )
        )
        .orderedPlans(
            Arrays.asList(
                OrderedPlansInGroup
                    .builder()
                    .planId("plan_id")
                    .build()
            )
        )
        .preventDowngradesWhenOverLimit(true)
        .preventSelfServiceDowngrade(true)
        .prorationBehavior(ProrationBehavior.CREATE_PRORATIONS)
        .showAsMonthlyPrices(true)
        .showCredits(true)
        .showFeatureDescription(true)
        .showPeriodToggle(true)
        .showZeroPriceAsFree(true)
        .syncCustomerBillingDetails(true)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**planGroupId:** `String` — plan_group_id
    
</dd>
</dl>

<dl>
<dd>

**addOnCompatibilities:** `Optional<List<CompatiblePlans>>` 
    
</dd>
</dl>

<dl>
<dd>

**addOnIds:** `List<String>` — Use OrderedAddOns instead
    
</dd>
</dl>

<dl>
<dd>

**checkoutCollectAddress:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**checkoutCollectEmail:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**checkoutCollectPhone:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**customPlanConfig:** `Optional<CustomPlanConfig>` 
    
</dd>
</dl>

<dl>
<dd>

**customPlanId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**enableTaxCollection:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**fallbackPlanId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**initialPlanId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**initialPlanPriceId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**orderedAddOns:** `List<OrderedPlansInGroup>` 
    
</dd>
</dl>

<dl>
<dd>

**orderedBundleList:** `List<PlanGroupBundleOrder>` 
    
</dd>
</dl>

<dl>
<dd>

**orderedPlans:** `List<OrderedPlansInGroup>` 
    
</dd>
</dl>

<dl>
<dd>

**preventDowngradesWhenOverLimit:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**preventSelfServiceDowngrade:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**preventSelfServiceDowngradeButtonText:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**preventSelfServiceDowngradeUrl:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**prorationBehavior:** `ProrationBehavior` 
    
</dd>
</dl>

<dl>
<dd>

**scheduledDowngradeBehavior:** `Optional<ScheduledDowngradeConfigBehavior>` 
    
</dd>
</dl>

<dl>
<dd>

**scheduledDowngradePreventWhenOverLimit:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**showAsMonthlyPrices:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**showCredits:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**showFeatureDescription:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**showPeriodToggle:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**showZeroPriceAsFree:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**syncCustomerBillingDetails:** `Boolean` 
    
</dd>
</dl>

<dl>
<dd>

**trialDays:** `Optional<Integer>` 
    
</dd>
</dl>

<dl>
<dd>

**trialExpiryPlanId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**trialExpiryPlanPriceId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**trialPaymentMethodRequired:** `Optional<Boolean>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## accesstokens
<details><summary><code>client.accesstokens.issueTemporaryAccessToken(request) -> IssueTemporaryAccessTokenResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.accesstokens().issueTemporaryAccessToken(
    IssueTemporaryAccessTokenRequestBody
        .builder()
        .lookup(
            new HashMap<String, String>() {{
                put("key", "value");
            }}
        )
        .resourceType("company")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**lookup:** `Map<String, String>` 
    
</dd>
</dl>

<dl>
<dd>

**resourceType:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## webhooks
<details><summary><code>client.webhooks.listWebhookEvents() -> ListWebhookEventsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.webhooks().listWebhookEvents(
    ListWebhookEventsRequest
        .builder()
        .q("q")
        .webhookId("webhook_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**webhookId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.webhooks.getWebhookEvent(webhookEventId) -> GetWebhookEventResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.webhooks().getWebhookEvent("webhook_event_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**webhookEventId:** `String` — webhook_event_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.webhooks.countWebhookEvents() -> CountWebhookEventsResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.webhooks().countWebhookEvents(
    CountWebhookEventsRequest
        .builder()
        .q("q")
        .webhookId("webhook_id")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ids:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**webhookId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.webhooks.listWebhooks() -> ListWebhooksResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.webhooks().listWebhooks(
    ListWebhooksRequest
        .builder()
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.webhooks.createWebhook(request) -> CreateWebhookResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.webhooks().createWebhook(
    CreateWebhookRequestBody
        .builder()
        .name("name")
        .requestTypes(
            Arrays.asList(WebhookRequestType.SUBSCRIPTION_TRIAL_ENDED)
        )
        .url("url")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**creditTriggerConfigs:** `Optional<List<CreditTriggerConfig>>` 
    
</dd>
</dl>

<dl>
<dd>

**entitlementTriggerConfigs:** `Optional<List<EntitlementTriggerConfig>>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**requestTypes:** `List<WebhookRequestType>` 
    
</dd>
</dl>

<dl>
<dd>

**url:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.webhooks.getWebhook(webhookId) -> GetWebhookResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.webhooks().getWebhook("webhook_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**webhookId:** `String` — webhook_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.webhooks.updateWebhook(webhookId, request) -> UpdateWebhookResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.webhooks().updateWebhook(
    "webhook_id",
    UpdateWebhookRequestBody
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**webhookId:** `String` — webhook_id
    
</dd>
</dl>

<dl>
<dd>

**creditTriggerConfigs:** `Optional<List<CreditTriggerConfig>>` 
    
</dd>
</dl>

<dl>
<dd>

**entitlementTriggerConfigs:** `Optional<List<EntitlementTriggerConfig>>` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**requestTypes:** `Optional<List<WebhookRequestType>>` 
    
</dd>
</dl>

<dl>
<dd>

**status:** `Optional<WebhookStatus>` 
    
</dd>
</dl>

<dl>
<dd>

**url:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.webhooks.deleteWebhook(webhookId) -> DeleteWebhookResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.webhooks().deleteWebhook("webhook_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**webhookId:** `String` — webhook_id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.webhooks.countWebhooks() -> CountWebhooksResponse</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.webhooks().countWebhooks(
    CountWebhooksRequest
        .builder()
        .q("q")
        .limit(1)
        .offset(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**q:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Page limit (default 100)
    
</dd>
</dl>

<dl>
<dd>

**offset:** `Optional<Integer>` — Page offset (default 0)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>
