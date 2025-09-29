package com.smartgridready.communicator.messaging.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.OperationNotSupportedException;

import com.smartgridready.communicator.common.api.values.StringValue;
import com.smartgridready.communicator.common.api.values.Value;
import com.smartgridready.communicator.common.helper.JsonHelper;
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

        Value payloadValue = StringValue.of(payload);
        String regexMatch = ".";
        if (messageFilter.getPlaintextFilter() != null) {
            var filter = messageFilter.getPlaintextFilter();
            regexMatch = filter.getMatchesRegex();
        } else if (messageFilter.getJmespathFilter() != null) {
            try {
                var filter = messageFilter.getJmespathFilter();
                regexMatch = filter.getMatchesRegex();
                payloadValue = JsonHelper.parseJsonResponse(filter.getQuery(), payloadValue.getString());
            } catch (GenDriverException e) {
                return false; // no match
            }
        }

        // regex matching for all filter types
        Pattern matchPattern = Pattern.compile(regexMatch);
        Matcher matcher = matchPattern.matcher(payloadValue.getString());
        return matcher.find();
    }

    @Override
    public void validate() throws OperationNotSupportedException {
        if (messageFilter == null) {
            return; // null means no-filter, that's fine
        }
        if (messageFilter.getPlaintextFilter() != null) {
            return;
        }
        if (messageFilter.getRegexFilter() != null) {
            throw new OperationNotSupportedException("Regex message filter not supported yet.");
        }
        if (messageFilter.getXpapathFilter() != null) {
            throw new OperationNotSupportedException("Xpath message filter not supported yet.");
        }
        if (messageFilter.getJmespathFilter() != null) {
            return;
        }
        throw new OperationNotSupportedException("Invalid message filter.");
    }
}
