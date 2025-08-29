package com.smartgridready.communicator.async.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements a process which is executed sequentially.
 */
public class Sequence extends Processor {

    private static final Logger LOG = LoggerFactory.getLogger(Sequence.class);

    private final List<Processor> processorList;

    /**
     * Constructs a new instance.
     */
    public Sequence() {
        processorList = new ArrayList<>();
    }

    @Override
    public void process(ProcessingType processingType) {
        LOG.info("Processing SEQUENCE");
        processorList.forEach(p -> p.process(ProcessingType.SEQUENCE));
    }

    /**
     * Adds the process to an asynchronous task processor.
     * @param processor the processor
     * @return the same instance
     */
    public Sequence add(Processor processor) {
        processorList.add(processor);
        return this;
    }
}
