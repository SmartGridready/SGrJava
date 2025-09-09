package com.smartgridready.communicator.async.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Base class for task processors.
 */
public abstract class Processor {

    private static final Long WAIT_TIMEOUT_MS = 1000L;

    private static final Logger LOG  = LoggerFactory.getLogger(Processor.class); 

    private final List<Executable> awaitList;

    Processor() {
        awaitList = new ArrayList<>();
    }

    /**
     * Executes the processes.
     * @param processingType the type of execution
     */
    public abstract void process(ProcessingType processingType);

    /**
     * Executes the processes in parallel. 
     */
    public void process() {
        process(ProcessingType.PARALLEL);
        handleAwait();
    }

    /**
     * Prepares awaiting processes to be finished.
     * @param executables the processes to await
     * @return the same instance
     */
    public Processor await(Executable ... executables) {
        Arrays.stream(executables).forEach(exec -> exec.setFinishedNotificationReceiver(awaitList));
        awaitList.addAll(Arrays.asList(executables));
        return this;
    }

    private void handleAwait() {
        long noOfProcessing = Integer.MAX_VALUE;
        while (noOfProcessing > 0) {
            try {
                noOfProcessing = awaitList.stream().filter(
                        readExec -> readExec.getExecStatus().isNotProcessed()).count();

                if (noOfProcessing > 0) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Waiting for {} requests to be finished: {}", noOfProcessing, awaitList);
                    }
                    synchronized (awaitList) {
                        awaitList.wait(WAIT_TIMEOUT_MS);
                    }
                }
            } catch (InterruptedException e) {
                LOG.warn("Await thread interrupted.");
                Thread.currentThread().interrupt();
                noOfProcessing = 0;
            }
        }
        LOG.debug("All requests processed. Terminating await().");
    }
}
