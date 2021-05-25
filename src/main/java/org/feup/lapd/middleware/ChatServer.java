package org.feup.lapd.middleware;

import org.eclipse.lsp4j.jsonrpc.services.JsonNotification;
import org.eclipse.lsp4j.jsonrpc.services.JsonRequest;
import org.eclipse.lsp4j.jsonrpc.services.JsonSegment;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@JsonSegment("server")
public interface ChatServer {

    /**
     * The `server/fetchMessage` request is sent by the client to fetch messages posted so far.
     */
    @JsonRequest
    CompletableFuture<List<UserMessage>> fetchMessages();

    /**
     * The `server/postMessage` notification is sent by the client to post a new message.
     * The server should store a message and broadcast it to all clients.
     */
    @JsonNotification
    void postMessage(UserMessage message);

    class UserMessage {

        /**
         * A user posted this message.
         */
        private String user;

        /**
         * A content of this message.
         */
        private String content;

        public UserMessage(String user, String message) {
            super();
            this.user = user;
            this.content = message;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    }
}


