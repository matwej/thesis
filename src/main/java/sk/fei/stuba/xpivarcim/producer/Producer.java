package sk.fei.stuba.xpivarcim.producer;

import sk.fei.stuba.xpivarcim.exceptions.MessagingResponseException;

public interface Producer<T> {
    T download(long id) throws MessagingResponseException;
    T update(T object) throws MessagingResponseException;
    void send(T object);
    <K>T sendAndReceive(K object, Class<K> type);
}
