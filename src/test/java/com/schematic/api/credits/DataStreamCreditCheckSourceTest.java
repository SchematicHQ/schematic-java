package com.schematic.api.credits;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.schematic.api.datastream.DataStreamClient;
import com.schematic.api.types.RulesengineCompany;
import com.schematic.api.types.RulesengineUser;
import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.Test;

/** When the lease path is allowed to wait on the socket for an entity, and when it is not. */
class DataStreamCreditCheckSourceTest {

    private static final Map<String, String> KEYS = Collections.singletonMap("id", "co_1");

    private static RulesengineCompany company() {
        return RulesengineCompany.builder()
                .accountId("acct")
                .environmentId("env")
                .id("co_1")
                .build();
    }

    private static RulesengineUser user() {
        return RulesengineUser.builder()
                .accountId("acct")
                .environmentId("env")
                .id("user_1")
                .build();
    }

    @Test
    void replicatorModeReadsTheCacheRatherThanWaitingOnASocketItDoesNotHave() {
        DataStreamClient dataStream = mock(DataStreamClient.class);
        when(dataStream.isReplicatorMode()).thenReturn(true);
        when(dataStream.getCachedCompany(KEYS)).thenReturn(company());
        when(dataStream.getCachedUser(KEYS)).thenReturn(user());
        DataStreamCreditCheckSource source = new DataStreamCreditCheckSource(dataStream);

        assertSame("co_1", source.getCompany(KEYS).getId());
        assertSame("user_1", source.getUser(KEYS).getId());

        // A live fetch here has nothing to send the request on, so it only waits out its own
        // timeout before answering nothing.
        verify(dataStream, never()).getCompany(any());
        verify(dataStream, never()).getUser(any());
    }

    @Test
    void aDisconnectedClientReadsTheCacheAndReturnsAMissImmediately() {
        DataStreamClient dataStream = mock(DataStreamClient.class);
        when(dataStream.isReplicatorMode()).thenReturn(false);
        when(dataStream.isConnected()).thenReturn(false);
        when(dataStream.getCachedCompany(KEYS)).thenReturn(null);
        when(dataStream.getCachedUser(KEYS)).thenReturn(null);
        DataStreamCreditCheckSource source = new DataStreamCreditCheckSource(dataStream);

        long startedAt = System.nanoTime();
        assertNull(source.getCompany(KEYS));
        assertNull(source.getUser(KEYS));
        long tookMillis = (System.nanoTime() - startedAt) / 1_000_000;

        // The plain check bails on this state without waiting; the lease path must not pay a
        // resource timeout per call before falling back to it.
        assertTrue(tookMillis < 500, "the lookups took " + tookMillis + "ms");
        verify(dataStream, never()).getCompany(any());
        verify(dataStream, never()).getUser(any());
    }

    @Test
    void aConnectedClientStillFetchesOverTheSocket() {
        DataStreamClient dataStream = mock(DataStreamClient.class);
        when(dataStream.isReplicatorMode()).thenReturn(false);
        when(dataStream.isConnected()).thenReturn(true);
        when(dataStream.getCompany(KEYS)).thenReturn(company());
        when(dataStream.getUser(KEYS)).thenReturn(user());
        DataStreamCreditCheckSource source = new DataStreamCreditCheckSource(dataStream);

        assertSame("co_1", source.getCompany(KEYS).getId());
        assertSame("user_1", source.getUser(KEYS).getId());

        // A cache miss a connected socket can still answer is worth the wait.
        verify(dataStream, never()).getCachedCompany(any());
        verify(dataStream, never()).getCachedUser(any());
    }
}
