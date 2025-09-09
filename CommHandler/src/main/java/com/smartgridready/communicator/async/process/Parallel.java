package com.smartgridready.communicator.async.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a process which is executed in parallel.
 */
public class Parallel extends Processor {

    private static final Logger LOG = LoggerFactory.getLogger(Parallel.class);

    private final List<Processor> processorList;

    /**
     * Constructs a new instance.
     */
    public Parallel() {
        processorList = new ArrayList<>();
    }

    @Override
    public void process(ProcessingType processingType) {
        LOG.info("Processing PARALLEL");
        processorList.parallelStream().forEach(
                p -> p.process(ProcessingType.PARALLEL));
    }

    /**
     * Adds the process to an asynchronous task processor.
     * @param processor the processor
     * @return the same instance
     */
    public Parallel add(Processor processor) {
        processorList.add(processor);
        return this;
    }
}
