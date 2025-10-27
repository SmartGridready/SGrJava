package com.smartgridready.communicator.messaging.impl;

import javax.naming.OperationNotSupportedException;

import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.common.helper.JsonHelper;
import com.smartgridready.communicator.common.helper.RegexHelper;
import com.smartgridready.communicator.common.helper.XPathHelper;
import com.smartgridready.driver.api.common.GenDriverException;
import com.smartgridready.driver.api.messaging.MessageFilterHandler;
import com.smartgridready.ns.v0.MessageFilter;

/**
 * Implements a message filter handler.
 */
public class MessageFilterHandlerImpl implements MessageFilterHandler {
    
    private final MessageFilter messageFilter;

    /**
     * Constructs a new instance.
     * @param messageFilter the message filter specification
     */
    public MessageFilterHandlerImpl(MessageFilter messageFilter) {
        this.messageFilter = messageFilter;
    }

    @Override
    public boolean isFilterMatch(String payload) {
        if (messageFilter == null) {
            return true;    // always match
        }
        if (payload == null) {
            return false;    // no match
        }

        String regexMatch = ".";
        Value payloadValue = StringValue.of(payload);
        if (messageFilter.getPlaintextFilter() != null) {
            var filter = messageFilter.getPlaintextFilter();
            regexMatch = filter.getMatchesRegex();
        } else if (messageFilter.getRegexFilter() != null) {
            var filter = messageFilter.getRegexFilter();
            regexMatch = filter.getMatchesRegex();
            payloadValue = RegexHelper.query(filter.getQuery(), payload);
        } else if (messageFilter.getJmespathFilter() != null) {
            try {
                var filter = messageFilter.getJmespathFilter();
                regexMatch = filter.getMatchesRegex();
                payloadValue = JsonHelper.parseJsonResponse(filter.getQuery(), payload);
            } catch (GenDriverException e) {
                return false; // no match
            }
        } else if (messageFilter.getXpapathFilter() != null) {
            try {
                var filter = messageFilter.getXpapathFilter();
                regexMatch = filter.getMatchesRegex();
                payloadValue = XPathHelper.parseXmlResponse(filter.getQuery(), payload);
            } catch (GenDriverException e) {
                return false; // no match
            }
        } else if (messageFilter.getJsonataFilter() != null) {
            try {
                var filter = messageFilter.getJsonataFilter();
                regexMatch = filter.getMatchesRegex();
                payloadValue = JsonHelper.parseJsonResponseWithJsonata(filter.getQuery(), payload);
            } catch (GenDriverException e) {
                return false; // no match
            }
        }

        // regex matching for all filter types
        return RegexHelper.match(regexMatch, payloadValue.getString());
    }

    @Override
    public void validate() throws OperationNotSupportedException {
        if (messageFilter == null) {
            return; // null means no-filter, that's fine
        }
        if (
            (messageFilter.getPlaintextFilter() != null) ||
            (messageFilter.getRegexFilter() != null) ||
            (messageFilter.getXpapathFilter() != null) ||
            (messageFilter.getJmespathFilter() != null) ||
            (messageFilter.getJsonataFilter() != null)
        ) {
            return;
        }    
        throw new OperationNotSupportedException("Invalid message filter.");
    }
}
