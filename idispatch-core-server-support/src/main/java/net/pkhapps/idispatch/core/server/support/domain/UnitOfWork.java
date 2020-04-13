package net.pkhapps.idispatch.core.server.support.domain;

/**
 * TODO Document me
 *
 * @param <ResultT>
 */
@FunctionalInterface
public interface UnitOfWork<ResultT> {

    ResultT execute();
}
