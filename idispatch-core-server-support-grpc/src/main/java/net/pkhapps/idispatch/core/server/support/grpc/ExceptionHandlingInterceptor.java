package net.pkhapps.idispatch.core.server.support.grpc;

import io.grpc.*;
import net.pkhapps.idispatch.core.server.support.security.AccessDeniedException;
import net.pkhapps.idispatch.core.server.support.security.AuthenticationRequiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO Document me
 */
public class ExceptionHandlingInterceptor implements ServerInterceptor {

    /**
     *
     */
    public static final ExceptionHandlingInterceptor INSTANCE = new ExceptionHandlingInterceptor();
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlingInterceptor.class);

    private ExceptionHandlingInterceptor() {
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
        var listener = next.startCall(call, headers);
        return new ExceptionHandlingServerCallListener<>(listener, call, headers);
    }

    private static class ExceptionHandlingServerCallListener<ReqT, RespT> extends ForwardingServerCallListener.SimpleForwardingServerCallListener<ReqT> {

        private final ServerCall<ReqT, RespT> call;
        private final Metadata headers;

        protected ExceptionHandlingServerCallListener(ServerCall.Listener<ReqT> delegate, ServerCall<ReqT, RespT> call,
                                                      Metadata headers) {
            super(delegate);
            this.call = call;
            this.headers = headers;
        }

        @Override
        public void onHalfClose() {
            try {
                super.onHalfClose();
            } catch (RuntimeException ex) {
                handleException(ex);
                throw ex;
            }
        }

        @Override
        public void onReady() {
            try {
                super.onReady();
            } catch (RuntimeException ex) {
                handleException(ex);
                throw ex;
            }
        }

        private void handleException(RuntimeException exception) {
            LOGGER.debug("Handling exception", exception);
            if (exception instanceof AccessDeniedException) {
                call.close(Status.PERMISSION_DENIED.withDescription(exception.getMessage()), headers);
            } else if (exception instanceof AuthenticationRequiredException) {
                call.close(Status.UNAUTHENTICATED.withDescription(exception.getMessage()), headers);
            } else if (exception instanceof IllegalArgumentException) {
                call.close(Status.INVALID_ARGUMENT.withDescription(exception.getMessage()), headers);
            } else {
                call.close(Status.INTERNAL.withDescription(exception.getMessage()), headers);
            }
        }
    }
}
