package com.smartgridready.communicator.async.process;

import com.smartgridready.communicator.async.callable.AsyncResult;
import com.smartgridready.communicator.async.callable.DeviceWriteCallable;
import com.smartgridready.communicator.async.callable.WriteFunction;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;

/**
 * Implements an asynchronous read method.
 * @param <V> The type of value to write.
 */
public class WriteExec<V> extends Processor implements Executable {

    private static final Logger LOG = LoggerFactory.getLogger(WriteExec.class);

    private final DeviceWriteCallable<V> writeCallable;

    private final Scheduler scheduler;

    private Object finishedNotificationReceiver;

    private Disposable disposable;

    /**
     * Constructs a new instance.
     * @param functionalProfileName the functional profile name
     * @param dataPointName the data point name
     * @param writeFunction a reference to the actual method to be called
     */
    public WriteExec(String functionalProfileName, String dataPointName, WriteFunction<V> writeFunction) {
        this(functionalProfileName, dataPointName, writeFunction, Schedulers.io());
    }

    /**
     * Constructs a new instance with a custom scheduler.
     * @param functionalProfileName the functional profile name
     * @param dataPointName the data point name
     * @param writeFunction a reference to the actual method to be called
     * @param scheduler a task scheduler
     */
    public WriteExec(String functionalProfileName, String dataPointName, WriteFunction<V> writeFunction, Scheduler scheduler) {
        this.scheduler = scheduler;
        this.writeCallable = new DeviceWriteCallable<>(writeFunction, functionalProfileName, dataPointName);
    }

    @Override
    public void process(ProcessingType processingType) {
        try {
            Observable<AsyncResult<V>> observable = Observable.fromCallable(writeCallable);
            if (ProcessingType.PARALLEL == processingType) {
                LOG.info("WriteExec PARALLEL: {} - {}", writeCallable.getProfileName(), writeCallable.getProfileName());
                disposable = observable.subscribeOn(scheduler).subscribe(this::handleSuccess, this::handleError);
            } else {
                LOG.info("WriteExec SEQUENTIAL: {} - {}", writeCallable.getProfileName(), writeCallable.getDataPointName());
                disposable = observable.subscribe(this::handleSuccess, this::handleError);
            }
        } catch (Throwable e) {
            handleError(e);
        }
    }

    /**
     * Sets the value to write.
     * @param value a value
     */
    public void setWriteValue(V value) {
        this.writeCallable.setWriteValue(value);
    }
    
    private void handleSuccess(AsyncResult<V> result) {
        switch (result.getExecStatus()) {
            case SUCCESS:
                LOG.info("WriteExec RESULT {} - {} SUCCESS, value={}", result.getProfileName(), result.getDataPointName(), result.getValue());
                break;
            case ERROR:
                LOG.error("WriteExec RESULT {} - {} ERROR, error={}", result.getProfileName(), result.getDataPointName(), getExecThrowable().getMessage());
                break;
            case PROCESSING:
                LOG.warn("WriteExec RESULT {} - {} PROCESSING. Handle success called while still processing. This is unexpected behavior.", result.getProfileName(), result.getDataPointName());
                break;
            default:
                LOG.error("Unhandled execution status.");
        }
        notifyFinished();
    }
    
    private void handleError(Throwable t) {
        AsyncResult<V> result = writeCallable.getResult();
        result.setResponseTime(Instant.now());
        result.setExecStatus(ExecStatus.ERROR);
        result.setThrowable(t);
        LOG.error("WriteExec RESULT - {} - {} - {}", result.getProfileName(), result.getDataPointName(), result.getThrowable());
        notifyFinished();
    }

    @Override
    public AsyncResult<V> getResult() {
        return writeCallable.getResult();
    }

    /**
     * Gets the execution status.
     * @return an instance of {@link ExecStatus}
     */
	public ExecStatus getExecStatus() {
		return getResult().getExecStatus();
	}

    /**
     * Gets the exception that was thrown.
     * @return an instance of {@link Throwable}
     */
	public Throwable getExecThrowable() {
		return getResult().getThrowable();
	}

    /**
     * Gets the time stamp of sending the request.
     * @return an instance of {@link Instant}
     */
    public Instant getRequestTime() {
        return getResult().getRequestTime();
    }

    /**
     * Gets the time stamp of receiving the response.
     * @return an instance of {@link Instant}
     */
    public Instant getResponseTime() {
        return getResult().getResponseTime();
    }

    /**
     * Cleans up disposable resources.
     */
    public void cleanup() {
        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public String toString() {
        return getResult().toString();
    }

    @Override
    public void setFinishedNotificationReceiver(Object notificationReceiver) {
        if (this.finishedNotificationReceiver == null) {
            this.finishedNotificationReceiver = notificationReceiver;
        } else {
            throw new IllegalStateException("Attempt to set finishedNotification receiver twice.");
        }
    }

    private void notifyFinished() {
        if (finishedNotificationReceiver != null) {
            synchronized (finishedNotificationReceiver) {
                finishedNotificationReceiver.notifyAll();
            }
        }
    }
}
