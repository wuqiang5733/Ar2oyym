package org.xuxiaoxiao.myyora.services;

import org.xuxiaoxiao.myyora.infrastructure.ServiceResponse;
import org.xuxiaoxiao.myyora.services.entities.Message;

import java.util.List;

public final class Messages {
    private Messages() {
    }

    public static class DeleteMessageRequest {
        public int MessageId;

        public DeleteMessageRequest(int messageId) {
            MessageId = messageId;
        }
    }

    public static class DeleteMessageResponse extends ServiceResponse {
        public int MessageId;
    }

    public static class SearchMessagesRequest {
        public int FromContactId;
        public boolean IncludeSentMessages;
        public boolean IncludeReceivedMessages;

        public SearchMessagesRequest(int fromContactId, boolean includeSentMessages, boolean includeReceivedMessages) {
            FromContactId = fromContactId;
            IncludeSentMessages = includeSentMessages;
            IncludeReceivedMessages = includeReceivedMessages;
        }

        public SearchMessagesRequest(boolean includeSentMessages, boolean includeReceivedMessages) {
            IncludeSentMessages = includeSentMessages;
            IncludeReceivedMessages = includeReceivedMessages;
        }
    }

    public static class SeacrhMessagesResponse extends ServiceResponse {
        public List<Message> Messages;
    }
}
