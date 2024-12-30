/**
 * This file was auto-generated by Fern from our API Definition.
 */
package com.schematic.api;

import com.schematic.api.core.BaseSchematicApiException;
import com.schematic.api.core.BaseSchematicException;
import com.schematic.api.core.ClientOptions;
import com.schematic.api.core.ObjectMappers;
import com.schematic.api.core.RequestOptions;
import com.schematic.api.core.Suppliers;
import com.schematic.api.resources.accesstokens.AccesstokensClient;
import com.schematic.api.resources.accounts.AccountsClient;
import com.schematic.api.resources.billing.BillingClient;
import com.schematic.api.resources.checkout.CheckoutClient;
import com.schematic.api.resources.companies.CompaniesClient;
import com.schematic.api.resources.components.ComponentsClient;
import com.schematic.api.resources.crm.CrmClient;
import com.schematic.api.resources.entitlements.EntitlementsClient;
import com.schematic.api.resources.events.EventsClient;
import com.schematic.api.resources.features.FeaturesClient;
import com.schematic.api.resources.plangroups.PlangroupsClient;
import com.schematic.api.resources.plans.PlansClient;
import com.schematic.api.resources.webhooks.WebhooksClient;
import java.io.IOException;
import java.util.function.Supplier;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BaseSchematic {
    protected final ClientOptions clientOptions;

    protected final Supplier<AccountsClient> accountsClient;

    protected final Supplier<FeaturesClient> featuresClient;

    protected final Supplier<BillingClient> billingClient;

    protected final Supplier<CheckoutClient> checkoutClient;

    protected final Supplier<CompaniesClient> companiesClient;

    protected final Supplier<EntitlementsClient> entitlementsClient;

    protected final Supplier<ComponentsClient> componentsClient;

    protected final Supplier<CrmClient> crmClient;

    protected final Supplier<EventsClient> eventsClient;

    protected final Supplier<PlansClient> plansClient;

    protected final Supplier<PlangroupsClient> plangroupsClient;

    protected final Supplier<AccesstokensClient> accesstokensClient;

    protected final Supplier<WebhooksClient> webhooksClient;

    public BaseSchematic(ClientOptions clientOptions) {
        this.clientOptions = clientOptions;
        this.accountsClient = Suppliers.memoize(() -> new AccountsClient(clientOptions));
        this.featuresClient = Suppliers.memoize(() -> new FeaturesClient(clientOptions));
        this.billingClient = Suppliers.memoize(() -> new BillingClient(clientOptions));
        this.checkoutClient = Suppliers.memoize(() -> new CheckoutClient(clientOptions));
        this.companiesClient = Suppliers.memoize(() -> new CompaniesClient(clientOptions));
        this.entitlementsClient = Suppliers.memoize(() -> new EntitlementsClient(clientOptions));
        this.componentsClient = Suppliers.memoize(() -> new ComponentsClient(clientOptions));
        this.crmClient = Suppliers.memoize(() -> new CrmClient(clientOptions));
        this.eventsClient = Suppliers.memoize(() -> new EventsClient(clientOptions));
        this.plansClient = Suppliers.memoize(() -> new PlansClient(clientOptions));
        this.plangroupsClient = Suppliers.memoize(() -> new PlangroupsClient(clientOptions));
        this.accesstokensClient = Suppliers.memoize(() -> new AccesstokensClient(clientOptions));
        this.webhooksClient = Suppliers.memoize(() -> new WebhooksClient(clientOptions));
    }

    public void getCompanyPlans() {
        getCompanyPlans(null);
    }

    public void getCompanyPlans(RequestOptions requestOptions) {
        HttpUrl httpUrl = HttpUrl.parse(this.clientOptions.environment().getUrl())
                .newBuilder()
                .addPathSegments("company-plans")
                .build();
        Request okhttpRequest = new Request.Builder()
                .url(httpUrl)
                .method("GET", null)
                .headers(Headers.of(clientOptions.headers(requestOptions)))
                .build();
        OkHttpClient client = clientOptions.httpClient();
        if (requestOptions != null && requestOptions.getTimeout().isPresent()) {
            client = clientOptions.httpClientWithTimeout(requestOptions);
        }
        try (Response response = client.newCall(okhttpRequest).execute()) {
            ResponseBody responseBody = response.body();
            if (response.isSuccessful()) {
                return;
            }
            String responseBodyString = responseBody != null ? responseBody.string() : "{}";
            throw new BaseSchematicApiException(
                    "Error with status code " + response.code(),
                    response.code(),
                    ObjectMappers.JSON_MAPPER.readValue(responseBodyString, Object.class));
        } catch (IOException e) {
            throw new BaseSchematicException("Network error executing HTTP request", e);
        }
    }

    public AccountsClient accounts() {
        return this.accountsClient.get();
    }

    public FeaturesClient features() {
        return this.featuresClient.get();
    }

    public BillingClient billing() {
        return this.billingClient.get();
    }

    public CheckoutClient checkout() {
        return this.checkoutClient.get();
    }

    public CompaniesClient companies() {
        return this.companiesClient.get();
    }

    public EntitlementsClient entitlements() {
        return this.entitlementsClient.get();
    }

    public ComponentsClient components() {
        return this.componentsClient.get();
    }

    public CrmClient crm() {
        return this.crmClient.get();
    }

    public EventsClient events() {
        return this.eventsClient.get();
    }

    public PlansClient plans() {
        return this.plansClient.get();
    }

    public PlangroupsClient plangroups() {
        return this.plangroupsClient.get();
    }

    public AccesstokensClient accesstokens() {
        return this.accesstokensClient.get();
    }

    public WebhooksClient webhooks() {
        return this.webhooksClient.get();
    }

    public static BaseSchematicBuilder builder() {
        return new BaseSchematicBuilder();
    }
}
