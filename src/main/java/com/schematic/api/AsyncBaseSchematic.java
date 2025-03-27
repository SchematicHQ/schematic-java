/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api;

import com.schematic.api.core.ClientOptions;
import com.schematic.api.core.Suppliers;
import com.schematic.api.resources.accesstokens.AsyncAccesstokensClient;
import com.schematic.api.resources.accounts.AsyncAccountsClient;
import com.schematic.api.resources.billing.AsyncBillingClient;
import com.schematic.api.resources.checkout.AsyncCheckoutClient;
import com.schematic.api.resources.companies.AsyncCompaniesClient;
import com.schematic.api.resources.components.AsyncComponentsClient;
import com.schematic.api.resources.crm.AsyncCrmClient;
import com.schematic.api.resources.entitlements.AsyncEntitlementsClient;
import com.schematic.api.resources.events.AsyncEventsClient;
import com.schematic.api.resources.features.AsyncFeaturesClient;
import com.schematic.api.resources.plangroups.AsyncPlangroupsClient;
import com.schematic.api.resources.plans.AsyncPlansClient;
import com.schematic.api.resources.webhooks.AsyncWebhooksClient;
import java.util.function.Supplier;

public class AsyncBaseSchematic {
    protected final ClientOptions clientOptions;

    protected final Supplier<AsyncAccountsClient> accountsClient;

    protected final Supplier<AsyncFeaturesClient> featuresClient;

    protected final Supplier<AsyncBillingClient> billingClient;

    protected final Supplier<AsyncCheckoutClient> checkoutClient;

    protected final Supplier<AsyncCompaniesClient> companiesClient;

    protected final Supplier<AsyncEntitlementsClient> entitlementsClient;

    protected final Supplier<AsyncPlansClient> plansClient;

    protected final Supplier<AsyncComponentsClient> componentsClient;

    protected final Supplier<AsyncCrmClient> crmClient;

    protected final Supplier<AsyncEventsClient> eventsClient;

    protected final Supplier<AsyncPlangroupsClient> plangroupsClient;

    protected final Supplier<AsyncAccesstokensClient> accesstokensClient;

    protected final Supplier<AsyncWebhooksClient> webhooksClient;

    public AsyncBaseSchematic(ClientOptions clientOptions) {
        this.clientOptions = clientOptions;
        this.accountsClient = Suppliers.memoize(() -> new AsyncAccountsClient(clientOptions));
        this.featuresClient = Suppliers.memoize(() -> new AsyncFeaturesClient(clientOptions));
        this.billingClient = Suppliers.memoize(() -> new AsyncBillingClient(clientOptions));
        this.checkoutClient = Suppliers.memoize(() -> new AsyncCheckoutClient(clientOptions));
        this.companiesClient = Suppliers.memoize(() -> new AsyncCompaniesClient(clientOptions));
        this.entitlementsClient = Suppliers.memoize(() -> new AsyncEntitlementsClient(clientOptions));
        this.plansClient = Suppliers.memoize(() -> new AsyncPlansClient(clientOptions));
        this.componentsClient = Suppliers.memoize(() -> new AsyncComponentsClient(clientOptions));
        this.crmClient = Suppliers.memoize(() -> new AsyncCrmClient(clientOptions));
        this.eventsClient = Suppliers.memoize(() -> new AsyncEventsClient(clientOptions));
        this.plangroupsClient = Suppliers.memoize(() -> new AsyncPlangroupsClient(clientOptions));
        this.accesstokensClient = Suppliers.memoize(() -> new AsyncAccesstokensClient(clientOptions));
        this.webhooksClient = Suppliers.memoize(() -> new AsyncWebhooksClient(clientOptions));
    }

    public AsyncAccountsClient accounts() {
        return this.accountsClient.get();
    }

    public AsyncFeaturesClient features() {
        return this.featuresClient.get();
    }

    public AsyncBillingClient billing() {
        return this.billingClient.get();
    }

    public AsyncCheckoutClient checkout() {
        return this.checkoutClient.get();
    }

    public AsyncCompaniesClient companies() {
        return this.companiesClient.get();
    }

    public AsyncEntitlementsClient entitlements() {
        return this.entitlementsClient.get();
    }

    public AsyncPlansClient plans() {
        return this.plansClient.get();
    }

    public AsyncComponentsClient components() {
        return this.componentsClient.get();
    }

    public AsyncCrmClient crm() {
        return this.crmClient.get();
    }

    public AsyncEventsClient events() {
        return this.eventsClient.get();
    }

    public AsyncPlangroupsClient plangroups() {
        return this.plangroupsClient.get();
    }

    public AsyncAccesstokensClient accesstokens() {
        return this.accesstokensClient.get();
    }

    public AsyncWebhooksClient webhooks() {
        return this.webhooksClient.get();
    }

    public static AsyncBaseSchematicBuilder builder() {
        return new AsyncBaseSchematicBuilder();
    }
}
