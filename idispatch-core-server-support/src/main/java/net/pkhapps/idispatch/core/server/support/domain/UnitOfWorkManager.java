package net.pkhapps.idispatch.core.server.support.domain;

import org.jetbrains.annotations.NotNull;

/**
 * TODO Document me
 */
public interface UnitOfWorkManager {

    <ResultT> ResultT execute(@NotNull UnitOfWork<ResultT> unitOfWork);

    boolean currentlyExecutingUnitOfWork();
}
